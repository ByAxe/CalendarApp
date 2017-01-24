/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package controlls;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static core.commons.Utils.formatDateForCalendarTitle;
import static java.util.Arrays.asList;
import static java.util.Calendar.*;
import static javafx.event.ActionEvent.ACTION;

/**
 * Created by byaxe on 22.12.16.
 */
public class DateChooserSkin extends SkinBase<DateChooser> {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM yyyy");
    private final Date date;
    private final Label month;
    private final BorderPane content;

    public DateChooserSkin(DateChooser dateChooser) {
        super(dateChooser);
        // this date is the selected date
        date = dateChooser.getDate();
        final DatePickerPane calendarPane = new DatePickerPane(date);


        month = new Label(formatDateForCalendarTitle(calendarPane.getShownMonth()));
        HBox hbox = new HBox();


        // create the navigation Buttons
        Button yearBack = new JFXButton("<<");
        yearBack.addEventHandler(ACTION, event -> calendarPane.forward(-12));

        Button monthBack = new JFXButton("<");
        monthBack.addEventHandler(ACTION, event -> calendarPane.forward(-1));

        Button monthForward = new JFXButton(">");
        monthForward.addEventHandler(ACTION, event -> calendarPane.forward(1));

        Button yearForward = new JFXButton(">>");
        yearForward.addEventHandler(ACTION, event -> calendarPane.forward(12));

        customizeButtons(asList(yearBack, monthBack, monthForward, yearForward));

        // center the label and make it grab all free space
        HBox.setHgrow(month, Priority.ALWAYS);
        month.setMaxWidth(Double.MAX_VALUE);
        month.setAlignment(Pos.CENTER);
        month.setFont(Font.font(20));
        hbox.getChildren().addAll(yearBack, monthBack, month, monthForward, yearForward);

        // use a BorderPane to Layout the view
        content = new BorderPane();

        content.setPadding(new Insets(10, 0, 0, 0));
        hbox.setPadding(new Insets(0, 50, 0, 50));

        getChildren().add(content);
        content.setTop(hbox);
        content.setCenter(calendarPane);
    }

    private static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(ERA) == cal2.get(ERA)
                && cal1.get(YEAR) == cal2.get(YEAR)
                && cal1.get(DAY_OF_YEAR) == cal2.get(DAY_OF_YEAR));
    }

    private static boolean isToday(Calendar cal) {
        return isSameDay(cal, getInstance());
    }

    private void customizeButtons(List<Button> buttons) {
        buttons.forEach(b -> b.setCursor(Cursor.HAND));
    }
    // utility methods

    private static class CalendarCell extends StackPane {

        private final Date date;

        public CalendarCell(Date day, String text) {
            this.date = day;
            Label label = new Label(text);
            getChildren().addAll(label);
        }

        public Date getDate() {
            return date;
        }
    }

    /**
     * @author eppleton
     */
    public class DatePickerPane extends Region {

        private final Date selectedDate;
        private final Calendar cal;
        // this is used to format the day cells
        private final SimpleDateFormat sdf = new SimpleDateFormat("d");
        // empty cell header of weak-of-year row
        private final CalendarCell woyCell = new CalendarCell(new Date(), "");
        private CalendarCell selectedDayCell;
        private int rows, columns;//default
        private double MAIN_WIDTH = 730;
        private double MAIN_HEIGHT = 640;

        public DatePickerPane(Date date) {
            setPrefSize(MAIN_WIDTH, MAIN_HEIGHT);
            woyCell.getStyleClass().add("week-of-year-cell");
            setPadding(new Insets(30, 5, 5, 0));
            this.columns = 7;
            this.rows = 5;

            // use a copy of Date, because it's mutable
            // we'll helperDate it through the month
            cal = getInstance();
            Date helperDate = new Date(date.getTime());
            cal.setTime(helperDate);

            // the selectedDate is the date we will change, when a date is picked
            selectedDate = date;
            refresh();
        }

        /**
         * Move forward the specified number of Months, move backward by using
         * negative numbers
         *
         * @param i
         */
        public void forward(int i) {

            cal.add(MONTH, i);
            month.setText(formatDateForCalendarTitle(cal.getTime()));
            refresh();
        }

        private void refresh() {
            super.getChildren().clear();
            this.rows = 5; // most of the time 5 rows are ok
            // save a copy to reset the date after our loop
            Date copy = new Date(cal.getTime().getTime());

            // empty cell header of weak-of-year row
            super.getChildren().add(woyCell);

            // Display a styleable row of localized weekday symbols
            DateFormatSymbols symbols = new DateFormatSymbols();
            String[] dayNames = symbols.getShortWeekdays();

            // Other way - the Sunday will be the first day of week
//            for (int i = 1; i < dayNames.length - 1; i++) {
//                String temp = dayNames[i + 1];
//                dayNames[i + 1] = dayNames[i];
//                dayNames[i] = temp;
//            }

            // TODO use static constants to access weekdays, I suspect we
            // get problems with localization otherwise ( Day 1 = Sunday/ Monday in
            // different timezones
            for (int i = 1; i < 8; i++) { // array starts with an empty field
                CalendarCell calendarCell = new CalendarCell(cal.getTime(), dayNames[i]);
                calendarCell.getStyleClass().add("weekday-cell");
                super.getChildren().add(calendarCell);
            }

            // find out which month we're displaying
            cal.set(DAY_OF_MONTH, 1);
            final int month = cal.get(MONTH);

            int weekday = cal.get(DAY_OF_WEEK);

            // if the first day is a sunday we need to rewind 7 days otherwise the
            // code below would only start with the second week. There might be
            // better ways of doing this...
            if (weekday != SUNDAY) {
                // it might be possible, that we need to add a row at the end as well...

                Calendar check = getInstance();
                check.setTime(new Date(cal.getTime().getTime()));
                int lastDate = check.getActualMaximum(DATE);
                check.set(DATE, lastDate);
                if ((lastDate + weekday) > 36) {
                    rows = 6;
                }

                cal.add(DATE, -7);

            }
            cal.set(DAY_OF_WEEK, 1);


            // used to identify and style the cell with the selected date;
            Calendar testSelected = getInstance();
            testSelected.setTime(selectedDate);

            for (int i = 0; i < (rows); i++) {

//                 first column shows the week of year
                CalendarCell calendarCell = new CalendarCell(cal.getTime(), "" + cal.get(Calendar.WEEK_OF_YEAR));
                calendarCell.getStyleClass().add("week-of-year-cell");
                super.getChildren().add(calendarCell);

                // loop through current week
                for (int j = 0; j < columns; j++) {
                    String formatted = sdf.format(cal.getTime());
                    final CalendarCell dayCell = new CalendarCell(cal.getTime(), formatted);
                    dayCell.getStyleClass().add("calendar-cell");
                    if (cal.get(MONTH) != month) {
                        dayCell.getStyleClass().add("calendar-cell-inactive");
                    } else {
                        if (isSameDay(testSelected, cal)) {
                            dayCell.getStyleClass().add("calendar-cell-selected");
                            selectedDayCell = dayCell;
                        }
                        if (isToday(cal)) {
                            dayCell.getStyleClass().add("calendar-cell-today");
                        }

                    }
                    dayCell.setOnMouseClicked(e -> {
                        if (selectedDayCell != null) {
                            selectedDayCell.getStyleClass().add("calendar-cell");
                            selectedDayCell.getStyleClass().remove("calendar-cell-selected");
                        }
                        selectedDate.setTime(dayCell.getDate().getTime());
                        dayCell.getStyleClass().remove("calendar-cell");
                        dayCell.getStyleClass().add("calendar-cell-selected");
                        selectedDayCell = dayCell;
                        Calendar checkMonth = getInstance();
                        checkMonth.setTime(dayCell.getDate());

                        if (checkMonth.get(MONTH) != month) {
                            forward(checkMonth.get(MONTH) - month);
                        }
                    });

                    // grow the hovered cell in size
                    dayCell.setOnMouseEntered(e -> {
                        dayCell.setScaleX(1.1);
                        dayCell.setScaleY(1.1);
                    });

                    dayCell.setOnMouseExited(e -> {
                        dayCell.setScaleX(1);
                        dayCell.setScaleY(1);
                    });

                    dayCell.setCursor(Cursor.HAND);
//                    JFXRippler jfxRippler = new JFXRippler(dayCell);
//                    jfxRippler.setEnabled(true);

                    super.getChildren().add(dayCell);
                    cal.add(DATE, 1);  // number of days to add
                }
            }
            cal.setTime(copy);
        }

        /**
         * Overriden, don't add Children directly
         *
         * @return unmodifieable List
         */
        @Override
        protected ObservableList<Node> getChildren() {
            return FXCollections.unmodifiableObservableList(super.getChildren());
        }

        /**
         * get the current month our calendar displays. Should always give you the
         * correct one, even if some days of other mnths are also displayed
         *
         * @return
         */
        public Date getShownMonth() {
            return cal.getTime();
        }

        @Override
        protected void layoutChildren() {
            ObservableList<Node> children = getChildren();
            double width = getWidth();
            double height = getHeight();

            double cellWidth = (width / (columns + 1));
            double cellHeight = height / (rows + 1);

            for (int i = 0; i < (rows + 1); i++) {
                for (int j = 0; j < (columns + 1); j++) {
                    if (children.size() <= ((i * (columns + 1)) + j)) {
                        break;
                    }
                    Node get = children.get((i * (columns + 1)) + j);
                    layoutInArea(get, j * cellWidth, i * cellHeight, cellWidth, cellHeight, 0.0d, HPos.LEFT, VPos.TOP);
                }

            }
        }
    }
}
