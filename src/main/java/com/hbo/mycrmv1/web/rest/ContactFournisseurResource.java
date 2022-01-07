package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.ContactFournisseur;
import com.hbo.mycrmv1.repository.ContactFournisseurRepository;
import com.hbo.mycrmv1.service.ContactFournisseurService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.ContactFournisseur}.
 */
@RestController
@RequestMapping("/api")
public class ContactFournisseurResource {

    private final Logger log = LoggerFactory.getLogger(ContactFournisseurResource.class);

    private static final String ENTITY_NAME = "contactFournisseur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactFournisseurService contactFournisseurService;

    private final ContactFournisseurRepository contactFournisseurRepository;

    public ContactFournisseurResource(
        ContactFournisseurService contactFournisseurService,
        ContactFournisseurRepository contactFournisseurRepository
    ) {
        this.contactFournisseurService = contactFournisseurService;
        this.contactFournisseurRepository = contactFournisseurRepository;
    }

    /**
     * {@code POST  /contact-fournisseurs} : Create a new contactFournisseur.
     *
     * @param contactFournisseur the contactFournisseur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactFournisseur, or with status {@code 400 (Bad Request)} if the contactFournisseur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-fournisseurs")
    public ResponseEntity<ContactFournisseur> createContactFournisseur(@RequestBody ContactFournisseur contactFournisseur)
        throws URISyntaxException {
        log.debug("REST request to save ContactFournisseur : {}", contactFournisseur);
        if (contactFournisseur.getId() != null) {
            throw new BadRequestAlertException("A new contactFournisseur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactFournisseur result = contactFournisseurService.save(contactFournisseur);
        return ResponseEntity
            .created(new URI("/api/contact-fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-fournisseurs/:id} : Updates an existing contactFournisseur.
     *
     * @param id the id of the contactFournisseur to save.
     * @param contactFournisseur the contactFournisseur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactFournisseur,
     * or with status {@code 400 (Bad Request)} if the contactFournisseur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactFournisseur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-fournisseurs/{id}")
    public ResponseEntity<ContactFournisseur> updateContactFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContactFournisseur contactFournisseur
    ) throws URISyntaxException {
        log.debug("REST request to update ContactFournisseur : {}, {}", id, contactFournisseur);
        if (contactFournisseur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactFournisseur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactFournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContactFournisseur result = contactFournisseurService.save(contactFournisseur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactFournisseur.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contact-fournisseurs/:id} : Partial updates given fields of an existing contactFournisseur, field will ignore if it is null
     *
     * @param id the id of the contactFournisseur to save.
     * @param contactFournisseur the contactFournisseur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactFournisseur,
     * or with status {@code 400 (Bad Request)} if the contactFournisseur is not valid,
     * or with status {@code 404 (Not Found)} if the contactFournisseur is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactFournisseur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contact-fournisseurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactFournisseur> partialUpdateContactFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContactFournisseur contactFournisseur
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContactFournisseur partially : {}, {}", id, contactFournisseur);
        if (contactFournisseur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactFournisseur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactFournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactFournisseur> result = contactFournisseurService.partialUpdate(contactFournisseur);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactFournisseur.getId().toString())
        );
    }

    /**
     * {@code GET  /contact-fournisseurs} : get all the contactFournisseurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactFournisseurs in body.
     */
    @GetMapping("/contact-fournisseurs")
    public List<ContactFournisseur> getAllContactFournisseurs() {
        log.debug("REST request to get all ContactFournisseurs");
        return contactFournisseurService.findAll();
    }

    /**
     * {@code GET  /contact-fournisseurs/:id} : get the "id" contactFournisseur.
     *
     * @param id the id of the contactFournisseur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactFournisseur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-fournisseurs/{id}")
    public ResponseEntity<ContactFournisseur> getContactFournisseur(@PathVariable Long id) {
        log.debug("REST request to get ContactFournisseur : {}", id);
        Optional<ContactFournisseur> contactFournisseur = contactFournisseurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactFournisseur);
    }

    /**
     * {@code DELETE  /contact-fournisseurs/:id} : delete the "id" contactFournisseur.
     *
     * @param id the id of the contactFournisseur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-fournisseurs/{id}")
    public ResponseEntity<Void> deleteContactFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete ContactFournisseur : {}", id);
        contactFournisseurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
