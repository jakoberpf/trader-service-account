package de.ginisolutions.trader.account.web.rest;

import de.ginisolutions.trader.account.TraderServiceAccountApp;
import de.ginisolutions.trader.account.config.TestSecurityConfiguration;
import de.ginisolutions.trader.account.domain.UserAccount;
import de.ginisolutions.trader.account.repository.UserAccountRepository;
import de.ginisolutions.trader.account.service.UserAccountService;
import de.ginisolutions.trader.account.service.dto.UserAccountDTO;
import de.ginisolutions.trader.account.service.mapper.UserAccountMapper;

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
 * Integration tests for the {@link UserAccountResource} REST controller.
 */
@SpringBootTest(classes = { TraderServiceAccountApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserAccountResourceIT {

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private MockMvc restUserAccountMockMvc;

    private UserAccount userAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAccount createEntity() {
        UserAccount userAccount = new UserAccount()
            .owner(DEFAULT_OWNER);
        return userAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAccount createUpdatedEntity() {
        UserAccount userAccount = new UserAccount()
            .owner(UPDATED_OWNER);
        return userAccount;
    }

    @BeforeEach
    public void initTest() {
        userAccountRepository.deleteAll();
        userAccount = createEntity();
    }

    @Test
    public void createUserAccount() throws Exception {
        int databaseSizeBeforeCreate = userAccountRepository.findAll().size();
        // Create the UserAccount
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);
        restUserAccountMockMvc.perform(post("/api/user-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeCreate + 1);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getOwner()).isEqualTo(DEFAULT_OWNER);
    }

    @Test
    public void createUserAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAccountRepository.findAll().size();

        // Create the UserAccount with an existing ID
        userAccount.setId("existing_id");
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAccountMockMvc.perform(post("/api/user-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkOwnerIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAccountRepository.findAll().size();
        // set the field null
        userAccount.setOwner(null);

        // Create the UserAccount, which fails.
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);


        restUserAccountMockMvc.perform(post("/api/user-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isBadRequest());

        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllUserAccounts() throws Exception {
        // Initialize the database
        userAccountRepository.save(userAccount);

        // Get all the userAccountList
        restUserAccountMockMvc.perform(get("/api/user-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAccount.getId())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)));
    }
    
    @Test
    public void getUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.save(userAccount);

        // Get the userAccount
        restUserAccountMockMvc.perform(get("/api/user-accounts/{id}", userAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAccount.getId()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER));
    }
    @Test
    public void getNonExistingUserAccount() throws Exception {
        // Get the userAccount
        restUserAccountMockMvc.perform(get("/api/user-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.save(userAccount);

        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();

        // Update the userAccount
        UserAccount updatedUserAccount = userAccountRepository.findById(userAccount.getId()).get();
        updatedUserAccount
            .owner(UPDATED_OWNER);
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(updatedUserAccount);

        restUserAccountMockMvc.perform(put("/api/user-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isOk());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getOwner()).isEqualTo(UPDATED_OWNER);
    }

    @Test
    public void updateNonExistingUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();

        // Create the UserAccount
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAccountMockMvc.perform(put("/api/user-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.save(userAccount);

        int databaseSizeBeforeDelete = userAccountRepository.findAll().size();

        // Delete the userAccount
        restUserAccountMockMvc.perform(delete("/api/user-accounts/{id}", userAccount.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
