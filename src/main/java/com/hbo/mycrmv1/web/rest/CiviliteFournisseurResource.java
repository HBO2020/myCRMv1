package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.CiviliteFournisseur;
import com.hbo.mycrmv1.repository.CiviliteFournisseurRepository;
import com.hbo.mycrmv1.service.CiviliteFournisseurService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.CiviliteFournisseur}.
 */
@RestController
@RequestMapping("/api")
public class CiviliteFournisseurResource {

    private final Logger log = LoggerFactory.getLogger(CiviliteFournisseurResource.class);

    private static final String ENTITY_NAME = "civiliteFournisseur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CiviliteFournisseurService civiliteFournisseurService;

    private final CiviliteFournisseurRepository civiliteFournisseurRepository;

    public CiviliteFournisseurResource(
        CiviliteFournisseurService civiliteFournisseurService,
        CiviliteFournisseurRepository civiliteFournisseurRepository
    ) {
        this.civiliteFournisseurService = civiliteFournisseurService;
        this.civiliteFournisseurRepository = civiliteFournisseurRepository;
    }

    /**
     * {@code POST  /civilite-fournisseurs} : Create a new civiliteFournisseur.
     *
     * @param civiliteFournisseur the civiliteFournisseur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new civiliteFournisseur, or with status {@code 400 (Bad Request)} if the civiliteFournisseur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/civilite-fournisseurs")
    public ResponseEntity<CiviliteFournisseur> createCiviliteFournisseur(@RequestBody CiviliteFournisseur civiliteFournisseur)
        throws URISyntaxException {
        log.debug("REST request to save CiviliteFournisseur : {}", civiliteFournisseur);
        if (civiliteFournisseur.getId() != null) {
            throw new BadRequestAlertException("A new civiliteFournisseur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CiviliteFournisseur result = civiliteFournisseurService.save(civiliteFournisseur);
        return ResponseEntity
            .created(new URI("/api/civilite-fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /civilite-fournisseurs/:id} : Updates an existing civiliteFournisseur.
     *
     * @param id the id of the civiliteFournisseur to save.
     * @param civiliteFournisseur the civiliteFournisseur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated civiliteFournisseur,
     * or with status {@code 400 (Bad Request)} if the civiliteFournisseur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the civiliteFournisseur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/civilite-fournisseurs/{id}")
    public ResponseEntity<CiviliteFournisseur> updateCiviliteFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CiviliteFournisseur civiliteFournisseur
    ) throws URISyntaxException {
        log.debug("REST request to update CiviliteFournisseur : {}, {}", id, civiliteFournisseur);
        if (civiliteFournisseur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, civiliteFournisseur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!civiliteFournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CiviliteFournisseur result = civiliteFournisseurService.save(civiliteFournisseur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, civiliteFournisseur.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /civilite-fournisseurs/:id} : Partial updates given fields of an existing civiliteFournisseur, field will ignore if it is null
     *
     * @param id the id of the civiliteFournisseur to save.
     * @param civiliteFournisseur the civiliteFournisseur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated civiliteFournisseur,
     * or with status {@code 400 (Bad Request)} if the civiliteFournisseur is not valid,
     * or with status {@code 404 (Not Found)} if the civiliteFournisseur is not found,
     * or with status {@code 500 (Internal Server Error)} if the civiliteFournisseur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/civilite-fournisseurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CiviliteFournisseur> partialUpdateCiviliteFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CiviliteFournisseur civiliteFournisseur
    ) throws URISyntaxException {
        log.debug("REST request to partial update CiviliteFournisseur partially : {}, {}", id, civiliteFournisseur);
        if (civiliteFournisseur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, civiliteFournisseur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!civiliteFournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CiviliteFournisseur> result = civiliteFournisseurService.partialUpdate(civiliteFournisseur);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, civiliteFournisseur.getId().toString())
        );
    }

    /**
     * {@code GET  /civilite-fournisseurs} : get all the civiliteFournisseurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of civiliteFournisseurs in body.
     */
    @GetMapping("/civilite-fournisseurs")
    public List<CiviliteFournisseur> getAllCiviliteFournisseurs() {
        log.debug("REST request to get all CiviliteFournisseurs");
        return civiliteFournisseurService.findAll();
    }

    /**
     * {@code GET  /civilite-fournisseurs/:id} : get the "id" civiliteFournisseur.
     *
     * @param id the id of the civiliteFournisseur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the civiliteFournisseur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/civilite-fournisseurs/{id}")
    public ResponseEntity<CiviliteFournisseur> getCiviliteFournisseur(@PathVariable Long id) {
        log.debug("REST request to get CiviliteFournisseur : {}", id);
        Optional<CiviliteFournisseur> civiliteFournisseur = civiliteFournisseurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(civiliteFournisseur);
    }

    /**
     * {@code DELETE  /civilite-fournisseurs/:id} : delete the "id" civiliteFournisseur.
     *
     * @param id the id of the civiliteFournisseur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/civilite-fournisseurs/{id}")
    public ResponseEntity<Void> deleteCiviliteFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete CiviliteFournisseur : {}", id);
        civiliteFournisseurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
