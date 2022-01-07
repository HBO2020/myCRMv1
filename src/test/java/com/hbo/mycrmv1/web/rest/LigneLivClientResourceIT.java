package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.LigneLivClient;
import com.hbo.mycrmv1.repository.LigneLivClientRepository;
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
 * Integration tests for the {@link LigneLivClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LigneLivClientResourceIT {

    private static final Integer DEFAULT_LIV_QUANTITE_CL = 1;
    private static final Integer UPDATED_LIV_QUANTITE_CL = 2;

    private static final Integer DEFAULT_LIV_NM_PIECES_CL = 1;
    private static final Integer UPDATED_LIV_NM_PIECES_CL = 2;

    private static final Double DEFAULT_LIV_TOTAL_PRIX_CL = 1D;
    private static final Double UPDATED_LIV_TOTAL_PRIX_CL = 2D;

    private static final String ENTITY_API_URL = "/api/ligne-liv-clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LigneLivClientRepository ligneLivClientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLigneLivClientMockMvc;

    private LigneLivClient ligneLivClient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneLivClient createEntity(EntityManager em) {
        LigneLivClient ligneLivClient = new LigneLivClient()
            .livQuantiteCl(DEFAULT_LIV_QUANTITE_CL)
            .livNmPiecesCl(DEFAULT_LIV_NM_PIECES_CL)
            .livTotalPrixCl(DEFAULT_LIV_TOTAL_PRIX_CL);
        return ligneLivClient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneLivClient createUpdatedEntity(EntityManager em) {
        LigneLivClient ligneLivClient = new LigneLivClient()
            .livQuantiteCl(UPDATED_LIV_QUANTITE_CL)
            .livNmPiecesCl(UPDATED_LIV_NM_PIECES_CL)
            .livTotalPrixCl(UPDATED_LIV_TOTAL_PRIX_CL);
        return ligneLivClient;
    }

    @BeforeEach
    public void initTest() {
        ligneLivClient = createEntity(em);
    }

    @Test
    @Transactional
    void createLigneLivClient() throws Exception {
        int databaseSizeBeforeCreate = ligneLivClientRepository.findAll().size();
        // Create the LigneLivClient
        restLigneLivClientMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligneLivClient))
            )
            .andExpect(status().isCreated());

        // Validate the LigneLivClient in the database
        List<LigneLivClient> ligneLivClientList = ligneLivClientRepository.findAll();
        assertThat(ligneLivClientList).hasSize(databaseSizeBeforeCreate + 1);
        LigneLivClient testLigneLivClient = ligneLivClientList.get(ligneLivClientList.size() - 1);
        assertThat(testLigneLivClient.getLivQuantiteCl()).isEqualTo(DEFAULT_LIV_QUANTITE_CL);
        assertThat(testLigneLivClient.getLivNmPiecesCl()).isEqualTo(DEFAULT_LIV_NM_PIECES_CL);
        assertThat(testLigneLivClient.getLivTotalPrixCl()).isEqualTo(DEFAULT_LIV_TOTAL_PRIX_CL);
    }

    @Test
    @Transactional
    void createLigneLivClientWithExistingId() throws Exception {
        // Create the LigneLivClient with an existing ID
        ligneLivClient.setId(1L);

        int databaseSizeBeforeCreate = ligneLivClientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigneLivClientMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligneLivClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneLivClient in the database
        List<LigneLivClient> ligneLivClientList = ligneLivClientRepository.findAll();
        assertThat(ligneLivClientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLigneLivClients() throws Exception {
        // Initialize the database
        ligneLivClientRepository.saveAndFlush(ligneLivClient);

        // Get all the ligneLivClientList
        restLigneLivClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligneLivClient.getId().intValue())))
            .andExpect(jsonPath("$.[*].livQuantiteCl").value(hasItem(DEFAULT_LIV_QUANTITE_CL)))
            .andExpect(jsonPath("$.[*].livNmPiecesCl").value(hasItem(DEFAULT_LIV_NM_PIECES_CL)))
            .andExpect(jsonPath("$.[*].livTotalPrixCl").value(hasItem(DEFAULT_LIV_TOTAL_PRIX_CL.doubleValue())));
    }

    @Test
    @Transactional
    void getLigneLivClient() throws Exception {
        // Initialize the database
        ligneLivClientRepository.saveAndFlush(ligneLivClient);

        // Get the ligneLivClient
        restLigneLivClientMockMvc
            .perform(get(ENTITY_API_URL_ID, ligneLivClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ligneLivClient.getId().intValue()))
            .andExpect(jsonPath("$.livQuantiteCl").value(DEFAULT_LIV_QUANTITE_CL))
            .andExpect(jsonPath("$.livNmPiecesCl").value(DEFAULT_LIV_NM_PIECES_CL))
            .andExpect(jsonPath("$.livTotalPrixCl").value(DEFAULT_LIV_TOTAL_PRIX_CL.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingLigneLivClient() throws Exception {
        // Get the ligneLivClient
        restLigneLivClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLigneLivClient() throws Exception {
        // Initialize the database
        ligneLivClientRepository.saveAndFlush(ligneLivClient);

        int databaseSizeBeforeUpdate = ligneLivClientRepository.findAll().size();

        // Update the ligneLivClient
        LigneLivClient updatedLigneLivClient = ligneLivClientRepository.findById(ligneLivClient.getId()).get();
        // Disconnect from session so that the updates on updatedLigneLivClient are not directly saved in db
        em.detach(updatedLigneLivClient);
        updatedLigneLivClient
            .livQuantiteCl(UPDATED_LIV_QUANTITE_CL)
            .livNmPiecesCl(UPDATED_LIV_NM_PIECES_CL)
            .livTotalPrixCl(UPDATED_LIV_TOTAL_PRIX_CL);

        restLigneLivClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLigneLivClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLigneLivClient))
            )
            .andExpect(status().isOk());

        // Validate the LigneLivClient in the database
        List<LigneLivClient> ligneLivClientList = ligneLivClientRepository.findAll();
        assertThat(ligneLivClientList).hasSize(databaseSizeBeforeUpdate);
        LigneLivClient testLigneLivClient = ligneLivClientList.get(ligneLivClientList.size() - 1);
        assertThat(testLigneLivClient.getLivQuantiteCl()).isEqualTo(UPDATED_LIV_QUANTITE_CL);
        assertThat(testLigneLivClient.getLivNmPiecesCl()).isEqualTo(UPDATED_LIV_NM_PIECES_CL);
        assertThat(testLigneLivClient.getLivTotalPrixCl()).isEqualTo(UPDATED_LIV_TOTAL_PRIX_CL);
    }

    @Test
    @Transactional
    void putNonExistingLigneLivClient() throws Exception {
        int databaseSizeBeforeUpdate = ligneLivClientRepository.findAll().size();
        ligneLivClient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigneLivClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ligneLivClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ligneLivClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneLivClient in the database
        List<LigneLivClient> ligneLivClientList = ligneLivClientRepository.findAll();
        assertThat(ligneLivClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLigneLivClient() throws Exception {
        int databaseSizeBeforeUpdate = ligneLivClientRepository.findAll().size();
        ligneLivClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneLivClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ligneLivClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneLivClient in the database
        List<LigneLivClient> ligneLivClientList = ligneLivClientRepository.findAll();
        assertThat(ligneLivClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLigneLivClient() throws Exception {
        int databaseSizeBeforeUpdate = ligneLivClientRepository.findAll().size();
        ligneLivClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneLivClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligneLivClient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LigneLivClient in the database
        List<LigneLivClient> ligneLivClientList = ligneLivClientRepository.findAll();
        assertThat(ligneLivClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLigneLivClientWithPatch() throws Exception {
        // Initialize the database
        ligneLivClientRepository.saveAndFlush(ligneLivClient);

        int databaseSizeBeforeUpdate = ligneLivClientRepository.findAll().size();

        // Update the ligneLivClient using partial update
        LigneLivClient partialUpdatedLigneLivClient = new LigneLivClient();
        partialUpdatedLigneLivClient.setId(ligneLivClient.getId());

        partialUpdatedLigneLivClient.livNmPiecesCl(UPDATED_LIV_NM_PIECES_CL).livTotalPrixCl(UPDATED_LIV_TOTAL_PRIX_CL);

        restLigneLivClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigneLivClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLigneLivClient))
            )
            .andExpect(status().isOk());

        // Validate the LigneLivClient in the database
        List<LigneLivClient> ligneLivClientList = ligneLivClientRepository.findAll();
        assertThat(ligneLivClientList).hasSize(databaseSizeBeforeUpdate);
        LigneLivClient testLigneLivClient = ligneLivClientList.get(ligneLivClientList.size() - 1);
        assertThat(testLigneLivClient.getLivQuantiteCl()).isEqualTo(DEFAULT_LIV_QUANTITE_CL);
        assertThat(testLigneLivClient.getLivNmPiecesCl()).isEqualTo(UPDATED_LIV_NM_PIECES_CL);
        assertThat(testLigneLivClient.getLivTotalPrixCl()).isEqualTo(UPDATED_LIV_TOTAL_PRIX_CL);
    }

    @Test
    @Transactional
    void fullUpdateLigneLivClientWithPatch() throws Exception {
        // Initialize the database
        ligneLivClientRepository.saveAndFlush(ligneLivClient);

        int databaseSizeBeforeUpdate = ligneLivClientRepository.findAll().size();

        // Update the ligneLivClient using partial update
        LigneLivClient partialUpdatedLigneLivClient = new LigneLivClient();
        partialUpdatedLigneLivClient.setId(ligneLivClient.getId());

        partialUpdatedLigneLivClient
            .livQuantiteCl(UPDATED_LIV_QUANTITE_CL)
            .livNmPiecesCl(UPDATED_LIV_NM_PIECES_CL)
            .livTotalPrixCl(UPDATED_LIV_TOTAL_PRIX_CL);

        restLigneLivClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigneLivClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLigneLivClient))
            )
            .andExpect(status().isOk());

        // Validate the LigneLivClient in the database
        List<LigneLivClient> ligneLivClientList = ligneLivClientRepository.findAll();
        assertThat(ligneLivClientList).hasSize(databaseSizeBeforeUpdate);
        LigneLivClient testLigneLivClient = ligneLivClientList.get(ligneLivClientList.size() - 1);
        assertThat(testLigneLivClient.getLivQuantiteCl()).isEqualTo(UPDATED_LIV_QUANTITE_CL);
        assertThat(testLigneLivClient.getLivNmPiecesCl()).isEqualTo(UPDATED_LIV_NM_PIECES_CL);
        assertThat(testLigneLivClient.getLivTotalPrixCl()).isEqualTo(UPDATED_LIV_TOTAL_PRIX_CL);
    }

    @Test
    @Transactional
    void patchNonExistingLigneLivClient() throws Exception {
        int databaseSizeBeforeUpdate = ligneLivClientRepository.findAll().size();
        ligneLivClient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigneLivClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ligneLivClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ligneLivClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneLivClient in the database
        List<LigneLivClient> ligneLivClientList = ligneLivClientRepository.findAll();
        assertThat(ligneLivClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLigneLivClient() throws Exception {
        int databaseSizeBeforeUpdate = ligneLivClientRepository.findAll().size();
        ligneLivClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneLivClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ligneLivClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneLivClient in the database
        List<LigneLivClient> ligneLivClientList = ligneLivClientRepository.findAll();
        assertThat(ligneLivClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLigneLivClient() throws Exception {
        int databaseSizeBeforeUpdate = ligneLivClientRepository.findAll().size();
        ligneLivClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneLivClientMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ligneLivClient))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LigneLivClient in the database
        List<LigneLivClient> ligneLivClientList = ligneLivClientRepository.findAll();
        assertThat(ligneLivClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLigneLivClient() throws Exception {
        // Initialize the database
        ligneLivClientRepository.saveAndFlush(ligneLivClient);

        int databaseSizeBeforeDelete = ligneLivClientRepository.findAll().size();

        // Delete the ligneLivClient
        restLigneLivClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, ligneLivClient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LigneLivClient> ligneLivClientList = ligneLivClientRepository.findAll();
        assertThat(ligneLivClientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
