package de.ginisolutions.trader.account.service;

import de.ginisolutions.trader.account.domain.KeySet;
import de.ginisolutions.trader.account.repository.KeySetRepository;
import de.ginisolutions.trader.account.service.dto.KeySetDTO;
import de.ginisolutions.trader.account.service.mapper.KeySetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link KeySet}.
 */
@Service
public class KeySetService {

    private final Logger log = LoggerFactory.getLogger(KeySetService.class);

    private final KeySetRepository keySetRepository;

    private final KeySetMapper keySetMapper;

    public KeySetService(KeySetRepository keySetRepository, KeySetMapper keySetMapper) {
        this.keySetRepository = keySetRepository;
        this.keySetMapper = keySetMapper;
    }

    /**
     * Save a keySet.
     *
     * @param keySetDTO the entity to save.
     * @return the persisted entity.
     */
    public KeySetDTO save(KeySetDTO keySetDTO) {
        log.debug("Request to save KeySet : {}", keySetDTO);
        KeySet keySet = keySetMapper.toEntity(keySetDTO);
        keySet = keySetRepository.save(keySet);
        return keySetMapper.toDto(keySet);
    }

    /**
     * Get all the keySets.
     *
     * @return the list of entities.
     */
    public List<KeySetDTO> findAll() {
        log.debug("Request to get all KeySets");
        return keySetRepository.findAll().stream()
            .map(keySetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one keySet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<KeySetDTO> findOne(String id) {
        log.debug("Request to get KeySet : {}", id);
        return keySetRepository.findById(id)
            .map(keySetMapper::toDto);
    }

    /**
     * Delete the keySet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete KeySet : {}", id);

        keySetRepository.deleteById(id);
    }
}
