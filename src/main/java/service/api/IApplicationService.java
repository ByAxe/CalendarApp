/*
 * // Copyright © 2016 Litvinau Alekisei (ByAxe). All rights reserved.
 */

package service.api;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import jfxtras.scene.control.LocalDateTimeTextField;

/**
 * Created by byaxe on 08.12.16.
 */
public interface IApplicationService {

    void calendarCreateNewEvent(JFXTextField title, LocalDateTimeTextField starts, LocalDateTimeTextField ends,
                                JFXComboBox noticePeriod, JFXComboBox frequency, JFXComboBox priority,
                                ActionEvent actionEvent);

    void calendarCleanEventForm(JFXTextField title, LocalDateTimeTextField starts, LocalDateTimeTextField ends,
                                JFXComboBox noticePeriod, JFXComboBox frequency, JFXComboBox priority,
                                ActionEvent actionEvent);

    void calendarFillComboBoxes(JFXComboBox noticePeriodPicker, JFXComboBox frequencyPicker,
                                JFXComboBox priorityPicker);

    void calendarFillUpcomingEventsList(JFXListView<Label> calendarUpcomingEventsListView);

    void calendarAddContextMenuToUpcomingEventsList(JFXListView<Label> calendarUpcomingEventsListView);

    void showHelp();

    void showAbout();
}
