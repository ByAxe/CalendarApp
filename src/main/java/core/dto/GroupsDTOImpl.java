package core.dto;

import core.api.AEssence;
import core.api.IEssence;
import core.dto.api.IGroupsDTO;

/**
 * Created by byaxe on 26.11.16.
 */
public class GroupsDTOImpl extends AEssence implements IGroupsDTO {

    private String title;
    private String specialization;
    private String description;
    private String number;
    private Integer hours;

    public GroupsDTOImpl() {
    }

    public GroupsDTOImpl(IEssence essence) {
        super(essence);
    }

    public GroupsDTOImpl(IGroupsDTO essence) {
        if (essence != null) {
            this.title = essence.getTitle();
            this.specialization = essence.getSpecialization();
            this.description = essence.getDescription();
            this.number = essence.getNumber();
            this.hours = essence.getHours();
        }
    }

    public GroupsDTOImpl(String title, String specialization, String description, String number, Integer hours) {
        this.title = title;
        this.specialization = specialization;
        this.description = description;
        this.number = number;
        this.hours = hours;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }
}
