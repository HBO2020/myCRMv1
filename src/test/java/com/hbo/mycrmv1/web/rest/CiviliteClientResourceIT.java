package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.CiviliteClient;
import com.hbo.mycrmv1.repository.CiviliteClientRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CiviliteClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CiviliteClientResourceIT {

    private static final String DEFAULT_CIVILITE_LIBELLE_CL = "AAAAAAAAAA";
    private static final String UPDATED_CIVILITE_LIBELLE_CL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CIVILITE_CODE_CL = 1;
    private static final Integer UPDATED_CIVILITE_CODE_CL = 2;

    private static final String ENTITY_API_URL = "/api/civilite-clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CiviliteClientRepository civiliteClientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCiviliteClientMockMvc;

    private CiviliteClient civiliteClient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CiviliteClient createEntity(EntityManager em) {
        CiviliteClient civiliteClient = new CiviliteClient()
            .civiliteLibelleCl(DEFAULT_CIVILITE_LIBELLE_CL)
            .civiliteCodeCl(DEFAULT_CIVILITE_CODE_CL);
        return civiliteClient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CiviliteClient createUpdatedEntity(EntityManager em) {
        CiviliteClient civiliteClient = new CiviliteClient()
            .civiliteLibelleCl(UPDATED_CIVILITE_LIBELLE_CL)
            .civiliteCodeCl(UPDATED_CIVILITE_CODE_CL);
        return civiliteClient;
    }

    @BeforeEach
    public void initTest() {
        civiliteClient = createEntity(em);
    }

    @Test
    @Transactional
    void createCiviliteClient() throws Exception {
        int databaseSizeBeforeCreate = civiliteClientRepository.findAll().size();
        // Create the CiviliteClient
        restCiviliteClientMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(civiliteClient))
            )
            .andExpect(status().isCreated());

        // Validate the CiviliteClient in the database
        List<CiviliteClient> civiliteClientList = civiliteClientRepository.findAll();
        assertThat(civiliteClientList).hasSize(databaseSizeBeforeCreate + 1);
        CiviliteClient testCiviliteClient = civiliteClientList.get(civiliteClientList.size() - 1);
        assertThat(testCiviliteClient.getCiviliteLibelleCl()).isEqualTo(DEFAULT_CIVILITE_LIBELLE_CL);
        assertThat(testCiviliteClient.getCiviliteCodeCl()).isEqualTo(DEFAULT_CIVILITE_CODE_CL);
    }

    @Test
    @Transactional
    void createCiviliteClientWithExistingId() throws Exception {
        // Create the CiviliteClient with an existing ID
        civiliteClient.setId(1L);

        int databaseSizeBeforeCreate = civiliteClientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCiviliteClientMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(civiliteClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiviliteClient in the database
        List<CiviliteClient> civiliteClientList = civiliteClientRepository.findAll();
        assertThat(civiliteClientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCiviliteClients() throws Exception {
        // Initialize the database
        civiliteClientRepository.saveAndFlush(civiliteClient);

        // Get all the civiliteClientList
        restCiviliteClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(civiliteClient.getId().intValue())))
            .andExpect(jsonPath("$.[*].civiliteLibelleCl").value(hasItem(DEFAULT_CIVILITE_LIBELLE_CL)))
            .andExpect(jsonPath("$.[*].civiliteCodeCl").value(hasItem(DEFAULT_CIVILITE_CODE_CL)));
    }

    @Test
    @Transactional
    void getCiviliteClient() throws Exception {
        // Initialize the database
        civiliteClientRepository.saveAndFlush(civiliteClient);

        // Get the civiliteClient
        restCiviliteClientMockMvc
            .perform(get(ENTITY_API_URL_ID, civiliteClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(civiliteClient.getId().intValue()))
            .andExpect(jsonPath("$.civiliteLibelleCl").value(DEFAULT_CIVILITE_LIBELLE_CL))
            .andExpect(jsonPath("$.civiliteCodeCl").value(DEFAULT_CIVILITE_CODE_CL));
    }

    @Test
    @Transactional
    void getNonExistingCiviliteClient() throws Exception {
        // Get the civiliteClient
        restCiviliteClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCiviliteClient() throws Exception {
        // Initialize the database
        civiliteClientRepository.saveAndFlush(civiliteClient);

        int databaseSizeBeforeUpdate = civiliteClientRepository.findAll().size();

        // Update the civiliteClient
        CiviliteClient updatedCiviliteClient = civiliteClientRepository.findById(civiliteClient.getId()).get();
        // Disconnect from session so that the updates on updatedCiviliteClient are not directly saved in db
        em.detach(updatedCiviliteClient);
        updatedCiviliteClient.civiliteLibelleCl(UPDATED_CIVILITE_LIBELLE_CL).civiliteCodeCl(UPDATED_CIVILITE_CODE_CL);

        restCiviliteClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCiviliteClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCiviliteClient))
            )
            .andExpect(status().isOk());

        // Validate the CiviliteClient in the database
        List<CiviliteClient> civiliteClientList = civiliteClientRepository.findAll();
        assertThat(civiliteClientList).hasSize(databaseSizeBeforeUpdate);
        CiviliteClient testCiviliteClient = civiliteClientList.get(civiliteClientList.size() - 1);
        assertThat(testCiviliteClient.getCiviliteLibelleCl()).isEqualTo(UPDATED_CIVILITE_LIBELLE_CL);
        assertThat(testCiviliteClient.getCiviliteCodeCl()).isEqualTo(UPDATED_CIVILITE_CODE_CL);
    }

    @Test
    @Transactional
    void putNonExistingCiviliteClient() throws Exception {
        int databaseSizeBeforeUpdate = civiliteClientRepository.findAll().size();
        civiliteClient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiviliteClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, civiliteClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(civiliteClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiviliteClient in the database
        List<CiviliteClient> civiliteClientList = civiliteClientRepository.findAll();
        assertThat(civiliteClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCiviliteClient() throws Exception {
        int databaseSizeBeforeUpdate = civiliteClientRepository.findAll().size();
        civiliteClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCiviliteClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(civiliteClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiviliteClient in the database
        List<CiviliteClient> civiliteClientList = civiliteClientRepository.findAll();
        assertThat(civiliteClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCiviliteClient() throws Exception {
        int databaseSizeBeforeUpdate = civiliteClientRepository.findAll().size();
        civiliteClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCiviliteClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(civiliteClient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CiviliteClient in the database
        List<CiviliteClient> civiliteClientList = civiliteClientRepository.findAll();
        assertThat(civiliteClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCiviliteClientWithPatch() throws Exception {
        // Initialize the database
        civiliteClientRepository.saveAndFlush(civiliteClient);

        int databaseSizeBeforeUpdate = civiliteClientRepository.findAll().size();

        // Update the civiliteClient using partial update
        CiviliteClient partialUpdatedCiviliteClient = new CiviliteClient();
        partialUpdatedCiviliteClient.setId(civiliteClient.getId());

        partialUpdatedCiviliteClient.civiliteLibelleCl(UPDATED_CIVILITE_LIBELLE_CL).civiliteCodeCl(UPDATED_CIVILITE_CODE_CL);

        restCiviliteClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCiviliteClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCiviliteClient))
            )
            .andExpect(status().isOk());

        // Validate the CiviliteClient in the database
        List<CiviliteClient> civiliteClientList = civiliteClientRepository.findAll();
        assertThat(civiliteClientList).hasSize(databaseSizeBeforeUpdate);
        CiviliteClient testCiviliteClient = civiliteClientList.get(civiliteClientList.size() - 1);
        assertThat(testCiviliteClient.getCiviliteLibelleCl()).isEqualTo(UPDATED_CIVILITE_LIBELLE_CL);
        assertThat(testCiviliteClient.getCiviliteCodeCl()).isEqualTo(UPDATED_CIVILITE_CODE_CL);
    }

    @Test
    @Transactional
    void fullUpdateCiviliteClientWithPatch() throws Exception {
        // Initialize the database
        civiliteClientRepository.saveAndFlush(civiliteClient);

        int databaseSizeBeforeUpdate = civiliteClientRepository.findAll().size();

        // Update the civiliteClient using partial update
        CiviliteClient partialUpdatedCiviliteClient = new CiviliteClient();
        partialUpdatedCiviliteClient.setId(civiliteClient.getId());

        partialUpdatedCiviliteClient.civiliteLibelleCl(UPDATED_CIVILITE_LIBELLE_CL).civiliteCodeCl(UPDATED_CIVILITE_CODE_CL);

        restCiviliteClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCiviliteClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCiviliteClient))
            )
            .andExpect(status().isOk());

        // Validate the CiviliteClient in the database
        List<CiviliteClient> civiliteClientList = civiliteClientRepository.findAll();
        assertThat(civiliteClientList).hasSize(databaseSizeBeforeUpdate);
        CiviliteClient testCiviliteClient = civiliteClientList.get(civiliteClientList.size() - 1);
        assertThat(testCiviliteClient.getCiviliteLibelleCl()).isEqualTo(UPDATED_CIVILITE_LIBELLE_CL);
        assertThat(testCiviliteClient.getCiviliteCodeCl()).isEqualTo(UPDATED_CIVILITE_CODE_CL);
    }

    @Test
    @Transactional
    void patchNonExistingCiviliteClient() throws Exception {
        int databaseSizeBeforeUpdate = civiliteClientRepository.findAll().size();
        civiliteClient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiviliteClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, civiliteClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(civiliteClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiviliteClient in the database
        List<CiviliteClient> civiliteClientList = civiliteClientRepository.findAll();
        assertThat(civiliteClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCiviliteClient() throws Exception {
        int databaseSizeBeforeUpdate = civiliteClientRepository.findAll().size();
        civiliteClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCiviliteClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(civiliteClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiviliteClient in the database
        List<CiviliteClient> civiliteClientList = civiliteClientRepository.findAll();
        assertThat(civiliteClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCiviliteClient() throws Exception {
        int databaseSizeBeforeUpdate = civiliteClientRepository.findAll().size();
        civiliteClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCiviliteClientMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(civiliteClient))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CiviliteClient in the database
        List<CiviliteClient> civiliteClientList = civiliteClientRepository.findAll();
        assertThat(civiliteClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCiviliteClient() throws Exception {
        // Initialize the database
        civiliteClientRepository.saveAndFlush(civiliteClient);

        int databaseSizeBeforeDelete = civiliteClientRepository.findAll().size();

        // Delete the civiliteClient
        restCiviliteClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, civiliteClient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CiviliteClient> civiliteClientList = civiliteClientRepository.findAll();
        assertThat(civiliteClientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
