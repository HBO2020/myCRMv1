package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.PayementClient;
import com.hbo.mycrmv1.repository.PayementClientRepository;
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
 * Integration tests for the {@link PayementClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PayementClientResourceIT {

    private static final Integer DEFAULT_PAYEMENT_CL_IDENT = 1;
    private static final Integer UPDATED_PAYEMENT_CL_IDENT = 2;

    private static final LocalDate DEFAULT_PAYEMENT_CL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYEMENT_CL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PAYEMENT_CL_MODE = "AAAAAAAAAA";
    private static final String UPDATED_PAYEMENT_CL_MODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAYEMENT_CL_ECHEANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYEMENT_CL_ECHEANCE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PAYEMENT_CL_MONTANT = 1D;
    private static final Double UPDATED_PAYEMENT_CL_MONTANT = 2D;

    private static final String ENTITY_API_URL = "/api/payement-clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PayementClientRepository payementClientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPayementClientMockMvc;

    private PayementClient payementClient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayementClient createEntity(EntityManager em) {
        PayementClient payementClient = new PayementClient()
            .payementClIdent(DEFAULT_PAYEMENT_CL_IDENT)
            .payementClDate(DEFAULT_PAYEMENT_CL_DATE)
            .payementClMode(DEFAULT_PAYEMENT_CL_MODE)
            .payementClEcheance(DEFAULT_PAYEMENT_CL_ECHEANCE)
            .payementClMontant(DEFAULT_PAYEMENT_CL_MONTANT);
        return payementClient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayementClient createUpdatedEntity(EntityManager em) {
        PayementClient payementClient = new PayementClient()
            .payementClIdent(UPDATED_PAYEMENT_CL_IDENT)
            .payementClDate(UPDATED_PAYEMENT_CL_DATE)
            .payementClMode(UPDATED_PAYEMENT_CL_MODE)
            .payementClEcheance(UPDATED_PAYEMENT_CL_ECHEANCE)
            .payementClMontant(UPDATED_PAYEMENT_CL_MONTANT);
        return payementClient;
    }

    @BeforeEach
    public void initTest() {
        payementClient = createEntity(em);
    }

    @Test
    @Transactional
    void createPayementClient() throws Exception {
        int databaseSizeBeforeCreate = payementClientRepository.findAll().size();
        // Create the PayementClient
        restPayementClientMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payementClient))
            )
            .andExpect(status().isCreated());

        // Validate the PayementClient in the database
        List<PayementClient> payementClientList = payementClientRepository.findAll();
        assertThat(payementClientList).hasSize(databaseSizeBeforeCreate + 1);
        PayementClient testPayementClient = payementClientList.get(payementClientList.size() - 1);
        assertThat(testPayementClient.getPayementClIdent()).isEqualTo(DEFAULT_PAYEMENT_CL_IDENT);
        assertThat(testPayementClient.getPayementClDate()).isEqualTo(DEFAULT_PAYEMENT_CL_DATE);
        assertThat(testPayementClient.getPayementClMode()).isEqualTo(DEFAULT_PAYEMENT_CL_MODE);
        assertThat(testPayementClient.getPayementClEcheance()).isEqualTo(DEFAULT_PAYEMENT_CL_ECHEANCE);
        assertThat(testPayementClient.getPayementClMontant()).isEqualTo(DEFAULT_PAYEMENT_CL_MONTANT);
    }

    @Test
    @Transactional
    void createPayementClientWithExistingId() throws Exception {
        // Create the PayementClient with an existing ID
        payementClient.setId(1L);

        int databaseSizeBeforeCreate = payementClientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayementClientMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payementClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayementClient in the database
        List<PayementClient> payementClientList = payementClientRepository.findAll();
        assertThat(payementClientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayementClients() throws Exception {
        // Initialize the database
        payementClientRepository.saveAndFlush(payementClient);

        // Get all the payementClientList
        restPayementClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payementClient.getId().intValue())))
            .andExpect(jsonPath("$.[*].payementClIdent").value(hasItem(DEFAULT_PAYEMENT_CL_IDENT)))
            .andExpect(jsonPath("$.[*].payementClDate").value(hasItem(DEFAULT_PAYEMENT_CL_DATE.toString())))
            .andExpect(jsonPath("$.[*].payementClMode").value(hasItem(DEFAULT_PAYEMENT_CL_MODE)))
            .andExpect(jsonPath("$.[*].payementClEcheance").value(hasItem(DEFAULT_PAYEMENT_CL_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].payementClMontant").value(hasItem(DEFAULT_PAYEMENT_CL_MONTANT.doubleValue())));
    }

    @Test
    @Transactional
    void getPayementClient() throws Exception {
        // Initialize the database
        payementClientRepository.saveAndFlush(payementClient);

        // Get the payementClient
        restPayementClientMockMvc
            .perform(get(ENTITY_API_URL_ID, payementClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payementClient.getId().intValue()))
            .andExpect(jsonPath("$.payementClIdent").value(DEFAULT_PAYEMENT_CL_IDENT))
            .andExpect(jsonPath("$.payementClDate").value(DEFAULT_PAYEMENT_CL_DATE.toString()))
            .andExpect(jsonPath("$.payementClMode").value(DEFAULT_PAYEMENT_CL_MODE))
            .andExpect(jsonPath("$.payementClEcheance").value(DEFAULT_PAYEMENT_CL_ECHEANCE.toString()))
            .andExpect(jsonPath("$.payementClMontant").value(DEFAULT_PAYEMENT_CL_MONTANT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingPayementClient() throws Exception {
        // Get the payementClient
        restPayementClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPayementClient() throws Exception {
        // Initialize the database
        payementClientRepository.saveAndFlush(payementClient);

        int databaseSizeBeforeUpdate = payementClientRepository.findAll().size();

        // Update the payementClient
        PayementClient updatedPayementClient = payementClientRepository.findById(payementClient.getId()).get();
        // Disconnect from session so that the updates on updatedPayementClient are not directly saved in db
        em.detach(updatedPayementClient);
        updatedPayementClient
            .payementClIdent(UPDATED_PAYEMENT_CL_IDENT)
            .payementClDate(UPDATED_PAYEMENT_CL_DATE)
            .payementClMode(UPDATED_PAYEMENT_CL_MODE)
            .payementClEcheance(UPDATED_PAYEMENT_CL_ECHEANCE)
            .payementClMontant(UPDATED_PAYEMENT_CL_MONTANT);

        restPayementClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPayementClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPayementClient))
            )
            .andExpect(status().isOk());

        // Validate the PayementClient in the database
        List<PayementClient> payementClientList = payementClientRepository.findAll();
        assertThat(payementClientList).hasSize(databaseSizeBeforeUpdate);
        PayementClient testPayementClient = payementClientList.get(payementClientList.size() - 1);
        assertThat(testPayementClient.getPayementClIdent()).isEqualTo(UPDATED_PAYEMENT_CL_IDENT);
        assertThat(testPayementClient.getPayementClDate()).isEqualTo(UPDATED_PAYEMENT_CL_DATE);
        assertThat(testPayementClient.getPayementClMode()).isEqualTo(UPDATED_PAYEMENT_CL_MODE);
        assertThat(testPayementClient.getPayementClEcheance()).isEqualTo(UPDATED_PAYEMENT_CL_ECHEANCE);
        assertThat(testPayementClient.getPayementClMontant()).isEqualTo(UPDATED_PAYEMENT_CL_MONTANT);
    }

    @Test
    @Transactional
    void putNonExistingPayementClient() throws Exception {
        int databaseSizeBeforeUpdate = payementClientRepository.findAll().size();
        payementClient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayementClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payementClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payementClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayementClient in the database
        List<PayementClient> payementClientList = payementClientRepository.findAll();
        assertThat(payementClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayementClient() throws Exception {
        int databaseSizeBeforeUpdate = payementClientRepository.findAll().size();
        payementClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayementClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payementClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayementClient in the database
        List<PayementClient> payementClientList = payementClientRepository.findAll();
        assertThat(payementClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayementClient() throws Exception {
        int databaseSizeBeforeUpdate = payementClientRepository.findAll().size();
        payementClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayementClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payementClient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayementClient in the database
        List<PayementClient> payementClientList = payementClientRepository.findAll();
        assertThat(payementClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePayementClientWithPatch() throws Exception {
        // Initialize the database
        payementClientRepository.saveAndFlush(payementClient);

        int databaseSizeBeforeUpdate = payementClientRepository.findAll().size();

        // Update the payementClient using partial update
        PayementClient partialUpdatedPayementClient = new PayementClient();
        partialUpdatedPayementClient.setId(payementClient.getId());

        partialUpdatedPayementClient
            .payementClIdent(UPDATED_PAYEMENT_CL_IDENT)
            .payementClDate(UPDATED_PAYEMENT_CL_DATE)
            .payementClMontant(UPDATED_PAYEMENT_CL_MONTANT);

        restPayementClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayementClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayementClient))
            )
            .andExpect(status().isOk());

        // Validate the PayementClient in the database
        List<PayementClient> payementClientList = payementClientRepository.findAll();
        assertThat(payementClientList).hasSize(databaseSizeBeforeUpdate);
        PayementClient testPayementClient = payementClientList.get(payementClientList.size() - 1);
        assertThat(testPayementClient.getPayementClIdent()).isEqualTo(UPDATED_PAYEMENT_CL_IDENT);
        assertThat(testPayementClient.getPayementClDate()).isEqualTo(UPDATED_PAYEMENT_CL_DATE);
        assertThat(testPayementClient.getPayementClMode()).isEqualTo(DEFAULT_PAYEMENT_CL_MODE);
        assertThat(testPayementClient.getPayementClEcheance()).isEqualTo(DEFAULT_PAYEMENT_CL_ECHEANCE);
        assertThat(testPayementClient.getPayementClMontant()).isEqualTo(UPDATED_PAYEMENT_CL_MONTANT);
    }

    @Test
    @Transactional
    void fullUpdatePayementClientWithPatch() throws Exception {
        // Initialize the database
        payementClientRepository.saveAndFlush(payementClient);

        int databaseSizeBeforeUpdate = payementClientRepository.findAll().size();

        // Update the payementClient using partial update
        PayementClient partialUpdatedPayementClient = new PayementClient();
        partialUpdatedPayementClient.setId(payementClient.getId());

        partialUpdatedPayementClient
            .payementClIdent(UPDATED_PAYEMENT_CL_IDENT)
            .payementClDate(UPDATED_PAYEMENT_CL_DATE)
            .payementClMode(UPDATED_PAYEMENT_CL_MODE)
            .payementClEcheance(UPDATED_PAYEMENT_CL_ECHEANCE)
            .payementClMontant(UPDATED_PAYEMENT_CL_MONTANT);

        restPayementClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayementClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayementClient))
            )
            .andExpect(status().isOk());

        // Validate the PayementClient in the database
        List<PayementClient> payementClientList = payementClientRepository.findAll();
        assertThat(payementClientList).hasSize(databaseSizeBeforeUpdate);
        PayementClient testPayementClient = payementClientList.get(payementClientList.size() - 1);
        assertThat(testPayementClient.getPayementClIdent()).isEqualTo(UPDATED_PAYEMENT_CL_IDENT);
        assertThat(testPayementClient.getPayementClDate()).isEqualTo(UPDATED_PAYEMENT_CL_DATE);
        assertThat(testPayementClient.getPayementClMode()).isEqualTo(UPDATED_PAYEMENT_CL_MODE);
        assertThat(testPayementClient.getPayementClEcheance()).isEqualTo(UPDATED_PAYEMENT_CL_ECHEANCE);
        assertThat(testPayementClient.getPayementClMontant()).isEqualTo(UPDATED_PAYEMENT_CL_MONTANT);
    }

    @Test
    @Transactional
    void patchNonExistingPayementClient() throws Exception {
        int databaseSizeBeforeUpdate = payementClientRepository.findAll().size();
        payementClient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayementClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payementClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payementClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayementClient in the database
        List<PayementClient> payementClientList = payementClientRepository.findAll();
        assertThat(payementClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayementClient() throws Exception {
        int databaseSizeBeforeUpdate = payementClientRepository.findAll().size();
        payementClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayementClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payementClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayementClient in the database
        List<PayementClient> payementClientList = payementClientRepository.findAll();
        assertThat(payementClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayementClient() throws Exception {
        int databaseSizeBeforeUpdate = payementClientRepository.findAll().size();
        payementClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayementClientMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(payementClient))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayementClient in the database
        List<PayementClient> payementClientList = payementClientRepository.findAll();
        assertThat(payementClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayementClient() throws Exception {
        // Initialize the database
        payementClientRepository.saveAndFlush(payementClient);

        int databaseSizeBeforeDelete = payementClientRepository.findAll().size();

        // Delete the payementClient
        restPayementClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, payementClient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PayementClient> payementClientList = payementClientRepository.findAll();
        assertThat(payementClientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
