package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.CiviliteClient;
import com.hbo.mycrmv1.repository.CiviliteClientRepository;
import com.hbo.mycrmv1.service.CiviliteClientService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.CiviliteClient}.
 */
@RestController
@RequestMapping("/api")
public class CiviliteClientResource {

    private final Logger log = LoggerFactory.getLogger(CiviliteClientResource.class);

    private static final String ENTITY_NAME = "civiliteClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CiviliteClientService civiliteClientService;

    private final CiviliteClientRepository civiliteClientRepository;

    public CiviliteClientResource(CiviliteClientService civiliteClientService, CiviliteClientRepository civiliteClientRepository) {
        this.civiliteClientService = civiliteClientService;
        this.civiliteClientRepository = civiliteClientRepository;
    }

    /**
     * {@code POST  /civilite-clients} : Create a new civiliteClient.
     *
     * @param civiliteClient the civiliteClient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new civiliteClient, or with status {@code 400 (Bad Request)} if the civiliteClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/civilite-clients")
    public ResponseEntity<CiviliteClient> createCiviliteClient(@RequestBody CiviliteClient civiliteClient) throws URISyntaxException {
        log.debug("REST request to save CiviliteClient : {}", civiliteClient);
        if (civiliteClient.getId() != null) {
            throw new BadRequestAlertException("A new civiliteClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CiviliteClient result = civiliteClientService.save(civiliteClient);
        return ResponseEntity
            .created(new URI("/api/civilite-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /civilite-clients/:id} : Updates an existing civiliteClient.
     *
     * @param id the id of the civiliteClient to save.
     * @param civiliteClient the civiliteClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated civiliteClient,
     * or with status {@code 400 (Bad Request)} if the civiliteClient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the civiliteClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/civilite-clients/{id}")
    public ResponseEntity<CiviliteClient> updateCiviliteClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CiviliteClient civiliteClient
    ) throws URISyntaxException {
        log.debug("REST request to update CiviliteClient : {}, {}", id, civiliteClient);
        if (civiliteClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, civiliteClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!civiliteClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CiviliteClient result = civiliteClientService.save(civiliteClient);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, civiliteClient.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /civilite-clients/:id} : Partial updates given fields of an existing civiliteClient, field will ignore if it is null
     *
     * @param id the id of the civiliteClient to save.
     * @param civiliteClient the civiliteClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated civiliteClient,
     * or with status {@code 400 (Bad Request)} if the civiliteClient is not valid,
     * or with status {@code 404 (Not Found)} if the civiliteClient is not found,
     * or with status {@code 500 (Internal Server Error)} if the civiliteClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/civilite-clients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CiviliteClient> partialUpdateCiviliteClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CiviliteClient civiliteClient
    ) throws URISyntaxException {
        log.debug("REST request to partial update CiviliteClient partially : {}, {}", id, civiliteClient);
        if (civiliteClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, civiliteClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!civiliteClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CiviliteClient> result = civiliteClientService.partialUpdate(civiliteClient);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, civiliteClient.getId().toString())
        );
    }

    /**
     * {@code GET  /civilite-clients} : get all the civiliteClients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of civiliteClients in body.
     */
    @GetMapping("/civilite-clients")
    public List<CiviliteClient> getAllCiviliteClients() {
        log.debug("REST request to get all CiviliteClients");
        return civiliteClientService.findAll();
    }

    /**
     * {@code GET  /civilite-clients/:id} : get the "id" civiliteClient.
     *
     * @param id the id of the civiliteClient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the civiliteClient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/civilite-clients/{id}")
    public ResponseEntity<CiviliteClient> getCiviliteClient(@PathVariable Long id) {
        log.debug("REST request to get CiviliteClient : {}", id);
        Optional<CiviliteClient> civiliteClient = civiliteClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(civiliteClient);
    }

    /**
     * {@code DELETE  /civilite-clients/:id} : delete the "id" civiliteClient.
     *
     * @param id the id of the civiliteClient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/civilite-clients/{id}")
    public ResponseEntity<Void> deleteCiviliteClient(@PathVariable Long id) {
        log.debug("REST request to delete CiviliteClient : {}", id);
        civiliteClientService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
