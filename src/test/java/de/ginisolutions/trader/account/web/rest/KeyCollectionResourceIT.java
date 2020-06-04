package de.ginisolutions.trader.account.web.rest;

import de.ginisolutions.trader.account.TraderServiceAccountApp;
import de.ginisolutions.trader.account.config.TestSecurityConfiguration;
import de.ginisolutions.trader.account.domain.KeyCollection;
import de.ginisolutions.trader.account.repository.KeyCollectionRepository;
import de.ginisolutions.trader.account.service.KeyCollectionService;
import de.ginisolutions.trader.account.service.dto.KeyCollectionDTO;
import de.ginisolutions.trader.account.service.mapper.KeyCollectionMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link KeyCollectionResource} REST controller.
 */
@SpringBootTest(classes = { TraderServiceAccountApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class KeyCollectionResourceIT {

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    @Autowired
    private KeyCollectionRepository keyCollectionRepository;

    @Autowired
    private KeyCollectionMapper keyCollectionMapper;

    @Autowired
    private KeyCollectionService keyCollectionService;

    @Autowired
    private MockMvc restKeyCollectionMockMvc;

    private KeyCollection keyCollection;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeyCollection createEntity() {
        KeyCollection keyCollection = new KeyCollection()
            .owner(DEFAULT_OWNER);
        return keyCollection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeyCollection createUpdatedEntity() {
        KeyCollection keyCollection = new KeyCollection()
            .owner(UPDATED_OWNER);
        return keyCollection;
    }

    @BeforeEach
    public void initTest() {
        keyCollectionRepository.deleteAll();
        keyCollection = createEntity();
    }

    @Test
    public void createKeyCollection() throws Exception {
        int databaseSizeBeforeCreate = keyCollectionRepository.findAll().size();
        // Create the KeyCollection
        KeyCollectionDTO keyCollectionDTO = keyCollectionMapper.toDto(keyCollection);
        restKeyCollectionMockMvc.perform(post("/api/key-collections").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyCollectionDTO)))
            .andExpect(status().isCreated());

        // Validate the KeyCollection in the database
        List<KeyCollection> keyCollectionList = keyCollectionRepository.findAll();
        assertThat(keyCollectionList).hasSize(databaseSizeBeforeCreate + 1);
        KeyCollection testKeyCollection = keyCollectionList.get(keyCollectionList.size() - 1);
        assertThat(testKeyCollection.getOwner()).isEqualTo(DEFAULT_OWNER);
    }

    @Test
    public void createKeyCollectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = keyCollectionRepository.findAll().size();

        // Create the KeyCollection with an existing ID
        keyCollection.setId("existing_id");
        KeyCollectionDTO keyCollectionDTO = keyCollectionMapper.toDto(keyCollection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeyCollectionMockMvc.perform(post("/api/key-collections").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyCollectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KeyCollection in the database
        List<KeyCollection> keyCollectionList = keyCollectionRepository.findAll();
        assertThat(keyCollectionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkOwnerIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyCollectionRepository.findAll().size();
        // set the field null
        keyCollection.setOwner(null);

        // Create the KeyCollection, which fails.
        KeyCollectionDTO keyCollectionDTO = keyCollectionMapper.toDto(keyCollection);


        restKeyCollectionMockMvc.perform(post("/api/key-collections").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyCollectionDTO)))
            .andExpect(status().isBadRequest());

        List<KeyCollection> keyCollectionList = keyCollectionRepository.findAll();
        assertThat(keyCollectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllKeyCollections() throws Exception {
        // Initialize the database
        keyCollectionRepository.save(keyCollection);

        // Get all the keyCollectionList
        restKeyCollectionMockMvc.perform(get("/api/key-collections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keyCollection.getId())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)));
    }
    
    @Test
    public void getKeyCollection() throws Exception {
        // Initialize the database
        keyCollectionRepository.save(keyCollection);

        // Get the keyCollection
        restKeyCollectionMockMvc.perform(get("/api/key-collections/{id}", keyCollection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(keyCollection.getId()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER));
    }
    @Test
    public void getNonExistingKeyCollection() throws Exception {
        // Get the keyCollection
        restKeyCollectionMockMvc.perform(get("/api/key-collections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateKeyCollection() throws Exception {
        // Initialize the database
        keyCollectionRepository.save(keyCollection);

        int databaseSizeBeforeUpdate = keyCollectionRepository.findAll().size();

        // Update the keyCollection
        KeyCollection updatedKeyCollection = keyCollectionRepository.findById(keyCollection.getId()).get();
        updatedKeyCollection
            .owner(UPDATED_OWNER);
        KeyCollectionDTO keyCollectionDTO = keyCollectionMapper.toDto(updatedKeyCollection);

        restKeyCollectionMockMvc.perform(put("/api/key-collections").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyCollectionDTO)))
            .andExpect(status().isOk());

        // Validate the KeyCollection in the database
        List<KeyCollection> keyCollectionList = keyCollectionRepository.findAll();
        assertThat(keyCollectionList).hasSize(databaseSizeBeforeUpdate);
        KeyCollection testKeyCollection = keyCollectionList.get(keyCollectionList.size() - 1);
        assertThat(testKeyCollection.getOwner()).isEqualTo(UPDATED_OWNER);
    }

    @Test
    public void updateNonExistingKeyCollection() throws Exception {
        int databaseSizeBeforeUpdate = keyCollectionRepository.findAll().size();

        // Create the KeyCollection
        KeyCollectionDTO keyCollectionDTO = keyCollectionMapper.toDto(keyCollection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeyCollectionMockMvc.perform(put("/api/key-collections").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyCollectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KeyCollection in the database
        List<KeyCollection> keyCollectionList = keyCollectionRepository.findAll();
        assertThat(keyCollectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteKeyCollection() throws Exception {
        // Initialize the database
        keyCollectionRepository.save(keyCollection);

        int databaseSizeBeforeDelete = keyCollectionRepository.findAll().size();

        // Delete the keyCollection
        restKeyCollectionMockMvc.perform(delete("/api/key-collections/{id}", keyCollection.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KeyCollection> keyCollectionList = keyCollectionRepository.findAll();
        assertThat(keyCollectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
