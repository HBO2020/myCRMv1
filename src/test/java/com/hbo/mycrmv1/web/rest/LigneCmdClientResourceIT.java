package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.LigneCmdClient;
import com.hbo.mycrmv1.repository.LigneCmdClientRepository;
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
 * Integration tests for the {@link LigneCmdClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LigneCmdClientResourceIT {

    private static final Integer DEFAULT_CMD_QN_CL = 1;
    private static final Integer UPDATED_CMD_QN_CL = 2;

    private static final Integer DEFAULT_CMD_NM_PIECES_CL = 1;
    private static final Integer UPDATED_CMD_NM_PIECES_CL = 2;

    private static final String ENTITY_API_URL = "/api/ligne-cmd-clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LigneCmdClientRepository ligneCmdClientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLigneCmdClientMockMvc;

    private LigneCmdClient ligneCmdClient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneCmdClient createEntity(EntityManager em) {
        LigneCmdClient ligneCmdClient = new LigneCmdClient().cmdQnCl(DEFAULT_CMD_QN_CL).cmdNmPiecesCl(DEFAULT_CMD_NM_PIECES_CL);
        return ligneCmdClient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneCmdClient createUpdatedEntity(EntityManager em) {
        LigneCmdClient ligneCmdClient = new LigneCmdClient().cmdQnCl(UPDATED_CMD_QN_CL).cmdNmPiecesCl(UPDATED_CMD_NM_PIECES_CL);
        return ligneCmdClient;
    }

    @BeforeEach
    public void initTest() {
        ligneCmdClient = createEntity(em);
    }

    @Test
    @Transactional
    void createLigneCmdClient() throws Exception {
        int databaseSizeBeforeCreate = ligneCmdClientRepository.findAll().size();
        // Create the LigneCmdClient
        restLigneCmdClientMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligneCmdClient))
            )
            .andExpect(status().isCreated());

        // Validate the LigneCmdClient in the database
        List<LigneCmdClient> ligneCmdClientList = ligneCmdClientRepository.findAll();
        assertThat(ligneCmdClientList).hasSize(databaseSizeBeforeCreate + 1);
        LigneCmdClient testLigneCmdClient = ligneCmdClientList.get(ligneCmdClientList.size() - 1);
        assertThat(testLigneCmdClient.getCmdQnCl()).isEqualTo(DEFAULT_CMD_QN_CL);
        assertThat(testLigneCmdClient.getCmdNmPiecesCl()).isEqualTo(DEFAULT_CMD_NM_PIECES_CL);
    }

    @Test
    @Transactional
    void createLigneCmdClientWithExistingId() throws Exception {
        // Create the LigneCmdClient with an existing ID
        ligneCmdClient.setId(1L);

        int databaseSizeBeforeCreate = ligneCmdClientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigneCmdClientMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligneCmdClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneCmdClient in the database
        List<LigneCmdClient> ligneCmdClientList = ligneCmdClientRepository.findAll();
        assertThat(ligneCmdClientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLigneCmdClients() throws Exception {
        // Initialize the database
        ligneCmdClientRepository.saveAndFlush(ligneCmdClient);

        // Get all the ligneCmdClientList
        restLigneCmdClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligneCmdClient.getId().intValue())))
            .andExpect(jsonPath("$.[*].cmdQnCl").value(hasItem(DEFAULT_CMD_QN_CL)))
            .andExpect(jsonPath("$.[*].cmdNmPiecesCl").value(hasItem(DEFAULT_CMD_NM_PIECES_CL)));
    }

    @Test
    @Transactional
    void getLigneCmdClient() throws Exception {
        // Initialize the database
        ligneCmdClientRepository.saveAndFlush(ligneCmdClient);

        // Get the ligneCmdClient
        restLigneCmdClientMockMvc
            .perform(get(ENTITY_API_URL_ID, ligneCmdClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ligneCmdClient.getId().intValue()))
            .andExpect(jsonPath("$.cmdQnCl").value(DEFAULT_CMD_QN_CL))
            .andExpect(jsonPath("$.cmdNmPiecesCl").value(DEFAULT_CMD_NM_PIECES_CL));
    }

    @Test
    @Transactional
    void getNonExistingLigneCmdClient() throws Exception {
        // Get the ligneCmdClient
        restLigneCmdClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLigneCmdClient() throws Exception {
        // Initialize the database
        ligneCmdClientRepository.saveAndFlush(ligneCmdClient);

        int databaseSizeBeforeUpdate = ligneCmdClientRepository.findAll().size();

        // Update the ligneCmdClient
        LigneCmdClient updatedLigneCmdClient = ligneCmdClientRepository.findById(ligneCmdClient.getId()).get();
        // Disconnect from session so that the updates on updatedLigneCmdClient are not directly saved in db
        em.detach(updatedLigneCmdClient);
        updatedLigneCmdClient.cmdQnCl(UPDATED_CMD_QN_CL).cmdNmPiecesCl(UPDATED_CMD_NM_PIECES_CL);

        restLigneCmdClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLigneCmdClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLigneCmdClient))
            )
            .andExpect(status().isOk());

        // Validate the LigneCmdClient in the database
        List<LigneCmdClient> ligneCmdClientList = ligneCmdClientRepository.findAll();
        assertThat(ligneCmdClientList).hasSize(databaseSizeBeforeUpdate);
        LigneCmdClient testLigneCmdClient = ligneCmdClientList.get(ligneCmdClientList.size() - 1);
        assertThat(testLigneCmdClient.getCmdQnCl()).isEqualTo(UPDATED_CMD_QN_CL);
        assertThat(testLigneCmdClient.getCmdNmPiecesCl()).isEqualTo(UPDATED_CMD_NM_PIECES_CL);
    }

    @Test
    @Transactional
    void putNonExistingLigneCmdClient() throws Exception {
        int databaseSizeBeforeUpdate = ligneCmdClientRepository.findAll().size();
        ligneCmdClient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigneCmdClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ligneCmdClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ligneCmdClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneCmdClient in the database
        List<LigneCmdClient> ligneCmdClientList = ligneCmdClientRepository.findAll();
        assertThat(ligneCmdClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLigneCmdClient() throws Exception {
        int databaseSizeBeforeUpdate = ligneCmdClientRepository.findAll().size();
        ligneCmdClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneCmdClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ligneCmdClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneCmdClient in the database
        List<LigneCmdClient> ligneCmdClientList = ligneCmdClientRepository.findAll();
        assertThat(ligneCmdClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLigneCmdClient() throws Exception {
        int databaseSizeBeforeUpdate = ligneCmdClientRepository.findAll().size();
        ligneCmdClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneCmdClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligneCmdClient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LigneCmdClient in the database
        List<LigneCmdClient> ligneCmdClientList = ligneCmdClientRepository.findAll();
        assertThat(ligneCmdClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLigneCmdClientWithPatch() throws Exception {
        // Initialize the database
        ligneCmdClientRepository.saveAndFlush(ligneCmdClient);

        int databaseSizeBeforeUpdate = ligneCmdClientRepository.findAll().size();

        // Update the ligneCmdClient using partial update
        LigneCmdClient partialUpdatedLigneCmdClient = new LigneCmdClient();
        partialUpdatedLigneCmdClient.setId(ligneCmdClient.getId());

        partialUpdatedLigneCmdClient.cmdQnCl(UPDATED_CMD_QN_CL).cmdNmPiecesCl(UPDATED_CMD_NM_PIECES_CL);

        restLigneCmdClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigneCmdClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLigneCmdClient))
            )
            .andExpect(status().isOk());

        // Validate the LigneCmdClient in the database
        List<LigneCmdClient> ligneCmdClientList = ligneCmdClientRepository.findAll();
        assertThat(ligneCmdClientList).hasSize(databaseSizeBeforeUpdate);
        LigneCmdClient testLigneCmdClient = ligneCmdClientList.get(ligneCmdClientList.size() - 1);
        assertThat(testLigneCmdClient.getCmdQnCl()).isEqualTo(UPDATED_CMD_QN_CL);
        assertThat(testLigneCmdClient.getCmdNmPiecesCl()).isEqualTo(UPDATED_CMD_NM_PIECES_CL);
    }

    @Test
    @Transactional
    void fullUpdateLigneCmdClientWithPatch() throws Exception {
        // Initialize the database
        ligneCmdClientRepository.saveAndFlush(ligneCmdClient);

        int databaseSizeBeforeUpdate = ligneCmdClientRepository.findAll().size();

        // Update the ligneCmdClient using partial update
        LigneCmdClient partialUpdatedLigneCmdClient = new LigneCmdClient();
        partialUpdatedLigneCmdClient.setId(ligneCmdClient.getId());

        partialUpdatedLigneCmdClient.cmdQnCl(UPDATED_CMD_QN_CL).cmdNmPiecesCl(UPDATED_CMD_NM_PIECES_CL);

        restLigneCmdClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigneCmdClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLigneCmdClient))
            )
            .andExpect(status().isOk());

        // Validate the LigneCmdClient in the database
        List<LigneCmdClient> ligneCmdClientList = ligneCmdClientRepository.findAll();
        assertThat(ligneCmdClientList).hasSize(databaseSizeBeforeUpdate);
        LigneCmdClient testLigneCmdClient = ligneCmdClientList.get(ligneCmdClientList.size() - 1);
        assertThat(testLigneCmdClient.getCmdQnCl()).isEqualTo(UPDATED_CMD_QN_CL);
        assertThat(testLigneCmdClient.getCmdNmPiecesCl()).isEqualTo(UPDATED_CMD_NM_PIECES_CL);
    }

    @Test
    @Transactional
    void patchNonExistingLigneCmdClient() throws Exception {
        int databaseSizeBeforeUpdate = ligneCmdClientRepository.findAll().size();
        ligneCmdClient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigneCmdClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ligneCmdClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ligneCmdClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneCmdClient in the database
        List<LigneCmdClient> ligneCmdClientList = ligneCmdClientRepository.findAll();
        assertThat(ligneCmdClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLigneCmdClient() throws Exception {
        int databaseSizeBeforeUpdate = ligneCmdClientRepository.findAll().size();
        ligneCmdClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneCmdClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ligneCmdClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneCmdClient in the database
        List<LigneCmdClient> ligneCmdClientList = ligneCmdClientRepository.findAll();
        assertThat(ligneCmdClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLigneCmdClient() throws Exception {
        int databaseSizeBeforeUpdate = ligneCmdClientRepository.findAll().size();
        ligneCmdClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneCmdClientMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ligneCmdClient))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LigneCmdClient in the database
        List<LigneCmdClient> ligneCmdClientList = ligneCmdClientRepository.findAll();
        assertThat(ligneCmdClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLigneCmdClient() throws Exception {
        // Initialize the database
        ligneCmdClientRepository.saveAndFlush(ligneCmdClient);

        int databaseSizeBeforeDelete = ligneCmdClientRepository.findAll().size();

        // Delete the ligneCmdClient
        restLigneCmdClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, ligneCmdClient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LigneCmdClient> ligneCmdClientList = ligneCmdClientRepository.findAll();
        assertThat(ligneCmdClientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
