package core.dto.api;

import core.api.IEssence;
import core.enums.Frequency;
import core.enums.NoticePeriod;
import core.enums.Priority;

import java.util.Date;

/**
 * Created by byaxe on 26.11.16.
 */
public interface IEventsDTO extends IEssence {

    Date getStarts();

    void setStarts(Date starts);

    Date getEnds();

    void setEnds(Date ends);

    String getTitle();

    void setTitle(String title);

    NoticePeriod getNoticePeriod();

    void setNoticePeriod(NoticePeriod noticePeriod);

    Priority getPriority();

    void setPriority(Priority priority);

    Frequency getFrequency();

    void setFrequency(Frequency frequency);

}
