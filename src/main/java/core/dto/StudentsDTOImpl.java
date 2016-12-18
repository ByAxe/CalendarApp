package core.dto;

import core.api.AEssence;
import core.api.IEssence;
import core.dto.api.IStudentsDTO;

/**
 * Created by byaxe on 18.12.16.
 */
public class StudentsDTOImpl extends AEssence implements IStudentsDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String telephone;
    private String address;

    public StudentsDTOImpl() {
    }

    public StudentsDTOImpl(IEssence essence) {
        super(essence);
    }

    public StudentsDTOImpl(Long id, String firstName, String middleName, String lastName, String telephone, String address) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.address = address;
    }

    public StudentsDTOImpl(IStudentsDTO essence) {
        if (essence == null) return;

        this.id = essence.getId();
        this.uuid = essence.getUuid();
        this.dtUpdate = essence.getDtUpdate();

        this.firstName = essence.getFirstName();
        this.middleName = essence.getMiddleName();
        this.lastName = essence.getLastName();
        this.telephone = essence.getTelephone();
        this.address = essence.getAddress();
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
