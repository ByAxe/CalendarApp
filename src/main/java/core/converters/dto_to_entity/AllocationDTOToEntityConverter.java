package core.converters.dto_to_entity;

import core.dto.api.IAllocationDTO;
import model.entity.AllocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static core.commons.Utils.convertLocalDateTimeToDate;

/**
 * Created by byaxe on 18.12.16.
 */
@Component
public class AllocationDTOToEntityConverter implements Converter<IAllocationDTO, AllocationEntity> {

    private final GroupsDTOToEntityConverter groupsDTOToEntityConverter;
    private final OrganisationsDTOToEntityConverter organisationsDTOToEntityConverter;
    private final StudentsDTOToEntityConverter studentsDTOToEntityConverter;
    private final OrdersDTOToEntityConverter ordersDTOToEntityConverter;

    @Autowired
    public AllocationDTOToEntityConverter(GroupsDTOToEntityConverter groupsDTOToEntityConverter, OrganisationsDTOToEntityConverter organisationsDTOToEntityConverter,
                                          StudentsDTOToEntityConverter studentsDTOToEntityConverter, OrdersDTOToEntityConverter ordersDTOToEntityConverter) {
        this.groupsDTOToEntityConverter = groupsDTOToEntityConverter;
        this.organisationsDTOToEntityConverter = organisationsDTOToEntityConverter;
        this.studentsDTOToEntityConverter = studentsDTOToEntityConverter;
        this.ordersDTOToEntityConverter = ordersDTOToEntityConverter;
    }

    @Override
    public AllocationEntity convert(IAllocationDTO source) {
        if (source == null) return null;

        AllocationEntity target = new AllocationEntity();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setParentUuid(source.getParentUuid());

        target.setArmy(source.isArmy());
        target.setFreeAllocation(source.isFreeAllocation());
        target.setVoluntaryCompensation(source.isVoluntaryCompensation());

        target.setStage(source.getStage());
        target.setCortOrderNumber(source.getCortOrderNumber());
        target.setVoluntaryCompensationOrderNumber(source.getVoluntaryCompensationOrderNumber());

        target.setVoluntaryCompensationOrderDate(convertLocalDateTimeToDate(source.getVoluntaryCompensationOrderDate()));
        target.setVoluntaryCompensationConfirmationDate(convertLocalDateTimeToDate(source.getVoluntaryCompensationConfirmationDate()));
        target.setCortOrderDate(convertLocalDateTimeToDate(source.getCortOrderDate()));

        target.setConfirmations(source.getConfirmations());

        target.setGroup(groupsDTOToEntityConverter.convert(source.getGroup()));
        target.setOrganisation(organisationsDTOToEntityConverter.convert(source.getOrganisation()));
        target.setStudent(studentsDTOToEntityConverter.convert(source.getStudent()));
        target.setOrder(ordersDTOToEntityConverter.convert(source.getOrder()));

        return target;
    }
}
