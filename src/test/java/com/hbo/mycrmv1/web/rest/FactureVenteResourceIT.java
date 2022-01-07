package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.FactureVente;
import com.hbo.mycrmv1.repository.FactureVenteRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link FactureVenteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FactureVenteResourceIT {

    private static final Integer DEFAULT_VENTE_IDENT_FAC = 1;
    private static final Integer UPDATED_VENTE_IDENT_FAC = 2;

    private static final LocalDate DEFAULT_VENTE_DATE_EFFET = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VENTE_DATE_EFFET = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VENTE_DATE_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VENTE_DATE_UPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VENTE_STATUS_FACT = "AAAAAAAAAA";
    private static final String UPDATED_VENTE_STATUS_FACT = "BBBBBBBBBB";

    private static final Double DEFAULT_VENTE_MONTANT_HT = 1D;
    private static final Double UPDATED_VENTE_MONTANT_HT = 2D;

    private static final Double DEFAULT_VENTE_MONTANT_TVA = 1D;
    private static final Double UPDATED_VENTE_MONTANT_TVA = 2D;

    private static final Double DEFAULT_VENTE_MONTANT_TTC = 1D;
    private static final Double UPDATED_VENTE_MONTANT_TTC = 2D;

    private static final String ENTITY_API_URL = "/api/facture-ventes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FactureVenteRepository factureVenteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFactureVenteMockMvc;

    private FactureVente factureVente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactureVente createEntity(EntityManager em) {
        FactureVente factureVente = new FactureVente()
            .venteIdentFac(DEFAULT_VENTE_IDENT_FAC)
            .venteDateEffet(DEFAULT_VENTE_DATE_EFFET)
            .venteDateUpdate(DEFAULT_VENTE_DATE_UPDATE)
            .venteStatusFact(DEFAULT_VENTE_STATUS_FACT)
            .venteMontantHT(DEFAULT_VENTE_MONTANT_HT)
            .venteMontantTVA(DEFAULT_VENTE_MONTANT_TVA)
            .venteMontantTTC(DEFAULT_VENTE_MONTANT_TTC);
        return factureVente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactureVente createUpdatedEntity(EntityManager em) {
        FactureVente factureVente = new FactureVente()
            .venteIdentFac(UPDATED_VENTE_IDENT_FAC)
            .venteDateEffet(UPDATED_VENTE_DATE_EFFET)
            .venteDateUpdate(UPDATED_VENTE_DATE_UPDATE)
            .venteStatusFact(UPDATED_VENTE_STATUS_FACT)
            .venteMontantHT(UPDATED_VENTE_MONTANT_HT)
            .venteMontantTVA(UPDATED_VENTE_MONTANT_TVA)
            .venteMontantTTC(UPDATED_VENTE_MONTANT_TTC);
        return factureVente;
    }

    @BeforeEach
    public void initTest() {
        factureVente = createEntity(em);
    }

    @Test
    @Transactional
    void createFactureVente() throws Exception {
        int databaseSizeBeforeCreate = factureVenteRepository.findAll().size();
        // Create the FactureVente
        restFactureVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factureVente)))
            .andExpect(status().isCreated());

        // Validate the FactureVente in the database
        List<FactureVente> factureVenteList = factureVenteRepository.findAll();
        assertThat(factureVenteList).hasSize(databaseSizeBeforeCreate + 1);
        FactureVente testFactureVente = factureVenteList.get(factureVenteList.size() - 1);
        assertThat(testFactureVente.getVenteIdentFac()).isEqualTo(DEFAULT_VENTE_IDENT_FAC);
        assertThat(testFactureVente.getVenteDateEffet()).isEqualTo(DEFAULT_VENTE_DATE_EFFET);
        assertThat(testFactureVente.getVenteDateUpdate()).isEqualTo(DEFAULT_VENTE_DATE_UPDATE);
        assertThat(testFactureVente.getVenteStatusFact()).isEqualTo(DEFAULT_VENTE_STATUS_FACT);
        assertThat(testFactureVente.getVenteMontantHT()).isEqualTo(DEFAULT_VENTE_MONTANT_HT);
        assertThat(testFactureVente.getVenteMontantTVA()).isEqualTo(DEFAULT_VENTE_MONTANT_TVA);
        assertThat(testFactureVente.getVenteMontantTTC()).isEqualTo(DEFAULT_VENTE_MONTANT_TTC);
    }

    @Test
    @Transactional
    void createFactureVenteWithExistingId() throws Exception {
        // Create the FactureVente with an existing ID
        factureVente.setId(1L);

        int databaseSizeBeforeCreate = factureVenteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactureVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factureVente)))
            .andExpect(status().isBadRequest());

        // Validate the FactureVente in the database
        List<FactureVente> factureVenteList = factureVenteRepository.findAll();
        assertThat(factureVenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFactureVentes() throws Exception {
        // Initialize the database
        factureVenteRepository.saveAndFlush(factureVente);

        // Get all the factureVenteList
        restFactureVenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factureVente.getId().intValue())))
            .andExpect(jsonPath("$.[*].venteIdentFac").value(hasItem(DEFAULT_VENTE_IDENT_FAC)))
            .andExpect(jsonPath("$.[*].venteDateEffet").value(hasItem(DEFAULT_VENTE_DATE_EFFET.toString())))
            .andExpect(jsonPath("$.[*].venteDateUpdate").value(hasItem(DEFAULT_VENTE_DATE_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].venteStatusFact").value(hasItem(DEFAULT_VENTE_STATUS_FACT)))
            .andExpect(jsonPath("$.[*].venteMontantHT").value(hasItem(DEFAULT_VENTE_MONTANT_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].venteMontantTVA").value(hasItem(DEFAULT_VENTE_MONTANT_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].venteMontantTTC").value(hasItem(DEFAULT_VENTE_MONTANT_TTC.doubleValue())));
    }

    @Test
    @Transactional
    void getFactureVente() throws Exception {
        // Initialize the database
        factureVenteRepository.saveAndFlush(factureVente);

        // Get the factureVente
        restFactureVenteMockMvc
            .perform(get(ENTITY_API_URL_ID, factureVente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factureVente.getId().intValue()))
            .andExpect(jsonPath("$.venteIdentFac").value(DEFAULT_VENTE_IDENT_FAC))
            .andExpect(jsonPath("$.venteDateEffet").value(DEFAULT_VENTE_DATE_EFFET.toString()))
            .andExpect(jsonPath("$.venteDateUpdate").value(DEFAULT_VENTE_DATE_UPDATE.toString()))
            .andExpect(jsonPath("$.venteStatusFact").value(DEFAULT_VENTE_STATUS_FACT))
            .andExpect(jsonPath("$.venteMontantHT").value(DEFAULT_VENTE_MONTANT_HT.doubleValue()))
            .andExpect(jsonPath("$.venteMontantTVA").value(DEFAULT_VENTE_MONTANT_TVA.doubleValue()))
            .andExpect(jsonPath("$.venteMontantTTC").value(DEFAULT_VENTE_MONTANT_TTC.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingFactureVente() throws Exception {
        // Get the factureVente
        restFactureVenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFactureVente() throws Exception {
        // Initialize the database
        factureVenteRepository.saveAndFlush(factureVente);

        int databaseSizeBeforeUpdate = factureVenteRepository.findAll().size();

        // Update the factureVente
        FactureVente updatedFactureVente = factureVenteRepository.findById(factureVente.getId()).get();
        // Disconnect from session so that the updates on updatedFactureVente are not directly saved in db
        em.detach(updatedFactureVente);
        updatedFactureVente
            .venteIdentFac(UPDATED_VENTE_IDENT_FAC)
            .venteDateEffet(UPDATED_VENTE_DATE_EFFET)
            .venteDateUpdate(UPDATED_VENTE_DATE_UPDATE)
            .venteStatusFact(UPDATED_VENTE_STATUS_FACT)
            .venteMontantHT(UPDATED_VENTE_MONTANT_HT)
            .venteMontantTVA(UPDATED_VENTE_MONTANT_TVA)
            .venteMontantTTC(UPDATED_VENTE_MONTANT_TTC);

        restFactureVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFactureVente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFactureVente))
            )
            .andExpect(status().isOk());

        // Validate the FactureVente in the database
        List<FactureVente> factureVenteList = factureVenteRepository.findAll();
        assertThat(factureVenteList).hasSize(databaseSizeBeforeUpdate);
        FactureVente testFactureVente = factureVenteList.get(factureVenteList.size() - 1);
        assertThat(testFactureVente.getVenteIdentFac()).isEqualTo(UPDATED_VENTE_IDENT_FAC);
        assertThat(testFactureVente.getVenteDateEffet()).isEqualTo(UPDATED_VENTE_DATE_EFFET);
        assertThat(testFactureVente.getVenteDateUpdate()).isEqualTo(UPDATED_VENTE_DATE_UPDATE);
        assertThat(testFactureVente.getVenteStatusFact()).isEqualTo(UPDATED_VENTE_STATUS_FACT);
        assertThat(testFactureVente.getVenteMontantHT()).isEqualTo(UPDATED_VENTE_MONTANT_HT);
        assertThat(testFactureVente.getVenteMontantTVA()).isEqualTo(UPDATED_VENTE_MONTANT_TVA);
        assertThat(testFactureVente.getVenteMontantTTC()).isEqualTo(UPDATED_VENTE_MONTANT_TTC);
    }

    @Test
    @Transactional
    void putNonExistingFactureVente() throws Exception {
        int databaseSizeBeforeUpdate = factureVenteRepository.findAll().size();
        factureVente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factureVente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureVente))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureVente in the database
        List<FactureVente> factureVenteList = factureVenteRepository.findAll();
        assertThat(factureVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFactureVente() throws Exception {
        int databaseSizeBeforeUpdate = factureVenteRepository.findAll().size();
        factureVente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureVente))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureVente in the database
        List<FactureVente> factureVenteList = factureVenteRepository.findAll();
        assertThat(factureVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFactureVente() throws Exception {
        int databaseSizeBeforeUpdate = factureVenteRepository.findAll().size();
        factureVente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureVenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factureVente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactureVente in the database
        List<FactureVente> factureVenteList = factureVenteRepository.findAll();
        assertThat(factureVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFactureVenteWithPatch() throws Exception {
        // Initialize the database
        factureVenteRepository.saveAndFlush(factureVente);

        int databaseSizeBeforeUpdate = factureVenteRepository.findAll().size();

        // Update the factureVente using partial update
        FactureVente partialUpdatedFactureVente = new FactureVente();
        partialUpdatedFactureVente.setId(factureVente.getId());

        partialUpdatedFactureVente
            .venteIdentFac(UPDATED_VENTE_IDENT_FAC)
            .venteDateUpdate(UPDATED_VENTE_DATE_UPDATE)
            .venteMontantHT(UPDATED_VENTE_MONTANT_HT)
            .venteMontantTTC(UPDATED_VENTE_MONTANT_TTC);

        restFactureVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactureVente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactureVente))
            )
            .andExpect(status().isOk());

        // Validate the FactureVente in the database
        List<FactureVente> factureVenteList = factureVenteRepository.findAll();
        assertThat(factureVenteList).hasSize(databaseSizeBeforeUpdate);
        FactureVente testFactureVente = factureVenteList.get(factureVenteList.size() - 1);
        assertThat(testFactureVente.getVenteIdentFac()).isEqualTo(UPDATED_VENTE_IDENT_FAC);
        assertThat(testFactureVente.getVenteDateEffet()).isEqualTo(DEFAULT_VENTE_DATE_EFFET);
        assertThat(testFactureVente.getVenteDateUpdate()).isEqualTo(UPDATED_VENTE_DATE_UPDATE);
        assertThat(testFactureVente.getVenteStatusFact()).isEqualTo(DEFAULT_VENTE_STATUS_FACT);
        assertThat(testFactureVente.getVenteMontantHT()).isEqualTo(UPDATED_VENTE_MONTANT_HT);
        assertThat(testFactureVente.getVenteMontantTVA()).isEqualTo(DEFAULT_VENTE_MONTANT_TVA);
        assertThat(testFactureVente.getVenteMontantTTC()).isEqualTo(UPDATED_VENTE_MONTANT_TTC);
    }

    @Test
    @Transactional
    void fullUpdateFactureVenteWithPatch() throws Exception {
        // Initialize the database
        factureVenteRepository.saveAndFlush(factureVente);

        int databaseSizeBeforeUpdate = factureVenteRepository.findAll().size();

        // Update the factureVente using partial update
        FactureVente partialUpdatedFactureVente = new FactureVente();
        partialUpdatedFactureVente.setId(factureVente.getId());

        partialUpdatedFactureVente
            .venteIdentFac(UPDATED_VENTE_IDENT_FAC)
            .venteDateEffet(UPDATED_VENTE_DATE_EFFET)
            .venteDateUpdate(UPDATED_VENTE_DATE_UPDATE)
            .venteStatusFact(UPDATED_VENTE_STATUS_FACT)
            .venteMontantHT(UPDATED_VENTE_MONTANT_HT)
            .venteMontantTVA(UPDATED_VENTE_MONTANT_TVA)
            .venteMontantTTC(UPDATED_VENTE_MONTANT_TTC);

        restFactureVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactureVente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactureVente))
            )
            .andExpect(status().isOk());

        // Validate the FactureVente in the database
        List<FactureVente> factureVenteList = factureVenteRepository.findAll();
        assertThat(factureVenteList).hasSize(databaseSizeBeforeUpdate);
        FactureVente testFactureVente = factureVenteList.get(factureVenteList.size() - 1);
        assertThat(testFactureVente.getVenteIdentFac()).isEqualTo(UPDATED_VENTE_IDENT_FAC);
        assertThat(testFactureVente.getVenteDateEffet()).isEqualTo(UPDATED_VENTE_DATE_EFFET);
        assertThat(testFactureVente.getVenteDateUpdate()).isEqualTo(UPDATED_VENTE_DATE_UPDATE);
        assertThat(testFactureVente.getVenteStatusFact()).isEqualTo(UPDATED_VENTE_STATUS_FACT);
        assertThat(testFactureVente.getVenteMontantHT()).isEqualTo(UPDATED_VENTE_MONTANT_HT);
        assertThat(testFactureVente.getVenteMontantTVA()).isEqualTo(UPDATED_VENTE_MONTANT_TVA);
        assertThat(testFactureVente.getVenteMontantTTC()).isEqualTo(UPDATED_VENTE_MONTANT_TTC);
    }

    @Test
    @Transactional
    void patchNonExistingFactureVente() throws Exception {
        int databaseSizeBeforeUpdate = factureVenteRepository.findAll().size();
        factureVente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, factureVente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factureVente))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureVente in the database
        List<FactureVente> factureVenteList = factureVenteRepository.findAll();
        assertThat(factureVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFactureVente() throws Exception {
        int databaseSizeBeforeUpdate = factureVenteRepository.findAll().size();
        factureVente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factureVente))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureVente in the database
        List<FactureVente> factureVenteList = factureVenteRepository.findAll();
        assertThat(factureVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFactureVente() throws Exception {
        int databaseSizeBeforeUpdate = factureVenteRepository.findAll().size();
        factureVente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureVenteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(factureVente))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactureVente in the database
        List<FactureVente> factureVenteList = factureVenteRepository.findAll();
        assertThat(factureVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFactureVente() throws Exception {
        // Initialize the database
        factureVenteRepository.saveAndFlush(factureVente);

        int databaseSizeBeforeDelete = factureVenteRepository.findAll().size();

        // Delete the factureVente
        restFactureVenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, factureVente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FactureVente> factureVenteList = factureVenteRepository.findAll();
        assertThat(factureVenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
