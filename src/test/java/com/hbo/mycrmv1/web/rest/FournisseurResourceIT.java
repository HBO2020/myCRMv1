package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.Fournisseur;
import com.hbo.mycrmv1.repository.FournisseurRepository;
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
 * Integration tests for the {@link FournisseurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FournisseurResourceIT {

    private static final Integer DEFAULT_FR_IDENT = 1;
    private static final Integer UPDATED_FR_IDENT = 2;

    private static final String DEFAULT_FR_RAISON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_FR_RAISON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_FR_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_FR_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_FR_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_FR_CODE_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_FR_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_FR_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_FR_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_FR_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_FR_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_FR_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_FR_NUMERO_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_FR_NUMERO_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_FR_NUMERO_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FR_NUMERO_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_FR_NUMERO_FIXE = "AAAAAAAAAA";
    private static final String UPDATED_FR_NUMERO_FIXE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FR_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FR_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FR_DATE_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FR_DATE_UPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FR_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_FR_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_FR_NUMERO_SIRET = "AAAAAAAAAA";
    private static final String UPDATED_FR_NUMERO_SIRET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fournisseurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFournisseurMockMvc;

    private Fournisseur fournisseur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fournisseur createEntity(EntityManager em) {
        Fournisseur fournisseur = new Fournisseur()
            .frIdent(DEFAULT_FR_IDENT)
            .frRaisonSocial(DEFAULT_FR_RAISON_SOCIAL)
            .frAdresse(DEFAULT_FR_ADRESSE)
            .frCodePostal(DEFAULT_FR_CODE_POSTAL)
            .frVille(DEFAULT_FR_VILLE)
            .frCountry(DEFAULT_FR_COUNTRY)
            .frEmail(DEFAULT_FR_EMAIL)
            .frNumeroMobile(DEFAULT_FR_NUMERO_MOBILE)
            .frNumeroFax(DEFAULT_FR_NUMERO_FAX)
            .frNumeroFixe(DEFAULT_FR_NUMERO_FIXE)
            .frDateCreation(DEFAULT_FR_DATE_CREATION)
            .frDateUpdate(DEFAULT_FR_DATE_UPDATE)
            .frStatus(DEFAULT_FR_STATUS)
            .frNumeroSiret(DEFAULT_FR_NUMERO_SIRET);
        return fournisseur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fournisseur createUpdatedEntity(EntityManager em) {
        Fournisseur fournisseur = new Fournisseur()
            .frIdent(UPDATED_FR_IDENT)
            .frRaisonSocial(UPDATED_FR_RAISON_SOCIAL)
            .frAdresse(UPDATED_FR_ADRESSE)
            .frCodePostal(UPDATED_FR_CODE_POSTAL)
            .frVille(UPDATED_FR_VILLE)
            .frCountry(UPDATED_FR_COUNTRY)
            .frEmail(UPDATED_FR_EMAIL)
            .frNumeroMobile(UPDATED_FR_NUMERO_MOBILE)
            .frNumeroFax(UPDATED_FR_NUMERO_FAX)
            .frNumeroFixe(UPDATED_FR_NUMERO_FIXE)
            .frDateCreation(UPDATED_FR_DATE_CREATION)
            .frDateUpdate(UPDATED_FR_DATE_UPDATE)
            .frStatus(UPDATED_FR_STATUS)
            .frNumeroSiret(UPDATED_FR_NUMERO_SIRET);
        return fournisseur;
    }

    @BeforeEach
    public void initTest() {
        fournisseur = createEntity(em);
    }

    @Test
    @Transactional
    void createFournisseur() throws Exception {
        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();
        // Create the Fournisseur
        restFournisseurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isCreated());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getFrIdent()).isEqualTo(DEFAULT_FR_IDENT);
        assertThat(testFournisseur.getFrRaisonSocial()).isEqualTo(DEFAULT_FR_RAISON_SOCIAL);
        assertThat(testFournisseur.getFrAdresse()).isEqualTo(DEFAULT_FR_ADRESSE);
        assertThat(testFournisseur.getFrCodePostal()).isEqualTo(DEFAULT_FR_CODE_POSTAL);
        assertThat(testFournisseur.getFrVille()).isEqualTo(DEFAULT_FR_VILLE);
        assertThat(testFournisseur.getFrCountry()).isEqualTo(DEFAULT_FR_COUNTRY);
        assertThat(testFournisseur.getFrEmail()).isEqualTo(DEFAULT_FR_EMAIL);
        assertThat(testFournisseur.getFrNumeroMobile()).isEqualTo(DEFAULT_FR_NUMERO_MOBILE);
        assertThat(testFournisseur.getFrNumeroFax()).isEqualTo(DEFAULT_FR_NUMERO_FAX);
        assertThat(testFournisseur.getFrNumeroFixe()).isEqualTo(DEFAULT_FR_NUMERO_FIXE);
        assertThat(testFournisseur.getFrDateCreation()).isEqualTo(DEFAULT_FR_DATE_CREATION);
        assertThat(testFournisseur.getFrDateUpdate()).isEqualTo(DEFAULT_FR_DATE_UPDATE);
        assertThat(testFournisseur.getFrStatus()).isEqualTo(DEFAULT_FR_STATUS);
        assertThat(testFournisseur.getFrNumeroSiret()).isEqualTo(DEFAULT_FR_NUMERO_SIRET);
    }

    @Test
    @Transactional
    void createFournisseurWithExistingId() throws Exception {
        // Create the Fournisseur with an existing ID
        fournisseur.setId(1L);

        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFournisseurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFournisseurs() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList
        restFournisseurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].frIdent").value(hasItem(DEFAULT_FR_IDENT)))
            .andExpect(jsonPath("$.[*].frRaisonSocial").value(hasItem(DEFAULT_FR_RAISON_SOCIAL)))
            .andExpect(jsonPath("$.[*].frAdresse").value(hasItem(DEFAULT_FR_ADRESSE)))
            .andExpect(jsonPath("$.[*].frCodePostal").value(hasItem(DEFAULT_FR_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].frVille").value(hasItem(DEFAULT_FR_VILLE)))
            .andExpect(jsonPath("$.[*].frCountry").value(hasItem(DEFAULT_FR_COUNTRY)))
            .andExpect(jsonPath("$.[*].frEmail").value(hasItem(DEFAULT_FR_EMAIL)))
            .andExpect(jsonPath("$.[*].frNumeroMobile").value(hasItem(DEFAULT_FR_NUMERO_MOBILE)))
            .andExpect(jsonPath("$.[*].frNumeroFax").value(hasItem(DEFAULT_FR_NUMERO_FAX)))
            .andExpect(jsonPath("$.[*].frNumeroFixe").value(hasItem(DEFAULT_FR_NUMERO_FIXE)))
            .andExpect(jsonPath("$.[*].frDateCreation").value(hasItem(DEFAULT_FR_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].frDateUpdate").value(hasItem(DEFAULT_FR_DATE_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].frStatus").value(hasItem(DEFAULT_FR_STATUS)))
            .andExpect(jsonPath("$.[*].frNumeroSiret").value(hasItem(DEFAULT_FR_NUMERO_SIRET)));
    }

    @Test
    @Transactional
    void getFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get the fournisseur
        restFournisseurMockMvc
            .perform(get(ENTITY_API_URL_ID, fournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fournisseur.getId().intValue()))
            .andExpect(jsonPath("$.frIdent").value(DEFAULT_FR_IDENT))
            .andExpect(jsonPath("$.frRaisonSocial").value(DEFAULT_FR_RAISON_SOCIAL))
            .andExpect(jsonPath("$.frAdresse").value(DEFAULT_FR_ADRESSE))
            .andExpect(jsonPath("$.frCodePostal").value(DEFAULT_FR_CODE_POSTAL))
            .andExpect(jsonPath("$.frVille").value(DEFAULT_FR_VILLE))
            .andExpect(jsonPath("$.frCountry").value(DEFAULT_FR_COUNTRY))
            .andExpect(jsonPath("$.frEmail").value(DEFAULT_FR_EMAIL))
            .andExpect(jsonPath("$.frNumeroMobile").value(DEFAULT_FR_NUMERO_MOBILE))
            .andExpect(jsonPath("$.frNumeroFax").value(DEFAULT_FR_NUMERO_FAX))
            .andExpect(jsonPath("$.frNumeroFixe").value(DEFAULT_FR_NUMERO_FIXE))
            .andExpect(jsonPath("$.frDateCreation").value(DEFAULT_FR_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.frDateUpdate").value(DEFAULT_FR_DATE_UPDATE.toString()))
            .andExpect(jsonPath("$.frStatus").value(DEFAULT_FR_STATUS))
            .andExpect(jsonPath("$.frNumeroSiret").value(DEFAULT_FR_NUMERO_SIRET));
    }

    @Test
    @Transactional
    void getNonExistingFournisseur() throws Exception {
        // Get the fournisseur
        restFournisseurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur
        Fournisseur updatedFournisseur = fournisseurRepository.findById(fournisseur.getId()).get();
        // Disconnect from session so that the updates on updatedFournisseur are not directly saved in db
        em.detach(updatedFournisseur);
        updatedFournisseur
            .frIdent(UPDATED_FR_IDENT)
            .frRaisonSocial(UPDATED_FR_RAISON_SOCIAL)
            .frAdresse(UPDATED_FR_ADRESSE)
            .frCodePostal(UPDATED_FR_CODE_POSTAL)
            .frVille(UPDATED_FR_VILLE)
            .frCountry(UPDATED_FR_COUNTRY)
            .frEmail(UPDATED_FR_EMAIL)
            .frNumeroMobile(UPDATED_FR_NUMERO_MOBILE)
            .frNumeroFax(UPDATED_FR_NUMERO_FAX)
            .frNumeroFixe(UPDATED_FR_NUMERO_FIXE)
            .frDateCreation(UPDATED_FR_DATE_CREATION)
            .frDateUpdate(UPDATED_FR_DATE_UPDATE)
            .frStatus(UPDATED_FR_STATUS)
            .frNumeroSiret(UPDATED_FR_NUMERO_SIRET);

        restFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getFrIdent()).isEqualTo(UPDATED_FR_IDENT);
        assertThat(testFournisseur.getFrRaisonSocial()).isEqualTo(UPDATED_FR_RAISON_SOCIAL);
        assertThat(testFournisseur.getFrAdresse()).isEqualTo(UPDATED_FR_ADRESSE);
        assertThat(testFournisseur.getFrCodePostal()).isEqualTo(UPDATED_FR_CODE_POSTAL);
        assertThat(testFournisseur.getFrVille()).isEqualTo(UPDATED_FR_VILLE);
        assertThat(testFournisseur.getFrCountry()).isEqualTo(UPDATED_FR_COUNTRY);
        assertThat(testFournisseur.getFrEmail()).isEqualTo(UPDATED_FR_EMAIL);
        assertThat(testFournisseur.getFrNumeroMobile()).isEqualTo(UPDATED_FR_NUMERO_MOBILE);
        assertThat(testFournisseur.getFrNumeroFax()).isEqualTo(UPDATED_FR_NUMERO_FAX);
        assertThat(testFournisseur.getFrNumeroFixe()).isEqualTo(UPDATED_FR_NUMERO_FIXE);
        assertThat(testFournisseur.getFrDateCreation()).isEqualTo(UPDATED_FR_DATE_CREATION);
        assertThat(testFournisseur.getFrDateUpdate()).isEqualTo(UPDATED_FR_DATE_UPDATE);
        assertThat(testFournisseur.getFrStatus()).isEqualTo(UPDATED_FR_STATUS);
        assertThat(testFournisseur.getFrNumeroSiret()).isEqualTo(UPDATED_FR_NUMERO_SIRET);
    }

    @Test
    @Transactional
    void putNonExistingFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFournisseurWithPatch() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur using partial update
        Fournisseur partialUpdatedFournisseur = new Fournisseur();
        partialUpdatedFournisseur.setId(fournisseur.getId());

        partialUpdatedFournisseur
            .frRaisonSocial(UPDATED_FR_RAISON_SOCIAL)
            .frAdresse(UPDATED_FR_ADRESSE)
            .frCodePostal(UPDATED_FR_CODE_POSTAL)
            .frNumeroFax(UPDATED_FR_NUMERO_FAX)
            .frNumeroFixe(UPDATED_FR_NUMERO_FIXE)
            .frDateCreation(UPDATED_FR_DATE_CREATION)
            .frStatus(UPDATED_FR_STATUS);

        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getFrIdent()).isEqualTo(DEFAULT_FR_IDENT);
        assertThat(testFournisseur.getFrRaisonSocial()).isEqualTo(UPDATED_FR_RAISON_SOCIAL);
        assertThat(testFournisseur.getFrAdresse()).isEqualTo(UPDATED_FR_ADRESSE);
        assertThat(testFournisseur.getFrCodePostal()).isEqualTo(UPDATED_FR_CODE_POSTAL);
        assertThat(testFournisseur.getFrVille()).isEqualTo(DEFAULT_FR_VILLE);
        assertThat(testFournisseur.getFrCountry()).isEqualTo(DEFAULT_FR_COUNTRY);
        assertThat(testFournisseur.getFrEmail()).isEqualTo(DEFAULT_FR_EMAIL);
        assertThat(testFournisseur.getFrNumeroMobile()).isEqualTo(DEFAULT_FR_NUMERO_MOBILE);
        assertThat(testFournisseur.getFrNumeroFax()).isEqualTo(UPDATED_FR_NUMERO_FAX);
        assertThat(testFournisseur.getFrNumeroFixe()).isEqualTo(UPDATED_FR_NUMERO_FIXE);
        assertThat(testFournisseur.getFrDateCreation()).isEqualTo(UPDATED_FR_DATE_CREATION);
        assertThat(testFournisseur.getFrDateUpdate()).isEqualTo(DEFAULT_FR_DATE_UPDATE);
        assertThat(testFournisseur.getFrStatus()).isEqualTo(UPDATED_FR_STATUS);
        assertThat(testFournisseur.getFrNumeroSiret()).isEqualTo(DEFAULT_FR_NUMERO_SIRET);
    }

    @Test
    @Transactional
    void fullUpdateFournisseurWithPatch() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur using partial update
        Fournisseur partialUpdatedFournisseur = new Fournisseur();
        partialUpdatedFournisseur.setId(fournisseur.getId());

        partialUpdatedFournisseur
            .frIdent(UPDATED_FR_IDENT)
            .frRaisonSocial(UPDATED_FR_RAISON_SOCIAL)
            .frAdresse(UPDATED_FR_ADRESSE)
            .frCodePostal(UPDATED_FR_CODE_POSTAL)
            .frVille(UPDATED_FR_VILLE)
            .frCountry(UPDATED_FR_COUNTRY)
            .frEmail(UPDATED_FR_EMAIL)
            .frNumeroMobile(UPDATED_FR_NUMERO_MOBILE)
            .frNumeroFax(UPDATED_FR_NUMERO_FAX)
            .frNumeroFixe(UPDATED_FR_NUMERO_FIXE)
            .frDateCreation(UPDATED_FR_DATE_CREATION)
            .frDateUpdate(UPDATED_FR_DATE_UPDATE)
            .frStatus(UPDATED_FR_STATUS)
            .frNumeroSiret(UPDATED_FR_NUMERO_SIRET);

        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getFrIdent()).isEqualTo(UPDATED_FR_IDENT);
        assertThat(testFournisseur.getFrRaisonSocial()).isEqualTo(UPDATED_FR_RAISON_SOCIAL);
        assertThat(testFournisseur.getFrAdresse()).isEqualTo(UPDATED_FR_ADRESSE);
        assertThat(testFournisseur.getFrCodePostal()).isEqualTo(UPDATED_FR_CODE_POSTAL);
        assertThat(testFournisseur.getFrVille()).isEqualTo(UPDATED_FR_VILLE);
        assertThat(testFournisseur.getFrCountry()).isEqualTo(UPDATED_FR_COUNTRY);
        assertThat(testFournisseur.getFrEmail()).isEqualTo(UPDATED_FR_EMAIL);
        assertThat(testFournisseur.getFrNumeroMobile()).isEqualTo(UPDATED_FR_NUMERO_MOBILE);
        assertThat(testFournisseur.getFrNumeroFax()).isEqualTo(UPDATED_FR_NUMERO_FAX);
        assertThat(testFournisseur.getFrNumeroFixe()).isEqualTo(UPDATED_FR_NUMERO_FIXE);
        assertThat(testFournisseur.getFrDateCreation()).isEqualTo(UPDATED_FR_DATE_CREATION);
        assertThat(testFournisseur.getFrDateUpdate()).isEqualTo(UPDATED_FR_DATE_UPDATE);
        assertThat(testFournisseur.getFrStatus()).isEqualTo(UPDATED_FR_STATUS);
        assertThat(testFournisseur.getFrNumeroSiret()).isEqualTo(UPDATED_FR_NUMERO_SIRET);
    }

    @Test
    @Transactional
    void patchNonExistingFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeDelete = fournisseurRepository.findAll().size();

        // Delete the fournisseur
        restFournisseurMockMvc
            .perform(delete(ENTITY_API_URL_ID, fournisseur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
