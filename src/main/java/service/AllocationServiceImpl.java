/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service;

import com.jfoenix.controls.JFXComboBox;
import core.dto.api.IAllocationDTO;
import core.dto.api.IAllocationTableDTO;
import core.enums.EventType;
import core.enums.Stage;
import javafx.scene.control.TableView;
import model.entity.AllocationEntity;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.AllocationRepository;
import service.api.*;
import service.quartz.JobInitializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by byaxe on 18.12.16.
 */
@Service("allocationService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class AllocationServiceImpl implements IAllocationService {

    private static final Logger logger = LoggerFactory.getLogger(AllocationServiceImpl.class);

    private final AllocationRepository allocationRepository;
    private final ConversionService conversionService;
    private final IGroupsService groupsService;
    private final IOrganisationsService organisationsService;
    private final IStudentsService studentsService;
    private final IOrdersService ordersService;

    @Autowired
    private JobInitializer jobInitializer;

    @Autowired
    public AllocationServiceImpl(AllocationRepository allocationRepository, ConversionService conversionService,
                                 IGroupsService groupsService, IOrganisationsService organisationsService,
                                 IStudentsService studentsService, IOrdersService ordersService) {
        this.allocationRepository = allocationRepository;
        this.conversionService = conversionService;
        this.groupsService = groupsService;
        this.organisationsService = organisationsService;
        this.studentsService = studentsService;
        this.ordersService = ordersService;
    }

    /**
     * Заполняем комбобоксы значениями
     *
     * @param allocationTableStageComboBox то что нужно заполнить
     */
    @Override
    public void fillComboBox(JFXComboBox allocationTableStageComboBox) {
        allocationTableStageComboBox.getItems()
                .addAll(Stream.of(Stage.values()).map(Stage::getTitle).collect(toList()));
    }

    /**
     * Устанавливаем значения по умолчанию на управляющие элементы во вкладке Распределение (Таблица)
     *
     * @param allocationTableStageComboBox Заполнен ступенями образования {@link Stage}
     */
    @Override
    public void setDefaultValues(JFXComboBox allocationTableStageComboBox) {
        allocationTableStageComboBox.setValue(Stage.FIRST.getTitle());
    }

    /**
     * Экспортируем данные из таблицы распределения в эксель
     *
     * @param file            Файл в который будет сохранена таблица
     * @param allocationTable Таблица которая будет сохранена в файл
     */
    @Override
    public void exportTableToExcel(File file, TableView<IAllocationTableDTO> allocationTable) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet spreadsheet = workbook.createSheet("Учет распределения");
        HSSFRow row;

        row = spreadsheet.createRow(0);
        for (int j = 0; j < allocationTable.getColumns().size(); j++) {
            row.createCell(j).setCellValue(allocationTable.getColumns().get(j).getText());
        }

        for (int i = 1; i < allocationTable.getItems().size(); i++) {
            row = spreadsheet.createRow(i);
            for (int j = 0; j < allocationTable.getColumns().size(); j++) {
                row.createCell(j).setCellValue(String.valueOf(allocationTable.getColumns().get(j).getCellData(i - 1)));
            }
        }

        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();
    }

    @Override
    @Transactional
    public IAllocationDTO save(IAllocationDTO dto) {
        if (dto == null) throw new IllegalArgumentException();

        AllocationEntity entity = convertDtoToEntity(dto);

        AllocationEntity savedEntity = allocationRepository.save(entity);

        IAllocationDTO allocation = convertEntityToDto(savedEntity);

        jobInitializer.synchronizeJobWithAllocation(allocation, EventType.SAVE);

        return allocation;
    }

    @Override
    @Transactional
    public Iterable<IAllocationDTO> save(Iterable<IAllocationDTO> dtoList) {
        if (dtoList == null) throw new IllegalArgumentException();

        List<AllocationEntity> entities = convertListDtoToEntity(dtoList);

        Iterable<AllocationEntity> savedEntity = allocationRepository.save(entities);

        List<IAllocationDTO> allocations = convertListEntityToDto(savedEntity);

        allocations.forEach(a -> jobInitializer.synchronizeJobWithAllocation(a, EventType.SAVE));

        return allocations;
    }

    /**
     * Перемещение записи в архив
     *
     * @param id идентификатор перемещаемой записи
     */
    @Override
    public void moveToArchive(Long id) {
        try {
            IAllocationDTO row = findOne(id);
            row.setArchive(true);
            save(row);
        } catch (Exception e) {
            logger.error("Ошибка при попытке перемещения записи в архив", e);
            e.printStackTrace();
        }
    }

    @Override
    public List<IAllocationTableDTO> findByArchiveTrue() {
        List<AllocationEntity> entities = allocationRepository.findByArchiveTrue();

        return entities.stream()
                .map(e -> conversionService.convert(e, IAllocationTableDTO.class))
                .collect(toList());
    }

    @Override
    public List<IAllocationTableDTO> findByArchiveFalse() {
        List<AllocationEntity> entities = allocationRepository.findByArchiveFalse();

        return entities.stream()
                .map(e -> conversionService.convert(e, IAllocationTableDTO.class))
                .collect(toList());
    }

    @Override
    public List<IAllocationTableDTO> find(Stage stage, Boolean archive, Integer issueYear) {
        List<AllocationEntity> entities = allocationRepository.find(stage, archive, issueYear);

        return entities.stream()
                .map(e -> conversionService.convert(e, IAllocationTableDTO.class))
                .collect(toList());
    }

    @Override
    public Iterable<IAllocationDTO> findAll(Sort sort) {
        Iterable<AllocationEntity> entities = allocationRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    @Override
    public Page<IAllocationDTO> findAll(Pageable pageable) {
        Page<AllocationEntity> entities = allocationRepository.findAll(pageable);

        return new PageImpl<>(convertListEntityToDto(entities));
    }

    @Override
    public Iterable<IAllocationDTO> findAll() {
        Iterable<AllocationEntity> entities = allocationRepository.findAll();

        return convertListEntityToDto(entities);
    }

    @Override
    public Iterable<IAllocationDTO> findAll(Iterable<Long> idList) {
        Iterable<AllocationEntity> entities = allocationRepository.findAll(idList);

        return convertListEntityToDto(entities);
    }

    @Override
    public IAllocationDTO findOne(Long id) {
        AllocationEntity entity = allocationRepository.findOne(id);

        return convertEntityToDto(entity);
    }

    @Override
    public long count() {
        return allocationRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return allocationRepository.exists(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        jobInitializer.synchronizeJobWithAllocation(findOne(id), EventType.DELETE);

        allocationRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete(IAllocationDTO dto) {
        jobInitializer.synchronizeJobWithAllocation(dto, EventType.DELETE);

        AllocationEntity entity = convertDtoToEntity(dto);

        allocationRepository.delete(entity);
    }

    @Override
    @Transactional
    public void delete(Iterable<? extends IAllocationDTO> dtoList) {
        dtoList.forEach(a -> jobInitializer.synchronizeJobWithAllocation(a, EventType.DELETE));

        List<AllocationEntity> entities = convertListDtoToEntity((Iterable<IAllocationDTO>) dtoList);

        allocationRepository.delete(entities);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AllocationEntity convertDtoToEntity(IAllocationDTO dto) {
        return conversionService.convert(dto, AllocationEntity.class);
    }

    @Override
    public IAllocationDTO convertEntityToDto(AllocationEntity entity) {
        return conversionService.convert(entity, IAllocationDTO.class);
    }
}
