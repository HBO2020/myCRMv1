package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.ContactClient;
import com.hbo.mycrmv1.repository.ContactClientRepository;
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
 * Integration tests for the {@link ContactClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactClientResourceIT {

    private static final String DEFAULT_CONTACT_NAME_CL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NAME_CL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PRENOM_CL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PRENOM_CL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL_CL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL_CL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_MOBILE_PHONE_CL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_MOBILE_PHONE_CL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_STATUS_CL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_STATUS_CL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contact-clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactClientRepository contactClientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactClientMockMvc;

    private ContactClient contactClient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactClient createEntity(EntityManager em) {
        ContactClient contactClient = new ContactClient()
            .contactNameCl(DEFAULT_CONTACT_NAME_CL)
            .contactPrenomCl(DEFAULT_CONTACT_PRENOM_CL)
            .contactEmailCl(DEFAULT_CONTACT_EMAIL_CL)
            .contactMobilePhoneCl(DEFAULT_CONTACT_MOBILE_PHONE_CL)
            .contactStatusCl(DEFAULT_CONTACT_STATUS_CL);
        return contactClient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactClient createUpdatedEntity(EntityManager em) {
        ContactClient contactClient = new ContactClient()
            .contactNameCl(UPDATED_CONTACT_NAME_CL)
            .contactPrenomCl(UPDATED_CONTACT_PRENOM_CL)
            .contactEmailCl(UPDATED_CONTACT_EMAIL_CL)
            .contactMobilePhoneCl(UPDATED_CONTACT_MOBILE_PHONE_CL)
            .contactStatusCl(UPDATED_CONTACT_STATUS_CL);
        return contactClient;
    }

    @BeforeEach
    public void initTest() {
        contactClient = createEntity(em);
    }

    @Test
    @Transactional
    void createContactClient() throws Exception {
        int databaseSizeBeforeCreate = contactClientRepository.findAll().size();
        // Create the ContactClient
        restContactClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactClient)))
            .andExpect(status().isCreated());

        // Validate the ContactClient in the database
        List<ContactClient> contactClientList = contactClientRepository.findAll();
        assertThat(contactClientList).hasSize(databaseSizeBeforeCreate + 1);
        ContactClient testContactClient = contactClientList.get(contactClientList.size() - 1);
        assertThat(testContactClient.getContactNameCl()).isEqualTo(DEFAULT_CONTACT_NAME_CL);
        assertThat(testContactClient.getContactPrenomCl()).isEqualTo(DEFAULT_CONTACT_PRENOM_CL);
        assertThat(testContactClient.getContactEmailCl()).isEqualTo(DEFAULT_CONTACT_EMAIL_CL);
        assertThat(testContactClient.getContactMobilePhoneCl()).isEqualTo(DEFAULT_CONTACT_MOBILE_PHONE_CL);
        assertThat(testContactClient.getContactStatusCl()).isEqualTo(DEFAULT_CONTACT_STATUS_CL);
    }

    @Test
    @Transactional
    void createContactClientWithExistingId() throws Exception {
        // Create the ContactClient with an existing ID
        contactClient.setId(1L);

        int databaseSizeBeforeCreate = contactClientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactClient)))
            .andExpect(status().isBadRequest());

        // Validate the ContactClient in the database
        List<ContactClient> contactClientList = contactClientRepository.findAll();
        assertThat(contactClientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContactClients() throws Exception {
        // Initialize the database
        contactClientRepository.saveAndFlush(contactClient);

        // Get all the contactClientList
        restContactClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactClient.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactNameCl").value(hasItem(DEFAULT_CONTACT_NAME_CL)))
            .andExpect(jsonPath("$.[*].contactPrenomCl").value(hasItem(DEFAULT_CONTACT_PRENOM_CL)))
            .andExpect(jsonPath("$.[*].contactEmailCl").value(hasItem(DEFAULT_CONTACT_EMAIL_CL)))
            .andExpect(jsonPath("$.[*].contactMobilePhoneCl").value(hasItem(DEFAULT_CONTACT_MOBILE_PHONE_CL)))
            .andExpect(jsonPath("$.[*].contactStatusCl").value(hasItem(DEFAULT_CONTACT_STATUS_CL)));
    }

    @Test
    @Transactional
    void getContactClient() throws Exception {
        // Initialize the database
        contactClientRepository.saveAndFlush(contactClient);

        // Get the contactClient
        restContactClientMockMvc
            .perform(get(ENTITY_API_URL_ID, contactClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactClient.getId().intValue()))
            .andExpect(jsonPath("$.contactNameCl").value(DEFAULT_CONTACT_NAME_CL))
            .andExpect(jsonPath("$.contactPrenomCl").value(DEFAULT_CONTACT_PRENOM_CL))
            .andExpect(jsonPath("$.contactEmailCl").value(DEFAULT_CONTACT_EMAIL_CL))
            .andExpect(jsonPath("$.contactMobilePhoneCl").value(DEFAULT_CONTACT_MOBILE_PHONE_CL))
            .andExpect(jsonPath("$.contactStatusCl").value(DEFAULT_CONTACT_STATUS_CL));
    }

    @Test
    @Transactional
    void getNonExistingContactClient() throws Exception {
        // Get the contactClient
        restContactClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContactClient() throws Exception {
        // Initialize the database
        contactClientRepository.saveAndFlush(contactClient);

        int databaseSizeBeforeUpdate = contactClientRepository.findAll().size();

        // Update the contactClient
        ContactClient updatedContactClient = contactClientRepository.findById(contactClient.getId()).get();
        // Disconnect from session so that the updates on updatedContactClient are not directly saved in db
        em.detach(updatedContactClient);
        updatedContactClient
            .contactNameCl(UPDATED_CONTACT_NAME_CL)
            .contactPrenomCl(UPDATED_CONTACT_PRENOM_CL)
            .contactEmailCl(UPDATED_CONTACT_EMAIL_CL)
            .contactMobilePhoneCl(UPDATED_CONTACT_MOBILE_PHONE_CL)
            .contactStatusCl(UPDATED_CONTACT_STATUS_CL);

        restContactClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContactClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContactClient))
            )
            .andExpect(status().isOk());

        // Validate the ContactClient in the database
        List<ContactClient> contactClientList = contactClientRepository.findAll();
        assertThat(contactClientList).hasSize(databaseSizeBeforeUpdate);
        ContactClient testContactClient = contactClientList.get(contactClientList.size() - 1);
        assertThat(testContactClient.getContactNameCl()).isEqualTo(UPDATED_CONTACT_NAME_CL);
        assertThat(testContactClient.getContactPrenomCl()).isEqualTo(UPDATED_CONTACT_PRENOM_CL);
        assertThat(testContactClient.getContactEmailCl()).isEqualTo(UPDATED_CONTACT_EMAIL_CL);
        assertThat(testContactClient.getContactMobilePhoneCl()).isEqualTo(UPDATED_CONTACT_MOBILE_PHONE_CL);
        assertThat(testContactClient.getContactStatusCl()).isEqualTo(UPDATED_CONTACT_STATUS_CL);
    }

    @Test
    @Transactional
    void putNonExistingContactClient() throws Exception {
        int databaseSizeBeforeUpdate = contactClientRepository.findAll().size();
        contactClient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactClient in the database
        List<ContactClient> contactClientList = contactClientRepository.findAll();
        assertThat(contactClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContactClient() throws Exception {
        int databaseSizeBeforeUpdate = contactClientRepository.findAll().size();
        contactClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactClient in the database
        List<ContactClient> contactClientList = contactClientRepository.findAll();
        assertThat(contactClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContactClient() throws Exception {
        int databaseSizeBeforeUpdate = contactClientRepository.findAll().size();
        contactClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactClient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactClient in the database
        List<ContactClient> contactClientList = contactClientRepository.findAll();
        assertThat(contactClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactClientWithPatch() throws Exception {
        // Initialize the database
        contactClientRepository.saveAndFlush(contactClient);

        int databaseSizeBeforeUpdate = contactClientRepository.findAll().size();

        // Update the contactClient using partial update
        ContactClient partialUpdatedContactClient = new ContactClient();
        partialUpdatedContactClient.setId(contactClient.getId());

        partialUpdatedContactClient.contactPrenomCl(UPDATED_CONTACT_PRENOM_CL).contactEmailCl(UPDATED_CONTACT_EMAIL_CL);

        restContactClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactClient))
            )
            .andExpect(status().isOk());

        // Validate the ContactClient in the database
        List<ContactClient> contactClientList = contactClientRepository.findAll();
        assertThat(contactClientList).hasSize(databaseSizeBeforeUpdate);
        ContactClient testContactClient = contactClientList.get(contactClientList.size() - 1);
        assertThat(testContactClient.getContactNameCl()).isEqualTo(DEFAULT_CONTACT_NAME_CL);
        assertThat(testContactClient.getContactPrenomCl()).isEqualTo(UPDATED_CONTACT_PRENOM_CL);
        assertThat(testContactClient.getContactEmailCl()).isEqualTo(UPDATED_CONTACT_EMAIL_CL);
        assertThat(testContactClient.getContactMobilePhoneCl()).isEqualTo(DEFAULT_CONTACT_MOBILE_PHONE_CL);
        assertThat(testContactClient.getContactStatusCl()).isEqualTo(DEFAULT_CONTACT_STATUS_CL);
    }

    @Test
    @Transactional
    void fullUpdateContactClientWithPatch() throws Exception {
        // Initialize the database
        contactClientRepository.saveAndFlush(contactClient);

        int databaseSizeBeforeUpdate = contactClientRepository.findAll().size();

        // Update the contactClient using partial update
        ContactClient partialUpdatedContactClient = new ContactClient();
        partialUpdatedContactClient.setId(contactClient.getId());

        partialUpdatedContactClient
            .contactNameCl(UPDATED_CONTACT_NAME_CL)
            .contactPrenomCl(UPDATED_CONTACT_PRENOM_CL)
            .contactEmailCl(UPDATED_CONTACT_EMAIL_CL)
            .contactMobilePhoneCl(UPDATED_CONTACT_MOBILE_PHONE_CL)
            .contactStatusCl(UPDATED_CONTACT_STATUS_CL);

        restContactClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactClient))
            )
            .andExpect(status().isOk());

        // Validate the ContactClient in the database
        List<ContactClient> contactClientList = contactClientRepository.findAll();
        assertThat(contactClientList).hasSize(databaseSizeBeforeUpdate);
        ContactClient testContactClient = contactClientList.get(contactClientList.size() - 1);
        assertThat(testContactClient.getContactNameCl()).isEqualTo(UPDATED_CONTACT_NAME_CL);
        assertThat(testContactClient.getContactPrenomCl()).isEqualTo(UPDATED_CONTACT_PRENOM_CL);
        assertThat(testContactClient.getContactEmailCl()).isEqualTo(UPDATED_CONTACT_EMAIL_CL);
        assertThat(testContactClient.getContactMobilePhoneCl()).isEqualTo(UPDATED_CONTACT_MOBILE_PHONE_CL);
        assertThat(testContactClient.getContactStatusCl()).isEqualTo(UPDATED_CONTACT_STATUS_CL);
    }

    @Test
    @Transactional
    void patchNonExistingContactClient() throws Exception {
        int databaseSizeBeforeUpdate = contactClientRepository.findAll().size();
        contactClient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactClient in the database
        List<ContactClient> contactClientList = contactClientRepository.findAll();
        assertThat(contactClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContactClient() throws Exception {
        int databaseSizeBeforeUpdate = contactClientRepository.findAll().size();
        contactClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactClient in the database
        List<ContactClient> contactClientList = contactClientRepository.findAll();
        assertThat(contactClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContactClient() throws Exception {
        int databaseSizeBeforeUpdate = contactClientRepository.findAll().size();
        contactClient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactClientMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contactClient))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactClient in the database
        List<ContactClient> contactClientList = contactClientRepository.findAll();
        assertThat(contactClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContactClient() throws Exception {
        // Initialize the database
        contactClientRepository.saveAndFlush(contactClient);

        int databaseSizeBeforeDelete = contactClientRepository.findAll().size();

        // Delete the contactClient
        restContactClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, contactClient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactClient> contactClientList = contactClientRepository.findAll();
        assertThat(contactClientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
