/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package core.dto;

import core.api.AEssence;
import core.api.IEssence;
import core.dto.api.IOrdersDTO;

import java.time.LocalDateTime;

/**
 * Created by byaxe on 18.12.16.
 */
public class OrdersDTOImpl extends AEssence implements IOrdersDTO {

    private String profession;
    private Integer rank;
    private LocalDateTime starts;
    private String number;
    private String payload;

    public OrdersDTOImpl() {
    }

    public OrdersDTOImpl(IEssence essence) {
        super(essence);
    }

    public OrdersDTOImpl(Long id, String profession, Integer rank, LocalDateTime starts, String number, String payload) {
        this.id = id;
        this.profession = profession;
        this.rank = rank;
        this.starts = starts;
        this.number = number;
        this.payload = payload;
    }

    public OrdersDTOImpl(IOrdersDTO essence) {
        if (essence == null) return;

        this.id = essence.getId();
        this.uuid = essence.getUuid();
        this.dtUpdate = essence.getDtUpdate();

        this.profession = essence.getProfession();
        this.rank = essence.getRank();
        this.starts = essence.getStarts();
        this.number = essence.getNumber();
        this.payload = essence.getPayload();

    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public LocalDateTime getStarts() {
        return starts;
    }

    public void setStarts(LocalDateTime starts) {
        this.starts = starts;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
