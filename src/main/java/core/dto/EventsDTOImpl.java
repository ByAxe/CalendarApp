/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package core.dto;

import core.api.AEssence;
import core.api.IEssence;
import core.dto.api.IEventsDTO;
import core.enums.Frequency;
import core.enums.NoticePeriod;
import core.enums.Priority;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import static core.commons.Utils.*;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by byaxe on 26.11.16.
 */
public class EventsDTOImpl extends AEssence implements IEventsDTO {

    private LocalDateTime starts;
    private LocalDateTime ends;
    private String title;
    private NoticePeriod noticePeriod;
    private Priority priority;
    private Frequency frequency;

    public EventsDTOImpl() {
    }

    public EventsDTOImpl(IEssence essence) {
        super(essence);
    }

    public EventsDTOImpl(IEventsDTO event) {
        if (event == null) return;

        this.id = event.getId();
        this.starts = event.getStarts();
        this.ends = event.getEnds();
        this.title = event.getTitle();
        this.noticePeriod = event.getNoticePeriod();
        this.priority = event.getPriority();
        this.frequency = event.getFrequency();

    }

    public EventsDTOImpl(Long id, LocalDateTime starts, LocalDateTime ends, String title,
                         NoticePeriod noticePeriod, Priority priority, Frequency frequency) {
        this.id = id;
        this.starts = starts;
        this.ends = ends;
        this.title = title;
        this.noticePeriod = noticePeriod;
        this.priority = priority;
        this.frequency = frequency;
    }

    @Override
    public String toPrettyString() {
        final long daysBetween = now().until(starts, DAYS);

        String today = now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("ru")).toUpperCase();
        String dayOfWeek = starts.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("ru")).toUpperCase();

        if (dayOfWeek.equals(today)) dayOfWeek = TODAY;
        else if (daysBetween == 1) dayOfWeek = TOMORROW;

        return title + ", " + dayOfWeek
                + ((!dayOfWeek.equals(TODAY) && !dayOfWeek.equals(TOMORROW)) ? ", через " + daysBetween + "д." : "")
                + "\n" + starts.format(PRETTY_TIME_FORMATTER) + " - " + ends.format(PRETTY_TIME_FORMATTER);
    }

    public LocalDateTime getStarts() {
        return starts;
    }

    public void setStarts(LocalDateTime starts) {
        this.starts = starts;
    }

    public LocalDateTime getEnds() {
        return ends;
    }

    public void setEnds(LocalDateTime ends) {
        this.ends = ends;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NoticePeriod getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(NoticePeriod noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }
}
