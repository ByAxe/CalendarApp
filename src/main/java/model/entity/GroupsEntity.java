package model.entity;

import model.api.APersistentEntity;

import javax.persistence.*;

import static core.api.IEssence.ID_COLUMN_NAME;
import static core.api.IEssence.UUID_COLUMN_NAME;

/**
 * Created by byaxe on 26.11.16.
 */
@Entity
@Table(name = "groups", uniqueConstraints = {
        @UniqueConstraint(columnNames = ID_COLUMN_NAME),
        @UniqueConstraint(columnNames = UUID_COLUMN_NAME)
}, indexes = {
        @Index(name = "groups_id_idx", unique = true, columnList = ID_COLUMN_NAME),
        @Index(name = "groups_uuid_idx", unique = true, columnList = UUID_COLUMN_NAME)
})
@SequenceGenerator(name = "groups_id_seq", sequenceName = "groups_id_seq", allocationSize = 1)
public class GroupsEntity extends APersistentEntity {

    private Long id;
    private String title;
    private String specialization;
    private String description;
    private String number;
    private Integer hours;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_id_seq")
    @Column(name = ID_COLUMN_NAME, unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "title", nullable = false, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "specialization")
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Column(name = "description", length = 1000, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "number", nullable = false, length = 10)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(name = "hours")
    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }
}
