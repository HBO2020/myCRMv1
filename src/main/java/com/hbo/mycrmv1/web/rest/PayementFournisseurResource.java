package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.PayementFournisseur;
import com.hbo.mycrmv1.repository.PayementFournisseurRepository;
import com.hbo.mycrmv1.service.PayementFournisseurService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.PayementFournisseur}.
 */
@RestController
@RequestMapping("/api")
public class PayementFournisseurResource {

    private final Logger log = LoggerFactory.getLogger(PayementFournisseurResource.class);

    private static final String ENTITY_NAME = "payementFournisseur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayementFournisseurService payementFournisseurService;

    private final PayementFournisseurRepository payementFournisseurRepository;

    public PayementFournisseurResource(
        PayementFournisseurService payementFournisseurService,
        PayementFournisseurRepository payementFournisseurRepository
    ) {
        this.payementFournisseurService = payementFournisseurService;
        this.payementFournisseurRepository = payementFournisseurRepository;
    }

    /**
     * {@code POST  /payement-fournisseurs} : Create a new payementFournisseur.
     *
     * @param payementFournisseur the payementFournisseur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payementFournisseur, or with status {@code 400 (Bad Request)} if the payementFournisseur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payement-fournisseurs")
    public ResponseEntity<PayementFournisseur> createPayementFournisseur(@RequestBody PayementFournisseur payementFournisseur)
        throws URISyntaxException {
        log.debug("REST request to save PayementFournisseur : {}", payementFournisseur);
        if (payementFournisseur.getId() != null) {
            throw new BadRequestAlertException("A new payementFournisseur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayementFournisseur result = payementFournisseurService.save(payementFournisseur);
        return ResponseEntity
            .created(new URI("/api/payement-fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payement-fournisseurs/:id} : Updates an existing payementFournisseur.
     *
     * @param id the id of the payementFournisseur to save.
     * @param payementFournisseur the payementFournisseur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payementFournisseur,
     * or with status {@code 400 (Bad Request)} if the payementFournisseur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payementFournisseur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payement-fournisseurs/{id}")
    public ResponseEntity<PayementFournisseur> updatePayementFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayementFournisseur payementFournisseur
    ) throws URISyntaxException {
        log.debug("REST request to update PayementFournisseur : {}, {}", id, payementFournisseur);
        if (payementFournisseur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payementFournisseur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payementFournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PayementFournisseur result = payementFournisseurService.save(payementFournisseur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payementFournisseur.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payement-fournisseurs/:id} : Partial updates given fields of an existing payementFournisseur, field will ignore if it is null
     *
     * @param id the id of the payementFournisseur to save.
     * @param payementFournisseur the payementFournisseur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payementFournisseur,
     * or with status {@code 400 (Bad Request)} if the payementFournisseur is not valid,
     * or with status {@code 404 (Not Found)} if the payementFournisseur is not found,
     * or with status {@code 500 (Internal Server Error)} if the payementFournisseur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payement-fournisseurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PayementFournisseur> partialUpdatePayementFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayementFournisseur payementFournisseur
    ) throws URISyntaxException {
        log.debug("REST request to partial update PayementFournisseur partially : {}, {}", id, payementFournisseur);
        if (payementFournisseur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payementFournisseur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payementFournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PayementFournisseur> result = payementFournisseurService.partialUpdate(payementFournisseur);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payementFournisseur.getId().toString())
        );
    }

    /**
     * {@code GET  /payement-fournisseurs} : get all the payementFournisseurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payementFournisseurs in body.
     */
    @GetMapping("/payement-fournisseurs")
    public List<PayementFournisseur> getAllPayementFournisseurs() {
        log.debug("REST request to get all PayementFournisseurs");
        return payementFournisseurService.findAll();
    }

    /**
     * {@code GET  /payement-fournisseurs/:id} : get the "id" payementFournisseur.
     *
     * @param id the id of the payementFournisseur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payementFournisseur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payement-fournisseurs/{id}")
    public ResponseEntity<PayementFournisseur> getPayementFournisseur(@PathVariable Long id) {
        log.debug("REST request to get PayementFournisseur : {}", id);
        Optional<PayementFournisseur> payementFournisseur = payementFournisseurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(payementFournisseur);
    }

    /**
     * {@code DELETE  /payement-fournisseurs/:id} : delete the "id" payementFournisseur.
     *
     * @param id the id of the payementFournisseur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payement-fournisseurs/{id}")
    public ResponseEntity<Void> deletePayementFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete PayementFournisseur : {}", id);
        payementFournisseurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
