package de.ginisolutions.trader.account.service.mapper;


import de.ginisolutions.trader.account.domain.*;
import de.ginisolutions.trader.account.service.dto.KeySetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link KeySet} and its DTO {@link KeySetDTO}.
 */
@Mapper(componentModel = "spring", uses = {KeyCollectionMapper.class})
public interface KeySetMapper extends EntityMapper<KeySetDTO, KeySet> {

    @Mapping(source = "keyCollection.id", target = "keyCollectionId")
    KeySetDTO toDto(KeySet keySet);

    @Mapping(source = "keyCollectionId", target = "keyCollection")
    KeySet toEntity(KeySetDTO keySetDTO);

    default KeySet fromId(String id) {
        if (id == null) {
            return null;
        }
        KeySet keySet = new KeySet();
        keySet.setId(id);
        return keySet;
    }
}
