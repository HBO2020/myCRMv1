package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.LigneLivFournisseur;
import com.hbo.mycrmv1.repository.LigneLivFournisseurRepository;
import com.hbo.mycrmv1.service.LigneLivFournisseurService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.LigneLivFournisseur}.
 */
@RestController
@RequestMapping("/api")
public class LigneLivFournisseurResource {

    private final Logger log = LoggerFactory.getLogger(LigneLivFournisseurResource.class);

    private static final String ENTITY_NAME = "ligneLivFournisseur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigneLivFournisseurService ligneLivFournisseurService;

    private final LigneLivFournisseurRepository ligneLivFournisseurRepository;

    public LigneLivFournisseurResource(
        LigneLivFournisseurService ligneLivFournisseurService,
        LigneLivFournisseurRepository ligneLivFournisseurRepository
    ) {
        this.ligneLivFournisseurService = ligneLivFournisseurService;
        this.ligneLivFournisseurRepository = ligneLivFournisseurRepository;
    }

    /**
     * {@code POST  /ligne-liv-fournisseurs} : Create a new ligneLivFournisseur.
     *
     * @param ligneLivFournisseur the ligneLivFournisseur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligneLivFournisseur, or with status {@code 400 (Bad Request)} if the ligneLivFournisseur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ligne-liv-fournisseurs")
    public ResponseEntity<LigneLivFournisseur> createLigneLivFournisseur(@RequestBody LigneLivFournisseur ligneLivFournisseur)
        throws URISyntaxException {
        log.debug("REST request to save LigneLivFournisseur : {}", ligneLivFournisseur);
        if (ligneLivFournisseur.getId() != null) {
            throw new BadRequestAlertException("A new ligneLivFournisseur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LigneLivFournisseur result = ligneLivFournisseurService.save(ligneLivFournisseur);
        return ResponseEntity
            .created(new URI("/api/ligne-liv-fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ligne-liv-fournisseurs/:id} : Updates an existing ligneLivFournisseur.
     *
     * @param id the id of the ligneLivFournisseur to save.
     * @param ligneLivFournisseur the ligneLivFournisseur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneLivFournisseur,
     * or with status {@code 400 (Bad Request)} if the ligneLivFournisseur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligneLivFournisseur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ligne-liv-fournisseurs/{id}")
    public ResponseEntity<LigneLivFournisseur> updateLigneLivFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LigneLivFournisseur ligneLivFournisseur
    ) throws URISyntaxException {
        log.debug("REST request to update LigneLivFournisseur : {}, {}", id, ligneLivFournisseur);
        if (ligneLivFournisseur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneLivFournisseur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligneLivFournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LigneLivFournisseur result = ligneLivFournisseurService.save(ligneLivFournisseur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneLivFournisseur.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ligne-liv-fournisseurs/:id} : Partial updates given fields of an existing ligneLivFournisseur, field will ignore if it is null
     *
     * @param id the id of the ligneLivFournisseur to save.
     * @param ligneLivFournisseur the ligneLivFournisseur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneLivFournisseur,
     * or with status {@code 400 (Bad Request)} if the ligneLivFournisseur is not valid,
     * or with status {@code 404 (Not Found)} if the ligneLivFournisseur is not found,
     * or with status {@code 500 (Internal Server Error)} if the ligneLivFournisseur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ligne-liv-fournisseurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LigneLivFournisseur> partialUpdateLigneLivFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LigneLivFournisseur ligneLivFournisseur
    ) throws URISyntaxException {
        log.debug("REST request to partial update LigneLivFournisseur partially : {}, {}", id, ligneLivFournisseur);
        if (ligneLivFournisseur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneLivFournisseur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligneLivFournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LigneLivFournisseur> result = ligneLivFournisseurService.partialUpdate(ligneLivFournisseur);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneLivFournisseur.getId().toString())
        );
    }

    /**
     * {@code GET  /ligne-liv-fournisseurs} : get all the ligneLivFournisseurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligneLivFournisseurs in body.
     */
    @GetMapping("/ligne-liv-fournisseurs")
    public List<LigneLivFournisseur> getAllLigneLivFournisseurs() {
        log.debug("REST request to get all LigneLivFournisseurs");
        return ligneLivFournisseurService.findAll();
    }

    /**
     * {@code GET  /ligne-liv-fournisseurs/:id} : get the "id" ligneLivFournisseur.
     *
     * @param id the id of the ligneLivFournisseur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ligneLivFournisseur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ligne-liv-fournisseurs/{id}")
    public ResponseEntity<LigneLivFournisseur> getLigneLivFournisseur(@PathVariable Long id) {
        log.debug("REST request to get LigneLivFournisseur : {}", id);
        Optional<LigneLivFournisseur> ligneLivFournisseur = ligneLivFournisseurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ligneLivFournisseur);
    }

    /**
     * {@code DELETE  /ligne-liv-fournisseurs/:id} : delete the "id" ligneLivFournisseur.
     *
     * @param id the id of the ligneLivFournisseur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ligne-liv-fournisseurs/{id}")
    public ResponseEntity<Void> deleteLigneLivFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete LigneLivFournisseur : {}", id);
        ligneLivFournisseurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
