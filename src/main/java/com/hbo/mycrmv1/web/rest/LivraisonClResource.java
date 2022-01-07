package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.LivraisonCl;
import com.hbo.mycrmv1.repository.LivraisonClRepository;
import com.hbo.mycrmv1.service.LivraisonClService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.LivraisonCl}.
 */
@RestController
@RequestMapping("/api")
public class LivraisonClResource {

    private final Logger log = LoggerFactory.getLogger(LivraisonClResource.class);

    private static final String ENTITY_NAME = "livraisonCl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LivraisonClService livraisonClService;

    private final LivraisonClRepository livraisonClRepository;

    public LivraisonClResource(LivraisonClService livraisonClService, LivraisonClRepository livraisonClRepository) {
        this.livraisonClService = livraisonClService;
        this.livraisonClRepository = livraisonClRepository;
    }

    /**
     * {@code POST  /livraison-cls} : Create a new livraisonCl.
     *
     * @param livraisonCl the livraisonCl to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new livraisonCl, or with status {@code 400 (Bad Request)} if the livraisonCl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/livraison-cls")
    public ResponseEntity<LivraisonCl> createLivraisonCl(@RequestBody LivraisonCl livraisonCl) throws URISyntaxException {
        log.debug("REST request to save LivraisonCl : {}", livraisonCl);
        if (livraisonCl.getId() != null) {
            throw new BadRequestAlertException("A new livraisonCl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LivraisonCl result = livraisonClService.save(livraisonCl);
        return ResponseEntity
            .created(new URI("/api/livraison-cls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /livraison-cls/:id} : Updates an existing livraisonCl.
     *
     * @param id the id of the livraisonCl to save.
     * @param livraisonCl the livraisonCl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livraisonCl,
     * or with status {@code 400 (Bad Request)} if the livraisonCl is not valid,
     * or with status {@code 500 (Internal Server Error)} if the livraisonCl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/livraison-cls/{id}")
    public ResponseEntity<LivraisonCl> updateLivraisonCl(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LivraisonCl livraisonCl
    ) throws URISyntaxException {
        log.debug("REST request to update LivraisonCl : {}, {}", id, livraisonCl);
        if (livraisonCl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, livraisonCl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!livraisonClRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LivraisonCl result = livraisonClService.save(livraisonCl);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, livraisonCl.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /livraison-cls/:id} : Partial updates given fields of an existing livraisonCl, field will ignore if it is null
     *
     * @param id the id of the livraisonCl to save.
     * @param livraisonCl the livraisonCl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livraisonCl,
     * or with status {@code 400 (Bad Request)} if the livraisonCl is not valid,
     * or with status {@code 404 (Not Found)} if the livraisonCl is not found,
     * or with status {@code 500 (Internal Server Error)} if the livraisonCl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/livraison-cls/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LivraisonCl> partialUpdateLivraisonCl(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LivraisonCl livraisonCl
    ) throws URISyntaxException {
        log.debug("REST request to partial update LivraisonCl partially : {}, {}", id, livraisonCl);
        if (livraisonCl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, livraisonCl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!livraisonClRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LivraisonCl> result = livraisonClService.partialUpdate(livraisonCl);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, livraisonCl.getId().toString())
        );
    }

    /**
     * {@code GET  /livraison-cls} : get all the livraisonCls.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of livraisonCls in body.
     */
    @GetMapping("/livraison-cls")
    public List<LivraisonCl> getAllLivraisonCls() {
        log.debug("REST request to get all LivraisonCls");
        return livraisonClService.findAll();
    }

    /**
     * {@code GET  /livraison-cls/:id} : get the "id" livraisonCl.
     *
     * @param id the id of the livraisonCl to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the livraisonCl, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/livraison-cls/{id}")
    public ResponseEntity<LivraisonCl> getLivraisonCl(@PathVariable Long id) {
        log.debug("REST request to get LivraisonCl : {}", id);
        Optional<LivraisonCl> livraisonCl = livraisonClService.findOne(id);
        return ResponseUtil.wrapOrNotFound(livraisonCl);
    }

    /**
     * {@code DELETE  /livraison-cls/:id} : delete the "id" livraisonCl.
     *
     * @param id the id of the livraisonCl to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/livraison-cls/{id}")
    public ResponseEntity<Void> deleteLivraisonCl(@PathVariable Long id) {
        log.debug("REST request to delete LivraisonCl : {}", id);
        livraisonClService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
