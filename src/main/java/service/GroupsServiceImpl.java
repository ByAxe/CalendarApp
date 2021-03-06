/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import core.commons.Result;
import core.dto.GroupsDTOImpl;
import core.dto.api.IGroupsDTO;
import core.dto.api.IRulersDTO;
import core.enums.Stage;
import core.validators.api.IValidator;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import jfxtras.scene.control.LocalDateTextField;
import model.entity.GroupsEntity;
import model.entity.RulersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.GroupsRepository;
import service.api.IGroupsService;
import service.api.IRulersService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static core.commons.Utils.getIdFromComboBox;
import static core.commons.Utils.raiseMessageBox;
import static core.enums.ResultEnum.SUCCESS;
import static java.util.stream.Collectors.toList;
import static javafx.scene.control.Alert.AlertType.*;

/**
 * Created by byaxe on 26.11.16.
 */
@Service("groupsService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class GroupsServiceImpl implements IGroupsService {

    private final GroupsRepository groupsRepository;
    private final IRulersService rulersService;
    private final ConversionService conversionService;
    private final IValidator<Result, IGroupsDTO> validator;

    @Value("${delete.context.menu.item}")
    private String DELETE_CONTEXT_MENU_ITEM;

    @Value("${change.context.menu.item}")
    private String CHANGE_CONTEXT_MENU_ITEM;

    @Value("${delete.element.title}")
    private String DELETE_ELEMENT_TITLE;

    @Value("${delete.element.header}")
    private String DELETE_ELEMENT_HEADER;

    @Value("${change.element.title}")
    private String CHANGE_ELEMENT_TITLE;

    @Value("${change.element.body}")
    private String CHANGE_ELEMENT_BODY;

    @Value("${change.element.header}")
    private String CHANGE_ELEMENT_HEADER;

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

    @Value("${delete.constraint.violation.title}")
    private String DELETE_CONSTRAINT_VIOLATION_TITLE;

    @Value("${delete.constraint.violation.header}")
    private String DELETE_CONSTRAINT_VIOLATION_HEADER;

    @Value("${delete.constraint.violation.body}")
    private String DELETE_CONSTRAINT_VIOLATION_BODY;

    @Autowired
    public GroupsServiceImpl(GroupsRepository groupsRepository, IRulersService rulersService, ConversionService conversionService, IValidator<Result, IGroupsDTO> validator) {
        this.groupsRepository = groupsRepository;
        this.rulersService = rulersService;
        this.conversionService = conversionService;
        this.validator = validator;
    }

    @Override
    public void fillGroupsList(JFXListView<Label> groupsList) {
        groupsList.getItems()
                .setAll(((List<IGroupsDTO>) findAll())
                        .stream()
                        .map(group -> {
                            Label label = new Label(group.toPrettyString());
                            label.setId(String.valueOf(group.getUuid()));
                            return label;
                        })
                        .collect(toList()));
    }

    @Override
    @Transactional
    public void addContextMenuToGroupsList(JFXListView<Label> groupsList, JFXTextField id, JFXTextField title,
                                           JFXTextField qualification, JFXTextField number, JFXTextField specialisation,
                                           JFXTextField description, JFXTextField hours, JFXComboBox rulers,
                                           LocalDateTextField issueDate, JFXComboBox stage) {
        final ContextMenu managementGroupsListContextMenu = new ContextMenu();

        MenuItem delete = new MenuItem(DELETE_CONTEXT_MENU_ITEM);
        MenuItem change = new MenuItem(CHANGE_CONTEXT_MENU_ITEM);

        managementGroupsListContextMenu.getItems().addAll(delete, change);

        delete.setOnAction(event -> {
            Label item = groupsList.getSelectionModel().getSelectedItem();

            // Если кликнули по пустому месту
            if (item == null) return;

            boolean isOk = raiseMessageBox(CONFIRMATION, DELETE_ELEMENT_TITLE, DELETE_ELEMENT_HEADER, "Вы действительно хотите удалить данный элемент: \"" + item.getText() + "\"?");

            // Если отказался удалять выбранный элемент
            if (!isOk) return;

            // Если он все же подтвердил удаление - удаляем элемент
            IGroupsDTO dto = findByUuid(UUID.fromString(item.getId()));
            try {
                delete(dto);
            } catch (Exception e) {
                raiseMessageBox(ERROR, DELETE_CONSTRAINT_VIOLATION_TITLE, DELETE_CONSTRAINT_VIOLATION_HEADER, DELETE_CONSTRAINT_VIOLATION_BODY);
                return;
            }

            id.setText("0");
            title.setText("");
            qualification.setText("");
            number.setText("");
            specialisation.setText("");
            description.setText("");
            hours.setText("");
            stage.setValue(stage.getItems().get(0));
            issueDate.setLocalDate(LocalDate.now());

            // Обновляем список, иначе для пользователя останется виден удаленный элемент
            fillGroupsList(groupsList);

        });

        change.setOnAction(event -> {
            Label item = groupsList.getSelectionModel().getSelectedItem();

            // Если кликнули по пустому месту
            if (item == null) return;

            boolean isOk = raiseMessageBox(CONFIRMATION, CHANGE_ELEMENT_TITLE, CHANGE_ELEMENT_HEADER, CHANGE_ELEMENT_BODY);

            // Если отказался редактировать выбранный элемент
            if (!isOk) return;

            // Получили группу из базы
            IGroupsDTO dto = findByUuid(UUID.fromString(item.getId()));

            // Поместили все данные в поля редактирования
            id.setText(String.valueOf(dto.getId()));
            title.setText(dto.getTitle());
            qualification.setText(dto.getQualification());
            number.setText(dto.getNumber());
            specialisation.setText(dto.getSpecialization());
            description.setText(dto.getDescription());
            hours.setText(String.valueOf(dto.getHours()));

            rulers.getItems().clear();
            Optional.ofNullable(((List<IRulersDTO>) rulersService.findAll()))
                    .ifPresent(l -> l.forEach(r -> {
                        rulers.getItems().add(r.toPrettyString());
                    }));

            rulers.setValue(dto.getRuler().toPrettyString());

            stage.setValue(dto.getStage().getAcronym());
            issueDate.setLocalDate(LocalDate.of(dto.getIssueYear(), dto.getIssueMonth(), 1));
        });

        groupsList.setContextMenu(managementGroupsListContextMenu);

    }

    @Override
    public IGroupsDTO findByUuid(UUID uuid) {
        GroupsEntity entity = groupsRepository.findByUuid(uuid);

        return convertEntityToDto(entity);
    }

    @Override
    @Transactional
    public void save(Long id, String title, String qualification, String number, String specialisation, String description,
                     String hours, LocalDate issueDate, String stage, String ruler) {
        IGroupsDTO group;

        // Новичок
        if (id == 0) {
            group = new GroupsDTOImpl();
            group.setUuid(UUID.randomUUID());
            group.setDtUpdate(new Date());
        } else {
            group = findOne(id);
        }

        Alert.AlertType alertType;
        String alertTitle;
        String alertHeader;
        String alertBody;

        group.setId(id);
        group.setTitle(title);
        group.setQualification(qualification);
        group.setNumber(number);
        group.setSpecialization(specialisation);
        group.setDescription(description);

        Optional.ofNullable(issueDate).ifPresent(d -> {
            group.setIssueYear(d.getYear());
            group.setIssueMonth(d.getMonthValue());
        });

        Stream.of(Stage.values()).forEach(s -> {
            if (s.getAcronym().equals(stage)) group.setStage(s);
        });

        try {
            group.setHours(Integer.valueOf(hours));
        } catch (NumberFormatException e) {
            alertType = ERROR;

            alertTitle = VALIDATION_ERROR_TITLE;
            alertHeader = VALIDATION_ERROR_HEADER;
            alertBody = "Количество часов должно быть числом.";

            raiseMessageBox(alertType, alertTitle, alertHeader, alertBody);
            return;
        }

        group.setRuler(rulersService.findOne(getIdFromComboBox(ruler)));

        Result result = validator.validate(group);


        if (Objects.equals(result.getResult(), SUCCESS)) {
            save(group);

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
    @Transactional
    public IGroupsDTO save(IGroupsDTO sourceDto) {
        if (sourceDto == null) throw new IllegalArgumentException();

        GroupsEntity newEntity = convertDtoToEntity(sourceDto);

        RulersEntity ruler = rulersService.getActualEntity(newEntity.getRuler().getId());

        newEntity.setRuler(ruler);

        GroupsEntity savedEntity = groupsRepository.save(newEntity);

        return convertEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public Iterable<IGroupsDTO> save(Iterable<IGroupsDTO> sourceDtoList) {
        if (sourceDtoList == null) throw new IllegalArgumentException();

        List<GroupsEntity> sourceEntities = convertListDtoToEntity(sourceDtoList);

        sourceEntities.forEach(newEntity -> {
            RulersEntity ruler = rulersService.getActualEntity(newEntity.getRuler().getId());
            newEntity.setRuler(ruler);
        });

        Iterable<GroupsEntity> savedEntities = groupsRepository.saveAll(sourceEntities);

        return convertListEntityToDto(savedEntities);
    }

    @Override
    public Iterable<IGroupsDTO> findAll(Sort sort) {
        Iterable<GroupsEntity> entities = groupsRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    @Override
    public Page<IGroupsDTO> findAll(Pageable pageable) {
        Page<GroupsEntity> entities = groupsRepository.findAll(pageable);

        List<IGroupsDTO> dtoList = convertListEntityToDto(entities);

        return new PageImpl<>(dtoList);
    }

    @Override
    public Iterable<IGroupsDTO> findAll() {
        Iterable<GroupsEntity> entities = groupsRepository.findAll();

        return convertListEntityToDto(entities);
    }

    @Override
    public Iterable<IGroupsDTO> findAll(Iterable<Long> ids) {
        Iterable<GroupsEntity> entities = groupsRepository.findAllById(ids);

        return convertListEntityToDto(entities);
    }

    @Override
    public IGroupsDTO findOne(Long id) {
        Optional<GroupsEntity> entity = groupsRepository.findById(id);

        return entity.map(this::convertEntityToDto).orElse(null);
    }

    @Override
    public long count() {
        return groupsRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return groupsRepository.existsById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        groupsRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(IGroupsDTO dto) {
        GroupsEntity entity = convertDtoToEntity(dto);

        groupsRepository.delete(entity);
    }

    @Override
    @Transactional
    public void delete(Iterable<? extends IGroupsDTO> dtoList) {
        List<GroupsEntity> entities = convertListDtoToEntity((Iterable<IGroupsDTO>) dtoList);

        groupsRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }


    public GroupsEntity convertDtoToEntity(IGroupsDTO dto) {
        return conversionService.convert(dto, GroupsEntity.class);
    }

    public IGroupsDTO convertEntityToDto(GroupsEntity entity) {
        return conversionService.convert(entity, IGroupsDTO.class);
    }
}
