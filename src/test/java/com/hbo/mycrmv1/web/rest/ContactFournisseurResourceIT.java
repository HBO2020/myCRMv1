package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.ContactFournisseur;
import com.hbo.mycrmv1.repository.ContactFournisseurRepository;
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
 * Integration tests for the {@link ContactFournisseurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactFournisseurResourceIT {

    private static final String DEFAULT_CONTACT_FR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_FR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTFR_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTFR_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_FR_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_FR_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_FR_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_FR_MOBILE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_FR_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_FR_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contact-fournisseurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactFournisseurRepository contactFournisseurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactFournisseurMockMvc;

    private ContactFournisseur contactFournisseur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactFournisseur createEntity(EntityManager em) {
        ContactFournisseur contactFournisseur = new ContactFournisseur()
            .contactFrName(DEFAULT_CONTACT_FR_NAME)
            .contactfrPrenom(DEFAULT_CONTACTFR_PRENOM)
            .contactFrEmail(DEFAULT_CONTACT_FR_EMAIL)
            .contactFrMobilePhone(DEFAULT_CONTACT_FR_MOBILE_PHONE)
            .contactFrStatus(DEFAULT_CONTACT_FR_STATUS);
        return contactFournisseur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactFournisseur createUpdatedEntity(EntityManager em) {
        ContactFournisseur contactFournisseur = new ContactFournisseur()
            .contactFrName(UPDATED_CONTACT_FR_NAME)
            .contactfrPrenom(UPDATED_CONTACTFR_PRENOM)
            .contactFrEmail(UPDATED_CONTACT_FR_EMAIL)
            .contactFrMobilePhone(UPDATED_CONTACT_FR_MOBILE_PHONE)
            .contactFrStatus(UPDATED_CONTACT_FR_STATUS);
        return contactFournisseur;
    }

    @BeforeEach
    public void initTest() {
        contactFournisseur = createEntity(em);
    }

    @Test
    @Transactional
    void createContactFournisseur() throws Exception {
        int databaseSizeBeforeCreate = contactFournisseurRepository.findAll().size();
        // Create the ContactFournisseur
        restContactFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactFournisseur))
            )
            .andExpect(status().isCreated());

        // Validate the ContactFournisseur in the database
        List<ContactFournisseur> contactFournisseurList = contactFournisseurRepository.findAll();
        assertThat(contactFournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        ContactFournisseur testContactFournisseur = contactFournisseurList.get(contactFournisseurList.size() - 1);
        assertThat(testContactFournisseur.getContactFrName()).isEqualTo(DEFAULT_CONTACT_FR_NAME);
        assertThat(testContactFournisseur.getContactfrPrenom()).isEqualTo(DEFAULT_CONTACTFR_PRENOM);
        assertThat(testContactFournisseur.getContactFrEmail()).isEqualTo(DEFAULT_CONTACT_FR_EMAIL);
        assertThat(testContactFournisseur.getContactFrMobilePhone()).isEqualTo(DEFAULT_CONTACT_FR_MOBILE_PHONE);
        assertThat(testContactFournisseur.getContactFrStatus()).isEqualTo(DEFAULT_CONTACT_FR_STATUS);
    }

    @Test
    @Transactional
    void createContactFournisseurWithExistingId() throws Exception {
        // Create the ContactFournisseur with an existing ID
        contactFournisseur.setId(1L);

        int databaseSizeBeforeCreate = contactFournisseurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactFournisseur in the database
        List<ContactFournisseur> contactFournisseurList = contactFournisseurRepository.findAll();
        assertThat(contactFournisseurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContactFournisseurs() throws Exception {
        // Initialize the database
        contactFournisseurRepository.saveAndFlush(contactFournisseur);

        // Get all the contactFournisseurList
        restContactFournisseurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactFournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactFrName").value(hasItem(DEFAULT_CONTACT_FR_NAME)))
            .andExpect(jsonPath("$.[*].contactfrPrenom").value(hasItem(DEFAULT_CONTACTFR_PRENOM)))
            .andExpect(jsonPath("$.[*].contactFrEmail").value(hasItem(DEFAULT_CONTACT_FR_EMAIL)))
            .andExpect(jsonPath("$.[*].contactFrMobilePhone").value(hasItem(DEFAULT_CONTACT_FR_MOBILE_PHONE)))
            .andExpect(jsonPath("$.[*].contactFrStatus").value(hasItem(DEFAULT_CONTACT_FR_STATUS)));
    }

    @Test
    @Transactional
    void getContactFournisseur() throws Exception {
        // Initialize the database
        contactFournisseurRepository.saveAndFlush(contactFournisseur);

        // Get the contactFournisseur
        restContactFournisseurMockMvc
            .perform(get(ENTITY_API_URL_ID, contactFournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactFournisseur.getId().intValue()))
            .andExpect(jsonPath("$.contactFrName").value(DEFAULT_CONTACT_FR_NAME))
            .andExpect(jsonPath("$.contactfrPrenom").value(DEFAULT_CONTACTFR_PRENOM))
            .andExpect(jsonPath("$.contactFrEmail").value(DEFAULT_CONTACT_FR_EMAIL))
            .andExpect(jsonPath("$.contactFrMobilePhone").value(DEFAULT_CONTACT_FR_MOBILE_PHONE))
            .andExpect(jsonPath("$.contactFrStatus").value(DEFAULT_CONTACT_FR_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingContactFournisseur() throws Exception {
        // Get the contactFournisseur
        restContactFournisseurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContactFournisseur() throws Exception {
        // Initialize the database
        contactFournisseurRepository.saveAndFlush(contactFournisseur);

        int databaseSizeBeforeUpdate = contactFournisseurRepository.findAll().size();

        // Update the contactFournisseur
        ContactFournisseur updatedContactFournisseur = contactFournisseurRepository.findById(contactFournisseur.getId()).get();
        // Disconnect from session so that the updates on updatedContactFournisseur are not directly saved in db
        em.detach(updatedContactFournisseur);
        updatedContactFournisseur
            .contactFrName(UPDATED_CONTACT_FR_NAME)
            .contactfrPrenom(UPDATED_CONTACTFR_PRENOM)
            .contactFrEmail(UPDATED_CONTACT_FR_EMAIL)
            .contactFrMobilePhone(UPDATED_CONTACT_FR_MOBILE_PHONE)
            .contactFrStatus(UPDATED_CONTACT_FR_STATUS);

        restContactFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContactFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContactFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the ContactFournisseur in the database
        List<ContactFournisseur> contactFournisseurList = contactFournisseurRepository.findAll();
        assertThat(contactFournisseurList).hasSize(databaseSizeBeforeUpdate);
        ContactFournisseur testContactFournisseur = contactFournisseurList.get(contactFournisseurList.size() - 1);
        assertThat(testContactFournisseur.getContactFrName()).isEqualTo(UPDATED_CONTACT_FR_NAME);
        assertThat(testContactFournisseur.getContactfrPrenom()).isEqualTo(UPDATED_CONTACTFR_PRENOM);
        assertThat(testContactFournisseur.getContactFrEmail()).isEqualTo(UPDATED_CONTACT_FR_EMAIL);
        assertThat(testContactFournisseur.getContactFrMobilePhone()).isEqualTo(UPDATED_CONTACT_FR_MOBILE_PHONE);
        assertThat(testContactFournisseur.getContactFrStatus()).isEqualTo(UPDATED_CONTACT_FR_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingContactFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = contactFournisseurRepository.findAll().size();
        contactFournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactFournisseur in the database
        List<ContactFournisseur> contactFournisseurList = contactFournisseurRepository.findAll();
        assertThat(contactFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContactFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = contactFournisseurRepository.findAll().size();
        contactFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactFournisseur in the database
        List<ContactFournisseur> contactFournisseurList = contactFournisseurRepository.findAll();
        assertThat(contactFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContactFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = contactFournisseurRepository.findAll().size();
        contactFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactFournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactFournisseur in the database
        List<ContactFournisseur> contactFournisseurList = contactFournisseurRepository.findAll();
        assertThat(contactFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactFournisseurWithPatch() throws Exception {
        // Initialize the database
        contactFournisseurRepository.saveAndFlush(contactFournisseur);

        int databaseSizeBeforeUpdate = contactFournisseurRepository.findAll().size();

        // Update the contactFournisseur using partial update
        ContactFournisseur partialUpdatedContactFournisseur = new ContactFournisseur();
        partialUpdatedContactFournisseur.setId(contactFournisseur.getId());

        partialUpdatedContactFournisseur
            .contactFrName(UPDATED_CONTACT_FR_NAME)
            .contactfrPrenom(UPDATED_CONTACTFR_PRENOM)
            .contactFrMobilePhone(UPDATED_CONTACT_FR_MOBILE_PHONE);

        restContactFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the ContactFournisseur in the database
        List<ContactFournisseur> contactFournisseurList = contactFournisseurRepository.findAll();
        assertThat(contactFournisseurList).hasSize(databaseSizeBeforeUpdate);
        ContactFournisseur testContactFournisseur = contactFournisseurList.get(contactFournisseurList.size() - 1);
        assertThat(testContactFournisseur.getContactFrName()).isEqualTo(UPDATED_CONTACT_FR_NAME);
        assertThat(testContactFournisseur.getContactfrPrenom()).isEqualTo(UPDATED_CONTACTFR_PRENOM);
        assertThat(testContactFournisseur.getContactFrEmail()).isEqualTo(DEFAULT_CONTACT_FR_EMAIL);
        assertThat(testContactFournisseur.getContactFrMobilePhone()).isEqualTo(UPDATED_CONTACT_FR_MOBILE_PHONE);
        assertThat(testContactFournisseur.getContactFrStatus()).isEqualTo(DEFAULT_CONTACT_FR_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateContactFournisseurWithPatch() throws Exception {
        // Initialize the database
        contactFournisseurRepository.saveAndFlush(contactFournisseur);

        int databaseSizeBeforeUpdate = contactFournisseurRepository.findAll().size();

        // Update the contactFournisseur using partial update
        ContactFournisseur partialUpdatedContactFournisseur = new ContactFournisseur();
        partialUpdatedContactFournisseur.setId(contactFournisseur.getId());

        partialUpdatedContactFournisseur
            .contactFrName(UPDATED_CONTACT_FR_NAME)
            .contactfrPrenom(UPDATED_CONTACTFR_PRENOM)
            .contactFrEmail(UPDATED_CONTACT_FR_EMAIL)
            .contactFrMobilePhone(UPDATED_CONTACT_FR_MOBILE_PHONE)
            .contactFrStatus(UPDATED_CONTACT_FR_STATUS);

        restContactFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the ContactFournisseur in the database
        List<ContactFournisseur> contactFournisseurList = contactFournisseurRepository.findAll();
        assertThat(contactFournisseurList).hasSize(databaseSizeBeforeUpdate);
        ContactFournisseur testContactFournisseur = contactFournisseurList.get(contactFournisseurList.size() - 1);
        assertThat(testContactFournisseur.getContactFrName()).isEqualTo(UPDATED_CONTACT_FR_NAME);
        assertThat(testContactFournisseur.getContactfrPrenom()).isEqualTo(UPDATED_CONTACTFR_PRENOM);
        assertThat(testContactFournisseur.getContactFrEmail()).isEqualTo(UPDATED_CONTACT_FR_EMAIL);
        assertThat(testContactFournisseur.getContactFrMobilePhone()).isEqualTo(UPDATED_CONTACT_FR_MOBILE_PHONE);
        assertThat(testContactFournisseur.getContactFrStatus()).isEqualTo(UPDATED_CONTACT_FR_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingContactFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = contactFournisseurRepository.findAll().size();
        contactFournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactFournisseur in the database
        List<ContactFournisseur> contactFournisseurList = contactFournisseurRepository.findAll();
        assertThat(contactFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContactFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = contactFournisseurRepository.findAll().size();
        contactFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactFournisseur in the database
        List<ContactFournisseur> contactFournisseurList = contactFournisseurRepository.findAll();
        assertThat(contactFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContactFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = contactFournisseurRepository.findAll().size();
        contactFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactFournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactFournisseur in the database
        List<ContactFournisseur> contactFournisseurList = contactFournisseurRepository.findAll();
        assertThat(contactFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContactFournisseur() throws Exception {
        // Initialize the database
        contactFournisseurRepository.saveAndFlush(contactFournisseur);

        int databaseSizeBeforeDelete = contactFournisseurRepository.findAll().size();

        // Delete the contactFournisseur
        restContactFournisseurMockMvc
            .perform(delete(ENTITY_API_URL_ID, contactFournisseur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactFournisseur> contactFournisseurList = contactFournisseurRepository.findAll();
        assertThat(contactFournisseurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
