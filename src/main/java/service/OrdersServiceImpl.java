/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import core.commons.Result;
import core.dto.OrdersDTOImpl;
import core.dto.api.IOrdersDTO;
import core.validators.api.IValidator;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import jfxtras.scene.control.LocalDateTextField;
import model.entity.OrdersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.OrdersRepository;
import service.api.IOrdersService;

import java.time.LocalDate;
import java.util.*;

import static core.commons.Utils.convertLocalDateToLocalDateTime;
import static core.commons.Utils.raiseMessageBox;
import static core.enums.ResultEnum.SUCCESS;
import static java.util.stream.Collectors.toList;
import static javafx.scene.control.Alert.AlertType.*;

/**
 * Created by byaxe on 18.12.16.
 */
@Service("ordersService")
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class OrdersServiceImpl implements IOrdersService {

    private final OrdersRepository ordersRepository;
    private final ConversionService conversionService;
    private final IValidator<Result, IOrdersDTO> validator;

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
    public OrdersServiceImpl(ConversionService conversionService, OrdersRepository ordersRepository,
                             IValidator<Result, IOrdersDTO> validator) {
        this.conversionService = conversionService;
        this.ordersRepository = ordersRepository;
        this.validator = validator;
    }

    @Override
    public void fillOrdersList(JFXListView<Label> ordersList) {
        ordersList.getItems()
                .setAll(((List<IOrdersDTO>) findAll())
                        .stream()
                        .map(order -> {
                            Label label = new Label(order.toPrettyString());
                            label.setId(String.valueOf(order.getUuid()));
                            return label;
                        })
                        .collect(toList()));
    }

    @Override
    @Transactional
    public void addContextMenuToStudentsList(JFXListView<Label> ordersList, JFXTextField id, LocalDateTextField starts,
                                             JFXTextField number, JFXTextField profession, JFXTextArea payload) {
        final ContextMenu managementOrdersListContextMenu = new ContextMenu();

        MenuItem delete = new MenuItem(DELETE_CONTEXT_MENU_ITEM);
        MenuItem change = new MenuItem(CHANGE_CONTEXT_MENU_ITEM);

        managementOrdersListContextMenu.getItems().addAll(delete, change);

        delete.setOnAction(event -> {
            Label item = ordersList.getSelectionModel().getSelectedItem();

            // Если кликнули по пустому месту
            if (item == null) return;

            boolean isOk = raiseMessageBox(CONFIRMATION, DELETE_ELEMENT_TITLE, DELETE_ELEMENT_HEADER, "Вы действительно хотите удалить данный элемент: \"" + item.getText() + "\"?");

            // Если отказался удалять выбранный элемент
            if (!isOk) return;

            // Если он все же подтвердил удаление - удаляем элемент
            IOrdersDTO dto = findByUuid(UUID.fromString(item.getId()));
            try {
                delete(dto);
            } catch (Exception e) {
                raiseMessageBox(ERROR, DELETE_CONSTRAINT_VIOLATION_TITLE, DELETE_CONSTRAINT_VIOLATION_HEADER, DELETE_CONSTRAINT_VIOLATION_BODY);
                return;
            }

            id.setText("0");
            starts.setLocalDate(LocalDate.now());
            profession.setText("");
            number.setText("");
            payload.setText("");

            // Обновляем список, иначе для пользователя останется виден удаленный элемент
            fillOrdersList(ordersList);
        });

        change.setOnAction(event -> {
            Label item = ordersList.getSelectionModel().getSelectedItem();

            // Если кликнули по пустому месту
            if (item == null) return;

            boolean isOk = raiseMessageBox(CONFIRMATION, CHANGE_ELEMENT_TITLE, CHANGE_ELEMENT_HEADER, CHANGE_ELEMENT_BODY);

            // Если отказался редактировать выбранный элемент
            if (!isOk) return;

            // Получили группу из базы
            IOrdersDTO dto = findByUuid(UUID.fromString(item.getId()));

            // Поместили все данные в поля редактирования
            id.setText(String.valueOf(dto.getId()));
            starts.setLocalDate(LocalDate.from(dto.getStarts()));
            profession.setText(dto.getProfession());
            number.setText(dto.getNumber());
            payload.setText(dto.getPayload());
        });

        ordersList.setContextMenu(managementOrdersListContextMenu);
    }

    @Override
    public IOrdersDTO findByUuid(UUID uuid) {
        OrdersEntity entity = ordersRepository.findByUuid(uuid);

        return convertEntityToDto(entity);
    }

    @Override
    @Transactional
    public void save(Long id, LocalDate starts, String profession, String number, String payload) {
        IOrdersDTO order;

        // Новичок
        if (id == 0) {
            order = new OrdersDTOImpl();
            order.setUuid(UUID.randomUUID());
            order.setDtUpdate(new Date());
        } else {
            order = findOne(id);
        }

        Alert.AlertType alertType;
        String alertTitle;
        String alertHeader;
        String alertBody;

        order.setId(id);
        order.setStarts(convertLocalDateToLocalDateTime(starts));
        order.setProfession(profession);
        order.setNumber(number);
        order.setPayload(payload);

        Result result = validator.validate(order);

        if (Objects.equals(result.getResult(), SUCCESS)) {
            save(order);

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
    public IOrdersDTO save(IOrdersDTO dto) {
        OrdersEntity entity = convertDtoToEntity(dto);

        OrdersEntity savedEntity = ordersRepository.save(entity);

        return convertEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public Iterable<IOrdersDTO> save(Iterable<IOrdersDTO> dtoList) {
        List<OrdersEntity> sourceEntities = convertListDtoToEntity(dtoList);

        Iterable<OrdersEntity> savedEntities = ordersRepository.saveAll(sourceEntities);

        return convertListEntityToDto(savedEntities);
    }

    @Override
    public Iterable<IOrdersDTO> findAll(Sort sort) {
        Iterable<OrdersEntity> entities = ordersRepository.findAll(sort);

        return convertListEntityToDto(entities);
    }

    @Override
    public Page<IOrdersDTO> findAll(Pageable pageable) {
        Page<OrdersEntity> entities = ordersRepository.findAll(pageable);

        return new PageImpl<>(convertListEntityToDto(entities));
    }

    @Override
    public Iterable<IOrdersDTO> findAll() {
        Iterable<OrdersEntity> entities = ordersRepository.findAll();

        return convertListEntityToDto(entities);
    }

    @Override
    public Iterable<IOrdersDTO> findAll(Iterable<Long> idList) {
        Iterable<OrdersEntity> entities = ordersRepository.findAllById(idList);

        return convertListEntityToDto(entities);
    }

    @Override
    public IOrdersDTO findOne(Long id) {
        Optional<OrdersEntity> entity = ordersRepository.findById(id);

        return entity.map(this::convertEntityToDto).orElse(null);
    }

    @Override
    public long count() {
        return ordersRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return ordersRepository.existsById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ordersRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(IOrdersDTO dto) {
        ordersRepository.delete(convertDtoToEntity(dto));
    }

    @Override
    @Transactional
    public void delete(Iterable<? extends IOrdersDTO> dtoList) {
        List<OrdersEntity> entities = convertListDtoToEntity((Iterable<IOrdersDTO>) dtoList);

        ordersRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrdersEntity convertDtoToEntity(IOrdersDTO dto) {
        return conversionService.convert(dto, OrdersEntity.class);
    }

    @Override
    public IOrdersDTO convertEntityToDto(OrdersEntity entity) {
        return conversionService.convert(entity, IOrdersDTO.class);
    }
}
