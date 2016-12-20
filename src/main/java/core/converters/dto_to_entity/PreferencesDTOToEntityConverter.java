package core.converters.dto_to_entity;

import core.dto.api.IPreferencesDTO;
import model.entity.PreferencesEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by byaxe on 20.12.16.
 */
@Component
public class PreferencesDTOToEntityConverter implements Converter<IPreferencesDTO, PreferencesEntity> {

    @Override
    public PreferencesEntity convert(IPreferencesDTO source) {
        if (source == null) return null;

        PreferencesEntity target = new PreferencesEntity();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setArchiveTerm(source.getArchiveTerm());
        target.setConfirmationNoticeTerm(source.getConfirmationNoticeTerm());
        target.setNotificationsEnabled(source.isNotificationsEnabled());
        target.setArchiveEnabled(source.isArchiveEnabled());
        target.setAllocationEndNoticeTerm(source.getAllocationEndNoticeTerm());

        return target;
    }
}
