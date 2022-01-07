package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.LivraisonCl;
import com.hbo.mycrmv1.repository.LivraisonClRepository;
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
 * Integration tests for the {@link LivraisonClResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LivraisonClResourceIT {

    private static final Integer DEFAULT_BON_LIV_IDENT_CL = 1;
    private static final Integer UPDATED_BON_LIV_IDENT_CL = 2;

    private static final LocalDate DEFAULT_LIV_DATE_CL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LIV_DATE_CL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LIV_DATE_UPDATE_CL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LIV_DATE_UPDATE_CL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LIV_DATE_EFFET_CL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LIV_DATE_EFFET_CL = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_BON_LIV_TOTAL_CL = 1D;
    private static final Double UPDATED_BON_LIV_TOTAL_CL = 2D;

    private static final String ENTITY_API_URL = "/api/livraison-cls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LivraisonClRepository livraisonClRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLivraisonClMockMvc;

    private LivraisonCl livraisonCl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LivraisonCl createEntity(EntityManager em) {
        LivraisonCl livraisonCl = new LivraisonCl()
            .bonLivIdentCl(DEFAULT_BON_LIV_IDENT_CL)
            .livDateCl(DEFAULT_LIV_DATE_CL)
            .livDateUpdateCl(DEFAULT_LIV_DATE_UPDATE_CL)
            .livDateEffetCl(DEFAULT_LIV_DATE_EFFET_CL)
            .bonLivTotalCl(DEFAULT_BON_LIV_TOTAL_CL);
        return livraisonCl;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LivraisonCl createUpdatedEntity(EntityManager em) {
        LivraisonCl livraisonCl = new LivraisonCl()
            .bonLivIdentCl(UPDATED_BON_LIV_IDENT_CL)
            .livDateCl(UPDATED_LIV_DATE_CL)
            .livDateUpdateCl(UPDATED_LIV_DATE_UPDATE_CL)
            .livDateEffetCl(UPDATED_LIV_DATE_EFFET_CL)
            .bonLivTotalCl(UPDATED_BON_LIV_TOTAL_CL);
        return livraisonCl;
    }

    @BeforeEach
    public void initTest() {
        livraisonCl = createEntity(em);
    }

    @Test
    @Transactional
    void createLivraisonCl() throws Exception {
        int databaseSizeBeforeCreate = livraisonClRepository.findAll().size();
        // Create the LivraisonCl
        restLivraisonClMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livraisonCl)))
            .andExpect(status().isCreated());

        // Validate the LivraisonCl in the database
        List<LivraisonCl> livraisonClList = livraisonClRepository.findAll();
        assertThat(livraisonClList).hasSize(databaseSizeBeforeCreate + 1);
        LivraisonCl testLivraisonCl = livraisonClList.get(livraisonClList.size() - 1);
        assertThat(testLivraisonCl.getBonLivIdentCl()).isEqualTo(DEFAULT_BON_LIV_IDENT_CL);
        assertThat(testLivraisonCl.getLivDateCl()).isEqualTo(DEFAULT_LIV_DATE_CL);
        assertThat(testLivraisonCl.getLivDateUpdateCl()).isEqualTo(DEFAULT_LIV_DATE_UPDATE_CL);
        assertThat(testLivraisonCl.getLivDateEffetCl()).isEqualTo(DEFAULT_LIV_DATE_EFFET_CL);
        assertThat(testLivraisonCl.getBonLivTotalCl()).isEqualTo(DEFAULT_BON_LIV_TOTAL_CL);
    }

    @Test
    @Transactional
    void createLivraisonClWithExistingId() throws Exception {
        // Create the LivraisonCl with an existing ID
        livraisonCl.setId(1L);

        int databaseSizeBeforeCreate = livraisonClRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivraisonClMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livraisonCl)))
            .andExpect(status().isBadRequest());

        // Validate the LivraisonCl in the database
        List<LivraisonCl> livraisonClList = livraisonClRepository.findAll();
        assertThat(livraisonClList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLivraisonCls() throws Exception {
        // Initialize the database
        livraisonClRepository.saveAndFlush(livraisonCl);

        // Get all the livraisonClList
        restLivraisonClMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livraisonCl.getId().intValue())))
            .andExpect(jsonPath("$.[*].bonLivIdentCl").value(hasItem(DEFAULT_BON_LIV_IDENT_CL)))
            .andExpect(jsonPath("$.[*].livDateCl").value(hasItem(DEFAULT_LIV_DATE_CL.toString())))
            .andExpect(jsonPath("$.[*].livDateUpdateCl").value(hasItem(DEFAULT_LIV_DATE_UPDATE_CL.toString())))
            .andExpect(jsonPath("$.[*].livDateEffetCl").value(hasItem(DEFAULT_LIV_DATE_EFFET_CL.toString())))
            .andExpect(jsonPath("$.[*].bonLivTotalCl").value(hasItem(DEFAULT_BON_LIV_TOTAL_CL.doubleValue())));
    }

    @Test
    @Transactional
    void getLivraisonCl() throws Exception {
        // Initialize the database
        livraisonClRepository.saveAndFlush(livraisonCl);

        // Get the livraisonCl
        restLivraisonClMockMvc
            .perform(get(ENTITY_API_URL_ID, livraisonCl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(livraisonCl.getId().intValue()))
            .andExpect(jsonPath("$.bonLivIdentCl").value(DEFAULT_BON_LIV_IDENT_CL))
            .andExpect(jsonPath("$.livDateCl").value(DEFAULT_LIV_DATE_CL.toString()))
            .andExpect(jsonPath("$.livDateUpdateCl").value(DEFAULT_LIV_DATE_UPDATE_CL.toString()))
            .andExpect(jsonPath("$.livDateEffetCl").value(DEFAULT_LIV_DATE_EFFET_CL.toString()))
            .andExpect(jsonPath("$.bonLivTotalCl").value(DEFAULT_BON_LIV_TOTAL_CL.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingLivraisonCl() throws Exception {
        // Get the livraisonCl
        restLivraisonClMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLivraisonCl() throws Exception {
        // Initialize the database
        livraisonClRepository.saveAndFlush(livraisonCl);

        int databaseSizeBeforeUpdate = livraisonClRepository.findAll().size();

        // Update the livraisonCl
        LivraisonCl updatedLivraisonCl = livraisonClRepository.findById(livraisonCl.getId()).get();
        // Disconnect from session so that the updates on updatedLivraisonCl are not directly saved in db
        em.detach(updatedLivraisonCl);
        updatedLivraisonCl
            .bonLivIdentCl(UPDATED_BON_LIV_IDENT_CL)
            .livDateCl(UPDATED_LIV_DATE_CL)
            .livDateUpdateCl(UPDATED_LIV_DATE_UPDATE_CL)
            .livDateEffetCl(UPDATED_LIV_DATE_EFFET_CL)
            .bonLivTotalCl(UPDATED_BON_LIV_TOTAL_CL);

        restLivraisonClMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLivraisonCl.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLivraisonCl))
            )
            .andExpect(status().isOk());

        // Validate the LivraisonCl in the database
        List<LivraisonCl> livraisonClList = livraisonClRepository.findAll();
        assertThat(livraisonClList).hasSize(databaseSizeBeforeUpdate);
        LivraisonCl testLivraisonCl = livraisonClList.get(livraisonClList.size() - 1);
        assertThat(testLivraisonCl.getBonLivIdentCl()).isEqualTo(UPDATED_BON_LIV_IDENT_CL);
        assertThat(testLivraisonCl.getLivDateCl()).isEqualTo(UPDATED_LIV_DATE_CL);
        assertThat(testLivraisonCl.getLivDateUpdateCl()).isEqualTo(UPDATED_LIV_DATE_UPDATE_CL);
        assertThat(testLivraisonCl.getLivDateEffetCl()).isEqualTo(UPDATED_LIV_DATE_EFFET_CL);
        assertThat(testLivraisonCl.getBonLivTotalCl()).isEqualTo(UPDATED_BON_LIV_TOTAL_CL);
    }

    @Test
    @Transactional
    void putNonExistingLivraisonCl() throws Exception {
        int databaseSizeBeforeUpdate = livraisonClRepository.findAll().size();
        livraisonCl.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivraisonClMockMvc
            .perform(
                put(ENTITY_API_URL_ID, livraisonCl.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(livraisonCl))
            )
            .andExpect(status().isBadRequest());

        // Validate the LivraisonCl in the database
        List<LivraisonCl> livraisonClList = livraisonClRepository.findAll();
        assertThat(livraisonClList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLivraisonCl() throws Exception {
        int databaseSizeBeforeUpdate = livraisonClRepository.findAll().size();
        livraisonCl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivraisonClMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(livraisonCl))
            )
            .andExpect(status().isBadRequest());

        // Validate the LivraisonCl in the database
        List<LivraisonCl> livraisonClList = livraisonClRepository.findAll();
        assertThat(livraisonClList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLivraisonCl() throws Exception {
        int databaseSizeBeforeUpdate = livraisonClRepository.findAll().size();
        livraisonCl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivraisonClMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livraisonCl)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LivraisonCl in the database
        List<LivraisonCl> livraisonClList = livraisonClRepository.findAll();
        assertThat(livraisonClList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLivraisonClWithPatch() throws Exception {
        // Initialize the database
        livraisonClRepository.saveAndFlush(livraisonCl);

        int databaseSizeBeforeUpdate = livraisonClRepository.findAll().size();

        // Update the livraisonCl using partial update
        LivraisonCl partialUpdatedLivraisonCl = new LivraisonCl();
        partialUpdatedLivraisonCl.setId(livraisonCl.getId());

        partialUpdatedLivraisonCl
            .bonLivIdentCl(UPDATED_BON_LIV_IDENT_CL)
            .livDateUpdateCl(UPDATED_LIV_DATE_UPDATE_CL)
            .bonLivTotalCl(UPDATED_BON_LIV_TOTAL_CL);

        restLivraisonClMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLivraisonCl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLivraisonCl))
            )
            .andExpect(status().isOk());

        // Validate the LivraisonCl in the database
        List<LivraisonCl> livraisonClList = livraisonClRepository.findAll();
        assertThat(livraisonClList).hasSize(databaseSizeBeforeUpdate);
        LivraisonCl testLivraisonCl = livraisonClList.get(livraisonClList.size() - 1);
        assertThat(testLivraisonCl.getBonLivIdentCl()).isEqualTo(UPDATED_BON_LIV_IDENT_CL);
        assertThat(testLivraisonCl.getLivDateCl()).isEqualTo(DEFAULT_LIV_DATE_CL);
        assertThat(testLivraisonCl.getLivDateUpdateCl()).isEqualTo(UPDATED_LIV_DATE_UPDATE_CL);
        assertThat(testLivraisonCl.getLivDateEffetCl()).isEqualTo(DEFAULT_LIV_DATE_EFFET_CL);
        assertThat(testLivraisonCl.getBonLivTotalCl()).isEqualTo(UPDATED_BON_LIV_TOTAL_CL);
    }

    @Test
    @Transactional
    void fullUpdateLivraisonClWithPatch() throws Exception {
        // Initialize the database
        livraisonClRepository.saveAndFlush(livraisonCl);

        int databaseSizeBeforeUpdate = livraisonClRepository.findAll().size();

        // Update the livraisonCl using partial update
        LivraisonCl partialUpdatedLivraisonCl = new LivraisonCl();
        partialUpdatedLivraisonCl.setId(livraisonCl.getId());

        partialUpdatedLivraisonCl
            .bonLivIdentCl(UPDATED_BON_LIV_IDENT_CL)
            .livDateCl(UPDATED_LIV_DATE_CL)
            .livDateUpdateCl(UPDATED_LIV_DATE_UPDATE_CL)
            .livDateEffetCl(UPDATED_LIV_DATE_EFFET_CL)
            .bonLivTotalCl(UPDATED_BON_LIV_TOTAL_CL);

        restLivraisonClMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLivraisonCl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLivraisonCl))
            )
            .andExpect(status().isOk());

        // Validate the LivraisonCl in the database
        List<LivraisonCl> livraisonClList = livraisonClRepository.findAll();
        assertThat(livraisonClList).hasSize(databaseSizeBeforeUpdate);
        LivraisonCl testLivraisonCl = livraisonClList.get(livraisonClList.size() - 1);
        assertThat(testLivraisonCl.getBonLivIdentCl()).isEqualTo(UPDATED_BON_LIV_IDENT_CL);
        assertThat(testLivraisonCl.getLivDateCl()).isEqualTo(UPDATED_LIV_DATE_CL);
        assertThat(testLivraisonCl.getLivDateUpdateCl()).isEqualTo(UPDATED_LIV_DATE_UPDATE_CL);
        assertThat(testLivraisonCl.getLivDateEffetCl()).isEqualTo(UPDATED_LIV_DATE_EFFET_CL);
        assertThat(testLivraisonCl.getBonLivTotalCl()).isEqualTo(UPDATED_BON_LIV_TOTAL_CL);
    }

    @Test
    @Transactional
    void patchNonExistingLivraisonCl() throws Exception {
        int databaseSizeBeforeUpdate = livraisonClRepository.findAll().size();
        livraisonCl.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivraisonClMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, livraisonCl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(livraisonCl))
            )
            .andExpect(status().isBadRequest());

        // Validate the LivraisonCl in the database
        List<LivraisonCl> livraisonClList = livraisonClRepository.findAll();
        assertThat(livraisonClList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLivraisonCl() throws Exception {
        int databaseSizeBeforeUpdate = livraisonClRepository.findAll().size();
        livraisonCl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivraisonClMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(livraisonCl))
            )
            .andExpect(status().isBadRequest());

        // Validate the LivraisonCl in the database
        List<LivraisonCl> livraisonClList = livraisonClRepository.findAll();
        assertThat(livraisonClList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLivraisonCl() throws Exception {
        int databaseSizeBeforeUpdate = livraisonClRepository.findAll().size();
        livraisonCl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivraisonClMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(livraisonCl))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LivraisonCl in the database
        List<LivraisonCl> livraisonClList = livraisonClRepository.findAll();
        assertThat(livraisonClList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLivraisonCl() throws Exception {
        // Initialize the database
        livraisonClRepository.saveAndFlush(livraisonCl);

        int databaseSizeBeforeDelete = livraisonClRepository.findAll().size();

        // Delete the livraisonCl
        restLivraisonClMockMvc
            .perform(delete(ENTITY_API_URL_ID, livraisonCl.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LivraisonCl> livraisonClList = livraisonClRepository.findAll();
        assertThat(livraisonClList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
