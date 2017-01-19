/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.service;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.core.commons.Result;
import com.core.dto.RulersDTOImpl;
import com.core.dto.api.IRulersDTO;
import com.core.validators.api.IValidator;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import com.model.entity.RulersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.repository.RulersRepository;
import com.service.api.IRulersService;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.core.commons.Utils.raiseMessageBox;
import static com.core.enums.ResultEnum.SUCCESS;
import static java.util.stream.Collectors.toList;
import static javafx.scene.control.Alert.AlertType.*;

/**
 * Created by byaxe on 17.12.16.
 */
@Service("rulersService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class RulersServiceImpl implements IRulersService {

    private final RulersRepository rulersRepository;
    private final ConversionService conversionService;
    private final IValidator<Result, IRulersDTO> validator;


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
    public RulersServiceImpl(RulersRepository rulersRepository, ConversionService conversionService, IValidator<Result, IRulersDTO> validator) {
        this.rulersRepository = rulersRepository;
        this.conversionService = conversionService;
        this.validator = validator;
    }

    @Override
    public void fillRulersList(JFXListView<Label> rulersList) {
        rulersList.getItems()
                .setAll(((List<IRulersDTO>) findAll())
                        .stream()
                        .map(ruler -> {
                            Label label = new Label(ruler.toPrettyString());
                            label.setId(String.valueOf(ruler.getUuid()));
                            return label;
                        })
                        .collect(toList()));
    }

    @Override
    public void addContextMenuToRulersList(JFXListView<Label> rulersList, JFXTextField id, JFXTextField firstName,
                                           JFXTextField middleName, JFXTextField lastName, JFXTextField payload) {
        final ContextMenu managementRulersListContextMenu = new ContextMenu();

        MenuItem delete = new MenuItem(DELETE_CONTEXT_MENU_ITEM);
        MenuItem change = new MenuItem(CHANGE_CONTEXT_MENU_ITEM);

        managementRulersListContextMenu.getItems().addAll(delete, change);

        delete.setOnAction(event -> {
            Label item = rulersList.getSelectionModel().getSelectedItem();

            // Если кликнули по пустому месту
            if (item == null) return;

            boolean isOk = raiseMessageBox(CONFIRMATION, DELETE_ELEMENT_TITLE, DELETE_ELEMENT_HEADER, "Вы действительно хотите удалить данный элемент: \"" + item.getText() + "\"?");

            // Если отказался удалять выбранный элемент
            if (!isOk) return;

            IRulersDTO dto = findByUuid(UUID.fromString(item.getId()));

            try {
                delete(dto);
            } catch (Exception e) {
                raiseMessageBox(ERROR, DELETE_CONSTRAINT_VIOLATION_TITLE, DELETE_CONSTRAINT_VIOLATION_HEADER, DELETE_CONSTRAINT_VIOLATION_BODY);
                return;
            }

            id.setText("0");
            firstName.setText("");
            middleName.setText("");
            lastName.setText("");
            payload.setText("");

            // Обновляем список, иначе для пользователя останется виден удаленный элемент
            fillRulersList(rulersList);

        });

        change.setOnAction(event -> {

            Label item = rulersList.getSelectionModel().getSelectedItem();

            // Если кликнули по пустому месту
            if (item == null) return;

            boolean isOk = raiseMessageBox(CONFIRMATION, CHANGE_ELEMENT_TITLE, CHANGE_ELEMENT_HEADER, CHANGE_ELEMENT_BODY);

            // Если отказался редактировать выбранный элемент
            if (!isOk) return;

            // Получили группу из базы
            IRulersDTO ruler = findByUuid(UUID.fromString(item.getId()));

            // Поместили все данные в поля редактирования
            id.setText(String.valueOf(ruler.getId()));
            firstName.setText(ruler.getFirstName());
            middleName.setText(ruler.getMiddleName());
            lastName.setText(ruler.getLastName());
            payload.setText(ruler.getPayload());
        });

        rulersList.setContextMenu(managementRulersListContextMenu);
    }

    @Override
    @Transactional
    public void save(Long id, String firstName, String middleName, String lastName, String payload) {
        IRulersDTO ruler;

        // Новичок
        if (id == 0) {
            ruler = new RulersDTOImpl();
            ruler.setUuid(UUID.randomUUID());
            ruler.setDtUpdate(new Date());
        } else {
            ruler = findOne(id);
        }

        ruler.setId(id);
        ruler.setFirstName(firstName);
        ruler.setMiddleName(middleName);
        ruler.setLastName(lastName);
        ruler.setPayload(payload);

        Result result = validator.validate(ruler);

        Alert.AlertType alertType;
        String alertTitle;
        String alertHeader;
        String alertBody;

        if (Objects.equals(result.getResult(), SUCCESS)) {
            save(ruler);

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
    public IRulersDTO save(IRulersDTO dto) {
        if (dto == null) throw new IllegalArgumentException();

        RulersEntity sourceEntity = convertDtoToEntity(dto);

        RulersEntity savedEntity = rulersRepository.save(sourceEntity);

        return convertEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public Iterable<IRulersDTO> save(Iterable<IRulersDTO> dtoList) {
        if (dtoList == null) throw new IllegalArgumentException();

        List<RulersEntity> sourceEntities = convertListDtoToEntity(dtoList);

        Iterable<RulersEntity> savedEntities = rulersRepository.save(sourceEntities);

        return convertListEntityToDto(savedEntities);
    }

    @Override
    public IRulersDTO findByUuid(UUID uuid) {
        RulersEntity entity = rulersRepository.findByUuid(uuid);

        return convertEntityToDto(entity);
    }

    @Override
    public Iterable<IRulersDTO> findAll(Sort sort) {
        Iterable<RulersEntity> entities = rulersRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    @Override
    public Page<IRulersDTO> findAll(Pageable pageable) {
        Page<RulersEntity> entities = rulersRepository.findAll(pageable);

        List<IRulersDTO> dtoList = convertListEntityToDto(entities);

        return new PageImpl<>(dtoList);
    }

    @Override
    public Iterable<IRulersDTO> findAll() {
        Iterable<RulersEntity> entities = rulersRepository.findAll();

        return convertListEntityToDto(entities);
    }

    @Override
    public Iterable<IRulersDTO> findAll(Iterable<Long> ids) {
        Iterable<RulersEntity> entities = rulersRepository.findAll(ids);

        return convertListEntityToDto(entities);
    }

    @Override
    public IRulersDTO findOne(Long id) {
        return convertEntityToDto(getActualEntity(id));
    }

    @Override
    public RulersEntity getActualEntity(Long id) {
        return rulersRepository.findOne(id);
    }

    @Override
    public long count() {
        return rulersRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return rulersRepository.exists(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        rulersRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete(IRulersDTO dto) {
        RulersEntity entity = convertDtoToEntity(dto);

        rulersRepository.delete(entity);
    }

    @Override
    @Transactional
    public void delete(Iterable<? extends IRulersDTO> dtoList) {
        List<RulersEntity> entities = convertListDtoToEntity((Iterable<IRulersDTO>) dtoList);

        rulersRepository.delete(entities);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RulersEntity convertDtoToEntity(IRulersDTO dto) {
        return conversionService.convert(dto, RulersEntity.class);
    }

    @Override
    public IRulersDTO convertEntityToDto(RulersEntity entity) {
        return conversionService.convert(entity, IRulersDTO.class);
    }
}
