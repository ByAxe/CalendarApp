package core.dto.api;

import core.api.IEssence;

/**
 * Created by byaxe on 26.11.16.
 */
public interface IGroupsDTO extends IEssence {

    String getTitle();

    void setTitle(String title);

    String getSpecialization();

    void setSpecialization(String specialization);

    String getDescription();

    void setDescription(String description);

    String getNumber();

    void setNumber(String number);

    Integer getHours();

    void setHours(Integer hours);
}
