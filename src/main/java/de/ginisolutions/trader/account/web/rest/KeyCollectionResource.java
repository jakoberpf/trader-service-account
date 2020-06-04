package de.ginisolutions.trader.account.web.rest;

import de.ginisolutions.trader.account.service.KeyCollectionService;
import de.ginisolutions.trader.account.web.rest.errors.BadRequestAlertException;
import de.ginisolutions.trader.account.service.dto.KeyCollectionDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link de.ginisolutions.trader.account.domain.KeyCollection}.
 */
@RestController
@RequestMapping("/api")
public class KeyCollectionResource {

    private final Logger log = LoggerFactory.getLogger(KeyCollectionResource.class);

    private static final String ENTITY_NAME = "traderServiceAccountKeyCollection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KeyCollectionService keyCollectionService;

    public KeyCollectionResource(KeyCollectionService keyCollectionService) {
        this.keyCollectionService = keyCollectionService;
    }

    /**
     * {@code POST  /key-collections} : Create a new keyCollection.
     *
     * @param keyCollectionDTO the keyCollectionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keyCollectionDTO, or with status {@code 400 (Bad Request)} if the keyCollection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/key-collections")
    public ResponseEntity<KeyCollectionDTO> createKeyCollection(@Valid @RequestBody KeyCollectionDTO keyCollectionDTO) throws URISyntaxException {
        log.debug("REST request to save KeyCollection : {}", keyCollectionDTO);
        if (keyCollectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new keyCollection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KeyCollectionDTO result = keyCollectionService.save(keyCollectionDTO);
        return ResponseEntity.created(new URI("/api/key-collections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /key-collections} : Updates an existing keyCollection.
     *
     * @param keyCollectionDTO the keyCollectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keyCollectionDTO,
     * or with status {@code 400 (Bad Request)} if the keyCollectionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the keyCollectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/key-collections")
    public ResponseEntity<KeyCollectionDTO> updateKeyCollection(@Valid @RequestBody KeyCollectionDTO keyCollectionDTO) throws URISyntaxException {
        log.debug("REST request to update KeyCollection : {}", keyCollectionDTO);
        if (keyCollectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KeyCollectionDTO result = keyCollectionService.save(keyCollectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, keyCollectionDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /key-collections} : get all the keyCollections.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keyCollections in body.
     */
    @GetMapping("/key-collections")
    public List<KeyCollectionDTO> getAllKeyCollections(@RequestParam(required = false) String filter) {
        if ("useraccount-is-null".equals(filter)) {
            log.debug("REST request to get all KeyCollections where userAccount is null");
            return keyCollectionService.findAllWhereUserAccountIsNull();
        }
        log.debug("REST request to get all KeyCollections");
        return keyCollectionService.findAll();
    }

    /**
     * {@code GET  /key-collections/:id} : get the "id" keyCollection.
     *
     * @param id the id of the keyCollectionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keyCollectionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/key-collections/{id}")
    public ResponseEntity<KeyCollectionDTO> getKeyCollection(@PathVariable String id) {
        log.debug("REST request to get KeyCollection : {}", id);
        Optional<KeyCollectionDTO> keyCollectionDTO = keyCollectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(keyCollectionDTO);
    }

    /**
     * {@code DELETE  /key-collections/:id} : delete the "id" keyCollection.
     *
     * @param id the id of the keyCollectionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/key-collections/{id}")
    public ResponseEntity<Void> deleteKeyCollection(@PathVariable String id) {
        log.debug("REST request to delete KeyCollection : {}", id);

        keyCollectionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
