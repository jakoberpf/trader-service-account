package de.ginisolutions.trader.account.service;

import de.ginisolutions.trader.account.domain.UserAccount;
import de.ginisolutions.trader.account.repository.UserAccountRepository;
import de.ginisolutions.trader.account.service.dto.UserAccountDTO;
import de.ginisolutions.trader.account.service.mapper.UserAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserAccount}.
 */
@Service
public class UserAccountService {

    private final Logger log = LoggerFactory.getLogger(UserAccountService.class);

    private final UserAccountRepository userAccountRepository;

    private final UserAccountMapper userAccountMapper;

    public UserAccountService(UserAccountRepository userAccountRepository, UserAccountMapper userAccountMapper) {
        this.userAccountRepository = userAccountRepository;
        this.userAccountMapper = userAccountMapper;
    }

    /**
     * Save a userAccount.
     *
     * @param userAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public UserAccountDTO save(UserAccountDTO userAccountDTO) {
        log.debug("Request to save UserAccount : {}", userAccountDTO);
        UserAccount userAccount = userAccountMapper.toEntity(userAccountDTO);
        userAccount = userAccountRepository.save(userAccount);
        return userAccountMapper.toDto(userAccount);
    }

    /**
     * Get all the userAccounts.
     *
     * @return the list of entities.
     */
    public List<UserAccountDTO> findAll() {
        log.debug("Request to get all UserAccounts");
        return userAccountRepository.findAll().stream()
            .map(userAccountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one userAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<UserAccountDTO> findOne(String id) {
        log.debug("Request to get UserAccount : {}", id);
        return userAccountRepository.findById(id)
            .map(userAccountMapper::toDto);
    }

    /**
     * Delete the userAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete UserAccount : {}", id);

        userAccountRepository.deleteById(id);
    }
}
