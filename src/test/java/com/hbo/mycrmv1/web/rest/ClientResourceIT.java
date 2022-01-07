package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.Client;
import com.hbo.mycrmv1.repository.ClientRepository;
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
 * Integration tests for the {@link ClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientResourceIT {

    private static final Integer DEFAULT_CL_IDENT = 1;
    private static final Integer UPDATED_CL_IDENT = 2;

    private static final String DEFAULT_CL_RAISON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_CL_RAISON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_CL_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_CL_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_CL_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CL_CODE_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_CL_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_CL_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_CL_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_CL_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CL_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CL_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CL_NUMERO_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_CL_NUMERO_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_CL_NUMERO_FAX = "AAAAAAAAAA";
    private static final String UPDATED_CL_NUMERO_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_CL_NUMERO_FIXE = "AAAAAAAAAA";
    private static final String UPDATED_CL_NUMERO_FIXE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CL_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CL_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CL_DATE_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CL_DATE_UPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CL_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CL_NUMERO_SIRET = "AAAAAAAAAA";
    private static final String UPDATED_CL_NUMERO_SIRET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .clIdent(DEFAULT_CL_IDENT)
            .clRaisonSocial(DEFAULT_CL_RAISON_SOCIAL)
            .clAdresse(DEFAULT_CL_ADRESSE)
            .clCodePostal(DEFAULT_CL_CODE_POSTAL)
            .clVille(DEFAULT_CL_VILLE)
            .clCountry(DEFAULT_CL_COUNTRY)
            .clEmail(DEFAULT_CL_EMAIL)
            .clNumeroMobile(DEFAULT_CL_NUMERO_MOBILE)
            .clNumeroFax(DEFAULT_CL_NUMERO_FAX)
            .clNumeroFixe(DEFAULT_CL_NUMERO_FIXE)
            .clDateCreation(DEFAULT_CL_DATE_CREATION)
            .clDateUpdate(DEFAULT_CL_DATE_UPDATE)
            .clStatus(DEFAULT_CL_STATUS)
            .clNumeroSiret(DEFAULT_CL_NUMERO_SIRET);
        return client;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .clIdent(UPDATED_CL_IDENT)
            .clRaisonSocial(UPDATED_CL_RAISON_SOCIAL)
            .clAdresse(UPDATED_CL_ADRESSE)
            .clCodePostal(UPDATED_CL_CODE_POSTAL)
            .clVille(UPDATED_CL_VILLE)
            .clCountry(UPDATED_CL_COUNTRY)
            .clEmail(UPDATED_CL_EMAIL)
            .clNumeroMobile(UPDATED_CL_NUMERO_MOBILE)
            .clNumeroFax(UPDATED_CL_NUMERO_FAX)
            .clNumeroFixe(UPDATED_CL_NUMERO_FIXE)
            .clDateCreation(UPDATED_CL_DATE_CREATION)
            .clDateUpdate(UPDATED_CL_DATE_UPDATE)
            .clStatus(UPDATED_CL_STATUS)
            .clNumeroSiret(UPDATED_CL_NUMERO_SIRET);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClIdent()).isEqualTo(DEFAULT_CL_IDENT);
        assertThat(testClient.getClRaisonSocial()).isEqualTo(DEFAULT_CL_RAISON_SOCIAL);
        assertThat(testClient.getClAdresse()).isEqualTo(DEFAULT_CL_ADRESSE);
        assertThat(testClient.getClCodePostal()).isEqualTo(DEFAULT_CL_CODE_POSTAL);
        assertThat(testClient.getClVille()).isEqualTo(DEFAULT_CL_VILLE);
        assertThat(testClient.getClCountry()).isEqualTo(DEFAULT_CL_COUNTRY);
        assertThat(testClient.getClEmail()).isEqualTo(DEFAULT_CL_EMAIL);
        assertThat(testClient.getClNumeroMobile()).isEqualTo(DEFAULT_CL_NUMERO_MOBILE);
        assertThat(testClient.getClNumeroFax()).isEqualTo(DEFAULT_CL_NUMERO_FAX);
        assertThat(testClient.getClNumeroFixe()).isEqualTo(DEFAULT_CL_NUMERO_FIXE);
        assertThat(testClient.getClDateCreation()).isEqualTo(DEFAULT_CL_DATE_CREATION);
        assertThat(testClient.getClDateUpdate()).isEqualTo(DEFAULT_CL_DATE_UPDATE);
        assertThat(testClient.getClStatus()).isEqualTo(DEFAULT_CL_STATUS);
        assertThat(testClient.getClNumeroSiret()).isEqualTo(DEFAULT_CL_NUMERO_SIRET);
    }

    @Test
    @Transactional
    void createClientWithExistingId() throws Exception {
        // Create the Client with an existing ID
        client.setId(1L);

        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].clIdent").value(hasItem(DEFAULT_CL_IDENT)))
            .andExpect(jsonPath("$.[*].clRaisonSocial").value(hasItem(DEFAULT_CL_RAISON_SOCIAL)))
            .andExpect(jsonPath("$.[*].clAdresse").value(hasItem(DEFAULT_CL_ADRESSE)))
            .andExpect(jsonPath("$.[*].clCodePostal").value(hasItem(DEFAULT_CL_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].clVille").value(hasItem(DEFAULT_CL_VILLE)))
            .andExpect(jsonPath("$.[*].clCountry").value(hasItem(DEFAULT_CL_COUNTRY)))
            .andExpect(jsonPath("$.[*].clEmail").value(hasItem(DEFAULT_CL_EMAIL)))
            .andExpect(jsonPath("$.[*].clNumeroMobile").value(hasItem(DEFAULT_CL_NUMERO_MOBILE)))
            .andExpect(jsonPath("$.[*].clNumeroFax").value(hasItem(DEFAULT_CL_NUMERO_FAX)))
            .andExpect(jsonPath("$.[*].clNumeroFixe").value(hasItem(DEFAULT_CL_NUMERO_FIXE)))
            .andExpect(jsonPath("$.[*].clDateCreation").value(hasItem(DEFAULT_CL_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].clDateUpdate").value(hasItem(DEFAULT_CL_DATE_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].clStatus").value(hasItem(DEFAULT_CL_STATUS)))
            .andExpect(jsonPath("$.[*].clNumeroSiret").value(hasItem(DEFAULT_CL_NUMERO_SIRET)));
    }

    @Test
    @Transactional
    void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc
            .perform(get(ENTITY_API_URL_ID, client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.clIdent").value(DEFAULT_CL_IDENT))
            .andExpect(jsonPath("$.clRaisonSocial").value(DEFAULT_CL_RAISON_SOCIAL))
            .andExpect(jsonPath("$.clAdresse").value(DEFAULT_CL_ADRESSE))
            .andExpect(jsonPath("$.clCodePostal").value(DEFAULT_CL_CODE_POSTAL))
            .andExpect(jsonPath("$.clVille").value(DEFAULT_CL_VILLE))
            .andExpect(jsonPath("$.clCountry").value(DEFAULT_CL_COUNTRY))
            .andExpect(jsonPath("$.clEmail").value(DEFAULT_CL_EMAIL))
            .andExpect(jsonPath("$.clNumeroMobile").value(DEFAULT_CL_NUMERO_MOBILE))
            .andExpect(jsonPath("$.clNumeroFax").value(DEFAULT_CL_NUMERO_FAX))
            .andExpect(jsonPath("$.clNumeroFixe").value(DEFAULT_CL_NUMERO_FIXE))
            .andExpect(jsonPath("$.clDateCreation").value(DEFAULT_CL_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.clDateUpdate").value(DEFAULT_CL_DATE_UPDATE.toString()))
            .andExpect(jsonPath("$.clStatus").value(DEFAULT_CL_STATUS))
            .andExpect(jsonPath("$.clNumeroSiret").value(DEFAULT_CL_NUMERO_SIRET));
    }

    @Test
    @Transactional
    void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .clIdent(UPDATED_CL_IDENT)
            .clRaisonSocial(UPDATED_CL_RAISON_SOCIAL)
            .clAdresse(UPDATED_CL_ADRESSE)
            .clCodePostal(UPDATED_CL_CODE_POSTAL)
            .clVille(UPDATED_CL_VILLE)
            .clCountry(UPDATED_CL_COUNTRY)
            .clEmail(UPDATED_CL_EMAIL)
            .clNumeroMobile(UPDATED_CL_NUMERO_MOBILE)
            .clNumeroFax(UPDATED_CL_NUMERO_FAX)
            .clNumeroFixe(UPDATED_CL_NUMERO_FIXE)
            .clDateCreation(UPDATED_CL_DATE_CREATION)
            .clDateUpdate(UPDATED_CL_DATE_UPDATE)
            .clStatus(UPDATED_CL_STATUS)
            .clNumeroSiret(UPDATED_CL_NUMERO_SIRET);

        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClIdent()).isEqualTo(UPDATED_CL_IDENT);
        assertThat(testClient.getClRaisonSocial()).isEqualTo(UPDATED_CL_RAISON_SOCIAL);
        assertThat(testClient.getClAdresse()).isEqualTo(UPDATED_CL_ADRESSE);
        assertThat(testClient.getClCodePostal()).isEqualTo(UPDATED_CL_CODE_POSTAL);
        assertThat(testClient.getClVille()).isEqualTo(UPDATED_CL_VILLE);
        assertThat(testClient.getClCountry()).isEqualTo(UPDATED_CL_COUNTRY);
        assertThat(testClient.getClEmail()).isEqualTo(UPDATED_CL_EMAIL);
        assertThat(testClient.getClNumeroMobile()).isEqualTo(UPDATED_CL_NUMERO_MOBILE);
        assertThat(testClient.getClNumeroFax()).isEqualTo(UPDATED_CL_NUMERO_FAX);
        assertThat(testClient.getClNumeroFixe()).isEqualTo(UPDATED_CL_NUMERO_FIXE);
        assertThat(testClient.getClDateCreation()).isEqualTo(UPDATED_CL_DATE_CREATION);
        assertThat(testClient.getClDateUpdate()).isEqualTo(UPDATED_CL_DATE_UPDATE);
        assertThat(testClient.getClStatus()).isEqualTo(UPDATED_CL_STATUS);
        assertThat(testClient.getClNumeroSiret()).isEqualTo(UPDATED_CL_NUMERO_SIRET);
    }

    @Test
    @Transactional
    void putNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, client.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .clIdent(UPDATED_CL_IDENT)
            .clRaisonSocial(UPDATED_CL_RAISON_SOCIAL)
            .clAdresse(UPDATED_CL_ADRESSE)
            .clEmail(UPDATED_CL_EMAIL)
            .clNumeroFax(UPDATED_CL_NUMERO_FAX)
            .clNumeroFixe(UPDATED_CL_NUMERO_FIXE)
            .clDateCreation(UPDATED_CL_DATE_CREATION)
            .clDateUpdate(UPDATED_CL_DATE_UPDATE)
            .clStatus(UPDATED_CL_STATUS)
            .clNumeroSiret(UPDATED_CL_NUMERO_SIRET);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClIdent()).isEqualTo(UPDATED_CL_IDENT);
        assertThat(testClient.getClRaisonSocial()).isEqualTo(UPDATED_CL_RAISON_SOCIAL);
        assertThat(testClient.getClAdresse()).isEqualTo(UPDATED_CL_ADRESSE);
        assertThat(testClient.getClCodePostal()).isEqualTo(DEFAULT_CL_CODE_POSTAL);
        assertThat(testClient.getClVille()).isEqualTo(DEFAULT_CL_VILLE);
        assertThat(testClient.getClCountry()).isEqualTo(DEFAULT_CL_COUNTRY);
        assertThat(testClient.getClEmail()).isEqualTo(UPDATED_CL_EMAIL);
        assertThat(testClient.getClNumeroMobile()).isEqualTo(DEFAULT_CL_NUMERO_MOBILE);
        assertThat(testClient.getClNumeroFax()).isEqualTo(UPDATED_CL_NUMERO_FAX);
        assertThat(testClient.getClNumeroFixe()).isEqualTo(UPDATED_CL_NUMERO_FIXE);
        assertThat(testClient.getClDateCreation()).isEqualTo(UPDATED_CL_DATE_CREATION);
        assertThat(testClient.getClDateUpdate()).isEqualTo(UPDATED_CL_DATE_UPDATE);
        assertThat(testClient.getClStatus()).isEqualTo(UPDATED_CL_STATUS);
        assertThat(testClient.getClNumeroSiret()).isEqualTo(UPDATED_CL_NUMERO_SIRET);
    }

    @Test
    @Transactional
    void fullUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .clIdent(UPDATED_CL_IDENT)
            .clRaisonSocial(UPDATED_CL_RAISON_SOCIAL)
            .clAdresse(UPDATED_CL_ADRESSE)
            .clCodePostal(UPDATED_CL_CODE_POSTAL)
            .clVille(UPDATED_CL_VILLE)
            .clCountry(UPDATED_CL_COUNTRY)
            .clEmail(UPDATED_CL_EMAIL)
            .clNumeroMobile(UPDATED_CL_NUMERO_MOBILE)
            .clNumeroFax(UPDATED_CL_NUMERO_FAX)
            .clNumeroFixe(UPDATED_CL_NUMERO_FIXE)
            .clDateCreation(UPDATED_CL_DATE_CREATION)
            .clDateUpdate(UPDATED_CL_DATE_UPDATE)
            .clStatus(UPDATED_CL_STATUS)
            .clNumeroSiret(UPDATED_CL_NUMERO_SIRET);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClIdent()).isEqualTo(UPDATED_CL_IDENT);
        assertThat(testClient.getClRaisonSocial()).isEqualTo(UPDATED_CL_RAISON_SOCIAL);
        assertThat(testClient.getClAdresse()).isEqualTo(UPDATED_CL_ADRESSE);
        assertThat(testClient.getClCodePostal()).isEqualTo(UPDATED_CL_CODE_POSTAL);
        assertThat(testClient.getClVille()).isEqualTo(UPDATED_CL_VILLE);
        assertThat(testClient.getClCountry()).isEqualTo(UPDATED_CL_COUNTRY);
        assertThat(testClient.getClEmail()).isEqualTo(UPDATED_CL_EMAIL);
        assertThat(testClient.getClNumeroMobile()).isEqualTo(UPDATED_CL_NUMERO_MOBILE);
        assertThat(testClient.getClNumeroFax()).isEqualTo(UPDATED_CL_NUMERO_FAX);
        assertThat(testClient.getClNumeroFixe()).isEqualTo(UPDATED_CL_NUMERO_FIXE);
        assertThat(testClient.getClDateCreation()).isEqualTo(UPDATED_CL_DATE_CREATION);
        assertThat(testClient.getClDateUpdate()).isEqualTo(UPDATED_CL_DATE_UPDATE);
        assertThat(testClient.getClStatus()).isEqualTo(UPDATED_CL_STATUS);
        assertThat(testClient.getClNumeroSiret()).isEqualTo(UPDATED_CL_NUMERO_SIRET);
    }

    @Test
    @Transactional
    void patchNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, client.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, client.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
