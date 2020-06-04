package de.ginisolutions.trader.account.web.rest;

import de.ginisolutions.trader.account.service.KeySetService;
import de.ginisolutions.trader.account.web.rest.errors.BadRequestAlertException;
import de.ginisolutions.trader.account.service.dto.KeySetDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.ginisolutions.trader.account.domain.KeySet}.
 */
@RestController
@RequestMapping("/api")
public class KeySetResource {

    private final Logger log = LoggerFactory.getLogger(KeySetResource.class);

    private static final String ENTITY_NAME = "traderServiceAccountKeySet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KeySetService keySetService;

    public KeySetResource(KeySetService keySetService) {
        this.keySetService = keySetService;
    }

    /**
     * {@code POST  /key-sets} : Create a new keySet.
     *
     * @param keySetDTO the keySetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keySetDTO, or with status {@code 400 (Bad Request)} if the keySet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/key-sets")
    public ResponseEntity<KeySetDTO> createKeySet(@Valid @RequestBody KeySetDTO keySetDTO) throws URISyntaxException {
        log.debug("REST request to save KeySet : {}", keySetDTO);
        if (keySetDTO.getId() != null) {
            throw new BadRequestAlertException("A new keySet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KeySetDTO result = keySetService.save(keySetDTO);
        return ResponseEntity.created(new URI("/api/key-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /key-sets} : Updates an existing keySet.
     *
     * @param keySetDTO the keySetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keySetDTO,
     * or with status {@code 400 (Bad Request)} if the keySetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the keySetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/key-sets")
    public ResponseEntity<KeySetDTO> updateKeySet(@Valid @RequestBody KeySetDTO keySetDTO) throws URISyntaxException {
        log.debug("REST request to update KeySet : {}", keySetDTO);
        if (keySetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KeySetDTO result = keySetService.save(keySetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, keySetDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /key-sets} : get all the keySets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keySets in body.
     */
    @GetMapping("/key-sets")
    public List<KeySetDTO> getAllKeySets() {
        log.debug("REST request to get all KeySets");
        return keySetService.findAll();
    }

    /**
     * {@code GET  /key-sets/:id} : get the "id" keySet.
     *
     * @param id the id of the keySetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keySetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/key-sets/{id}")
    public ResponseEntity<KeySetDTO> getKeySet(@PathVariable String id) {
        log.debug("REST request to get KeySet : {}", id);
        Optional<KeySetDTO> keySetDTO = keySetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(keySetDTO);
    }

    /**
     * {@code DELETE  /key-sets/:id} : delete the "id" keySet.
     *
     * @param id the id of the keySetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/key-sets/{id}")
    public ResponseEntity<Void> deleteKeySet(@PathVariable String id) {
        log.debug("REST request to delete KeySet : {}", id);

        keySetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
