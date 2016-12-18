package core.dto;

import core.api.AEssence;
import core.api.IEssence;
import core.dto.api.IGroupsDTO;
import core.dto.api.IRulersDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by byaxe on 17.12.16.
 */
public class RulersDTOImpl extends AEssence implements IRulersDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String payload;
    private Set<IGroupsDTO> groups = new HashSet<>(1);

    public RulersDTOImpl() {
    }

    public RulersDTOImpl(IEssence essence) {
        super(essence);
    }

    public RulersDTOImpl(String firstName, String middleName, String lastName, String payload, Set<IGroupsDTO> groups) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.payload = payload;
        this.groups = groups;
    }


    public RulersDTOImpl(IRulersDTO rulersDTO) {
        if (rulersDTO != null) {
            this.firstName = rulersDTO.getFirstName();
            this.middleName = rulersDTO.getMiddleName();
            this.lastName = rulersDTO.getLastName();
            this.payload = rulersDTO.getPayload();
            this.groups = rulersDTO.getGroups();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Set<IGroupsDTO> getGroups() {
        return groups;
    }

    public void setGroups(Set<IGroupsDTO> groups) {
        this.groups = groups;
    }
}
