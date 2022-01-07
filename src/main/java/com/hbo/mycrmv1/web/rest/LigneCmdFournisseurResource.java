package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.LigneCmdFournisseur;
import com.hbo.mycrmv1.repository.LigneCmdFournisseurRepository;
import com.hbo.mycrmv1.service.LigneCmdFournisseurService;
import com.hbo.mycrmv1.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hbo.mycrmv1.domain.LigneCmdFournisseur}.
 */
@RestController
@RequestMapping("/api")
public class LigneCmdFournisseurResource {

    private final Logger log = LoggerFactory.getLogger(LigneCmdFournisseurResource.class);

    private static final String ENTITY_NAME = "ligneCmdFournisseur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigneCmdFournisseurService ligneCmdFournisseurService;

    private final LigneCmdFournisseurRepository ligneCmdFournisseurRepository;

    public LigneCmdFournisseurResource(
        LigneCmdFournisseurService ligneCmdFournisseurService,
        LigneCmdFournisseurRepository ligneCmdFournisseurRepository
    ) {
        this.ligneCmdFournisseurService = ligneCmdFournisseurService;
        this.ligneCmdFournisseurRepository = ligneCmdFournisseurRepository;
    }

    /**
     * {@code POST  /ligne-cmd-fournisseurs} : Create a new ligneCmdFournisseur.
     *
     * @param ligneCmdFournisseur the ligneCmdFournisseur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligneCmdFournisseur, or with status {@code 400 (Bad Request)} if the ligneCmdFournisseur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ligne-cmd-fournisseurs")
    public ResponseEntity<LigneCmdFournisseur> createLigneCmdFournisseur(@RequestBody LigneCmdFournisseur ligneCmdFournisseur)
        throws URISyntaxException {
        log.debug("REST request to save LigneCmdFournisseur : {}", ligneCmdFournisseur);
        if (ligneCmdFournisseur.getId() != null) {
            throw new BadRequestAlertException("A new ligneCmdFournisseur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LigneCmdFournisseur result = ligneCmdFournisseurService.save(ligneCmdFournisseur);
        return ResponseEntity
            .created(new URI("/api/ligne-cmd-fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ligne-cmd-fournisseurs/:id} : Updates an existing ligneCmdFournisseur.
     *
     * @param id the id of the ligneCmdFournisseur to save.
     * @param ligneCmdFournisseur the ligneCmdFournisseur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneCmdFournisseur,
     * or with status {@code 400 (Bad Request)} if the ligneCmdFournisseur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligneCmdFournisseur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ligne-cmd-fournisseurs/{id}")
    public ResponseEntity<LigneCmdFournisseur> updateLigneCmdFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LigneCmdFournisseur ligneCmdFournisseur
    ) throws URISyntaxException {
        log.debug("REST request to update LigneCmdFournisseur : {}, {}", id, ligneCmdFournisseur);
        if (ligneCmdFournisseur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneCmdFournisseur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligneCmdFournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LigneCmdFournisseur result = ligneCmdFournisseurService.save(ligneCmdFournisseur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneCmdFournisseur.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ligne-cmd-fournisseurs/:id} : Partial updates given fields of an existing ligneCmdFournisseur, field will ignore if it is null
     *
     * @param id the id of the ligneCmdFournisseur to save.
     * @param ligneCmdFournisseur the ligneCmdFournisseur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneCmdFournisseur,
     * or with status {@code 400 (Bad Request)} if the ligneCmdFournisseur is not valid,
     * or with status {@code 404 (Not Found)} if the ligneCmdFournisseur is not found,
     * or with status {@code 500 (Internal Server Error)} if the ligneCmdFournisseur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ligne-cmd-fournisseurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LigneCmdFournisseur> partialUpdateLigneCmdFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LigneCmdFournisseur ligneCmdFournisseur
    ) throws URISyntaxException {
        log.debug("REST request to partial update LigneCmdFournisseur partially : {}, {}", id, ligneCmdFournisseur);
        if (ligneCmdFournisseur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneCmdFournisseur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligneCmdFournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LigneCmdFournisseur> result = ligneCmdFournisseurService.partialUpdate(ligneCmdFournisseur);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneCmdFournisseur.getId().toString())
        );
    }

    /**
     * {@code GET  /ligne-cmd-fournisseurs} : get all the ligneCmdFournisseurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligneCmdFournisseurs in body.
     */
    @GetMapping("/ligne-cmd-fournisseurs")
    public List<LigneCmdFournisseur> getAllLigneCmdFournisseurs() {
        log.debug("REST request to get all LigneCmdFournisseurs");
        return ligneCmdFournisseurService.findAll();
    }

    /**
     * {@code GET  /ligne-cmd-fournisseurs/:id} : get the "id" ligneCmdFournisseur.
     *
     * @param id the id of the ligneCmdFournisseur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ligneCmdFournisseur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ligne-cmd-fournisseurs/{id}")
    public ResponseEntity<LigneCmdFournisseur> getLigneCmdFournisseur(@PathVariable Long id) {
        log.debug("REST request to get LigneCmdFournisseur : {}", id);
        Optional<LigneCmdFournisseur> ligneCmdFournisseur = ligneCmdFournisseurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ligneCmdFournisseur);
    }

    /**
     * {@code DELETE  /ligne-cmd-fournisseurs/:id} : delete the "id" ligneCmdFournisseur.
     *
     * @param id the id of the ligneCmdFournisseur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ligne-cmd-fournisseurs/{id}")
    public ResponseEntity<Void> deleteLigneCmdFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete LigneCmdFournisseur : {}", id);
        ligneCmdFournisseurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
