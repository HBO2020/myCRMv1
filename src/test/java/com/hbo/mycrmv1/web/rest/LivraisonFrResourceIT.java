package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.LivraisonFr;
import com.hbo.mycrmv1.repository.LivraisonFrRepository;
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
 * Integration tests for the {@link LivraisonFrResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LivraisonFrResourceIT {

    private static final Integer DEFAULT_BON_LIV_IDENT = 1;
    private static final Integer UPDATED_BON_LIV_IDENT = 2;

    private static final LocalDate DEFAULT_LIV_FR_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LIV_FR_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LIV_FR_DATE_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LIV_FR_DATE_UPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LIV_DATE_EFFET = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LIV_DATE_EFFET = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_BON_LIV_TOTAL = 1D;
    private static final Double UPDATED_BON_LIV_TOTAL = 2D;

    private static final String ENTITY_API_URL = "/api/livraison-frs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LivraisonFrRepository livraisonFrRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLivraisonFrMockMvc;

    private LivraisonFr livraisonFr;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LivraisonFr createEntity(EntityManager em) {
        LivraisonFr livraisonFr = new LivraisonFr()
            .bonLivIdent(DEFAULT_BON_LIV_IDENT)
            .livFrDate(DEFAULT_LIV_FR_DATE)
            .livFrDateUpdate(DEFAULT_LIV_FR_DATE_UPDATE)
            .livDateEffet(DEFAULT_LIV_DATE_EFFET)
            .bonLivTotal(DEFAULT_BON_LIV_TOTAL);
        return livraisonFr;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LivraisonFr createUpdatedEntity(EntityManager em) {
        LivraisonFr livraisonFr = new LivraisonFr()
            .bonLivIdent(UPDATED_BON_LIV_IDENT)
            .livFrDate(UPDATED_LIV_FR_DATE)
            .livFrDateUpdate(UPDATED_LIV_FR_DATE_UPDATE)
            .livDateEffet(UPDATED_LIV_DATE_EFFET)
            .bonLivTotal(UPDATED_BON_LIV_TOTAL);
        return livraisonFr;
    }

    @BeforeEach
    public void initTest() {
        livraisonFr = createEntity(em);
    }

    @Test
    @Transactional
    void createLivraisonFr() throws Exception {
        int databaseSizeBeforeCreate = livraisonFrRepository.findAll().size();
        // Create the LivraisonFr
        restLivraisonFrMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livraisonFr)))
            .andExpect(status().isCreated());

        // Validate the LivraisonFr in the database
        List<LivraisonFr> livraisonFrList = livraisonFrRepository.findAll();
        assertThat(livraisonFrList).hasSize(databaseSizeBeforeCreate + 1);
        LivraisonFr testLivraisonFr = livraisonFrList.get(livraisonFrList.size() - 1);
        assertThat(testLivraisonFr.getBonLivIdent()).isEqualTo(DEFAULT_BON_LIV_IDENT);
        assertThat(testLivraisonFr.getLivFrDate()).isEqualTo(DEFAULT_LIV_FR_DATE);
        assertThat(testLivraisonFr.getLivFrDateUpdate()).isEqualTo(DEFAULT_LIV_FR_DATE_UPDATE);
        assertThat(testLivraisonFr.getLivDateEffet()).isEqualTo(DEFAULT_LIV_DATE_EFFET);
        assertThat(testLivraisonFr.getBonLivTotal()).isEqualTo(DEFAULT_BON_LIV_TOTAL);
    }

    @Test
    @Transactional
    void createLivraisonFrWithExistingId() throws Exception {
        // Create the LivraisonFr with an existing ID
        livraisonFr.setId(1L);

        int databaseSizeBeforeCreate = livraisonFrRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivraisonFrMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livraisonFr)))
            .andExpect(status().isBadRequest());

        // Validate the LivraisonFr in the database
        List<LivraisonFr> livraisonFrList = livraisonFrRepository.findAll();
        assertThat(livraisonFrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLivraisonFrs() throws Exception {
        // Initialize the database
        livraisonFrRepository.saveAndFlush(livraisonFr);

        // Get all the livraisonFrList
        restLivraisonFrMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livraisonFr.getId().intValue())))
            .andExpect(jsonPath("$.[*].bonLivIdent").value(hasItem(DEFAULT_BON_LIV_IDENT)))
            .andExpect(jsonPath("$.[*].livFrDate").value(hasItem(DEFAULT_LIV_FR_DATE.toString())))
            .andExpect(jsonPath("$.[*].livFrDateUpdate").value(hasItem(DEFAULT_LIV_FR_DATE_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].livDateEffet").value(hasItem(DEFAULT_LIV_DATE_EFFET.toString())))
            .andExpect(jsonPath("$.[*].bonLivTotal").value(hasItem(DEFAULT_BON_LIV_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    void getLivraisonFr() throws Exception {
        // Initialize the database
        livraisonFrRepository.saveAndFlush(livraisonFr);

        // Get the livraisonFr
        restLivraisonFrMockMvc
            .perform(get(ENTITY_API_URL_ID, livraisonFr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(livraisonFr.getId().intValue()))
            .andExpect(jsonPath("$.bonLivIdent").value(DEFAULT_BON_LIV_IDENT))
            .andExpect(jsonPath("$.livFrDate").value(DEFAULT_LIV_FR_DATE.toString()))
            .andExpect(jsonPath("$.livFrDateUpdate").value(DEFAULT_LIV_FR_DATE_UPDATE.toString()))
            .andExpect(jsonPath("$.livDateEffet").value(DEFAULT_LIV_DATE_EFFET.toString()))
            .andExpect(jsonPath("$.bonLivTotal").value(DEFAULT_BON_LIV_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingLivraisonFr() throws Exception {
        // Get the livraisonFr
        restLivraisonFrMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLivraisonFr() throws Exception {
        // Initialize the database
        livraisonFrRepository.saveAndFlush(livraisonFr);

        int databaseSizeBeforeUpdate = livraisonFrRepository.findAll().size();

        // Update the livraisonFr
        LivraisonFr updatedLivraisonFr = livraisonFrRepository.findById(livraisonFr.getId()).get();
        // Disconnect from session so that the updates on updatedLivraisonFr are not directly saved in db
        em.detach(updatedLivraisonFr);
        updatedLivraisonFr
            .bonLivIdent(UPDATED_BON_LIV_IDENT)
            .livFrDate(UPDATED_LIV_FR_DATE)
            .livFrDateUpdate(UPDATED_LIV_FR_DATE_UPDATE)
            .livDateEffet(UPDATED_LIV_DATE_EFFET)
            .bonLivTotal(UPDATED_BON_LIV_TOTAL);

        restLivraisonFrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLivraisonFr.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLivraisonFr))
            )
            .andExpect(status().isOk());

        // Validate the LivraisonFr in the database
        List<LivraisonFr> livraisonFrList = livraisonFrRepository.findAll();
        assertThat(livraisonFrList).hasSize(databaseSizeBeforeUpdate);
        LivraisonFr testLivraisonFr = livraisonFrList.get(livraisonFrList.size() - 1);
        assertThat(testLivraisonFr.getBonLivIdent()).isEqualTo(UPDATED_BON_LIV_IDENT);
        assertThat(testLivraisonFr.getLivFrDate()).isEqualTo(UPDATED_LIV_FR_DATE);
        assertThat(testLivraisonFr.getLivFrDateUpdate()).isEqualTo(UPDATED_LIV_FR_DATE_UPDATE);
        assertThat(testLivraisonFr.getLivDateEffet()).isEqualTo(UPDATED_LIV_DATE_EFFET);
        assertThat(testLivraisonFr.getBonLivTotal()).isEqualTo(UPDATED_BON_LIV_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingLivraisonFr() throws Exception {
        int databaseSizeBeforeUpdate = livraisonFrRepository.findAll().size();
        livraisonFr.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivraisonFrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, livraisonFr.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(livraisonFr))
            )
            .andExpect(status().isBadRequest());

        // Validate the LivraisonFr in the database
        List<LivraisonFr> livraisonFrList = livraisonFrRepository.findAll();
        assertThat(livraisonFrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLivraisonFr() throws Exception {
        int databaseSizeBeforeUpdate = livraisonFrRepository.findAll().size();
        livraisonFr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivraisonFrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(livraisonFr))
            )
            .andExpect(status().isBadRequest());

        // Validate the LivraisonFr in the database
        List<LivraisonFr> livraisonFrList = livraisonFrRepository.findAll();
        assertThat(livraisonFrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLivraisonFr() throws Exception {
        int databaseSizeBeforeUpdate = livraisonFrRepository.findAll().size();
        livraisonFr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivraisonFrMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livraisonFr)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LivraisonFr in the database
        List<LivraisonFr> livraisonFrList = livraisonFrRepository.findAll();
        assertThat(livraisonFrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLivraisonFrWithPatch() throws Exception {
        // Initialize the database
        livraisonFrRepository.saveAndFlush(livraisonFr);

        int databaseSizeBeforeUpdate = livraisonFrRepository.findAll().size();

        // Update the livraisonFr using partial update
        LivraisonFr partialUpdatedLivraisonFr = new LivraisonFr();
        partialUpdatedLivraisonFr.setId(livraisonFr.getId());

        partialUpdatedLivraisonFr.livFrDate(UPDATED_LIV_FR_DATE).bonLivTotal(UPDATED_BON_LIV_TOTAL);

        restLivraisonFrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLivraisonFr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLivraisonFr))
            )
            .andExpect(status().isOk());

        // Validate the LivraisonFr in the database
        List<LivraisonFr> livraisonFrList = livraisonFrRepository.findAll();
        assertThat(livraisonFrList).hasSize(databaseSizeBeforeUpdate);
        LivraisonFr testLivraisonFr = livraisonFrList.get(livraisonFrList.size() - 1);
        assertThat(testLivraisonFr.getBonLivIdent()).isEqualTo(DEFAULT_BON_LIV_IDENT);
        assertThat(testLivraisonFr.getLivFrDate()).isEqualTo(UPDATED_LIV_FR_DATE);
        assertThat(testLivraisonFr.getLivFrDateUpdate()).isEqualTo(DEFAULT_LIV_FR_DATE_UPDATE);
        assertThat(testLivraisonFr.getLivDateEffet()).isEqualTo(DEFAULT_LIV_DATE_EFFET);
        assertThat(testLivraisonFr.getBonLivTotal()).isEqualTo(UPDATED_BON_LIV_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateLivraisonFrWithPatch() throws Exception {
        // Initialize the database
        livraisonFrRepository.saveAndFlush(livraisonFr);

        int databaseSizeBeforeUpdate = livraisonFrRepository.findAll().size();

        // Update the livraisonFr using partial update
        LivraisonFr partialUpdatedLivraisonFr = new LivraisonFr();
        partialUpdatedLivraisonFr.setId(livraisonFr.getId());

        partialUpdatedLivraisonFr
            .bonLivIdent(UPDATED_BON_LIV_IDENT)
            .livFrDate(UPDATED_LIV_FR_DATE)
            .livFrDateUpdate(UPDATED_LIV_FR_DATE_UPDATE)
            .livDateEffet(UPDATED_LIV_DATE_EFFET)
            .bonLivTotal(UPDATED_BON_LIV_TOTAL);

        restLivraisonFrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLivraisonFr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLivraisonFr))
            )
            .andExpect(status().isOk());

        // Validate the LivraisonFr in the database
        List<LivraisonFr> livraisonFrList = livraisonFrRepository.findAll();
        assertThat(livraisonFrList).hasSize(databaseSizeBeforeUpdate);
        LivraisonFr testLivraisonFr = livraisonFrList.get(livraisonFrList.size() - 1);
        assertThat(testLivraisonFr.getBonLivIdent()).isEqualTo(UPDATED_BON_LIV_IDENT);
        assertThat(testLivraisonFr.getLivFrDate()).isEqualTo(UPDATED_LIV_FR_DATE);
        assertThat(testLivraisonFr.getLivFrDateUpdate()).isEqualTo(UPDATED_LIV_FR_DATE_UPDATE);
        assertThat(testLivraisonFr.getLivDateEffet()).isEqualTo(UPDATED_LIV_DATE_EFFET);
        assertThat(testLivraisonFr.getBonLivTotal()).isEqualTo(UPDATED_BON_LIV_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingLivraisonFr() throws Exception {
        int databaseSizeBeforeUpdate = livraisonFrRepository.findAll().size();
        livraisonFr.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivraisonFrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, livraisonFr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(livraisonFr))
            )
            .andExpect(status().isBadRequest());

        // Validate the LivraisonFr in the database
        List<LivraisonFr> livraisonFrList = livraisonFrRepository.findAll();
        assertThat(livraisonFrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLivraisonFr() throws Exception {
        int databaseSizeBeforeUpdate = livraisonFrRepository.findAll().size();
        livraisonFr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivraisonFrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(livraisonFr))
            )
            .andExpect(status().isBadRequest());

        // Validate the LivraisonFr in the database
        List<LivraisonFr> livraisonFrList = livraisonFrRepository.findAll();
        assertThat(livraisonFrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLivraisonFr() throws Exception {
        int databaseSizeBeforeUpdate = livraisonFrRepository.findAll().size();
        livraisonFr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivraisonFrMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(livraisonFr))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LivraisonFr in the database
        List<LivraisonFr> livraisonFrList = livraisonFrRepository.findAll();
        assertThat(livraisonFrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLivraisonFr() throws Exception {
        // Initialize the database
        livraisonFrRepository.saveAndFlush(livraisonFr);

        int databaseSizeBeforeDelete = livraisonFrRepository.findAll().size();

        // Delete the livraisonFr
        restLivraisonFrMockMvc
            .perform(delete(ENTITY_API_URL_ID, livraisonFr.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LivraisonFr> livraisonFrList = livraisonFrRepository.findAll();
        assertThat(livraisonFrList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
