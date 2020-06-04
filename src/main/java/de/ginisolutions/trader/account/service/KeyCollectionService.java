package de.ginisolutions.trader.account.service;

import de.ginisolutions.trader.account.domain.KeyCollection;
import de.ginisolutions.trader.account.repository.KeyCollectionRepository;
import de.ginisolutions.trader.account.service.dto.KeyCollectionDTO;
import de.ginisolutions.trader.account.service.mapper.KeyCollectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link KeyCollection}.
 */
@Service
public class KeyCollectionService {

    private final Logger log = LoggerFactory.getLogger(KeyCollectionService.class);

    private final KeyCollectionRepository keyCollectionRepository;

    private final KeyCollectionMapper keyCollectionMapper;

    public KeyCollectionService(KeyCollectionRepository keyCollectionRepository, KeyCollectionMapper keyCollectionMapper) {
        this.keyCollectionRepository = keyCollectionRepository;
        this.keyCollectionMapper = keyCollectionMapper;
    }

    /**
     * Save a keyCollection.
     *
     * @param keyCollectionDTO the entity to save.
     * @return the persisted entity.
     */
    public KeyCollectionDTO save(KeyCollectionDTO keyCollectionDTO) {
        log.debug("Request to save KeyCollection : {}", keyCollectionDTO);
        KeyCollection keyCollection = keyCollectionMapper.toEntity(keyCollectionDTO);
        keyCollection = keyCollectionRepository.save(keyCollection);
        return keyCollectionMapper.toDto(keyCollection);
    }

    /**
     * Get all the keyCollections.
     *
     * @return the list of entities.
     */
    public List<KeyCollectionDTO> findAll() {
        log.debug("Request to get all KeyCollections");
        return keyCollectionRepository.findAll().stream()
            .map(keyCollectionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  Get all the keyCollections where UserAccount is {@code null}.
     *  @return the list of entities.
     */
    public List<KeyCollectionDTO> findAllWhereUserAccountIsNull() {
        log.debug("Request to get all keyCollections where UserAccount is null");
        return StreamSupport
            .stream(keyCollectionRepository.findAll().spliterator(), false)
            .filter(keyCollection -> keyCollection.getUserAccount() == null)
            .map(keyCollectionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one keyCollection by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<KeyCollectionDTO> findOne(String id) {
        log.debug("Request to get KeyCollection : {}", id);
        return keyCollectionRepository.findById(id)
            .map(keyCollectionMapper::toDto);
    }

    /**
     * Delete the keyCollection by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete KeyCollection : {}", id);

        keyCollectionRepository.deleteById(id);
    }
}
