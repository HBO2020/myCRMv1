package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.PayementFournisseur;
import com.hbo.mycrmv1.repository.PayementFournisseurRepository;
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
 * Integration tests for the {@link PayementFournisseurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PayementFournisseurResourceIT {

    private static final Integer DEFAULT_PAYEMENT_FR_IDENT = 1;
    private static final Integer UPDATED_PAYEMENT_FR_IDENT = 2;

    private static final LocalDate DEFAULT_PAYEMENT_FR_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYEMENT_FR_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PAYEMENT_FR_MODE = "AAAAAAAAAA";
    private static final String UPDATED_PAYEMENT_FR_MODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAYEMENT_FR_ECHEANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYEMENT_FR_ECHEANCE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PAYEMENT_FR_MONTANT = 1D;
    private static final Double UPDATED_PAYEMENT_FR_MONTANT = 2D;

    private static final String ENTITY_API_URL = "/api/payement-fournisseurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PayementFournisseurRepository payementFournisseurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPayementFournisseurMockMvc;

    private PayementFournisseur payementFournisseur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayementFournisseur createEntity(EntityManager em) {
        PayementFournisseur payementFournisseur = new PayementFournisseur()
            .payementFrIdent(DEFAULT_PAYEMENT_FR_IDENT)
            .payementFrDate(DEFAULT_PAYEMENT_FR_DATE)
            .payementFrMode(DEFAULT_PAYEMENT_FR_MODE)
            .payementFrEcheance(DEFAULT_PAYEMENT_FR_ECHEANCE)
            .payementFrMontant(DEFAULT_PAYEMENT_FR_MONTANT);
        return payementFournisseur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayementFournisseur createUpdatedEntity(EntityManager em) {
        PayementFournisseur payementFournisseur = new PayementFournisseur()
            .payementFrIdent(UPDATED_PAYEMENT_FR_IDENT)
            .payementFrDate(UPDATED_PAYEMENT_FR_DATE)
            .payementFrMode(UPDATED_PAYEMENT_FR_MODE)
            .payementFrEcheance(UPDATED_PAYEMENT_FR_ECHEANCE)
            .payementFrMontant(UPDATED_PAYEMENT_FR_MONTANT);
        return payementFournisseur;
    }

    @BeforeEach
    public void initTest() {
        payementFournisseur = createEntity(em);
    }

    @Test
    @Transactional
    void createPayementFournisseur() throws Exception {
        int databaseSizeBeforeCreate = payementFournisseurRepository.findAll().size();
        // Create the PayementFournisseur
        restPayementFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payementFournisseur))
            )
            .andExpect(status().isCreated());

        // Validate the PayementFournisseur in the database
        List<PayementFournisseur> payementFournisseurList = payementFournisseurRepository.findAll();
        assertThat(payementFournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        PayementFournisseur testPayementFournisseur = payementFournisseurList.get(payementFournisseurList.size() - 1);
        assertThat(testPayementFournisseur.getPayementFrIdent()).isEqualTo(DEFAULT_PAYEMENT_FR_IDENT);
        assertThat(testPayementFournisseur.getPayementFrDate()).isEqualTo(DEFAULT_PAYEMENT_FR_DATE);
        assertThat(testPayementFournisseur.getPayementFrMode()).isEqualTo(DEFAULT_PAYEMENT_FR_MODE);
        assertThat(testPayementFournisseur.getPayementFrEcheance()).isEqualTo(DEFAULT_PAYEMENT_FR_ECHEANCE);
        assertThat(testPayementFournisseur.getPayementFrMontant()).isEqualTo(DEFAULT_PAYEMENT_FR_MONTANT);
    }

    @Test
    @Transactional
    void createPayementFournisseurWithExistingId() throws Exception {
        // Create the PayementFournisseur with an existing ID
        payementFournisseur.setId(1L);

        int databaseSizeBeforeCreate = payementFournisseurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayementFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payementFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayementFournisseur in the database
        List<PayementFournisseur> payementFournisseurList = payementFournisseurRepository.findAll();
        assertThat(payementFournisseurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayementFournisseurs() throws Exception {
        // Initialize the database
        payementFournisseurRepository.saveAndFlush(payementFournisseur);

        // Get all the payementFournisseurList
        restPayementFournisseurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payementFournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].payementFrIdent").value(hasItem(DEFAULT_PAYEMENT_FR_IDENT)))
            .andExpect(jsonPath("$.[*].payementFrDate").value(hasItem(DEFAULT_PAYEMENT_FR_DATE.toString())))
            .andExpect(jsonPath("$.[*].payementFrMode").value(hasItem(DEFAULT_PAYEMENT_FR_MODE)))
            .andExpect(jsonPath("$.[*].payementFrEcheance").value(hasItem(DEFAULT_PAYEMENT_FR_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].payementFrMontant").value(hasItem(DEFAULT_PAYEMENT_FR_MONTANT.doubleValue())));
    }

    @Test
    @Transactional
    void getPayementFournisseur() throws Exception {
        // Initialize the database
        payementFournisseurRepository.saveAndFlush(payementFournisseur);

        // Get the payementFournisseur
        restPayementFournisseurMockMvc
            .perform(get(ENTITY_API_URL_ID, payementFournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payementFournisseur.getId().intValue()))
            .andExpect(jsonPath("$.payementFrIdent").value(DEFAULT_PAYEMENT_FR_IDENT))
            .andExpect(jsonPath("$.payementFrDate").value(DEFAULT_PAYEMENT_FR_DATE.toString()))
            .andExpect(jsonPath("$.payementFrMode").value(DEFAULT_PAYEMENT_FR_MODE))
            .andExpect(jsonPath("$.payementFrEcheance").value(DEFAULT_PAYEMENT_FR_ECHEANCE.toString()))
            .andExpect(jsonPath("$.payementFrMontant").value(DEFAULT_PAYEMENT_FR_MONTANT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingPayementFournisseur() throws Exception {
        // Get the payementFournisseur
        restPayementFournisseurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPayementFournisseur() throws Exception {
        // Initialize the database
        payementFournisseurRepository.saveAndFlush(payementFournisseur);

        int databaseSizeBeforeUpdate = payementFournisseurRepository.findAll().size();

        // Update the payementFournisseur
        PayementFournisseur updatedPayementFournisseur = payementFournisseurRepository.findById(payementFournisseur.getId()).get();
        // Disconnect from session so that the updates on updatedPayementFournisseur are not directly saved in db
        em.detach(updatedPayementFournisseur);
        updatedPayementFournisseur
            .payementFrIdent(UPDATED_PAYEMENT_FR_IDENT)
            .payementFrDate(UPDATED_PAYEMENT_FR_DATE)
            .payementFrMode(UPDATED_PAYEMENT_FR_MODE)
            .payementFrEcheance(UPDATED_PAYEMENT_FR_ECHEANCE)
            .payementFrMontant(UPDATED_PAYEMENT_FR_MONTANT);

        restPayementFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPayementFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPayementFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the PayementFournisseur in the database
        List<PayementFournisseur> payementFournisseurList = payementFournisseurRepository.findAll();
        assertThat(payementFournisseurList).hasSize(databaseSizeBeforeUpdate);
        PayementFournisseur testPayementFournisseur = payementFournisseurList.get(payementFournisseurList.size() - 1);
        assertThat(testPayementFournisseur.getPayementFrIdent()).isEqualTo(UPDATED_PAYEMENT_FR_IDENT);
        assertThat(testPayementFournisseur.getPayementFrDate()).isEqualTo(UPDATED_PAYEMENT_FR_DATE);
        assertThat(testPayementFournisseur.getPayementFrMode()).isEqualTo(UPDATED_PAYEMENT_FR_MODE);
        assertThat(testPayementFournisseur.getPayementFrEcheance()).isEqualTo(UPDATED_PAYEMENT_FR_ECHEANCE);
        assertThat(testPayementFournisseur.getPayementFrMontant()).isEqualTo(UPDATED_PAYEMENT_FR_MONTANT);
    }

    @Test
    @Transactional
    void putNonExistingPayementFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = payementFournisseurRepository.findAll().size();
        payementFournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayementFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payementFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payementFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayementFournisseur in the database
        List<PayementFournisseur> payementFournisseurList = payementFournisseurRepository.findAll();
        assertThat(payementFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayementFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = payementFournisseurRepository.findAll().size();
        payementFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayementFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payementFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayementFournisseur in the database
        List<PayementFournisseur> payementFournisseurList = payementFournisseurRepository.findAll();
        assertThat(payementFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayementFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = payementFournisseurRepository.findAll().size();
        payementFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayementFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payementFournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayementFournisseur in the database
        List<PayementFournisseur> payementFournisseurList = payementFournisseurRepository.findAll();
        assertThat(payementFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePayementFournisseurWithPatch() throws Exception {
        // Initialize the database
        payementFournisseurRepository.saveAndFlush(payementFournisseur);

        int databaseSizeBeforeUpdate = payementFournisseurRepository.findAll().size();

        // Update the payementFournisseur using partial update
        PayementFournisseur partialUpdatedPayementFournisseur = new PayementFournisseur();
        partialUpdatedPayementFournisseur.setId(payementFournisseur.getId());

        partialUpdatedPayementFournisseur
            .payementFrIdent(UPDATED_PAYEMENT_FR_IDENT)
            .payementFrMode(UPDATED_PAYEMENT_FR_MODE)
            .payementFrMontant(UPDATED_PAYEMENT_FR_MONTANT);

        restPayementFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayementFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayementFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the PayementFournisseur in the database
        List<PayementFournisseur> payementFournisseurList = payementFournisseurRepository.findAll();
        assertThat(payementFournisseurList).hasSize(databaseSizeBeforeUpdate);
        PayementFournisseur testPayementFournisseur = payementFournisseurList.get(payementFournisseurList.size() - 1);
        assertThat(testPayementFournisseur.getPayementFrIdent()).isEqualTo(UPDATED_PAYEMENT_FR_IDENT);
        assertThat(testPayementFournisseur.getPayementFrDate()).isEqualTo(DEFAULT_PAYEMENT_FR_DATE);
        assertThat(testPayementFournisseur.getPayementFrMode()).isEqualTo(UPDATED_PAYEMENT_FR_MODE);
        assertThat(testPayementFournisseur.getPayementFrEcheance()).isEqualTo(DEFAULT_PAYEMENT_FR_ECHEANCE);
        assertThat(testPayementFournisseur.getPayementFrMontant()).isEqualTo(UPDATED_PAYEMENT_FR_MONTANT);
    }

    @Test
    @Transactional
    void fullUpdatePayementFournisseurWithPatch() throws Exception {
        // Initialize the database
        payementFournisseurRepository.saveAndFlush(payementFournisseur);

        int databaseSizeBeforeUpdate = payementFournisseurRepository.findAll().size();

        // Update the payementFournisseur using partial update
        PayementFournisseur partialUpdatedPayementFournisseur = new PayementFournisseur();
        partialUpdatedPayementFournisseur.setId(payementFournisseur.getId());

        partialUpdatedPayementFournisseur
            .payementFrIdent(UPDATED_PAYEMENT_FR_IDENT)
            .payementFrDate(UPDATED_PAYEMENT_FR_DATE)
            .payementFrMode(UPDATED_PAYEMENT_FR_MODE)
            .payementFrEcheance(UPDATED_PAYEMENT_FR_ECHEANCE)
            .payementFrMontant(UPDATED_PAYEMENT_FR_MONTANT);

        restPayementFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayementFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayementFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the PayementFournisseur in the database
        List<PayementFournisseur> payementFournisseurList = payementFournisseurRepository.findAll();
        assertThat(payementFournisseurList).hasSize(databaseSizeBeforeUpdate);
        PayementFournisseur testPayementFournisseur = payementFournisseurList.get(payementFournisseurList.size() - 1);
        assertThat(testPayementFournisseur.getPayementFrIdent()).isEqualTo(UPDATED_PAYEMENT_FR_IDENT);
        assertThat(testPayementFournisseur.getPayementFrDate()).isEqualTo(UPDATED_PAYEMENT_FR_DATE);
        assertThat(testPayementFournisseur.getPayementFrMode()).isEqualTo(UPDATED_PAYEMENT_FR_MODE);
        assertThat(testPayementFournisseur.getPayementFrEcheance()).isEqualTo(UPDATED_PAYEMENT_FR_ECHEANCE);
        assertThat(testPayementFournisseur.getPayementFrMontant()).isEqualTo(UPDATED_PAYEMENT_FR_MONTANT);
    }

    @Test
    @Transactional
    void patchNonExistingPayementFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = payementFournisseurRepository.findAll().size();
        payementFournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayementFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payementFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payementFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayementFournisseur in the database
        List<PayementFournisseur> payementFournisseurList = payementFournisseurRepository.findAll();
        assertThat(payementFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayementFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = payementFournisseurRepository.findAll().size();
        payementFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayementFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payementFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayementFournisseur in the database
        List<PayementFournisseur> payementFournisseurList = payementFournisseurRepository.findAll();
        assertThat(payementFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayementFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = payementFournisseurRepository.findAll().size();
        payementFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayementFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payementFournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayementFournisseur in the database
        List<PayementFournisseur> payementFournisseurList = payementFournisseurRepository.findAll();
        assertThat(payementFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayementFournisseur() throws Exception {
        // Initialize the database
        payementFournisseurRepository.saveAndFlush(payementFournisseur);

        int databaseSizeBeforeDelete = payementFournisseurRepository.findAll().size();

        // Delete the payementFournisseur
        restPayementFournisseurMockMvc
            .perform(delete(ENTITY_API_URL_ID, payementFournisseur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PayementFournisseur> payementFournisseurList = payementFournisseurRepository.findAll();
        assertThat(payementFournisseurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
