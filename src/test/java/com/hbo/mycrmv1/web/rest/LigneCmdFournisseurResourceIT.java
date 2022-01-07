package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.LigneCmdFournisseur;
import com.hbo.mycrmv1.repository.LigneCmdFournisseurRepository;
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
 * Integration tests for the {@link LigneCmdFournisseurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LigneCmdFournisseurResourceIT {

    private static final Integer DEFAULT_CMD_QN_FR = 1;
    private static final Integer UPDATED_CMD_QN_FR = 2;

    private static final Integer DEFAULT_CMD_NM_PIECES = 1;
    private static final Integer UPDATED_CMD_NM_PIECES = 2;

    private static final String ENTITY_API_URL = "/api/ligne-cmd-fournisseurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LigneCmdFournisseurRepository ligneCmdFournisseurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLigneCmdFournisseurMockMvc;

    private LigneCmdFournisseur ligneCmdFournisseur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneCmdFournisseur createEntity(EntityManager em) {
        LigneCmdFournisseur ligneCmdFournisseur = new LigneCmdFournisseur().cmdQnFr(DEFAULT_CMD_QN_FR).cmdNmPieces(DEFAULT_CMD_NM_PIECES);
        return ligneCmdFournisseur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneCmdFournisseur createUpdatedEntity(EntityManager em) {
        LigneCmdFournisseur ligneCmdFournisseur = new LigneCmdFournisseur().cmdQnFr(UPDATED_CMD_QN_FR).cmdNmPieces(UPDATED_CMD_NM_PIECES);
        return ligneCmdFournisseur;
    }

    @BeforeEach
    public void initTest() {
        ligneCmdFournisseur = createEntity(em);
    }

    @Test
    @Transactional
    void createLigneCmdFournisseur() throws Exception {
        int databaseSizeBeforeCreate = ligneCmdFournisseurRepository.findAll().size();
        // Create the LigneCmdFournisseur
        restLigneCmdFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligneCmdFournisseur))
            )
            .andExpect(status().isCreated());

        // Validate the LigneCmdFournisseur in the database
        List<LigneCmdFournisseur> ligneCmdFournisseurList = ligneCmdFournisseurRepository.findAll();
        assertThat(ligneCmdFournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        LigneCmdFournisseur testLigneCmdFournisseur = ligneCmdFournisseurList.get(ligneCmdFournisseurList.size() - 1);
        assertThat(testLigneCmdFournisseur.getCmdQnFr()).isEqualTo(DEFAULT_CMD_QN_FR);
        assertThat(testLigneCmdFournisseur.getCmdNmPieces()).isEqualTo(DEFAULT_CMD_NM_PIECES);
    }

    @Test
    @Transactional
    void createLigneCmdFournisseurWithExistingId() throws Exception {
        // Create the LigneCmdFournisseur with an existing ID
        ligneCmdFournisseur.setId(1L);

        int databaseSizeBeforeCreate = ligneCmdFournisseurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigneCmdFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligneCmdFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneCmdFournisseur in the database
        List<LigneCmdFournisseur> ligneCmdFournisseurList = ligneCmdFournisseurRepository.findAll();
        assertThat(ligneCmdFournisseurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLigneCmdFournisseurs() throws Exception {
        // Initialize the database
        ligneCmdFournisseurRepository.saveAndFlush(ligneCmdFournisseur);

        // Get all the ligneCmdFournisseurList
        restLigneCmdFournisseurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligneCmdFournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].cmdQnFr").value(hasItem(DEFAULT_CMD_QN_FR)))
            .andExpect(jsonPath("$.[*].cmdNmPieces").value(hasItem(DEFAULT_CMD_NM_PIECES)));
    }

    @Test
    @Transactional
    void getLigneCmdFournisseur() throws Exception {
        // Initialize the database
        ligneCmdFournisseurRepository.saveAndFlush(ligneCmdFournisseur);

        // Get the ligneCmdFournisseur
        restLigneCmdFournisseurMockMvc
            .perform(get(ENTITY_API_URL_ID, ligneCmdFournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ligneCmdFournisseur.getId().intValue()))
            .andExpect(jsonPath("$.cmdQnFr").value(DEFAULT_CMD_QN_FR))
            .andExpect(jsonPath("$.cmdNmPieces").value(DEFAULT_CMD_NM_PIECES));
    }

    @Test
    @Transactional
    void getNonExistingLigneCmdFournisseur() throws Exception {
        // Get the ligneCmdFournisseur
        restLigneCmdFournisseurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLigneCmdFournisseur() throws Exception {
        // Initialize the database
        ligneCmdFournisseurRepository.saveAndFlush(ligneCmdFournisseur);

        int databaseSizeBeforeUpdate = ligneCmdFournisseurRepository.findAll().size();

        // Update the ligneCmdFournisseur
        LigneCmdFournisseur updatedLigneCmdFournisseur = ligneCmdFournisseurRepository.findById(ligneCmdFournisseur.getId()).get();
        // Disconnect from session so that the updates on updatedLigneCmdFournisseur are not directly saved in db
        em.detach(updatedLigneCmdFournisseur);
        updatedLigneCmdFournisseur.cmdQnFr(UPDATED_CMD_QN_FR).cmdNmPieces(UPDATED_CMD_NM_PIECES);

        restLigneCmdFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLigneCmdFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLigneCmdFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the LigneCmdFournisseur in the database
        List<LigneCmdFournisseur> ligneCmdFournisseurList = ligneCmdFournisseurRepository.findAll();
        assertThat(ligneCmdFournisseurList).hasSize(databaseSizeBeforeUpdate);
        LigneCmdFournisseur testLigneCmdFournisseur = ligneCmdFournisseurList.get(ligneCmdFournisseurList.size() - 1);
        assertThat(testLigneCmdFournisseur.getCmdQnFr()).isEqualTo(UPDATED_CMD_QN_FR);
        assertThat(testLigneCmdFournisseur.getCmdNmPieces()).isEqualTo(UPDATED_CMD_NM_PIECES);
    }

    @Test
    @Transactional
    void putNonExistingLigneCmdFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = ligneCmdFournisseurRepository.findAll().size();
        ligneCmdFournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigneCmdFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ligneCmdFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ligneCmdFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneCmdFournisseur in the database
        List<LigneCmdFournisseur> ligneCmdFournisseurList = ligneCmdFournisseurRepository.findAll();
        assertThat(ligneCmdFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLigneCmdFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = ligneCmdFournisseurRepository.findAll().size();
        ligneCmdFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneCmdFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ligneCmdFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneCmdFournisseur in the database
        List<LigneCmdFournisseur> ligneCmdFournisseurList = ligneCmdFournisseurRepository.findAll();
        assertThat(ligneCmdFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLigneCmdFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = ligneCmdFournisseurRepository.findAll().size();
        ligneCmdFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneCmdFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligneCmdFournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LigneCmdFournisseur in the database
        List<LigneCmdFournisseur> ligneCmdFournisseurList = ligneCmdFournisseurRepository.findAll();
        assertThat(ligneCmdFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLigneCmdFournisseurWithPatch() throws Exception {
        // Initialize the database
        ligneCmdFournisseurRepository.saveAndFlush(ligneCmdFournisseur);

        int databaseSizeBeforeUpdate = ligneCmdFournisseurRepository.findAll().size();

        // Update the ligneCmdFournisseur using partial update
        LigneCmdFournisseur partialUpdatedLigneCmdFournisseur = new LigneCmdFournisseur();
        partialUpdatedLigneCmdFournisseur.setId(ligneCmdFournisseur.getId());

        partialUpdatedLigneCmdFournisseur.cmdQnFr(UPDATED_CMD_QN_FR).cmdNmPieces(UPDATED_CMD_NM_PIECES);

        restLigneCmdFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigneCmdFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLigneCmdFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the LigneCmdFournisseur in the database
        List<LigneCmdFournisseur> ligneCmdFournisseurList = ligneCmdFournisseurRepository.findAll();
        assertThat(ligneCmdFournisseurList).hasSize(databaseSizeBeforeUpdate);
        LigneCmdFournisseur testLigneCmdFournisseur = ligneCmdFournisseurList.get(ligneCmdFournisseurList.size() - 1);
        assertThat(testLigneCmdFournisseur.getCmdQnFr()).isEqualTo(UPDATED_CMD_QN_FR);
        assertThat(testLigneCmdFournisseur.getCmdNmPieces()).isEqualTo(UPDATED_CMD_NM_PIECES);
    }

    @Test
    @Transactional
    void fullUpdateLigneCmdFournisseurWithPatch() throws Exception {
        // Initialize the database
        ligneCmdFournisseurRepository.saveAndFlush(ligneCmdFournisseur);

        int databaseSizeBeforeUpdate = ligneCmdFournisseurRepository.findAll().size();

        // Update the ligneCmdFournisseur using partial update
        LigneCmdFournisseur partialUpdatedLigneCmdFournisseur = new LigneCmdFournisseur();
        partialUpdatedLigneCmdFournisseur.setId(ligneCmdFournisseur.getId());

        partialUpdatedLigneCmdFournisseur.cmdQnFr(UPDATED_CMD_QN_FR).cmdNmPieces(UPDATED_CMD_NM_PIECES);

        restLigneCmdFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigneCmdFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLigneCmdFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the LigneCmdFournisseur in the database
        List<LigneCmdFournisseur> ligneCmdFournisseurList = ligneCmdFournisseurRepository.findAll();
        assertThat(ligneCmdFournisseurList).hasSize(databaseSizeBeforeUpdate);
        LigneCmdFournisseur testLigneCmdFournisseur = ligneCmdFournisseurList.get(ligneCmdFournisseurList.size() - 1);
        assertThat(testLigneCmdFournisseur.getCmdQnFr()).isEqualTo(UPDATED_CMD_QN_FR);
        assertThat(testLigneCmdFournisseur.getCmdNmPieces()).isEqualTo(UPDATED_CMD_NM_PIECES);
    }

    @Test
    @Transactional
    void patchNonExistingLigneCmdFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = ligneCmdFournisseurRepository.findAll().size();
        ligneCmdFournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigneCmdFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ligneCmdFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ligneCmdFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneCmdFournisseur in the database
        List<LigneCmdFournisseur> ligneCmdFournisseurList = ligneCmdFournisseurRepository.findAll();
        assertThat(ligneCmdFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLigneCmdFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = ligneCmdFournisseurRepository.findAll().size();
        ligneCmdFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneCmdFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ligneCmdFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneCmdFournisseur in the database
        List<LigneCmdFournisseur> ligneCmdFournisseurList = ligneCmdFournisseurRepository.findAll();
        assertThat(ligneCmdFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLigneCmdFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = ligneCmdFournisseurRepository.findAll().size();
        ligneCmdFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneCmdFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ligneCmdFournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LigneCmdFournisseur in the database
        List<LigneCmdFournisseur> ligneCmdFournisseurList = ligneCmdFournisseurRepository.findAll();
        assertThat(ligneCmdFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLigneCmdFournisseur() throws Exception {
        // Initialize the database
        ligneCmdFournisseurRepository.saveAndFlush(ligneCmdFournisseur);

        int databaseSizeBeforeDelete = ligneCmdFournisseurRepository.findAll().size();

        // Delete the ligneCmdFournisseur
        restLigneCmdFournisseurMockMvc
            .perform(delete(ENTITY_API_URL_ID, ligneCmdFournisseur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LigneCmdFournisseur> ligneCmdFournisseurList = ligneCmdFournisseurRepository.findAll();
        assertThat(ligneCmdFournisseurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
