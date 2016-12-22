/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package core.dto.api;

import core.api.IEssence;
import core.enums.Frequency;
import core.enums.NoticePeriod;
import core.enums.Priority;

import java.time.LocalDateTime;

/**
 * Created by byaxe on 26.11.16.
 */
public interface IEventsDTO extends IEssence {

    LocalDateTime getStarts();

    void setStarts(LocalDateTime starts);

    LocalDateTime getEnds();

    void setEnds(LocalDateTime ends);

    String getTitle();

    void setTitle(String title);

    NoticePeriod getNoticePeriod();

    void setNoticePeriod(NoticePeriod noticePeriod);

    Priority getPriority();

    void setPriority(Priority priority);

    Frequency getFrequency();

    void setFrequency(Frequency frequency);

    String toPrettyString();
}
