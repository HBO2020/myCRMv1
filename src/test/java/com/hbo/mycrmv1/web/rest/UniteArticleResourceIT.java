package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.UniteArticle;
import com.hbo.mycrmv1.repository.UniteArticleRepository;
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
 * Integration tests for the {@link UniteArticleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UniteArticleResourceIT {

    private static final Integer DEFAULT_UNITE_CODE = 1;
    private static final Integer UPDATED_UNITE_CODE = 2;

    private static final String DEFAULT_UNITE_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_UNITE_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_UNITE_OPTION = "AAAAAAAAAA";
    private static final String UPDATED_UNITE_OPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/unite-articles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UniteArticleRepository uniteArticleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUniteArticleMockMvc;

    private UniteArticle uniteArticle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UniteArticle createEntity(EntityManager em) {
        UniteArticle uniteArticle = new UniteArticle()
            .uniteCode(DEFAULT_UNITE_CODE)
            .uniteLibelle(DEFAULT_UNITE_LIBELLE)
            .uniteOption(DEFAULT_UNITE_OPTION);
        return uniteArticle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UniteArticle createUpdatedEntity(EntityManager em) {
        UniteArticle uniteArticle = new UniteArticle()
            .uniteCode(UPDATED_UNITE_CODE)
            .uniteLibelle(UPDATED_UNITE_LIBELLE)
            .uniteOption(UPDATED_UNITE_OPTION);
        return uniteArticle;
    }

    @BeforeEach
    public void initTest() {
        uniteArticle = createEntity(em);
    }

    @Test
    @Transactional
    void createUniteArticle() throws Exception {
        int databaseSizeBeforeCreate = uniteArticleRepository.findAll().size();
        // Create the UniteArticle
        restUniteArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(uniteArticle)))
            .andExpect(status().isCreated());

        // Validate the UniteArticle in the database
        List<UniteArticle> uniteArticleList = uniteArticleRepository.findAll();
        assertThat(uniteArticleList).hasSize(databaseSizeBeforeCreate + 1);
        UniteArticle testUniteArticle = uniteArticleList.get(uniteArticleList.size() - 1);
        assertThat(testUniteArticle.getUniteCode()).isEqualTo(DEFAULT_UNITE_CODE);
        assertThat(testUniteArticle.getUniteLibelle()).isEqualTo(DEFAULT_UNITE_LIBELLE);
        assertThat(testUniteArticle.getUniteOption()).isEqualTo(DEFAULT_UNITE_OPTION);
    }

    @Test
    @Transactional
    void createUniteArticleWithExistingId() throws Exception {
        // Create the UniteArticle with an existing ID
        uniteArticle.setId(1L);

        int databaseSizeBeforeCreate = uniteArticleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniteArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(uniteArticle)))
            .andExpect(status().isBadRequest());

        // Validate the UniteArticle in the database
        List<UniteArticle> uniteArticleList = uniteArticleRepository.findAll();
        assertThat(uniteArticleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUniteArticles() throws Exception {
        // Initialize the database
        uniteArticleRepository.saveAndFlush(uniteArticle);

        // Get all the uniteArticleList
        restUniteArticleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uniteArticle.getId().intValue())))
            .andExpect(jsonPath("$.[*].uniteCode").value(hasItem(DEFAULT_UNITE_CODE)))
            .andExpect(jsonPath("$.[*].uniteLibelle").value(hasItem(DEFAULT_UNITE_LIBELLE)))
            .andExpect(jsonPath("$.[*].uniteOption").value(hasItem(DEFAULT_UNITE_OPTION)));
    }

    @Test
    @Transactional
    void getUniteArticle() throws Exception {
        // Initialize the database
        uniteArticleRepository.saveAndFlush(uniteArticle);

        // Get the uniteArticle
        restUniteArticleMockMvc
            .perform(get(ENTITY_API_URL_ID, uniteArticle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uniteArticle.getId().intValue()))
            .andExpect(jsonPath("$.uniteCode").value(DEFAULT_UNITE_CODE))
            .andExpect(jsonPath("$.uniteLibelle").value(DEFAULT_UNITE_LIBELLE))
            .andExpect(jsonPath("$.uniteOption").value(DEFAULT_UNITE_OPTION));
    }

    @Test
    @Transactional
    void getNonExistingUniteArticle() throws Exception {
        // Get the uniteArticle
        restUniteArticleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUniteArticle() throws Exception {
        // Initialize the database
        uniteArticleRepository.saveAndFlush(uniteArticle);

        int databaseSizeBeforeUpdate = uniteArticleRepository.findAll().size();

        // Update the uniteArticle
        UniteArticle updatedUniteArticle = uniteArticleRepository.findById(uniteArticle.getId()).get();
        // Disconnect from session so that the updates on updatedUniteArticle are not directly saved in db
        em.detach(updatedUniteArticle);
        updatedUniteArticle.uniteCode(UPDATED_UNITE_CODE).uniteLibelle(UPDATED_UNITE_LIBELLE).uniteOption(UPDATED_UNITE_OPTION);

        restUniteArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUniteArticle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUniteArticle))
            )
            .andExpect(status().isOk());

        // Validate the UniteArticle in the database
        List<UniteArticle> uniteArticleList = uniteArticleRepository.findAll();
        assertThat(uniteArticleList).hasSize(databaseSizeBeforeUpdate);
        UniteArticle testUniteArticle = uniteArticleList.get(uniteArticleList.size() - 1);
        assertThat(testUniteArticle.getUniteCode()).isEqualTo(UPDATED_UNITE_CODE);
        assertThat(testUniteArticle.getUniteLibelle()).isEqualTo(UPDATED_UNITE_LIBELLE);
        assertThat(testUniteArticle.getUniteOption()).isEqualTo(UPDATED_UNITE_OPTION);
    }

    @Test
    @Transactional
    void putNonExistingUniteArticle() throws Exception {
        int databaseSizeBeforeUpdate = uniteArticleRepository.findAll().size();
        uniteArticle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniteArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, uniteArticle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(uniteArticle))
            )
            .andExpect(status().isBadRequest());

        // Validate the UniteArticle in the database
        List<UniteArticle> uniteArticleList = uniteArticleRepository.findAll();
        assertThat(uniteArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUniteArticle() throws Exception {
        int databaseSizeBeforeUpdate = uniteArticleRepository.findAll().size();
        uniteArticle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniteArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(uniteArticle))
            )
            .andExpect(status().isBadRequest());

        // Validate the UniteArticle in the database
        List<UniteArticle> uniteArticleList = uniteArticleRepository.findAll();
        assertThat(uniteArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUniteArticle() throws Exception {
        int databaseSizeBeforeUpdate = uniteArticleRepository.findAll().size();
        uniteArticle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniteArticleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(uniteArticle)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UniteArticle in the database
        List<UniteArticle> uniteArticleList = uniteArticleRepository.findAll();
        assertThat(uniteArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUniteArticleWithPatch() throws Exception {
        // Initialize the database
        uniteArticleRepository.saveAndFlush(uniteArticle);

        int databaseSizeBeforeUpdate = uniteArticleRepository.findAll().size();

        // Update the uniteArticle using partial update
        UniteArticle partialUpdatedUniteArticle = new UniteArticle();
        partialUpdatedUniteArticle.setId(uniteArticle.getId());

        partialUpdatedUniteArticle.uniteCode(UPDATED_UNITE_CODE);

        restUniteArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUniteArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUniteArticle))
            )
            .andExpect(status().isOk());

        // Validate the UniteArticle in the database
        List<UniteArticle> uniteArticleList = uniteArticleRepository.findAll();
        assertThat(uniteArticleList).hasSize(databaseSizeBeforeUpdate);
        UniteArticle testUniteArticle = uniteArticleList.get(uniteArticleList.size() - 1);
        assertThat(testUniteArticle.getUniteCode()).isEqualTo(UPDATED_UNITE_CODE);
        assertThat(testUniteArticle.getUniteLibelle()).isEqualTo(DEFAULT_UNITE_LIBELLE);
        assertThat(testUniteArticle.getUniteOption()).isEqualTo(DEFAULT_UNITE_OPTION);
    }

    @Test
    @Transactional
    void fullUpdateUniteArticleWithPatch() throws Exception {
        // Initialize the database
        uniteArticleRepository.saveAndFlush(uniteArticle);

        int databaseSizeBeforeUpdate = uniteArticleRepository.findAll().size();

        // Update the uniteArticle using partial update
        UniteArticle partialUpdatedUniteArticle = new UniteArticle();
        partialUpdatedUniteArticle.setId(uniteArticle.getId());

        partialUpdatedUniteArticle.uniteCode(UPDATED_UNITE_CODE).uniteLibelle(UPDATED_UNITE_LIBELLE).uniteOption(UPDATED_UNITE_OPTION);

        restUniteArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUniteArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUniteArticle))
            )
            .andExpect(status().isOk());

        // Validate the UniteArticle in the database
        List<UniteArticle> uniteArticleList = uniteArticleRepository.findAll();
        assertThat(uniteArticleList).hasSize(databaseSizeBeforeUpdate);
        UniteArticle testUniteArticle = uniteArticleList.get(uniteArticleList.size() - 1);
        assertThat(testUniteArticle.getUniteCode()).isEqualTo(UPDATED_UNITE_CODE);
        assertThat(testUniteArticle.getUniteLibelle()).isEqualTo(UPDATED_UNITE_LIBELLE);
        assertThat(testUniteArticle.getUniteOption()).isEqualTo(UPDATED_UNITE_OPTION);
    }

    @Test
    @Transactional
    void patchNonExistingUniteArticle() throws Exception {
        int databaseSizeBeforeUpdate = uniteArticleRepository.findAll().size();
        uniteArticle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniteArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, uniteArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(uniteArticle))
            )
            .andExpect(status().isBadRequest());

        // Validate the UniteArticle in the database
        List<UniteArticle> uniteArticleList = uniteArticleRepository.findAll();
        assertThat(uniteArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUniteArticle() throws Exception {
        int databaseSizeBeforeUpdate = uniteArticleRepository.findAll().size();
        uniteArticle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniteArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(uniteArticle))
            )
            .andExpect(status().isBadRequest());

        // Validate the UniteArticle in the database
        List<UniteArticle> uniteArticleList = uniteArticleRepository.findAll();
        assertThat(uniteArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUniteArticle() throws Exception {
        int databaseSizeBeforeUpdate = uniteArticleRepository.findAll().size();
        uniteArticle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniteArticleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(uniteArticle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UniteArticle in the database
        List<UniteArticle> uniteArticleList = uniteArticleRepository.findAll();
        assertThat(uniteArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUniteArticle() throws Exception {
        // Initialize the database
        uniteArticleRepository.saveAndFlush(uniteArticle);

        int databaseSizeBeforeDelete = uniteArticleRepository.findAll().size();

        // Delete the uniteArticle
        restUniteArticleMockMvc
            .perform(delete(ENTITY_API_URL_ID, uniteArticle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UniteArticle> uniteArticleList = uniteArticleRepository.findAll();
        assertThat(uniteArticleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
