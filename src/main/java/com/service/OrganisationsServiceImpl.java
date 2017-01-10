/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.service;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.core.commons.Result;
import com.core.dto.OrganisationsDTOImpl;
import com.core.dto.api.IOrganisationsDTO;
import com.core.validators.api.IValidator;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import com.model.entity.OrganisationsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.repository.OrganisationsRepository;
import com.service.api.IOrganisationsService;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.core.commons.Utils.raiseMessageBox;
import static com.core.enums.ResultEnum.SUCCESS;
import static java.util.stream.Collectors.toList;
import static javafx.scene.control.Alert.AlertType.*;

/**
 * Created by byaxe on 18.12.16.
 */
@Service
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class OrganisationsServiceImpl implements IOrganisationsService {

    private final ConversionService conversionService;
    private final OrganisationsRepository organisationsRepository;
    private final IValidator<Result, IOrganisationsDTO> validator;

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

    @Value("${delete.constraint.violation.title}")
    private String DELETE_CONSTRAINT_VIOLATION_TITLE;

    @Value("${delete.constraint.violation.header}")
    private String DELETE_CONSTRAINT_VIOLATION_HEADER;

    @Value("${delete.constraint.violation.body}")
    private String DELETE_CONSTRAINT_VIOLATION_BODY;

    @Autowired
    public OrganisationsServiceImpl(ConversionService conversionService, OrganisationsRepository organisationsRepository, IValidator<Result, IOrganisationsDTO> validator) {
        this.conversionService = conversionService;
        this.organisationsRepository = organisationsRepository;
        this.validator = validator;
    }

    @Override
    @Transactional
    public void save(Long id, String title, String address, String telephone, String contacts) {
        IOrganisationsDTO organisation;

        // Новичок
        if (id == 0) {
            organisation = new OrganisationsDTOImpl();
            organisation.setUuid(UUID.randomUUID());
            organisation.setDtUpdate(new Date());
        } else {
            organisation = findOne(id);
        }

        organisation.setTitle(title);
        organisation.setAddress(address);
        organisation.setTelephone(telephone);
        organisation.setContacts(contacts);

        Result result = validator.validate(organisation);

        Alert.AlertType alertType;
        String alertTitle;
        String alertHeader;
        String alertBody;

        if (Objects.equals(result.getResult(), SUCCESS)) {
            save(organisation);

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
    public void fillOrganisationList(JFXListView<Label> organisationsList) {
        organisationsList.getItems()
                .setAll(((List<IOrganisationsDTO>) findAll())
                        .stream()
                        .map(organisation -> {
                            Label label = new Label(organisation.toPrettyString());
                            label.setId(String.valueOf(organisation.getUuid()));
                            return label;
                        })
                        .collect(toList()));
    }

    @Override
    @Transactional
    public void addContextMenuToOrganisationsList(JFXListView<Label> organisationsList, JFXTextField id,
                                                  JFXTextField title, JFXTextField address, JFXTextField telephone,
                                                  JFXTextField contacts) {
        final ContextMenu managementOrganisationsContextMenu = new ContextMenu();

        MenuItem delete = new MenuItem(DELETE_CONTEXT_MENU_ITEM);
        MenuItem change = new MenuItem(CHANGE_CONTEXT_MENU_ITEM);

        managementOrganisationsContextMenu.getItems().addAll(delete, change);


        delete.setOnAction(event -> {
            Label item = organisationsList.getSelectionModel().getSelectedItem();

            // Если кликнули по пустому месту
            if (item == null) return;

            boolean isOk = raiseMessageBox(CONFIRMATION, DELETE_ELEMENT_TITLE, DELETE_ELEMENT_HEADER, "Вы действительно хотите удалить данный элемент: \"" + item.getText() + "\"?");

            // Если отказался удалять выбранный элемент
            if (!isOk) return;

            // Если он все же подтвердил удаление - удаляем элемент
            IOrganisationsDTO dto = findByUuid(UUID.fromString(item.getId()));
            try {
                delete(dto);
            } catch (Exception e) {
                raiseMessageBox(ERROR, DELETE_CONSTRAINT_VIOLATION_TITLE, DELETE_CONSTRAINT_VIOLATION_HEADER, DELETE_CONSTRAINT_VIOLATION_BODY);
                return;
            }

            id.setText("0");
            title.setText("");
            telephone.setText("");
            address.setText("");
            contacts.setText("");

            // Обновляем список, иначе для пользователя останется виден удаленный элемент
            fillOrganisationList(organisationsList);

        });

        change.setOnAction(event -> {
            Label item = organisationsList.getSelectionModel().getSelectedItem();

            // Если кликнули по пустому месту
            if (item == null) return;

            boolean isOk = raiseMessageBox(CONFIRMATION, CHANGE_ELEMENT_TITLE, CHANGE_ELEMENT_HEADER, CHANGE_ELEMENT_BODY);

            // Если отказался редактировать выбранный элемент
            if (!isOk) return;

            // Получили группу из базы
            IOrganisationsDTO organisation = findByUuid(UUID.fromString(item.getId()));

            // Поместили все данные в поля редактирования
            id.setText(String.valueOf(organisation.getId()));
            title.setText(organisation.getTitle());
            address.setText(organisation.getAddress());
            telephone.setText(organisation.getTelephone());
            contacts.setText(organisation.getContacts());
        });

        organisationsList.setContextMenu(managementOrganisationsContextMenu);
    }

    @Override
    public IOrganisationsDTO findByUuid(UUID uuid) {
        OrganisationsEntity entity = organisationsRepository.findByUuid(uuid);

        return convertEntityToDto(entity);
    }

    @Override
    @Transactional
    public IOrganisationsDTO save(IOrganisationsDTO dto) {
        OrganisationsEntity entity = convertDtoToEntity(dto);

        OrganisationsEntity savedEntity = organisationsRepository.save(entity);

        return convertEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public Iterable<IOrganisationsDTO> save(Iterable<IOrganisationsDTO> dtoList) {
        List<OrganisationsEntity> entities = convertListDtoToEntity(dtoList);

        Iterable<OrganisationsEntity> savedEntities = organisationsRepository.save(entities);

        return convertListEntityToDto(savedEntities);
    }

    @Override
    public Iterable<IOrganisationsDTO> findAll(Sort sort) {
        Iterable<OrganisationsEntity> entities = organisationsRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    @Override
    public Page<IOrganisationsDTO> findAll(Pageable pageable) {
        Page<OrganisationsEntity> entities = organisationsRepository.findAll(pageable);

        return new PageImpl<>(convertListEntityToDto(entities));
    }

    @Override
    public Iterable<IOrganisationsDTO> findAll() {
        Iterable<OrganisationsEntity> entities = organisationsRepository.findAll();

        return convertListEntityToDto(entities);
    }

    @Override
    public Iterable<IOrganisationsDTO> findAll(Iterable<Long> idList) {
        Iterable<OrganisationsEntity> entities = organisationsRepository.findAll(idList);

        return convertListEntityToDto(entities);
    }

    @Override
    public IOrganisationsDTO findOne(Long id) {
        OrganisationsEntity entity = organisationsRepository.findOne(id);

        return convertEntityToDto(entity);
    }

    @Override
    public long count() {
        return organisationsRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return organisationsRepository.exists(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        organisationsRepository.delete(id);
    }

    @Override
    @Transactional
    public void delete(IOrganisationsDTO dto) {
        organisationsRepository.delete(convertDtoToEntity(dto));
    }

    @Override
    @Transactional
    public void delete(Iterable<? extends IOrganisationsDTO> dtoList) {
        organisationsRepository.delete(convertListDtoToEntity((Iterable<IOrganisationsDTO>) dtoList));
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrganisationsEntity convertDtoToEntity(IOrganisationsDTO dto) {
        return conversionService.convert(dto, OrganisationsEntity.class);
    }

    @Override
    public IOrganisationsDTO convertEntityToDto(OrganisationsEntity entity) {
        return conversionService.convert(entity, IOrganisationsDTO.class);
    }
}
