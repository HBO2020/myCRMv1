package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.ContactClient;
import com.hbo.mycrmv1.repository.ContactClientRepository;
import com.hbo.mycrmv1.service.ContactClientService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.ContactClient}.
 */
@RestController
@RequestMapping("/api")
public class ContactClientResource {

    private final Logger log = LoggerFactory.getLogger(ContactClientResource.class);

    private static final String ENTITY_NAME = "contactClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactClientService contactClientService;

    private final ContactClientRepository contactClientRepository;

    public ContactClientResource(ContactClientService contactClientService, ContactClientRepository contactClientRepository) {
        this.contactClientService = contactClientService;
        this.contactClientRepository = contactClientRepository;
    }

    /**
     * {@code POST  /contact-clients} : Create a new contactClient.
     *
     * @param contactClient the contactClient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactClient, or with status {@code 400 (Bad Request)} if the contactClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-clients")
    public ResponseEntity<ContactClient> createContactClient(@RequestBody ContactClient contactClient) throws URISyntaxException {
        log.debug("REST request to save ContactClient : {}", contactClient);
        if (contactClient.getId() != null) {
            throw new BadRequestAlertException("A new contactClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactClient result = contactClientService.save(contactClient);
        return ResponseEntity
            .created(new URI("/api/contact-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-clients/:id} : Updates an existing contactClient.
     *
     * @param id the id of the contactClient to save.
     * @param contactClient the contactClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactClient,
     * or with status {@code 400 (Bad Request)} if the contactClient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-clients/{id}")
    public ResponseEntity<ContactClient> updateContactClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContactClient contactClient
    ) throws URISyntaxException {
        log.debug("REST request to update ContactClient : {}, {}", id, contactClient);
        if (contactClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContactClient result = contactClientService.save(contactClient);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactClient.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contact-clients/:id} : Partial updates given fields of an existing contactClient, field will ignore if it is null
     *
     * @param id the id of the contactClient to save.
     * @param contactClient the contactClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactClient,
     * or with status {@code 400 (Bad Request)} if the contactClient is not valid,
     * or with status {@code 404 (Not Found)} if the contactClient is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contact-clients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactClient> partialUpdateContactClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContactClient contactClient
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContactClient partially : {}, {}", id, contactClient);
        if (contactClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactClient> result = contactClientService.partialUpdate(contactClient);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactClient.getId().toString())
        );
    }

    /**
     * {@code GET  /contact-clients} : get all the contactClients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactClients in body.
     */
    @GetMapping("/contact-clients")
    public List<ContactClient> getAllContactClients() {
        log.debug("REST request to get all ContactClients");
        return contactClientService.findAll();
    }

    /**
     * {@code GET  /contact-clients/:id} : get the "id" contactClient.
     *
     * @param id the id of the contactClient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactClient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-clients/{id}")
    public ResponseEntity<ContactClient> getContactClient(@PathVariable Long id) {
        log.debug("REST request to get ContactClient : {}", id);
        Optional<ContactClient> contactClient = contactClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactClient);
    }

    /**
     * {@code DELETE  /contact-clients/:id} : delete the "id" contactClient.
     *
     * @param id the id of the contactClient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-clients/{id}")
    public ResponseEntity<Void> deleteContactClient(@PathVariable Long id) {
        log.debug("REST request to delete ContactClient : {}", id);
        contactClientService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
