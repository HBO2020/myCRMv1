package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.Carts;
import com.hbo.mycrmv1.repository.CartsRepository;
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
 * Integration tests for the {@link CartsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CartsResourceIT {

    private static final Boolean DEFAULT_CART_IS_EMPTY = false;
    private static final Boolean UPDATED_CART_IS_EMPTY = true;

    private static final String DEFAULT_CART_USER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CART_USER_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CART_LIST_PRODUCT = "AAAAAAAAAA";
    private static final String UPDATED_CART_LIST_PRODUCT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/carts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CartsRepository cartsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCartsMockMvc;

    private Carts carts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carts createEntity(EntityManager em) {
        Carts carts = new Carts()
            .cartIsEmpty(DEFAULT_CART_IS_EMPTY)
            .cartUserEmail(DEFAULT_CART_USER_EMAIL)
            .cartListProduct(DEFAULT_CART_LIST_PRODUCT);
        return carts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carts createUpdatedEntity(EntityManager em) {
        Carts carts = new Carts()
            .cartIsEmpty(UPDATED_CART_IS_EMPTY)
            .cartUserEmail(UPDATED_CART_USER_EMAIL)
            .cartListProduct(UPDATED_CART_LIST_PRODUCT);
        return carts;
    }

    @BeforeEach
    public void initTest() {
        carts = createEntity(em);
    }

    @Test
    @Transactional
    void createCarts() throws Exception {
        int databaseSizeBeforeCreate = cartsRepository.findAll().size();
        // Create the Carts
        restCartsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carts)))
            .andExpect(status().isCreated());

        // Validate the Carts in the database
        List<Carts> cartsList = cartsRepository.findAll();
        assertThat(cartsList).hasSize(databaseSizeBeforeCreate + 1);
        Carts testCarts = cartsList.get(cartsList.size() - 1);
        assertThat(testCarts.getCartIsEmpty()).isEqualTo(DEFAULT_CART_IS_EMPTY);
        assertThat(testCarts.getCartUserEmail()).isEqualTo(DEFAULT_CART_USER_EMAIL);
        assertThat(testCarts.getCartListProduct()).isEqualTo(DEFAULT_CART_LIST_PRODUCT);
    }

    @Test
    @Transactional
    void createCartsWithExistingId() throws Exception {
        // Create the Carts with an existing ID
        carts.setId(1L);

        int databaseSizeBeforeCreate = cartsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carts)))
            .andExpect(status().isBadRequest());

        // Validate the Carts in the database
        List<Carts> cartsList = cartsRepository.findAll();
        assertThat(cartsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCarts() throws Exception {
        // Initialize the database
        cartsRepository.saveAndFlush(carts);

        // Get all the cartsList
        restCartsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carts.getId().intValue())))
            .andExpect(jsonPath("$.[*].cartIsEmpty").value(hasItem(DEFAULT_CART_IS_EMPTY.booleanValue())))
            .andExpect(jsonPath("$.[*].cartUserEmail").value(hasItem(DEFAULT_CART_USER_EMAIL)))
            .andExpect(jsonPath("$.[*].cartListProduct").value(hasItem(DEFAULT_CART_LIST_PRODUCT)));
    }

    @Test
    @Transactional
    void getCarts() throws Exception {
        // Initialize the database
        cartsRepository.saveAndFlush(carts);

        // Get the carts
        restCartsMockMvc
            .perform(get(ENTITY_API_URL_ID, carts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carts.getId().intValue()))
            .andExpect(jsonPath("$.cartIsEmpty").value(DEFAULT_CART_IS_EMPTY.booleanValue()))
            .andExpect(jsonPath("$.cartUserEmail").value(DEFAULT_CART_USER_EMAIL))
            .andExpect(jsonPath("$.cartListProduct").value(DEFAULT_CART_LIST_PRODUCT));
    }

    @Test
    @Transactional
    void getNonExistingCarts() throws Exception {
        // Get the carts
        restCartsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCarts() throws Exception {
        // Initialize the database
        cartsRepository.saveAndFlush(carts);

        int databaseSizeBeforeUpdate = cartsRepository.findAll().size();

        // Update the carts
        Carts updatedCarts = cartsRepository.findById(carts.getId()).get();
        // Disconnect from session so that the updates on updatedCarts are not directly saved in db
        em.detach(updatedCarts);
        updatedCarts.cartIsEmpty(UPDATED_CART_IS_EMPTY).cartUserEmail(UPDATED_CART_USER_EMAIL).cartListProduct(UPDATED_CART_LIST_PRODUCT);

        restCartsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCarts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCarts))
            )
            .andExpect(status().isOk());

        // Validate the Carts in the database
        List<Carts> cartsList = cartsRepository.findAll();
        assertThat(cartsList).hasSize(databaseSizeBeforeUpdate);
        Carts testCarts = cartsList.get(cartsList.size() - 1);
        assertThat(testCarts.getCartIsEmpty()).isEqualTo(UPDATED_CART_IS_EMPTY);
        assertThat(testCarts.getCartUserEmail()).isEqualTo(UPDATED_CART_USER_EMAIL);
        assertThat(testCarts.getCartListProduct()).isEqualTo(UPDATED_CART_LIST_PRODUCT);
    }

    @Test
    @Transactional
    void putNonExistingCarts() throws Exception {
        int databaseSizeBeforeUpdate = cartsRepository.findAll().size();
        carts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carts in the database
        List<Carts> cartsList = cartsRepository.findAll();
        assertThat(cartsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarts() throws Exception {
        int databaseSizeBeforeUpdate = cartsRepository.findAll().size();
        carts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carts in the database
        List<Carts> cartsList = cartsRepository.findAll();
        assertThat(cartsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarts() throws Exception {
        int databaseSizeBeforeUpdate = cartsRepository.findAll().size();
        carts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carts)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carts in the database
        List<Carts> cartsList = cartsRepository.findAll();
        assertThat(cartsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCartsWithPatch() throws Exception {
        // Initialize the database
        cartsRepository.saveAndFlush(carts);

        int databaseSizeBeforeUpdate = cartsRepository.findAll().size();

        // Update the carts using partial update
        Carts partialUpdatedCarts = new Carts();
        partialUpdatedCarts.setId(carts.getId());

        partialUpdatedCarts.cartUserEmail(UPDATED_CART_USER_EMAIL).cartListProduct(UPDATED_CART_LIST_PRODUCT);

        restCartsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarts))
            )
            .andExpect(status().isOk());

        // Validate the Carts in the database
        List<Carts> cartsList = cartsRepository.findAll();
        assertThat(cartsList).hasSize(databaseSizeBeforeUpdate);
        Carts testCarts = cartsList.get(cartsList.size() - 1);
        assertThat(testCarts.getCartIsEmpty()).isEqualTo(DEFAULT_CART_IS_EMPTY);
        assertThat(testCarts.getCartUserEmail()).isEqualTo(UPDATED_CART_USER_EMAIL);
        assertThat(testCarts.getCartListProduct()).isEqualTo(UPDATED_CART_LIST_PRODUCT);
    }

    @Test
    @Transactional
    void fullUpdateCartsWithPatch() throws Exception {
        // Initialize the database
        cartsRepository.saveAndFlush(carts);

        int databaseSizeBeforeUpdate = cartsRepository.findAll().size();

        // Update the carts using partial update
        Carts partialUpdatedCarts = new Carts();
        partialUpdatedCarts.setId(carts.getId());

        partialUpdatedCarts
            .cartIsEmpty(UPDATED_CART_IS_EMPTY)
            .cartUserEmail(UPDATED_CART_USER_EMAIL)
            .cartListProduct(UPDATED_CART_LIST_PRODUCT);

        restCartsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarts))
            )
            .andExpect(status().isOk());

        // Validate the Carts in the database
        List<Carts> cartsList = cartsRepository.findAll();
        assertThat(cartsList).hasSize(databaseSizeBeforeUpdate);
        Carts testCarts = cartsList.get(cartsList.size() - 1);
        assertThat(testCarts.getCartIsEmpty()).isEqualTo(UPDATED_CART_IS_EMPTY);
        assertThat(testCarts.getCartUserEmail()).isEqualTo(UPDATED_CART_USER_EMAIL);
        assertThat(testCarts.getCartListProduct()).isEqualTo(UPDATED_CART_LIST_PRODUCT);
    }

    @Test
    @Transactional
    void patchNonExistingCarts() throws Exception {
        int databaseSizeBeforeUpdate = cartsRepository.findAll().size();
        carts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carts in the database
        List<Carts> cartsList = cartsRepository.findAll();
        assertThat(cartsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarts() throws Exception {
        int databaseSizeBeforeUpdate = cartsRepository.findAll().size();
        carts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carts in the database
        List<Carts> cartsList = cartsRepository.findAll();
        assertThat(cartsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarts() throws Exception {
        int databaseSizeBeforeUpdate = cartsRepository.findAll().size();
        carts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(carts)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carts in the database
        List<Carts> cartsList = cartsRepository.findAll();
        assertThat(cartsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarts() throws Exception {
        // Initialize the database
        cartsRepository.saveAndFlush(carts);

        int databaseSizeBeforeDelete = cartsRepository.findAll().size();

        // Delete the carts
        restCartsMockMvc
            .perform(delete(ENTITY_API_URL_ID, carts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Carts> cartsList = cartsRepository.findAll();
        assertThat(cartsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
