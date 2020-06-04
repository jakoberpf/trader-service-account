package de.ginisolutions.trader.account.service.mapper;


import de.ginisolutions.trader.account.domain.*;
import de.ginisolutions.trader.account.service.dto.KeyCollectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link KeyCollection} and its DTO {@link KeyCollectionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KeyCollectionMapper extends EntityMapper<KeyCollectionDTO, KeyCollection> {


    @Mapping(target = "keySets", ignore = true)
    @Mapping(target = "removeKeySet", ignore = true)
    @Mapping(target = "userAccount", ignore = true)
    KeyCollection toEntity(KeyCollectionDTO keyCollectionDTO);

    default KeyCollection fromId(String id) {
        if (id == null) {
            return null;
        }
        KeyCollection keyCollection = new KeyCollection();
        keyCollection.setId(id);
        return keyCollection;
    }
}
