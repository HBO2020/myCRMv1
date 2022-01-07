package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.LigneLivFournisseur;
import com.hbo.mycrmv1.repository.LigneLivFournisseurRepository;
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
 * Integration tests for the {@link LigneLivFournisseurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LigneLivFournisseurResourceIT {

    private static final Integer DEFAULT_LIV_FR_QUANTITE = 1;
    private static final Integer UPDATED_LIV_FR_QUANTITE = 2;

    private static final Integer DEFAULT_LIV_FR_NM_PIECES = 1;
    private static final Integer UPDATED_LIV_FR_NM_PIECES = 2;

    private static final Double DEFAULT_LIV_FR_TOTAL_PRIX = 1D;
    private static final Double UPDATED_LIV_FR_TOTAL_PRIX = 2D;

    private static final String ENTITY_API_URL = "/api/ligne-liv-fournisseurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LigneLivFournisseurRepository ligneLivFournisseurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLigneLivFournisseurMockMvc;

    private LigneLivFournisseur ligneLivFournisseur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneLivFournisseur createEntity(EntityManager em) {
        LigneLivFournisseur ligneLivFournisseur = new LigneLivFournisseur()
            .livFrQuantite(DEFAULT_LIV_FR_QUANTITE)
            .livFrNmPieces(DEFAULT_LIV_FR_NM_PIECES)
            .livFrTotalPrix(DEFAULT_LIV_FR_TOTAL_PRIX);
        return ligneLivFournisseur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneLivFournisseur createUpdatedEntity(EntityManager em) {
        LigneLivFournisseur ligneLivFournisseur = new LigneLivFournisseur()
            .livFrQuantite(UPDATED_LIV_FR_QUANTITE)
            .livFrNmPieces(UPDATED_LIV_FR_NM_PIECES)
            .livFrTotalPrix(UPDATED_LIV_FR_TOTAL_PRIX);
        return ligneLivFournisseur;
    }

    @BeforeEach
    public void initTest() {
        ligneLivFournisseur = createEntity(em);
    }

    @Test
    @Transactional
    void createLigneLivFournisseur() throws Exception {
        int databaseSizeBeforeCreate = ligneLivFournisseurRepository.findAll().size();
        // Create the LigneLivFournisseur
        restLigneLivFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligneLivFournisseur))
            )
            .andExpect(status().isCreated());

        // Validate the LigneLivFournisseur in the database
        List<LigneLivFournisseur> ligneLivFournisseurList = ligneLivFournisseurRepository.findAll();
        assertThat(ligneLivFournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        LigneLivFournisseur testLigneLivFournisseur = ligneLivFournisseurList.get(ligneLivFournisseurList.size() - 1);
        assertThat(testLigneLivFournisseur.getLivFrQuantite()).isEqualTo(DEFAULT_LIV_FR_QUANTITE);
        assertThat(testLigneLivFournisseur.getLivFrNmPieces()).isEqualTo(DEFAULT_LIV_FR_NM_PIECES);
        assertThat(testLigneLivFournisseur.getLivFrTotalPrix()).isEqualTo(DEFAULT_LIV_FR_TOTAL_PRIX);
    }

    @Test
    @Transactional
    void createLigneLivFournisseurWithExistingId() throws Exception {
        // Create the LigneLivFournisseur with an existing ID
        ligneLivFournisseur.setId(1L);

        int databaseSizeBeforeCreate = ligneLivFournisseurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigneLivFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligneLivFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneLivFournisseur in the database
        List<LigneLivFournisseur> ligneLivFournisseurList = ligneLivFournisseurRepository.findAll();
        assertThat(ligneLivFournisseurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLigneLivFournisseurs() throws Exception {
        // Initialize the database
        ligneLivFournisseurRepository.saveAndFlush(ligneLivFournisseur);

        // Get all the ligneLivFournisseurList
        restLigneLivFournisseurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligneLivFournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].livFrQuantite").value(hasItem(DEFAULT_LIV_FR_QUANTITE)))
            .andExpect(jsonPath("$.[*].livFrNmPieces").value(hasItem(DEFAULT_LIV_FR_NM_PIECES)))
            .andExpect(jsonPath("$.[*].livFrTotalPrix").value(hasItem(DEFAULT_LIV_FR_TOTAL_PRIX.doubleValue())));
    }

    @Test
    @Transactional
    void getLigneLivFournisseur() throws Exception {
        // Initialize the database
        ligneLivFournisseurRepository.saveAndFlush(ligneLivFournisseur);

        // Get the ligneLivFournisseur
        restLigneLivFournisseurMockMvc
            .perform(get(ENTITY_API_URL_ID, ligneLivFournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ligneLivFournisseur.getId().intValue()))
            .andExpect(jsonPath("$.livFrQuantite").value(DEFAULT_LIV_FR_QUANTITE))
            .andExpect(jsonPath("$.livFrNmPieces").value(DEFAULT_LIV_FR_NM_PIECES))
            .andExpect(jsonPath("$.livFrTotalPrix").value(DEFAULT_LIV_FR_TOTAL_PRIX.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingLigneLivFournisseur() throws Exception {
        // Get the ligneLivFournisseur
        restLigneLivFournisseurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLigneLivFournisseur() throws Exception {
        // Initialize the database
        ligneLivFournisseurRepository.saveAndFlush(ligneLivFournisseur);

        int databaseSizeBeforeUpdate = ligneLivFournisseurRepository.findAll().size();

        // Update the ligneLivFournisseur
        LigneLivFournisseur updatedLigneLivFournisseur = ligneLivFournisseurRepository.findById(ligneLivFournisseur.getId()).get();
        // Disconnect from session so that the updates on updatedLigneLivFournisseur are not directly saved in db
        em.detach(updatedLigneLivFournisseur);
        updatedLigneLivFournisseur
            .livFrQuantite(UPDATED_LIV_FR_QUANTITE)
            .livFrNmPieces(UPDATED_LIV_FR_NM_PIECES)
            .livFrTotalPrix(UPDATED_LIV_FR_TOTAL_PRIX);

        restLigneLivFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLigneLivFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLigneLivFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the LigneLivFournisseur in the database
        List<LigneLivFournisseur> ligneLivFournisseurList = ligneLivFournisseurRepository.findAll();
        assertThat(ligneLivFournisseurList).hasSize(databaseSizeBeforeUpdate);
        LigneLivFournisseur testLigneLivFournisseur = ligneLivFournisseurList.get(ligneLivFournisseurList.size() - 1);
        assertThat(testLigneLivFournisseur.getLivFrQuantite()).isEqualTo(UPDATED_LIV_FR_QUANTITE);
        assertThat(testLigneLivFournisseur.getLivFrNmPieces()).isEqualTo(UPDATED_LIV_FR_NM_PIECES);
        assertThat(testLigneLivFournisseur.getLivFrTotalPrix()).isEqualTo(UPDATED_LIV_FR_TOTAL_PRIX);
    }

    @Test
    @Transactional
    void putNonExistingLigneLivFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = ligneLivFournisseurRepository.findAll().size();
        ligneLivFournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigneLivFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ligneLivFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ligneLivFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneLivFournisseur in the database
        List<LigneLivFournisseur> ligneLivFournisseurList = ligneLivFournisseurRepository.findAll();
        assertThat(ligneLivFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLigneLivFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = ligneLivFournisseurRepository.findAll().size();
        ligneLivFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneLivFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ligneLivFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneLivFournisseur in the database
        List<LigneLivFournisseur> ligneLivFournisseurList = ligneLivFournisseurRepository.findAll();
        assertThat(ligneLivFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLigneLivFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = ligneLivFournisseurRepository.findAll().size();
        ligneLivFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneLivFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligneLivFournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LigneLivFournisseur in the database
        List<LigneLivFournisseur> ligneLivFournisseurList = ligneLivFournisseurRepository.findAll();
        assertThat(ligneLivFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLigneLivFournisseurWithPatch() throws Exception {
        // Initialize the database
        ligneLivFournisseurRepository.saveAndFlush(ligneLivFournisseur);

        int databaseSizeBeforeUpdate = ligneLivFournisseurRepository.findAll().size();

        // Update the ligneLivFournisseur using partial update
        LigneLivFournisseur partialUpdatedLigneLivFournisseur = new LigneLivFournisseur();
        partialUpdatedLigneLivFournisseur.setId(ligneLivFournisseur.getId());

        partialUpdatedLigneLivFournisseur.livFrQuantite(UPDATED_LIV_FR_QUANTITE);

        restLigneLivFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigneLivFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLigneLivFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the LigneLivFournisseur in the database
        List<LigneLivFournisseur> ligneLivFournisseurList = ligneLivFournisseurRepository.findAll();
        assertThat(ligneLivFournisseurList).hasSize(databaseSizeBeforeUpdate);
        LigneLivFournisseur testLigneLivFournisseur = ligneLivFournisseurList.get(ligneLivFournisseurList.size() - 1);
        assertThat(testLigneLivFournisseur.getLivFrQuantite()).isEqualTo(UPDATED_LIV_FR_QUANTITE);
        assertThat(testLigneLivFournisseur.getLivFrNmPieces()).isEqualTo(DEFAULT_LIV_FR_NM_PIECES);
        assertThat(testLigneLivFournisseur.getLivFrTotalPrix()).isEqualTo(DEFAULT_LIV_FR_TOTAL_PRIX);
    }

    @Test
    @Transactional
    void fullUpdateLigneLivFournisseurWithPatch() throws Exception {
        // Initialize the database
        ligneLivFournisseurRepository.saveAndFlush(ligneLivFournisseur);

        int databaseSizeBeforeUpdate = ligneLivFournisseurRepository.findAll().size();

        // Update the ligneLivFournisseur using partial update
        LigneLivFournisseur partialUpdatedLigneLivFournisseur = new LigneLivFournisseur();
        partialUpdatedLigneLivFournisseur.setId(ligneLivFournisseur.getId());

        partialUpdatedLigneLivFournisseur
            .livFrQuantite(UPDATED_LIV_FR_QUANTITE)
            .livFrNmPieces(UPDATED_LIV_FR_NM_PIECES)
            .livFrTotalPrix(UPDATED_LIV_FR_TOTAL_PRIX);

        restLigneLivFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigneLivFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLigneLivFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the LigneLivFournisseur in the database
        List<LigneLivFournisseur> ligneLivFournisseurList = ligneLivFournisseurRepository.findAll();
        assertThat(ligneLivFournisseurList).hasSize(databaseSizeBeforeUpdate);
        LigneLivFournisseur testLigneLivFournisseur = ligneLivFournisseurList.get(ligneLivFournisseurList.size() - 1);
        assertThat(testLigneLivFournisseur.getLivFrQuantite()).isEqualTo(UPDATED_LIV_FR_QUANTITE);
        assertThat(testLigneLivFournisseur.getLivFrNmPieces()).isEqualTo(UPDATED_LIV_FR_NM_PIECES);
        assertThat(testLigneLivFournisseur.getLivFrTotalPrix()).isEqualTo(UPDATED_LIV_FR_TOTAL_PRIX);
    }

    @Test
    @Transactional
    void patchNonExistingLigneLivFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = ligneLivFournisseurRepository.findAll().size();
        ligneLivFournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigneLivFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ligneLivFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ligneLivFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneLivFournisseur in the database
        List<LigneLivFournisseur> ligneLivFournisseurList = ligneLivFournisseurRepository.findAll();
        assertThat(ligneLivFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLigneLivFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = ligneLivFournisseurRepository.findAll().size();
        ligneLivFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneLivFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ligneLivFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the LigneLivFournisseur in the database
        List<LigneLivFournisseur> ligneLivFournisseurList = ligneLivFournisseurRepository.findAll();
        assertThat(ligneLivFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLigneLivFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = ligneLivFournisseurRepository.findAll().size();
        ligneLivFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigneLivFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ligneLivFournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LigneLivFournisseur in the database
        List<LigneLivFournisseur> ligneLivFournisseurList = ligneLivFournisseurRepository.findAll();
        assertThat(ligneLivFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLigneLivFournisseur() throws Exception {
        // Initialize the database
        ligneLivFournisseurRepository.saveAndFlush(ligneLivFournisseur);

        int databaseSizeBeforeDelete = ligneLivFournisseurRepository.findAll().size();

        // Delete the ligneLivFournisseur
        restLigneLivFournisseurMockMvc
            .perform(delete(ENTITY_API_URL_ID, ligneLivFournisseur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LigneLivFournisseur> ligneLivFournisseurList = ligneLivFournisseurRepository.findAll();
        assertThat(ligneLivFournisseurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
