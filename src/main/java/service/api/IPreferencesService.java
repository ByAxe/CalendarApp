package service.api;

import core.dto.api.IPreferencesDTO;
import model.entity.PreferencesEntity;

/**
 * Created by byaxe on 20.12.16.
 */
public interface IPreferencesService extends IEssenceService<IPreferencesDTO, Long>,
        IConversionService<IPreferencesDTO, PreferencesEntity> {

    IPreferencesDTO get();
}
