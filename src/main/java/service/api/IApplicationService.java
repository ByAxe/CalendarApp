package service.api;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
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
}
