package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.CiviliteFournisseur;
import com.hbo.mycrmv1.repository.CiviliteFournisseurRepository;
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
 * Integration tests for the {@link CiviliteFournisseurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CiviliteFournisseurResourceIT {

    private static final String DEFAULT_CIVILITE_FR_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_CIVILITE_FR_LIBELLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CIVILITE_FR_CODE = 1;
    private static final Integer UPDATED_CIVILITE_FR_CODE = 2;

    private static final String ENTITY_API_URL = "/api/civilite-fournisseurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CiviliteFournisseurRepository civiliteFournisseurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCiviliteFournisseurMockMvc;

    private CiviliteFournisseur civiliteFournisseur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CiviliteFournisseur createEntity(EntityManager em) {
        CiviliteFournisseur civiliteFournisseur = new CiviliteFournisseur()
            .civiliteFrLibelle(DEFAULT_CIVILITE_FR_LIBELLE)
            .civiliteFrCode(DEFAULT_CIVILITE_FR_CODE);
        return civiliteFournisseur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CiviliteFournisseur createUpdatedEntity(EntityManager em) {
        CiviliteFournisseur civiliteFournisseur = new CiviliteFournisseur()
            .civiliteFrLibelle(UPDATED_CIVILITE_FR_LIBELLE)
            .civiliteFrCode(UPDATED_CIVILITE_FR_CODE);
        return civiliteFournisseur;
    }

    @BeforeEach
    public void initTest() {
        civiliteFournisseur = createEntity(em);
    }

    @Test
    @Transactional
    void createCiviliteFournisseur() throws Exception {
        int databaseSizeBeforeCreate = civiliteFournisseurRepository.findAll().size();
        // Create the CiviliteFournisseur
        restCiviliteFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(civiliteFournisseur))
            )
            .andExpect(status().isCreated());

        // Validate the CiviliteFournisseur in the database
        List<CiviliteFournisseur> civiliteFournisseurList = civiliteFournisseurRepository.findAll();
        assertThat(civiliteFournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        CiviliteFournisseur testCiviliteFournisseur = civiliteFournisseurList.get(civiliteFournisseurList.size() - 1);
        assertThat(testCiviliteFournisseur.getCiviliteFrLibelle()).isEqualTo(DEFAULT_CIVILITE_FR_LIBELLE);
        assertThat(testCiviliteFournisseur.getCiviliteFrCode()).isEqualTo(DEFAULT_CIVILITE_FR_CODE);
    }

    @Test
    @Transactional
    void createCiviliteFournisseurWithExistingId() throws Exception {
        // Create the CiviliteFournisseur with an existing ID
        civiliteFournisseur.setId(1L);

        int databaseSizeBeforeCreate = civiliteFournisseurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCiviliteFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(civiliteFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiviliteFournisseur in the database
        List<CiviliteFournisseur> civiliteFournisseurList = civiliteFournisseurRepository.findAll();
        assertThat(civiliteFournisseurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCiviliteFournisseurs() throws Exception {
        // Initialize the database
        civiliteFournisseurRepository.saveAndFlush(civiliteFournisseur);

        // Get all the civiliteFournisseurList
        restCiviliteFournisseurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(civiliteFournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].civiliteFrLibelle").value(hasItem(DEFAULT_CIVILITE_FR_LIBELLE)))
            .andExpect(jsonPath("$.[*].civiliteFrCode").value(hasItem(DEFAULT_CIVILITE_FR_CODE)));
    }

    @Test
    @Transactional
    void getCiviliteFournisseur() throws Exception {
        // Initialize the database
        civiliteFournisseurRepository.saveAndFlush(civiliteFournisseur);

        // Get the civiliteFournisseur
        restCiviliteFournisseurMockMvc
            .perform(get(ENTITY_API_URL_ID, civiliteFournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(civiliteFournisseur.getId().intValue()))
            .andExpect(jsonPath("$.civiliteFrLibelle").value(DEFAULT_CIVILITE_FR_LIBELLE))
            .andExpect(jsonPath("$.civiliteFrCode").value(DEFAULT_CIVILITE_FR_CODE));
    }

    @Test
    @Transactional
    void getNonExistingCiviliteFournisseur() throws Exception {
        // Get the civiliteFournisseur
        restCiviliteFournisseurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCiviliteFournisseur() throws Exception {
        // Initialize the database
        civiliteFournisseurRepository.saveAndFlush(civiliteFournisseur);

        int databaseSizeBeforeUpdate = civiliteFournisseurRepository.findAll().size();

        // Update the civiliteFournisseur
        CiviliteFournisseur updatedCiviliteFournisseur = civiliteFournisseurRepository.findById(civiliteFournisseur.getId()).get();
        // Disconnect from session so that the updates on updatedCiviliteFournisseur are not directly saved in db
        em.detach(updatedCiviliteFournisseur);
        updatedCiviliteFournisseur.civiliteFrLibelle(UPDATED_CIVILITE_FR_LIBELLE).civiliteFrCode(UPDATED_CIVILITE_FR_CODE);

        restCiviliteFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCiviliteFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCiviliteFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the CiviliteFournisseur in the database
        List<CiviliteFournisseur> civiliteFournisseurList = civiliteFournisseurRepository.findAll();
        assertThat(civiliteFournisseurList).hasSize(databaseSizeBeforeUpdate);
        CiviliteFournisseur testCiviliteFournisseur = civiliteFournisseurList.get(civiliteFournisseurList.size() - 1);
        assertThat(testCiviliteFournisseur.getCiviliteFrLibelle()).isEqualTo(UPDATED_CIVILITE_FR_LIBELLE);
        assertThat(testCiviliteFournisseur.getCiviliteFrCode()).isEqualTo(UPDATED_CIVILITE_FR_CODE);
    }

    @Test
    @Transactional
    void putNonExistingCiviliteFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = civiliteFournisseurRepository.findAll().size();
        civiliteFournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiviliteFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, civiliteFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(civiliteFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiviliteFournisseur in the database
        List<CiviliteFournisseur> civiliteFournisseurList = civiliteFournisseurRepository.findAll();
        assertThat(civiliteFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCiviliteFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = civiliteFournisseurRepository.findAll().size();
        civiliteFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCiviliteFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(civiliteFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiviliteFournisseur in the database
        List<CiviliteFournisseur> civiliteFournisseurList = civiliteFournisseurRepository.findAll();
        assertThat(civiliteFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCiviliteFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = civiliteFournisseurRepository.findAll().size();
        civiliteFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCiviliteFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(civiliteFournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CiviliteFournisseur in the database
        List<CiviliteFournisseur> civiliteFournisseurList = civiliteFournisseurRepository.findAll();
        assertThat(civiliteFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCiviliteFournisseurWithPatch() throws Exception {
        // Initialize the database
        civiliteFournisseurRepository.saveAndFlush(civiliteFournisseur);

        int databaseSizeBeforeUpdate = civiliteFournisseurRepository.findAll().size();

        // Update the civiliteFournisseur using partial update
        CiviliteFournisseur partialUpdatedCiviliteFournisseur = new CiviliteFournisseur();
        partialUpdatedCiviliteFournisseur.setId(civiliteFournisseur.getId());

        partialUpdatedCiviliteFournisseur.civiliteFrCode(UPDATED_CIVILITE_FR_CODE);

        restCiviliteFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCiviliteFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCiviliteFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the CiviliteFournisseur in the database
        List<CiviliteFournisseur> civiliteFournisseurList = civiliteFournisseurRepository.findAll();
        assertThat(civiliteFournisseurList).hasSize(databaseSizeBeforeUpdate);
        CiviliteFournisseur testCiviliteFournisseur = civiliteFournisseurList.get(civiliteFournisseurList.size() - 1);
        assertThat(testCiviliteFournisseur.getCiviliteFrLibelle()).isEqualTo(DEFAULT_CIVILITE_FR_LIBELLE);
        assertThat(testCiviliteFournisseur.getCiviliteFrCode()).isEqualTo(UPDATED_CIVILITE_FR_CODE);
    }

    @Test
    @Transactional
    void fullUpdateCiviliteFournisseurWithPatch() throws Exception {
        // Initialize the database
        civiliteFournisseurRepository.saveAndFlush(civiliteFournisseur);

        int databaseSizeBeforeUpdate = civiliteFournisseurRepository.findAll().size();

        // Update the civiliteFournisseur using partial update
        CiviliteFournisseur partialUpdatedCiviliteFournisseur = new CiviliteFournisseur();
        partialUpdatedCiviliteFournisseur.setId(civiliteFournisseur.getId());

        partialUpdatedCiviliteFournisseur.civiliteFrLibelle(UPDATED_CIVILITE_FR_LIBELLE).civiliteFrCode(UPDATED_CIVILITE_FR_CODE);

        restCiviliteFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCiviliteFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCiviliteFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the CiviliteFournisseur in the database
        List<CiviliteFournisseur> civiliteFournisseurList = civiliteFournisseurRepository.findAll();
        assertThat(civiliteFournisseurList).hasSize(databaseSizeBeforeUpdate);
        CiviliteFournisseur testCiviliteFournisseur = civiliteFournisseurList.get(civiliteFournisseurList.size() - 1);
        assertThat(testCiviliteFournisseur.getCiviliteFrLibelle()).isEqualTo(UPDATED_CIVILITE_FR_LIBELLE);
        assertThat(testCiviliteFournisseur.getCiviliteFrCode()).isEqualTo(UPDATED_CIVILITE_FR_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingCiviliteFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = civiliteFournisseurRepository.findAll().size();
        civiliteFournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiviliteFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, civiliteFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(civiliteFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiviliteFournisseur in the database
        List<CiviliteFournisseur> civiliteFournisseurList = civiliteFournisseurRepository.findAll();
        assertThat(civiliteFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCiviliteFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = civiliteFournisseurRepository.findAll().size();
        civiliteFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCiviliteFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(civiliteFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiviliteFournisseur in the database
        List<CiviliteFournisseur> civiliteFournisseurList = civiliteFournisseurRepository.findAll();
        assertThat(civiliteFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCiviliteFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = civiliteFournisseurRepository.findAll().size();
        civiliteFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCiviliteFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(civiliteFournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CiviliteFournisseur in the database
        List<CiviliteFournisseur> civiliteFournisseurList = civiliteFournisseurRepository.findAll();
        assertThat(civiliteFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCiviliteFournisseur() throws Exception {
        // Initialize the database
        civiliteFournisseurRepository.saveAndFlush(civiliteFournisseur);

        int databaseSizeBeforeDelete = civiliteFournisseurRepository.findAll().size();

        // Delete the civiliteFournisseur
        restCiviliteFournisseurMockMvc
            .perform(delete(ENTITY_API_URL_ID, civiliteFournisseur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CiviliteFournisseur> civiliteFournisseurList = civiliteFournisseurRepository.findAll();
        assertThat(civiliteFournisseurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
