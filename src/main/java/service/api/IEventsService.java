/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service.api;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import core.dto.api.IEventsDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import jfxtras.scene.control.LocalDateTimeTextField;
import model.entity.EventsEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by byaxe on 26.11.16.
 */
public interface IEventsService extends IEssenceService<IEventsDTO, Long>, IConversionService<IEventsDTO, EventsEntity> {
    List<IEventsDTO> findUpcomingEvents();

    List<IEventsDTO> findUpcomingEventsForPeriod(Date starts, Date ends);

    Long deleteByUuid(UUID uuid);

    void createNewEvent(JFXTextField title, LocalDateTimeTextField starts, LocalDateTimeTextField ends,
                        JFXComboBox noticePeriod, JFXComboBox frequency, JFXComboBox priority,
                        ActionEvent actionEvent);

    void cleanEventForm(JFXTextField title, LocalDateTimeTextField starts, LocalDateTimeTextField ends,
                        JFXComboBox noticePeriod, JFXComboBox frequency, JFXComboBox priority,
                        ActionEvent actionEvent);

    void fillComboBoxes(JFXComboBox noticePeriodPicker, JFXComboBox frequencyPicker,
                        JFXComboBox priorityPicker);

    void fillUpcomingEventsList(JFXListView<Label> calendarUpcomingEventsListView);

    void addContextMenuToUpcomingEventsList(JFXListView<Label> calendarUpcomingEventsListView);

    void createBigCalendar(Pane calendarRightPaneBottomBlock, Label calendarUpcomingEventsLabel, JFXListView<Label> calendarUpcomingEventsListView);

    IEventsDTO findByUuid(UUID uuid);

}
