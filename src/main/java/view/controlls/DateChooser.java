/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package view.controlls;

import javafx.scene.control.Control;

import java.util.Date;

/**
 * Created by byaxe on 22.12.16.
 */
public class DateChooser extends Control {
    private static final String DEFAULT_STYLE_CLASS = "date-chooser";
    private Date date;

    public DateChooser(Date preset) {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
        this.date = preset;
    }

    public DateChooser() {
        this(new Date(System.currentTimeMillis()));
    }

    @Override
    public String getUserAgentStylesheet() {
        return "css/calendar.css";
    }

    public Date getDate() {
        return date;
    }
}
