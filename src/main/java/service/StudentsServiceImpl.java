/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import core.commons.Result;
import core.dto.StudentsDTOImpl;
import core.dto.api.IStudentsDTO;
import core.validators.api.IValidator;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import model.entity.StudentsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.StudentsRepository;
import service.api.IStudentsService;

import java.util.*;

import static core.commons.Utils.raiseMessageBox;
import static core.enums.ResultEnum.SUCCESS;
import static java.util.stream.Collectors.toList;
import static javafx.scene.control.Alert.AlertType.*;

/**
 * Created by byaxe on 18.12.16.
 */
@Service("studentsService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class StudentsServiceImpl implements IStudentsService {

    private static final Logger logger = LoggerFactory.getLogger(StudentsServiceImpl.class);

    private final StudentsRepository studentsRepository;
    private final ConversionService conversionService;
    private final IValidator<Result, IStudentsDTO> validator;

    @Value("${validation.error.title}")
    private String VALIDATION_ERROR_TITLE;

    @Value("${validation.error.header}")
    private String VALIDATION_ERROR_HEADER;

    @Value("${validation.success.title}")
    private String VALIDATION_SUCCESS_TITLE;

    @Value("${validation.success.header}")
    private String VALIDATION_SUCCESS_HEADER;

    @Value("${validation.success.body}")
    private String VALIDATION_SUCCESS_BODY;

    @Value("${change.element.title}")
    private String CHANGE_ELEMENT_TITLE;

    @Value("${change.element.body}")
    private String CHANGE_ELEMENT_BODY;

    @Value("${change.element.header}")
    private String CHANGE_ELEMENT_HEADER;

    @Value("${delete.element.title}")
    private String DELETE_ELEMENT_TITLE;

    @Value("${delete.element.header}")
    private String DELETE_ELEMENT_HEADER;

    @Value("${delete.context.menu.item}")
    private String DELETE_CONTEXT_MENU_ITEM;

    @Value("${change.context.menu.item}")
    private String CHANGE_CONTEXT_MENU_ITEM;

    @Autowired
    public StudentsServiceImpl(StudentsRepository studentsRepository, ConversionService conversionService, IValidator<Result, IStudentsDTO> validator) {
        this.studentsRepository = studentsRepository;
        this.conversionService = conversionService;
        this.validator = validator;
    }

    @Override
    @Transactional
    public void save(long id, String firstName, String middleName, String lastName, String telephone, String address) {
        IStudentsDTO student;

        // Новичок
        if (id == 0) {
            student = new StudentsDTOImpl();
            student.setUuid(UUID.randomUUID());
            student.setDtUpdate(new Date());
        } else {
            student = findOne(id);
        }

        student.setFirstName(firstName);
        student.setMiddleName(middleName);
        student.setLastName(lastName);
        student.setTelephone(telephone);
        student.setAddress(address);

        Result result = validator.validate(student);

        Alert.AlertType alertType;
        String alertTitle;
        String alertHeader;
        String alertBody;

        if (Objects.equals(result.getResult(), SUCCESS)) {
            save(student);

            alertType = INFORMATION;

            alertTitle = VALIDATION_SUCCESS_TITLE;
            alertHeader = VALIDATION_SUCCESS_HEADER;
            alertBody = VALIDATION_SUCCESS_BODY;
        } else {
            alertType = ERROR;

            alertTitle = VALIDATION_ERROR_TITLE;
            alertHeader = VALIDATION_ERROR_HEADER;
            alertBody = result.errorsToString();
        }

        raiseMessageBox(alertType, alertTitle, alertHeader, alertBody);
    }

    @Override
    public void fillStudentsList(JFXListView<Label> studentsList) {
        studentsList.getItems()
                .setAll(((List<IStudentsDTO>) findAll())
                        .stream()
                        .map(student -> {
                            Label label = new Label(student.toPrettyString());
                            label.setId(String.valueOf(student.getUuid()));
                            return label;
                        })
                        .collect(toList()));
    }

    @Override
    @Transactional
    public void addContextMenuToStudentsList(JFXListView<Label> studentsList, JFXTextField id, JFXTextField firstName,
                                             JFXTextField middleName, JFXTextField lastName, JFXTextField telephone,
                                             JFXTextField address) {
        final ContextMenu managementStudentsListContextMenu = new ContextMenu();

        MenuItem delete = new MenuItem("Удалить");
        MenuItem change = new MenuItem("Редактировать");

        managementStudentsListContextMenu.getItems().addAll(delete, change);

        delete.setOnAction(event -> {
            Label item = studentsList.getSelectionModel().getSelectedItem();

            // Если кликнули по пустому месту
            if (item == null) return;

            boolean isOk = raiseMessageBox(CONFIRMATION, DELETE_ELEMENT_TITLE, DELETE_ELEMENT_HEADER, "Вы действительно хотите удалить данный элемент: \"" + item.getText() + "\"?");

            // Если отказался удалять выбранный элемент
            if (!isOk) return;

            // Если он все же подтвердил удаление - удаляем элемент
            Optional.ofNullable(((List<IStudentsDTO>) findAll()))
                    .ifPresent(l -> l.stream()
                            .filter(e -> Objects.equals(String.valueOf(e.getUuid()), item.getId()))
                            .forEach(this::delete));

            // Обновляем список, иначе для пользователя останется виден удаленный элемент
            fillStudentsList(studentsList);
        });

        change.setOnAction(event -> {

            Label item = studentsList.getSelectionModel().getSelectedItem();

            // Если кликнули по пустому месту
            if (item == null) return;

            boolean isOk = raiseMessageBox(CONFIRMATION, CHANGE_ELEMENT_TITLE, CHANGE_ELEMENT_HEADER, CHANGE_ELEMENT_BODY);

            // Если отказался редактировать выбранный элемент
            if (!isOk) return;

            // Получили выпускника из базы
            IStudentsDTO student = findByUuid(UUID.fromString(item.getId()));

            // Поместили все данные в поля редактирования
            id.setText(String.valueOf(student.getId()));
            firstName.setText(student.getFirstName());
            middleName.setText(student.getMiddleName());
            lastName.setText(student.getLastName());
            telephone.setText(student.getTelephone());
            address.setText(student.getAddress());
        });

        studentsList.setContextMenu(managementStudentsListContextMenu);
    }

    @Override
    @Transactional
    public IStudentsDTO save(IStudentsDTO dto) {
        StudentsEntity entity = convertDtoToEntity(dto);

        StudentsEntity savedEntity = studentsRepository.save(entity);

        return convertEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public Iterable<IStudentsDTO> save(Iterable<IStudentsDTO> dtoList) {
        List<StudentsEntity> entities = convertListDtoToEntity(dtoList);

        Iterable<StudentsEntity> savedEntities = studentsRepository.save(entities);

        return convertListEntityToDto(savedEntities);
    }

    @Override
    public IStudentsDTO findByUuid(UUID uuid) {
        StudentsEntity entity = studentsRepository.findByUuid(uuid);

        return convertEntityToDto(entity);
    }

    @Override
    public Iterable<IStudentsDTO> findAll(Sort sort) {
        Iterable<StudentsEntity> entities = studentsRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    @Override
    public Page<IStudentsDTO> findAll(Pageable pageable) {
        Page<StudentsEntity> entities = studentsRepository.findAll(pageable);

        return new PageImpl<>(convertListEntityToDto(entities));
    }

    @Override
    public Iterable<IStudentsDTO> findAll() {
        Iterable<StudentsEntity> entities = studentsRepository.findAll();

        return convertListEntityToDto(entities);
    }

    @Override
    public Iterable<IStudentsDTO> findAll(Iterable<Long> idList) {
        Iterable<StudentsEntity> entities = studentsRepository.findAll(idList);

        return convertListEntityToDto(entities);
    }

    @Override
    public IStudentsDTO findOne(Long id) {
        StudentsEntity entity = studentsRepository.findOne(id);

        return convertEntityToDto(entity);
    }

    @Override
    public long count() {
        return studentsRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return studentsRepository.exists(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        studentsRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete(IStudentsDTO dto) {
        StudentsEntity entity = convertDtoToEntity(dto);

        studentsRepository.delete(entity);
    }

    @Override
    @Transactional
    public void delete(Iterable<? extends IStudentsDTO> dtoList) {
        List<StudentsEntity> entities = convertListDtoToEntity((Iterable<IStudentsDTO>) dtoList);

        studentsRepository.delete(entities);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public StudentsEntity convertDtoToEntity(IStudentsDTO dto) {
        return conversionService.convert(dto, StudentsEntity.class);
    }

    @Override
    public IStudentsDTO convertEntityToDto(StudentsEntity entity) {
        return conversionService.convert(entity, IStudentsDTO.class);
    }
}
