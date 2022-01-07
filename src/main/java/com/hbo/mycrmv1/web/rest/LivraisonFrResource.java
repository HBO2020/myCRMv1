package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.LivraisonFr;
import com.hbo.mycrmv1.repository.LivraisonFrRepository;
import com.hbo.mycrmv1.service.LivraisonFrService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.LivraisonFr}.
 */
@RestController
@RequestMapping("/api")
public class LivraisonFrResource {

    private final Logger log = LoggerFactory.getLogger(LivraisonFrResource.class);

    private static final String ENTITY_NAME = "livraisonFr";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LivraisonFrService livraisonFrService;

    private final LivraisonFrRepository livraisonFrRepository;

    public LivraisonFrResource(LivraisonFrService livraisonFrService, LivraisonFrRepository livraisonFrRepository) {
        this.livraisonFrService = livraisonFrService;
        this.livraisonFrRepository = livraisonFrRepository;
    }

    /**
     * {@code POST  /livraison-frs} : Create a new livraisonFr.
     *
     * @param livraisonFr the livraisonFr to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new livraisonFr, or with status {@code 400 (Bad Request)} if the livraisonFr has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/livraison-frs")
    public ResponseEntity<LivraisonFr> createLivraisonFr(@RequestBody LivraisonFr livraisonFr) throws URISyntaxException {
        log.debug("REST request to save LivraisonFr : {}", livraisonFr);
        if (livraisonFr.getId() != null) {
            throw new BadRequestAlertException("A new livraisonFr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LivraisonFr result = livraisonFrService.save(livraisonFr);
        return ResponseEntity
            .created(new URI("/api/livraison-frs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /livraison-frs/:id} : Updates an existing livraisonFr.
     *
     * @param id the id of the livraisonFr to save.
     * @param livraisonFr the livraisonFr to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livraisonFr,
     * or with status {@code 400 (Bad Request)} if the livraisonFr is not valid,
     * or with status {@code 500 (Internal Server Error)} if the livraisonFr couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/livraison-frs/{id}")
    public ResponseEntity<LivraisonFr> updateLivraisonFr(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LivraisonFr livraisonFr
    ) throws URISyntaxException {
        log.debug("REST request to update LivraisonFr : {}, {}", id, livraisonFr);
        if (livraisonFr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, livraisonFr.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!livraisonFrRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LivraisonFr result = livraisonFrService.save(livraisonFr);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, livraisonFr.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /livraison-frs/:id} : Partial updates given fields of an existing livraisonFr, field will ignore if it is null
     *
     * @param id the id of the livraisonFr to save.
     * @param livraisonFr the livraisonFr to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livraisonFr,
     * or with status {@code 400 (Bad Request)} if the livraisonFr is not valid,
     * or with status {@code 404 (Not Found)} if the livraisonFr is not found,
     * or with status {@code 500 (Internal Server Error)} if the livraisonFr couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/livraison-frs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LivraisonFr> partialUpdateLivraisonFr(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LivraisonFr livraisonFr
    ) throws URISyntaxException {
        log.debug("REST request to partial update LivraisonFr partially : {}, {}", id, livraisonFr);
        if (livraisonFr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, livraisonFr.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!livraisonFrRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LivraisonFr> result = livraisonFrService.partialUpdate(livraisonFr);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, livraisonFr.getId().toString())
        );
    }

    /**
     * {@code GET  /livraison-frs} : get all the livraisonFrs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of livraisonFrs in body.
     */
    @GetMapping("/livraison-frs")
    public List<LivraisonFr> getAllLivraisonFrs() {
        log.debug("REST request to get all LivraisonFrs");
        return livraisonFrService.findAll();
    }

    /**
     * {@code GET  /livraison-frs/:id} : get the "id" livraisonFr.
     *
     * @param id the id of the livraisonFr to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the livraisonFr, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/livraison-frs/{id}")
    public ResponseEntity<LivraisonFr> getLivraisonFr(@PathVariable Long id) {
        log.debug("REST request to get LivraisonFr : {}", id);
        Optional<LivraisonFr> livraisonFr = livraisonFrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(livraisonFr);
    }

    /**
     * {@code DELETE  /livraison-frs/:id} : delete the "id" livraisonFr.
     *
     * @param id the id of the livraisonFr to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/livraison-frs/{id}")
    public ResponseEntity<Void> deleteLivraisonFr(@PathVariable Long id) {
        log.debug("REST request to delete LivraisonFr : {}", id);
        livraisonFrService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
