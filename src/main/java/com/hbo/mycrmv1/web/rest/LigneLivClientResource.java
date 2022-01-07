package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.LigneLivClient;
import com.hbo.mycrmv1.repository.LigneLivClientRepository;
import com.hbo.mycrmv1.service.LigneLivClientService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.LigneLivClient}.
 */
@RestController
@RequestMapping("/api")
public class LigneLivClientResource {

    private final Logger log = LoggerFactory.getLogger(LigneLivClientResource.class);

    private static final String ENTITY_NAME = "ligneLivClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigneLivClientService ligneLivClientService;

    private final LigneLivClientRepository ligneLivClientRepository;

    public LigneLivClientResource(LigneLivClientService ligneLivClientService, LigneLivClientRepository ligneLivClientRepository) {
        this.ligneLivClientService = ligneLivClientService;
        this.ligneLivClientRepository = ligneLivClientRepository;
    }

    /**
     * {@code POST  /ligne-liv-clients} : Create a new ligneLivClient.
     *
     * @param ligneLivClient the ligneLivClient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligneLivClient, or with status {@code 400 (Bad Request)} if the ligneLivClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ligne-liv-clients")
    public ResponseEntity<LigneLivClient> createLigneLivClient(@RequestBody LigneLivClient ligneLivClient) throws URISyntaxException {
        log.debug("REST request to save LigneLivClient : {}", ligneLivClient);
        if (ligneLivClient.getId() != null) {
            throw new BadRequestAlertException("A new ligneLivClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LigneLivClient result = ligneLivClientService.save(ligneLivClient);
        return ResponseEntity
            .created(new URI("/api/ligne-liv-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ligne-liv-clients/:id} : Updates an existing ligneLivClient.
     *
     * @param id the id of the ligneLivClient to save.
     * @param ligneLivClient the ligneLivClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneLivClient,
     * or with status {@code 400 (Bad Request)} if the ligneLivClient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligneLivClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ligne-liv-clients/{id}")
    public ResponseEntity<LigneLivClient> updateLigneLivClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LigneLivClient ligneLivClient
    ) throws URISyntaxException {
        log.debug("REST request to update LigneLivClient : {}, {}", id, ligneLivClient);
        if (ligneLivClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneLivClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligneLivClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LigneLivClient result = ligneLivClientService.save(ligneLivClient);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneLivClient.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ligne-liv-clients/:id} : Partial updates given fields of an existing ligneLivClient, field will ignore if it is null
     *
     * @param id the id of the ligneLivClient to save.
     * @param ligneLivClient the ligneLivClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneLivClient,
     * or with status {@code 400 (Bad Request)} if the ligneLivClient is not valid,
     * or with status {@code 404 (Not Found)} if the ligneLivClient is not found,
     * or with status {@code 500 (Internal Server Error)} if the ligneLivClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ligne-liv-clients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LigneLivClient> partialUpdateLigneLivClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LigneLivClient ligneLivClient
    ) throws URISyntaxException {
        log.debug("REST request to partial update LigneLivClient partially : {}, {}", id, ligneLivClient);
        if (ligneLivClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneLivClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligneLivClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LigneLivClient> result = ligneLivClientService.partialUpdate(ligneLivClient);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneLivClient.getId().toString())
        );
    }

    /**
     * {@code GET  /ligne-liv-clients} : get all the ligneLivClients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligneLivClients in body.
     */
    @GetMapping("/ligne-liv-clients")
    public List<LigneLivClient> getAllLigneLivClients() {
        log.debug("REST request to get all LigneLivClients");
        return ligneLivClientService.findAll();
    }

    /**
     * {@code GET  /ligne-liv-clients/:id} : get the "id" ligneLivClient.
     *
     * @param id the id of the ligneLivClient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ligneLivClient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ligne-liv-clients/{id}")
    public ResponseEntity<LigneLivClient> getLigneLivClient(@PathVariable Long id) {
        log.debug("REST request to get LigneLivClient : {}", id);
        Optional<LigneLivClient> ligneLivClient = ligneLivClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ligneLivClient);
    }

    /**
     * {@code DELETE  /ligne-liv-clients/:id} : delete the "id" ligneLivClient.
     *
     * @param id the id of the ligneLivClient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ligne-liv-clients/{id}")
    public ResponseEntity<Void> deleteLigneLivClient(@PathVariable Long id) {
        log.debug("REST request to delete LigneLivClient : {}", id);
        ligneLivClientService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
