package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.FactureAchat;
import com.hbo.mycrmv1.repository.FactureAchatRepository;
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
 * Integration tests for the {@link FactureAchatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FactureAchatResourceIT {

    private static final Integer DEFAULT_ACHAT_IDENT_FAC = 1;
    private static final Integer UPDATED_ACHAT_IDENT_FAC = 2;

    private static final LocalDate DEFAULT_ACHAT_DATE_EFFET = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACHAT_DATE_EFFET = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACHAT_DATE_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACHAT_DATE_UPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ACHAT_STATUS_FACT = "AAAAAAAAAA";
    private static final String UPDATED_ACHAT_STATUS_FACT = "BBBBBBBBBB";

    private static final Double DEFAULT_ACHAT_MONTANT_HT = 1D;
    private static final Double UPDATED_ACHAT_MONTANT_HT = 2D;

    private static final Double DEFAULT_ACHAT_MONTANT_TVA = 1D;
    private static final Double UPDATED_ACHAT_MONTANT_TVA = 2D;

    private static final Double DEFAULT_ACHAT_MONTANT_TTC = 1D;
    private static final Double UPDATED_ACHAT_MONTANT_TTC = 2D;

    private static final String ENTITY_API_URL = "/api/facture-achats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FactureAchatRepository factureAchatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFactureAchatMockMvc;

    private FactureAchat factureAchat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactureAchat createEntity(EntityManager em) {
        FactureAchat factureAchat = new FactureAchat()
            .achatIdentFac(DEFAULT_ACHAT_IDENT_FAC)
            .achatDateEffet(DEFAULT_ACHAT_DATE_EFFET)
            .achatDateUpdate(DEFAULT_ACHAT_DATE_UPDATE)
            .achatStatusFact(DEFAULT_ACHAT_STATUS_FACT)
            .achatMontantHT(DEFAULT_ACHAT_MONTANT_HT)
            .achatMontantTVA(DEFAULT_ACHAT_MONTANT_TVA)
            .achatMontantTTC(DEFAULT_ACHAT_MONTANT_TTC);
        return factureAchat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactureAchat createUpdatedEntity(EntityManager em) {
        FactureAchat factureAchat = new FactureAchat()
            .achatIdentFac(UPDATED_ACHAT_IDENT_FAC)
            .achatDateEffet(UPDATED_ACHAT_DATE_EFFET)
            .achatDateUpdate(UPDATED_ACHAT_DATE_UPDATE)
            .achatStatusFact(UPDATED_ACHAT_STATUS_FACT)
            .achatMontantHT(UPDATED_ACHAT_MONTANT_HT)
            .achatMontantTVA(UPDATED_ACHAT_MONTANT_TVA)
            .achatMontantTTC(UPDATED_ACHAT_MONTANT_TTC);
        return factureAchat;
    }

    @BeforeEach
    public void initTest() {
        factureAchat = createEntity(em);
    }

    @Test
    @Transactional
    void createFactureAchat() throws Exception {
        int databaseSizeBeforeCreate = factureAchatRepository.findAll().size();
        // Create the FactureAchat
        restFactureAchatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factureAchat)))
            .andExpect(status().isCreated());

        // Validate the FactureAchat in the database
        List<FactureAchat> factureAchatList = factureAchatRepository.findAll();
        assertThat(factureAchatList).hasSize(databaseSizeBeforeCreate + 1);
        FactureAchat testFactureAchat = factureAchatList.get(factureAchatList.size() - 1);
        assertThat(testFactureAchat.getAchatIdentFac()).isEqualTo(DEFAULT_ACHAT_IDENT_FAC);
        assertThat(testFactureAchat.getAchatDateEffet()).isEqualTo(DEFAULT_ACHAT_DATE_EFFET);
        assertThat(testFactureAchat.getAchatDateUpdate()).isEqualTo(DEFAULT_ACHAT_DATE_UPDATE);
        assertThat(testFactureAchat.getAchatStatusFact()).isEqualTo(DEFAULT_ACHAT_STATUS_FACT);
        assertThat(testFactureAchat.getAchatMontantHT()).isEqualTo(DEFAULT_ACHAT_MONTANT_HT);
        assertThat(testFactureAchat.getAchatMontantTVA()).isEqualTo(DEFAULT_ACHAT_MONTANT_TVA);
        assertThat(testFactureAchat.getAchatMontantTTC()).isEqualTo(DEFAULT_ACHAT_MONTANT_TTC);
    }

    @Test
    @Transactional
    void createFactureAchatWithExistingId() throws Exception {
        // Create the FactureAchat with an existing ID
        factureAchat.setId(1L);

        int databaseSizeBeforeCreate = factureAchatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactureAchatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factureAchat)))
            .andExpect(status().isBadRequest());

        // Validate the FactureAchat in the database
        List<FactureAchat> factureAchatList = factureAchatRepository.findAll();
        assertThat(factureAchatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFactureAchats() throws Exception {
        // Initialize the database
        factureAchatRepository.saveAndFlush(factureAchat);

        // Get all the factureAchatList
        restFactureAchatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factureAchat.getId().intValue())))
            .andExpect(jsonPath("$.[*].achatIdentFac").value(hasItem(DEFAULT_ACHAT_IDENT_FAC)))
            .andExpect(jsonPath("$.[*].achatDateEffet").value(hasItem(DEFAULT_ACHAT_DATE_EFFET.toString())))
            .andExpect(jsonPath("$.[*].achatDateUpdate").value(hasItem(DEFAULT_ACHAT_DATE_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].achatStatusFact").value(hasItem(DEFAULT_ACHAT_STATUS_FACT)))
            .andExpect(jsonPath("$.[*].achatMontantHT").value(hasItem(DEFAULT_ACHAT_MONTANT_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].achatMontantTVA").value(hasItem(DEFAULT_ACHAT_MONTANT_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].achatMontantTTC").value(hasItem(DEFAULT_ACHAT_MONTANT_TTC.doubleValue())));
    }

    @Test
    @Transactional
    void getFactureAchat() throws Exception {
        // Initialize the database
        factureAchatRepository.saveAndFlush(factureAchat);

        // Get the factureAchat
        restFactureAchatMockMvc
            .perform(get(ENTITY_API_URL_ID, factureAchat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factureAchat.getId().intValue()))
            .andExpect(jsonPath("$.achatIdentFac").value(DEFAULT_ACHAT_IDENT_FAC))
            .andExpect(jsonPath("$.achatDateEffet").value(DEFAULT_ACHAT_DATE_EFFET.toString()))
            .andExpect(jsonPath("$.achatDateUpdate").value(DEFAULT_ACHAT_DATE_UPDATE.toString()))
            .andExpect(jsonPath("$.achatStatusFact").value(DEFAULT_ACHAT_STATUS_FACT))
            .andExpect(jsonPath("$.achatMontantHT").value(DEFAULT_ACHAT_MONTANT_HT.doubleValue()))
            .andExpect(jsonPath("$.achatMontantTVA").value(DEFAULT_ACHAT_MONTANT_TVA.doubleValue()))
            .andExpect(jsonPath("$.achatMontantTTC").value(DEFAULT_ACHAT_MONTANT_TTC.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingFactureAchat() throws Exception {
        // Get the factureAchat
        restFactureAchatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFactureAchat() throws Exception {
        // Initialize the database
        factureAchatRepository.saveAndFlush(factureAchat);

        int databaseSizeBeforeUpdate = factureAchatRepository.findAll().size();

        // Update the factureAchat
        FactureAchat updatedFactureAchat = factureAchatRepository.findById(factureAchat.getId()).get();
        // Disconnect from session so that the updates on updatedFactureAchat are not directly saved in db
        em.detach(updatedFactureAchat);
        updatedFactureAchat
            .achatIdentFac(UPDATED_ACHAT_IDENT_FAC)
            .achatDateEffet(UPDATED_ACHAT_DATE_EFFET)
            .achatDateUpdate(UPDATED_ACHAT_DATE_UPDATE)
            .achatStatusFact(UPDATED_ACHAT_STATUS_FACT)
            .achatMontantHT(UPDATED_ACHAT_MONTANT_HT)
            .achatMontantTVA(UPDATED_ACHAT_MONTANT_TVA)
            .achatMontantTTC(UPDATED_ACHAT_MONTANT_TTC);

        restFactureAchatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFactureAchat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFactureAchat))
            )
            .andExpect(status().isOk());

        // Validate the FactureAchat in the database
        List<FactureAchat> factureAchatList = factureAchatRepository.findAll();
        assertThat(factureAchatList).hasSize(databaseSizeBeforeUpdate);
        FactureAchat testFactureAchat = factureAchatList.get(factureAchatList.size() - 1);
        assertThat(testFactureAchat.getAchatIdentFac()).isEqualTo(UPDATED_ACHAT_IDENT_FAC);
        assertThat(testFactureAchat.getAchatDateEffet()).isEqualTo(UPDATED_ACHAT_DATE_EFFET);
        assertThat(testFactureAchat.getAchatDateUpdate()).isEqualTo(UPDATED_ACHAT_DATE_UPDATE);
        assertThat(testFactureAchat.getAchatStatusFact()).isEqualTo(UPDATED_ACHAT_STATUS_FACT);
        assertThat(testFactureAchat.getAchatMontantHT()).isEqualTo(UPDATED_ACHAT_MONTANT_HT);
        assertThat(testFactureAchat.getAchatMontantTVA()).isEqualTo(UPDATED_ACHAT_MONTANT_TVA);
        assertThat(testFactureAchat.getAchatMontantTTC()).isEqualTo(UPDATED_ACHAT_MONTANT_TTC);
    }

    @Test
    @Transactional
    void putNonExistingFactureAchat() throws Exception {
        int databaseSizeBeforeUpdate = factureAchatRepository.findAll().size();
        factureAchat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureAchatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factureAchat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureAchat))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureAchat in the database
        List<FactureAchat> factureAchatList = factureAchatRepository.findAll();
        assertThat(factureAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFactureAchat() throws Exception {
        int databaseSizeBeforeUpdate = factureAchatRepository.findAll().size();
        factureAchat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureAchatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureAchat))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureAchat in the database
        List<FactureAchat> factureAchatList = factureAchatRepository.findAll();
        assertThat(factureAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFactureAchat() throws Exception {
        int databaseSizeBeforeUpdate = factureAchatRepository.findAll().size();
        factureAchat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureAchatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factureAchat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactureAchat in the database
        List<FactureAchat> factureAchatList = factureAchatRepository.findAll();
        assertThat(factureAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFactureAchatWithPatch() throws Exception {
        // Initialize the database
        factureAchatRepository.saveAndFlush(factureAchat);

        int databaseSizeBeforeUpdate = factureAchatRepository.findAll().size();

        // Update the factureAchat using partial update
        FactureAchat partialUpdatedFactureAchat = new FactureAchat();
        partialUpdatedFactureAchat.setId(factureAchat.getId());

        partialUpdatedFactureAchat
            .achatIdentFac(UPDATED_ACHAT_IDENT_FAC)
            .achatStatusFact(UPDATED_ACHAT_STATUS_FACT)
            .achatMontantHT(UPDATED_ACHAT_MONTANT_HT)
            .achatMontantTTC(UPDATED_ACHAT_MONTANT_TTC);

        restFactureAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactureAchat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactureAchat))
            )
            .andExpect(status().isOk());

        // Validate the FactureAchat in the database
        List<FactureAchat> factureAchatList = factureAchatRepository.findAll();
        assertThat(factureAchatList).hasSize(databaseSizeBeforeUpdate);
        FactureAchat testFactureAchat = factureAchatList.get(factureAchatList.size() - 1);
        assertThat(testFactureAchat.getAchatIdentFac()).isEqualTo(UPDATED_ACHAT_IDENT_FAC);
        assertThat(testFactureAchat.getAchatDateEffet()).isEqualTo(DEFAULT_ACHAT_DATE_EFFET);
        assertThat(testFactureAchat.getAchatDateUpdate()).isEqualTo(DEFAULT_ACHAT_DATE_UPDATE);
        assertThat(testFactureAchat.getAchatStatusFact()).isEqualTo(UPDATED_ACHAT_STATUS_FACT);
        assertThat(testFactureAchat.getAchatMontantHT()).isEqualTo(UPDATED_ACHAT_MONTANT_HT);
        assertThat(testFactureAchat.getAchatMontantTVA()).isEqualTo(DEFAULT_ACHAT_MONTANT_TVA);
        assertThat(testFactureAchat.getAchatMontantTTC()).isEqualTo(UPDATED_ACHAT_MONTANT_TTC);
    }

    @Test
    @Transactional
    void fullUpdateFactureAchatWithPatch() throws Exception {
        // Initialize the database
        factureAchatRepository.saveAndFlush(factureAchat);

        int databaseSizeBeforeUpdate = factureAchatRepository.findAll().size();

        // Update the factureAchat using partial update
        FactureAchat partialUpdatedFactureAchat = new FactureAchat();
        partialUpdatedFactureAchat.setId(factureAchat.getId());

        partialUpdatedFactureAchat
            .achatIdentFac(UPDATED_ACHAT_IDENT_FAC)
            .achatDateEffet(UPDATED_ACHAT_DATE_EFFET)
            .achatDateUpdate(UPDATED_ACHAT_DATE_UPDATE)
            .achatStatusFact(UPDATED_ACHAT_STATUS_FACT)
            .achatMontantHT(UPDATED_ACHAT_MONTANT_HT)
            .achatMontantTVA(UPDATED_ACHAT_MONTANT_TVA)
            .achatMontantTTC(UPDATED_ACHAT_MONTANT_TTC);

        restFactureAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactureAchat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactureAchat))
            )
            .andExpect(status().isOk());

        // Validate the FactureAchat in the database
        List<FactureAchat> factureAchatList = factureAchatRepository.findAll();
        assertThat(factureAchatList).hasSize(databaseSizeBeforeUpdate);
        FactureAchat testFactureAchat = factureAchatList.get(factureAchatList.size() - 1);
        assertThat(testFactureAchat.getAchatIdentFac()).isEqualTo(UPDATED_ACHAT_IDENT_FAC);
        assertThat(testFactureAchat.getAchatDateEffet()).isEqualTo(UPDATED_ACHAT_DATE_EFFET);
        assertThat(testFactureAchat.getAchatDateUpdate()).isEqualTo(UPDATED_ACHAT_DATE_UPDATE);
        assertThat(testFactureAchat.getAchatStatusFact()).isEqualTo(UPDATED_ACHAT_STATUS_FACT);
        assertThat(testFactureAchat.getAchatMontantHT()).isEqualTo(UPDATED_ACHAT_MONTANT_HT);
        assertThat(testFactureAchat.getAchatMontantTVA()).isEqualTo(UPDATED_ACHAT_MONTANT_TVA);
        assertThat(testFactureAchat.getAchatMontantTTC()).isEqualTo(UPDATED_ACHAT_MONTANT_TTC);
    }

    @Test
    @Transactional
    void patchNonExistingFactureAchat() throws Exception {
        int databaseSizeBeforeUpdate = factureAchatRepository.findAll().size();
        factureAchat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, factureAchat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factureAchat))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureAchat in the database
        List<FactureAchat> factureAchatList = factureAchatRepository.findAll();
        assertThat(factureAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFactureAchat() throws Exception {
        int databaseSizeBeforeUpdate = factureAchatRepository.findAll().size();
        factureAchat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factureAchat))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureAchat in the database
        List<FactureAchat> factureAchatList = factureAchatRepository.findAll();
        assertThat(factureAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFactureAchat() throws Exception {
        int databaseSizeBeforeUpdate = factureAchatRepository.findAll().size();
        factureAchat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureAchatMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(factureAchat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactureAchat in the database
        List<FactureAchat> factureAchatList = factureAchatRepository.findAll();
        assertThat(factureAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFactureAchat() throws Exception {
        // Initialize the database
        factureAchatRepository.saveAndFlush(factureAchat);

        int databaseSizeBeforeDelete = factureAchatRepository.findAll().size();

        // Delete the factureAchat
        restFactureAchatMockMvc
            .perform(delete(ENTITY_API_URL_ID, factureAchat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FactureAchat> factureAchatList = factureAchatRepository.findAll();
        assertThat(factureAchatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
