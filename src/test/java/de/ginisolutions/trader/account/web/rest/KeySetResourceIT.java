package de.ginisolutions.trader.account.web.rest;

import de.ginisolutions.trader.account.TraderServiceAccountApp;
import de.ginisolutions.trader.account.config.TestSecurityConfiguration;
import de.ginisolutions.trader.account.domain.KeySet;
import de.ginisolutions.trader.account.repository.KeySetRepository;
import de.ginisolutions.trader.account.service.KeySetService;
import de.ginisolutions.trader.account.service.dto.KeySetDTO;
import de.ginisolutions.trader.account.service.mapper.KeySetMapper;

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

import de.ginisolutions.trader.account.domain.enumeration.MARKET;
/**
 * Integration tests for the {@link KeySetResource} REST controller.
 */
@SpringBootTest(classes = { TraderServiceAccountApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class KeySetResourceIT {

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final MARKET DEFAULT_MARKET = MARKET.SAMPLE_ENUM;
    private static final MARKET UPDATED_MARKET = MARKET.SAMPLE_ENUM;

    private static final String DEFAULT_API_KEY = "AAAAAAAAAA";
    private static final String UPDATED_API_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_API_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_API_SECRET = "BBBBBBBBBB";

    @Autowired
    private KeySetRepository keySetRepository;

    @Autowired
    private KeySetMapper keySetMapper;

    @Autowired
    private KeySetService keySetService;

    @Autowired
    private MockMvc restKeySetMockMvc;

    private KeySet keySet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeySet createEntity() {
        KeySet keySet = new KeySet()
            .owner(DEFAULT_OWNER)
            .market(DEFAULT_MARKET)
            .apiKey(DEFAULT_API_KEY)
            .apiSecret(DEFAULT_API_SECRET);
        return keySet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeySet createUpdatedEntity() {
        KeySet keySet = new KeySet()
            .owner(UPDATED_OWNER)
            .market(UPDATED_MARKET)
            .apiKey(UPDATED_API_KEY)
            .apiSecret(UPDATED_API_SECRET);
        return keySet;
    }

    @BeforeEach
    public void initTest() {
        keySetRepository.deleteAll();
        keySet = createEntity();
    }

    @Test
    public void createKeySet() throws Exception {
        int databaseSizeBeforeCreate = keySetRepository.findAll().size();
        // Create the KeySet
        KeySetDTO keySetDTO = keySetMapper.toDto(keySet);
        restKeySetMockMvc.perform(post("/api/key-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keySetDTO)))
            .andExpect(status().isCreated());

        // Validate the KeySet in the database
        List<KeySet> keySetList = keySetRepository.findAll();
        assertThat(keySetList).hasSize(databaseSizeBeforeCreate + 1);
        KeySet testKeySet = keySetList.get(keySetList.size() - 1);
        assertThat(testKeySet.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testKeySet.getMarket()).isEqualTo(DEFAULT_MARKET);
        assertThat(testKeySet.getApiKey()).isEqualTo(DEFAULT_API_KEY);
        assertThat(testKeySet.getApiSecret()).isEqualTo(DEFAULT_API_SECRET);
    }

    @Test
    public void createKeySetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = keySetRepository.findAll().size();

        // Create the KeySet with an existing ID
        keySet.setId("existing_id");
        KeySetDTO keySetDTO = keySetMapper.toDto(keySet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeySetMockMvc.perform(post("/api/key-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keySetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KeySet in the database
        List<KeySet> keySetList = keySetRepository.findAll();
        assertThat(keySetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkOwnerIsRequired() throws Exception {
        int databaseSizeBeforeTest = keySetRepository.findAll().size();
        // set the field null
        keySet.setOwner(null);

        // Create the KeySet, which fails.
        KeySetDTO keySetDTO = keySetMapper.toDto(keySet);


        restKeySetMockMvc.perform(post("/api/key-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keySetDTO)))
            .andExpect(status().isBadRequest());

        List<KeySet> keySetList = keySetRepository.findAll();
        assertThat(keySetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMarketIsRequired() throws Exception {
        int databaseSizeBeforeTest = keySetRepository.findAll().size();
        // set the field null
        keySet.setMarket(null);

        // Create the KeySet, which fails.
        KeySetDTO keySetDTO = keySetMapper.toDto(keySet);


        restKeySetMockMvc.perform(post("/api/key-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keySetDTO)))
            .andExpect(status().isBadRequest());

        List<KeySet> keySetList = keySetRepository.findAll();
        assertThat(keySetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkApiKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = keySetRepository.findAll().size();
        // set the field null
        keySet.setApiKey(null);

        // Create the KeySet, which fails.
        KeySetDTO keySetDTO = keySetMapper.toDto(keySet);


        restKeySetMockMvc.perform(post("/api/key-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keySetDTO)))
            .andExpect(status().isBadRequest());

        List<KeySet> keySetList = keySetRepository.findAll();
        assertThat(keySetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkApiSecretIsRequired() throws Exception {
        int databaseSizeBeforeTest = keySetRepository.findAll().size();
        // set the field null
        keySet.setApiSecret(null);

        // Create the KeySet, which fails.
        KeySetDTO keySetDTO = keySetMapper.toDto(keySet);


        restKeySetMockMvc.perform(post("/api/key-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keySetDTO)))
            .andExpect(status().isBadRequest());

        List<KeySet> keySetList = keySetRepository.findAll();
        assertThat(keySetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllKeySets() throws Exception {
        // Initialize the database
        keySetRepository.save(keySet);

        // Get all the keySetList
        restKeySetMockMvc.perform(get("/api/key-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keySet.getId())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
            .andExpect(jsonPath("$.[*].market").value(hasItem(DEFAULT_MARKET.toString())))
            .andExpect(jsonPath("$.[*].apiKey").value(hasItem(DEFAULT_API_KEY)))
            .andExpect(jsonPath("$.[*].apiSecret").value(hasItem(DEFAULT_API_SECRET)));
    }
    
    @Test
    public void getKeySet() throws Exception {
        // Initialize the database
        keySetRepository.save(keySet);

        // Get the keySet
        restKeySetMockMvc.perform(get("/api/key-sets/{id}", keySet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(keySet.getId()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.market").value(DEFAULT_MARKET.toString()))
            .andExpect(jsonPath("$.apiKey").value(DEFAULT_API_KEY))
            .andExpect(jsonPath("$.apiSecret").value(DEFAULT_API_SECRET));
    }
    @Test
    public void getNonExistingKeySet() throws Exception {
        // Get the keySet
        restKeySetMockMvc.perform(get("/api/key-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateKeySet() throws Exception {
        // Initialize the database
        keySetRepository.save(keySet);

        int databaseSizeBeforeUpdate = keySetRepository.findAll().size();

        // Update the keySet
        KeySet updatedKeySet = keySetRepository.findById(keySet.getId()).get();
        updatedKeySet
            .owner(UPDATED_OWNER)
            .market(UPDATED_MARKET)
            .apiKey(UPDATED_API_KEY)
            .apiSecret(UPDATED_API_SECRET);
        KeySetDTO keySetDTO = keySetMapper.toDto(updatedKeySet);

        restKeySetMockMvc.perform(put("/api/key-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keySetDTO)))
            .andExpect(status().isOk());

        // Validate the KeySet in the database
        List<KeySet> keySetList = keySetRepository.findAll();
        assertThat(keySetList).hasSize(databaseSizeBeforeUpdate);
        KeySet testKeySet = keySetList.get(keySetList.size() - 1);
        assertThat(testKeySet.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testKeySet.getMarket()).isEqualTo(UPDATED_MARKET);
        assertThat(testKeySet.getApiKey()).isEqualTo(UPDATED_API_KEY);
        assertThat(testKeySet.getApiSecret()).isEqualTo(UPDATED_API_SECRET);
    }

    @Test
    public void updateNonExistingKeySet() throws Exception {
        int databaseSizeBeforeUpdate = keySetRepository.findAll().size();

        // Create the KeySet
        KeySetDTO keySetDTO = keySetMapper.toDto(keySet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeySetMockMvc.perform(put("/api/key-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keySetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KeySet in the database
        List<KeySet> keySetList = keySetRepository.findAll();
        assertThat(keySetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteKeySet() throws Exception {
        // Initialize the database
        keySetRepository.save(keySet);

        int databaseSizeBeforeDelete = keySetRepository.findAll().size();

        // Delete the keySet
        restKeySetMockMvc.perform(delete("/api/key-sets/{id}", keySet.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KeySet> keySetList = keySetRepository.findAll();
        assertThat(keySetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
