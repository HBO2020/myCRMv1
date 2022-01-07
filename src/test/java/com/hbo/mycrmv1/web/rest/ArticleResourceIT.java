package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.Article;
import com.hbo.mycrmv1.repository.ArticleRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ArticleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArticleResourceIT {

    private static final Integer DEFAULT_ARTCL_IDEN = 1;
    private static final Integer UPDATED_ARTCL_IDEN = 2;

    private static final String DEFAULT_ARTCL_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_ARTCL_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_ARTCL_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_ARTCL_DESIGNATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ARTCL_QN_STOCK = 1;
    private static final Integer UPDATED_ARTCL_QN_STOCK = 2;

    private static final byte[] DEFAULT_ARTCL_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARTCL_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARTCL_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARTCL_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ARTCL_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_ARTCL_SERIE = "BBBBBBBBBB";

    private static final Double DEFAULT_ARTCL_PRIX_ACHAT = 1D;
    private static final Double UPDATED_ARTCL_PRIX_ACHAT = 2D;

    private static final Double DEFAULT_ARTCL_PX_ACHAT_TOTAL = 1D;
    private static final Double UPDATED_ARTCL_PX_ACHAT_TOTAL = 2D;

    private static final Double DEFAULT_ARTCL_PX_VENTE_TOTAL = 1D;
    private static final Double UPDATED_ARTCL_PX_VENTE_TOTAL = 2D;

    private static final String ENTITY_API_URL = "/api/articles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleMockMvc;

    private Article article;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Article createEntity(EntityManager em) {
        Article article = new Article()
            .artclIden(DEFAULT_ARTCL_IDEN)
            .artclReference(DEFAULT_ARTCL_REFERENCE)
            .artclDesignation(DEFAULT_ARTCL_DESIGNATION)
            .artclQnStock(DEFAULT_ARTCL_QN_STOCK)
            .artclImg(DEFAULT_ARTCL_IMG)
            .artclImgContentType(DEFAULT_ARTCL_IMG_CONTENT_TYPE)
            .artclSerie(DEFAULT_ARTCL_SERIE)
            .artclPrixAchat(DEFAULT_ARTCL_PRIX_ACHAT)
            .artclPxAchatTotal(DEFAULT_ARTCL_PX_ACHAT_TOTAL)
            .artclPxVenteTotal(DEFAULT_ARTCL_PX_VENTE_TOTAL);
        return article;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Article createUpdatedEntity(EntityManager em) {
        Article article = new Article()
            .artclIden(UPDATED_ARTCL_IDEN)
            .artclReference(UPDATED_ARTCL_REFERENCE)
            .artclDesignation(UPDATED_ARTCL_DESIGNATION)
            .artclQnStock(UPDATED_ARTCL_QN_STOCK)
            .artclImg(UPDATED_ARTCL_IMG)
            .artclImgContentType(UPDATED_ARTCL_IMG_CONTENT_TYPE)
            .artclSerie(UPDATED_ARTCL_SERIE)
            .artclPrixAchat(UPDATED_ARTCL_PRIX_ACHAT)
            .artclPxAchatTotal(UPDATED_ARTCL_PX_ACHAT_TOTAL)
            .artclPxVenteTotal(UPDATED_ARTCL_PX_VENTE_TOTAL);
        return article;
    }

    @BeforeEach
    public void initTest() {
        article = createEntity(em);
    }

    @Test
    @Transactional
    void createArticle() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();
        // Create the Article
        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate + 1);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getArtclIden()).isEqualTo(DEFAULT_ARTCL_IDEN);
        assertThat(testArticle.getArtclReference()).isEqualTo(DEFAULT_ARTCL_REFERENCE);
        assertThat(testArticle.getArtclDesignation()).isEqualTo(DEFAULT_ARTCL_DESIGNATION);
        assertThat(testArticle.getArtclQnStock()).isEqualTo(DEFAULT_ARTCL_QN_STOCK);
        assertThat(testArticle.getArtclImg()).isEqualTo(DEFAULT_ARTCL_IMG);
        assertThat(testArticle.getArtclImgContentType()).isEqualTo(DEFAULT_ARTCL_IMG_CONTENT_TYPE);
        assertThat(testArticle.getArtclSerie()).isEqualTo(DEFAULT_ARTCL_SERIE);
        assertThat(testArticle.getArtclPrixAchat()).isEqualTo(DEFAULT_ARTCL_PRIX_ACHAT);
        assertThat(testArticle.getArtclPxAchatTotal()).isEqualTo(DEFAULT_ARTCL_PX_ACHAT_TOTAL);
        assertThat(testArticle.getArtclPxVenteTotal()).isEqualTo(DEFAULT_ARTCL_PX_VENTE_TOTAL);
    }

    @Test
    @Transactional
    void createArticleWithExistingId() throws Exception {
        // Create the Article with an existing ID
        article.setId(1L);

        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArticles() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get all the articleList
        restArticleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
            .andExpect(jsonPath("$.[*].artclIden").value(hasItem(DEFAULT_ARTCL_IDEN)))
            .andExpect(jsonPath("$.[*].artclReference").value(hasItem(DEFAULT_ARTCL_REFERENCE)))
            .andExpect(jsonPath("$.[*].artclDesignation").value(hasItem(DEFAULT_ARTCL_DESIGNATION)))
            .andExpect(jsonPath("$.[*].artclQnStock").value(hasItem(DEFAULT_ARTCL_QN_STOCK)))
            .andExpect(jsonPath("$.[*].artclImgContentType").value(hasItem(DEFAULT_ARTCL_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].artclImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARTCL_IMG))))
            .andExpect(jsonPath("$.[*].artclSerie").value(hasItem(DEFAULT_ARTCL_SERIE)))
            .andExpect(jsonPath("$.[*].artclPrixAchat").value(hasItem(DEFAULT_ARTCL_PRIX_ACHAT.doubleValue())))
            .andExpect(jsonPath("$.[*].artclPxAchatTotal").value(hasItem(DEFAULT_ARTCL_PX_ACHAT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].artclPxVenteTotal").value(hasItem(DEFAULT_ARTCL_PX_VENTE_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    void getArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get the article
        restArticleMockMvc
            .perform(get(ENTITY_API_URL_ID, article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(article.getId().intValue()))
            .andExpect(jsonPath("$.artclIden").value(DEFAULT_ARTCL_IDEN))
            .andExpect(jsonPath("$.artclReference").value(DEFAULT_ARTCL_REFERENCE))
            .andExpect(jsonPath("$.artclDesignation").value(DEFAULT_ARTCL_DESIGNATION))
            .andExpect(jsonPath("$.artclQnStock").value(DEFAULT_ARTCL_QN_STOCK))
            .andExpect(jsonPath("$.artclImgContentType").value(DEFAULT_ARTCL_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.artclImg").value(Base64Utils.encodeToString(DEFAULT_ARTCL_IMG)))
            .andExpect(jsonPath("$.artclSerie").value(DEFAULT_ARTCL_SERIE))
            .andExpect(jsonPath("$.artclPrixAchat").value(DEFAULT_ARTCL_PRIX_ACHAT.doubleValue()))
            .andExpect(jsonPath("$.artclPxAchatTotal").value(DEFAULT_ARTCL_PX_ACHAT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.artclPxVenteTotal").value(DEFAULT_ARTCL_PX_VENTE_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingArticle() throws Exception {
        // Get the article
        restArticleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article
        Article updatedArticle = articleRepository.findById(article.getId()).get();
        // Disconnect from session so that the updates on updatedArticle are not directly saved in db
        em.detach(updatedArticle);
        updatedArticle
            .artclIden(UPDATED_ARTCL_IDEN)
            .artclReference(UPDATED_ARTCL_REFERENCE)
            .artclDesignation(UPDATED_ARTCL_DESIGNATION)
            .artclQnStock(UPDATED_ARTCL_QN_STOCK)
            .artclImg(UPDATED_ARTCL_IMG)
            .artclImgContentType(UPDATED_ARTCL_IMG_CONTENT_TYPE)
            .artclSerie(UPDATED_ARTCL_SERIE)
            .artclPrixAchat(UPDATED_ARTCL_PRIX_ACHAT)
            .artclPxAchatTotal(UPDATED_ARTCL_PX_ACHAT_TOTAL)
            .artclPxVenteTotal(UPDATED_ARTCL_PX_VENTE_TOTAL);

        restArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArticle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedArticle))
            )
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getArtclIden()).isEqualTo(UPDATED_ARTCL_IDEN);
        assertThat(testArticle.getArtclReference()).isEqualTo(UPDATED_ARTCL_REFERENCE);
        assertThat(testArticle.getArtclDesignation()).isEqualTo(UPDATED_ARTCL_DESIGNATION);
        assertThat(testArticle.getArtclQnStock()).isEqualTo(UPDATED_ARTCL_QN_STOCK);
        assertThat(testArticle.getArtclImg()).isEqualTo(UPDATED_ARTCL_IMG);
        assertThat(testArticle.getArtclImgContentType()).isEqualTo(UPDATED_ARTCL_IMG_CONTENT_TYPE);
        assertThat(testArticle.getArtclSerie()).isEqualTo(UPDATED_ARTCL_SERIE);
        assertThat(testArticle.getArtclPrixAchat()).isEqualTo(UPDATED_ARTCL_PRIX_ACHAT);
        assertThat(testArticle.getArtclPxAchatTotal()).isEqualTo(UPDATED_ARTCL_PX_ACHAT_TOTAL);
        assertThat(testArticle.getArtclPxVenteTotal()).isEqualTo(UPDATED_ARTCL_PX_VENTE_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();
        article.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, article.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(article))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();
        article.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(article))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();
        article.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticleWithPatch() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article using partial update
        Article partialUpdatedArticle = new Article();
        partialUpdatedArticle.setId(article.getId());

        partialUpdatedArticle
            .artclIden(UPDATED_ARTCL_IDEN)
            .artclReference(UPDATED_ARTCL_REFERENCE)
            .artclDesignation(UPDATED_ARTCL_DESIGNATION)
            .artclSerie(UPDATED_ARTCL_SERIE)
            .artclPxVenteTotal(UPDATED_ARTCL_PX_VENTE_TOTAL);

        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticle))
            )
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getArtclIden()).isEqualTo(UPDATED_ARTCL_IDEN);
        assertThat(testArticle.getArtclReference()).isEqualTo(UPDATED_ARTCL_REFERENCE);
        assertThat(testArticle.getArtclDesignation()).isEqualTo(UPDATED_ARTCL_DESIGNATION);
        assertThat(testArticle.getArtclQnStock()).isEqualTo(DEFAULT_ARTCL_QN_STOCK);
        assertThat(testArticle.getArtclImg()).isEqualTo(DEFAULT_ARTCL_IMG);
        assertThat(testArticle.getArtclImgContentType()).isEqualTo(DEFAULT_ARTCL_IMG_CONTENT_TYPE);
        assertThat(testArticle.getArtclSerie()).isEqualTo(UPDATED_ARTCL_SERIE);
        assertThat(testArticle.getArtclPrixAchat()).isEqualTo(DEFAULT_ARTCL_PRIX_ACHAT);
        assertThat(testArticle.getArtclPxAchatTotal()).isEqualTo(DEFAULT_ARTCL_PX_ACHAT_TOTAL);
        assertThat(testArticle.getArtclPxVenteTotal()).isEqualTo(UPDATED_ARTCL_PX_VENTE_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateArticleWithPatch() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article using partial update
        Article partialUpdatedArticle = new Article();
        partialUpdatedArticle.setId(article.getId());

        partialUpdatedArticle
            .artclIden(UPDATED_ARTCL_IDEN)
            .artclReference(UPDATED_ARTCL_REFERENCE)
            .artclDesignation(UPDATED_ARTCL_DESIGNATION)
            .artclQnStock(UPDATED_ARTCL_QN_STOCK)
            .artclImg(UPDATED_ARTCL_IMG)
            .artclImgContentType(UPDATED_ARTCL_IMG_CONTENT_TYPE)
            .artclSerie(UPDATED_ARTCL_SERIE)
            .artclPrixAchat(UPDATED_ARTCL_PRIX_ACHAT)
            .artclPxAchatTotal(UPDATED_ARTCL_PX_ACHAT_TOTAL)
            .artclPxVenteTotal(UPDATED_ARTCL_PX_VENTE_TOTAL);

        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticle))
            )
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getArtclIden()).isEqualTo(UPDATED_ARTCL_IDEN);
        assertThat(testArticle.getArtclReference()).isEqualTo(UPDATED_ARTCL_REFERENCE);
        assertThat(testArticle.getArtclDesignation()).isEqualTo(UPDATED_ARTCL_DESIGNATION);
        assertThat(testArticle.getArtclQnStock()).isEqualTo(UPDATED_ARTCL_QN_STOCK);
        assertThat(testArticle.getArtclImg()).isEqualTo(UPDATED_ARTCL_IMG);
        assertThat(testArticle.getArtclImgContentType()).isEqualTo(UPDATED_ARTCL_IMG_CONTENT_TYPE);
        assertThat(testArticle.getArtclSerie()).isEqualTo(UPDATED_ARTCL_SERIE);
        assertThat(testArticle.getArtclPrixAchat()).isEqualTo(UPDATED_ARTCL_PRIX_ACHAT);
        assertThat(testArticle.getArtclPxAchatTotal()).isEqualTo(UPDATED_ARTCL_PX_ACHAT_TOTAL);
        assertThat(testArticle.getArtclPxVenteTotal()).isEqualTo(UPDATED_ARTCL_PX_VENTE_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();
        article.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, article.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(article))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();
        article.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(article))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();
        article.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        int databaseSizeBeforeDelete = articleRepository.findAll().size();

        // Delete the article
        restArticleMockMvc
            .perform(delete(ENTITY_API_URL_ID, article.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
