package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.FactureAchat;
import com.hbo.mycrmv1.repository.FactureAchatRepository;
import com.hbo.mycrmv1.service.FactureAchatService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.FactureAchat}.
 */
@RestController
@RequestMapping("/api")
public class FactureAchatResource {

    private final Logger log = LoggerFactory.getLogger(FactureAchatResource.class);

    private static final String ENTITY_NAME = "factureAchat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FactureAchatService factureAchatService;

    private final FactureAchatRepository factureAchatRepository;

    public FactureAchatResource(FactureAchatService factureAchatService, FactureAchatRepository factureAchatRepository) {
        this.factureAchatService = factureAchatService;
        this.factureAchatRepository = factureAchatRepository;
    }

    /**
     * {@code POST  /facture-achats} : Create a new factureAchat.
     *
     * @param factureAchat the factureAchat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new factureAchat, or with status {@code 400 (Bad Request)} if the factureAchat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facture-achats")
    public ResponseEntity<FactureAchat> createFactureAchat(@RequestBody FactureAchat factureAchat) throws URISyntaxException {
        log.debug("REST request to save FactureAchat : {}", factureAchat);
        if (factureAchat.getId() != null) {
            throw new BadRequestAlertException("A new factureAchat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FactureAchat result = factureAchatService.save(factureAchat);
        return ResponseEntity
            .created(new URI("/api/facture-achats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facture-achats/:id} : Updates an existing factureAchat.
     *
     * @param id the id of the factureAchat to save.
     * @param factureAchat the factureAchat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factureAchat,
     * or with status {@code 400 (Bad Request)} if the factureAchat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the factureAchat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facture-achats/{id}")
    public ResponseEntity<FactureAchat> updateFactureAchat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FactureAchat factureAchat
    ) throws URISyntaxException {
        log.debug("REST request to update FactureAchat : {}, {}", id, factureAchat);
        if (factureAchat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factureAchat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factureAchatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FactureAchat result = factureAchatService.save(factureAchat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, factureAchat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /facture-achats/:id} : Partial updates given fields of an existing factureAchat, field will ignore if it is null
     *
     * @param id the id of the factureAchat to save.
     * @param factureAchat the factureAchat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factureAchat,
     * or with status {@code 400 (Bad Request)} if the factureAchat is not valid,
     * or with status {@code 404 (Not Found)} if the factureAchat is not found,
     * or with status {@code 500 (Internal Server Error)} if the factureAchat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/facture-achats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FactureAchat> partialUpdateFactureAchat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FactureAchat factureAchat
    ) throws URISyntaxException {
        log.debug("REST request to partial update FactureAchat partially : {}, {}", id, factureAchat);
        if (factureAchat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factureAchat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factureAchatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FactureAchat> result = factureAchatService.partialUpdate(factureAchat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, factureAchat.getId().toString())
        );
    }

    /**
     * {@code GET  /facture-achats} : get all the factureAchats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of factureAchats in body.
     */
    @GetMapping("/facture-achats")
    public List<FactureAchat> getAllFactureAchats() {
        log.debug("REST request to get all FactureAchats");
        return factureAchatService.findAll();
    }

    /**
     * {@code GET  /facture-achats/:id} : get the "id" factureAchat.
     *
     * @param id the id of the factureAchat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the factureAchat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facture-achats/{id}")
    public ResponseEntity<FactureAchat> getFactureAchat(@PathVariable Long id) {
        log.debug("REST request to get FactureAchat : {}", id);
        Optional<FactureAchat> factureAchat = factureAchatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factureAchat);
    }

    /**
     * {@code DELETE  /facture-achats/:id} : delete the "id" factureAchat.
     *
     * @param id the id of the factureAchat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facture-achats/{id}")
    public ResponseEntity<Void> deleteFactureAchat(@PathVariable Long id) {
        log.debug("REST request to delete FactureAchat : {}", id);
        factureAchatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
