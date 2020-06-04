package de.ginisolutions.trader.account.service.mapper;


import de.ginisolutions.trader.account.domain.*;
import de.ginisolutions.trader.account.service.dto.UserAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAccount} and its DTO {@link UserAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {KeyCollectionMapper.class})
public interface UserAccountMapper extends EntityMapper<UserAccountDTO, UserAccount> {

    @Mapping(source = "keyCollection.id", target = "keyCollectionId")
    UserAccountDTO toDto(UserAccount userAccount);

    @Mapping(source = "keyCollectionId", target = "keyCollection")
    UserAccount toEntity(UserAccountDTO userAccountDTO);

    default UserAccount fromId(String id) {
        if (id == null) {
            return null;
        }
        UserAccount userAccount = new UserAccount();
        userAccount.setId(id);
        return userAccount;
    }
}
