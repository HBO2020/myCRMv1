package com.hbo.mycrmv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hbo.mycrmv1.IntegrationTest;
import com.hbo.mycrmv1.domain.CommandeFournisseur;
import com.hbo.mycrmv1.repository.CommandeFournisseurRepository;
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
 * Integration tests for the {@link CommandeFournisseurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommandeFournisseurResourceIT {

    private static final Integer DEFAULT_CMD_IDEN_FR = 1;
    private static final Integer UPDATED_CMD_IDEN_FR = 2;

    private static final LocalDate DEFAULT_CMD_DATE_EFFET_FR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CMD_DATE_EFFET_FR = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CMD_DATE_LIVRAISON_FR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CMD_DATE_LIVRAISON_FR = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/commande-fournisseurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommandeFournisseurRepository commandeFournisseurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandeFournisseurMockMvc;

    private CommandeFournisseur commandeFournisseur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeFournisseur createEntity(EntityManager em) {
        CommandeFournisseur commandeFournisseur = new CommandeFournisseur()
            .cmdIdenFr(DEFAULT_CMD_IDEN_FR)
            .cmdDateEffetFr(DEFAULT_CMD_DATE_EFFET_FR)
            .cmdDateLivraisonFr(DEFAULT_CMD_DATE_LIVRAISON_FR);
        return commandeFournisseur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeFournisseur createUpdatedEntity(EntityManager em) {
        CommandeFournisseur commandeFournisseur = new CommandeFournisseur()
            .cmdIdenFr(UPDATED_CMD_IDEN_FR)
            .cmdDateEffetFr(UPDATED_CMD_DATE_EFFET_FR)
            .cmdDateLivraisonFr(UPDATED_CMD_DATE_LIVRAISON_FR);
        return commandeFournisseur;
    }

    @BeforeEach
    public void initTest() {
        commandeFournisseur = createEntity(em);
    }

    @Test
    @Transactional
    void createCommandeFournisseur() throws Exception {
        int databaseSizeBeforeCreate = commandeFournisseurRepository.findAll().size();
        // Create the CommandeFournisseur
        restCommandeFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commandeFournisseur))
            )
            .andExpect(status().isCreated());

        // Validate the CommandeFournisseur in the database
        List<CommandeFournisseur> commandeFournisseurList = commandeFournisseurRepository.findAll();
        assertThat(commandeFournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        CommandeFournisseur testCommandeFournisseur = commandeFournisseurList.get(commandeFournisseurList.size() - 1);
        assertThat(testCommandeFournisseur.getCmdIdenFr()).isEqualTo(DEFAULT_CMD_IDEN_FR);
        assertThat(testCommandeFournisseur.getCmdDateEffetFr()).isEqualTo(DEFAULT_CMD_DATE_EFFET_FR);
        assertThat(testCommandeFournisseur.getCmdDateLivraisonFr()).isEqualTo(DEFAULT_CMD_DATE_LIVRAISON_FR);
    }

    @Test
    @Transactional
    void createCommandeFournisseurWithExistingId() throws Exception {
        // Create the CommandeFournisseur with an existing ID
        commandeFournisseur.setId(1L);

        int databaseSizeBeforeCreate = commandeFournisseurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commandeFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandeFournisseur in the database
        List<CommandeFournisseur> commandeFournisseurList = commandeFournisseurRepository.findAll();
        assertThat(commandeFournisseurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommandeFournisseurs() throws Exception {
        // Initialize the database
        commandeFournisseurRepository.saveAndFlush(commandeFournisseur);

        // Get all the commandeFournisseurList
        restCommandeFournisseurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeFournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].cmdIdenFr").value(hasItem(DEFAULT_CMD_IDEN_FR)))
            .andExpect(jsonPath("$.[*].cmdDateEffetFr").value(hasItem(DEFAULT_CMD_DATE_EFFET_FR.toString())))
            .andExpect(jsonPath("$.[*].cmdDateLivraisonFr").value(hasItem(DEFAULT_CMD_DATE_LIVRAISON_FR.toString())));
    }

    @Test
    @Transactional
    void getCommandeFournisseur() throws Exception {
        // Initialize the database
        commandeFournisseurRepository.saveAndFlush(commandeFournisseur);

        // Get the commandeFournisseur
        restCommandeFournisseurMockMvc
            .perform(get(ENTITY_API_URL_ID, commandeFournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandeFournisseur.getId().intValue()))
            .andExpect(jsonPath("$.cmdIdenFr").value(DEFAULT_CMD_IDEN_FR))
            .andExpect(jsonPath("$.cmdDateEffetFr").value(DEFAULT_CMD_DATE_EFFET_FR.toString()))
            .andExpect(jsonPath("$.cmdDateLivraisonFr").value(DEFAULT_CMD_DATE_LIVRAISON_FR.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCommandeFournisseur() throws Exception {
        // Get the commandeFournisseur
        restCommandeFournisseurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCommandeFournisseur() throws Exception {
        // Initialize the database
        commandeFournisseurRepository.saveAndFlush(commandeFournisseur);

        int databaseSizeBeforeUpdate = commandeFournisseurRepository.findAll().size();

        // Update the commandeFournisseur
        CommandeFournisseur updatedCommandeFournisseur = commandeFournisseurRepository.findById(commandeFournisseur.getId()).get();
        // Disconnect from session so that the updates on updatedCommandeFournisseur are not directly saved in db
        em.detach(updatedCommandeFournisseur);
        updatedCommandeFournisseur
            .cmdIdenFr(UPDATED_CMD_IDEN_FR)
            .cmdDateEffetFr(UPDATED_CMD_DATE_EFFET_FR)
            .cmdDateLivraisonFr(UPDATED_CMD_DATE_LIVRAISON_FR);

        restCommandeFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCommandeFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCommandeFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the CommandeFournisseur in the database
        List<CommandeFournisseur> commandeFournisseurList = commandeFournisseurRepository.findAll();
        assertThat(commandeFournisseurList).hasSize(databaseSizeBeforeUpdate);
        CommandeFournisseur testCommandeFournisseur = commandeFournisseurList.get(commandeFournisseurList.size() - 1);
        assertThat(testCommandeFournisseur.getCmdIdenFr()).isEqualTo(UPDATED_CMD_IDEN_FR);
        assertThat(testCommandeFournisseur.getCmdDateEffetFr()).isEqualTo(UPDATED_CMD_DATE_EFFET_FR);
        assertThat(testCommandeFournisseur.getCmdDateLivraisonFr()).isEqualTo(UPDATED_CMD_DATE_LIVRAISON_FR);
    }

    @Test
    @Transactional
    void putNonExistingCommandeFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = commandeFournisseurRepository.findAll().size();
        commandeFournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commandeFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandeFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandeFournisseur in the database
        List<CommandeFournisseur> commandeFournisseurList = commandeFournisseurRepository.findAll();
        assertThat(commandeFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommandeFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = commandeFournisseurRepository.findAll().size();
        commandeFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandeFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandeFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandeFournisseur in the database
        List<CommandeFournisseur> commandeFournisseurList = commandeFournisseurRepository.findAll();
        assertThat(commandeFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommandeFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = commandeFournisseurRepository.findAll().size();
        commandeFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandeFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commandeFournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommandeFournisseur in the database
        List<CommandeFournisseur> commandeFournisseurList = commandeFournisseurRepository.findAll();
        assertThat(commandeFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommandeFournisseurWithPatch() throws Exception {
        // Initialize the database
        commandeFournisseurRepository.saveAndFlush(commandeFournisseur);

        int databaseSizeBeforeUpdate = commandeFournisseurRepository.findAll().size();

        // Update the commandeFournisseur using partial update
        CommandeFournisseur partialUpdatedCommandeFournisseur = new CommandeFournisseur();
        partialUpdatedCommandeFournisseur.setId(commandeFournisseur.getId());

        partialUpdatedCommandeFournisseur.cmdDateEffetFr(UPDATED_CMD_DATE_EFFET_FR).cmdDateLivraisonFr(UPDATED_CMD_DATE_LIVRAISON_FR);

        restCommandeFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommandeFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommandeFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the CommandeFournisseur in the database
        List<CommandeFournisseur> commandeFournisseurList = commandeFournisseurRepository.findAll();
        assertThat(commandeFournisseurList).hasSize(databaseSizeBeforeUpdate);
        CommandeFournisseur testCommandeFournisseur = commandeFournisseurList.get(commandeFournisseurList.size() - 1);
        assertThat(testCommandeFournisseur.getCmdIdenFr()).isEqualTo(DEFAULT_CMD_IDEN_FR);
        assertThat(testCommandeFournisseur.getCmdDateEffetFr()).isEqualTo(UPDATED_CMD_DATE_EFFET_FR);
        assertThat(testCommandeFournisseur.getCmdDateLivraisonFr()).isEqualTo(UPDATED_CMD_DATE_LIVRAISON_FR);
    }

    @Test
    @Transactional
    void fullUpdateCommandeFournisseurWithPatch() throws Exception {
        // Initialize the database
        commandeFournisseurRepository.saveAndFlush(commandeFournisseur);

        int databaseSizeBeforeUpdate = commandeFournisseurRepository.findAll().size();

        // Update the commandeFournisseur using partial update
        CommandeFournisseur partialUpdatedCommandeFournisseur = new CommandeFournisseur();
        partialUpdatedCommandeFournisseur.setId(commandeFournisseur.getId());

        partialUpdatedCommandeFournisseur
            .cmdIdenFr(UPDATED_CMD_IDEN_FR)
            .cmdDateEffetFr(UPDATED_CMD_DATE_EFFET_FR)
            .cmdDateLivraisonFr(UPDATED_CMD_DATE_LIVRAISON_FR);

        restCommandeFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommandeFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommandeFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the CommandeFournisseur in the database
        List<CommandeFournisseur> commandeFournisseurList = commandeFournisseurRepository.findAll();
        assertThat(commandeFournisseurList).hasSize(databaseSizeBeforeUpdate);
        CommandeFournisseur testCommandeFournisseur = commandeFournisseurList.get(commandeFournisseurList.size() - 1);
        assertThat(testCommandeFournisseur.getCmdIdenFr()).isEqualTo(UPDATED_CMD_IDEN_FR);
        assertThat(testCommandeFournisseur.getCmdDateEffetFr()).isEqualTo(UPDATED_CMD_DATE_EFFET_FR);
        assertThat(testCommandeFournisseur.getCmdDateLivraisonFr()).isEqualTo(UPDATED_CMD_DATE_LIVRAISON_FR);
    }

    @Test
    @Transactional
    void patchNonExistingCommandeFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = commandeFournisseurRepository.findAll().size();
        commandeFournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commandeFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commandeFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandeFournisseur in the database
        List<CommandeFournisseur> commandeFournisseurList = commandeFournisseurRepository.findAll();
        assertThat(commandeFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommandeFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = commandeFournisseurRepository.findAll().size();
        commandeFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandeFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commandeFournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandeFournisseur in the database
        List<CommandeFournisseur> commandeFournisseurList = commandeFournisseurRepository.findAll();
        assertThat(commandeFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommandeFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = commandeFournisseurRepository.findAll().size();
        commandeFournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandeFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commandeFournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommandeFournisseur in the database
        List<CommandeFournisseur> commandeFournisseurList = commandeFournisseurRepository.findAll();
        assertThat(commandeFournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommandeFournisseur() throws Exception {
        // Initialize the database
        commandeFournisseurRepository.saveAndFlush(commandeFournisseur);

        int databaseSizeBeforeDelete = commandeFournisseurRepository.findAll().size();

        // Delete the commandeFournisseur
        restCommandeFournisseurMockMvc
            .perform(delete(ENTITY_API_URL_ID, commandeFournisseur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommandeFournisseur> commandeFournisseurList = commandeFournisseurRepository.findAll();
        assertThat(commandeFournisseurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
