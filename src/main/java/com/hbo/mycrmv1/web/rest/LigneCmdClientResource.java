package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.LigneCmdClient;
import com.hbo.mycrmv1.repository.LigneCmdClientRepository;
import com.hbo.mycrmv1.service.LigneCmdClientService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.LigneCmdClient}.
 */
@RestController
@RequestMapping("/api")
public class LigneCmdClientResource {

    private final Logger log = LoggerFactory.getLogger(LigneCmdClientResource.class);

    private static final String ENTITY_NAME = "ligneCmdClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigneCmdClientService ligneCmdClientService;

    private final LigneCmdClientRepository ligneCmdClientRepository;

    public LigneCmdClientResource(LigneCmdClientService ligneCmdClientService, LigneCmdClientRepository ligneCmdClientRepository) {
        this.ligneCmdClientService = ligneCmdClientService;
        this.ligneCmdClientRepository = ligneCmdClientRepository;
    }

    /**
     * {@code POST  /ligne-cmd-clients} : Create a new ligneCmdClient.
     *
     * @param ligneCmdClient the ligneCmdClient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligneCmdClient, or with status {@code 400 (Bad Request)} if the ligneCmdClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ligne-cmd-clients")
    public ResponseEntity<LigneCmdClient> createLigneCmdClient(@RequestBody LigneCmdClient ligneCmdClient) throws URISyntaxException {
        log.debug("REST request to save LigneCmdClient : {}", ligneCmdClient);
        if (ligneCmdClient.getId() != null) {
            throw new BadRequestAlertException("A new ligneCmdClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LigneCmdClient result = ligneCmdClientService.save(ligneCmdClient);
        return ResponseEntity
            .created(new URI("/api/ligne-cmd-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ligne-cmd-clients/:id} : Updates an existing ligneCmdClient.
     *
     * @param id the id of the ligneCmdClient to save.
     * @param ligneCmdClient the ligneCmdClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneCmdClient,
     * or with status {@code 400 (Bad Request)} if the ligneCmdClient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligneCmdClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ligne-cmd-clients/{id}")
    public ResponseEntity<LigneCmdClient> updateLigneCmdClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LigneCmdClient ligneCmdClient
    ) throws URISyntaxException {
        log.debug("REST request to update LigneCmdClient : {}, {}", id, ligneCmdClient);
        if (ligneCmdClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneCmdClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligneCmdClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LigneCmdClient result = ligneCmdClientService.save(ligneCmdClient);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneCmdClient.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ligne-cmd-clients/:id} : Partial updates given fields of an existing ligneCmdClient, field will ignore if it is null
     *
     * @param id the id of the ligneCmdClient to save.
     * @param ligneCmdClient the ligneCmdClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneCmdClient,
     * or with status {@code 400 (Bad Request)} if the ligneCmdClient is not valid,
     * or with status {@code 404 (Not Found)} if the ligneCmdClient is not found,
     * or with status {@code 500 (Internal Server Error)} if the ligneCmdClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ligne-cmd-clients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LigneCmdClient> partialUpdateLigneCmdClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LigneCmdClient ligneCmdClient
    ) throws URISyntaxException {
        log.debug("REST request to partial update LigneCmdClient partially : {}, {}", id, ligneCmdClient);
        if (ligneCmdClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneCmdClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligneCmdClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LigneCmdClient> result = ligneCmdClientService.partialUpdate(ligneCmdClient);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneCmdClient.getId().toString())
        );
    }

    /**
     * {@code GET  /ligne-cmd-clients} : get all the ligneCmdClients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligneCmdClients in body.
     */
    @GetMapping("/ligne-cmd-clients")
    public List<LigneCmdClient> getAllLigneCmdClients() {
        log.debug("REST request to get all LigneCmdClients");
        return ligneCmdClientService.findAll();
    }

    /**
     * {@code GET  /ligne-cmd-clients/:id} : get the "id" ligneCmdClient.
     *
     * @param id the id of the ligneCmdClient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ligneCmdClient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ligne-cmd-clients/{id}")
    public ResponseEntity<LigneCmdClient> getLigneCmdClient(@PathVariable Long id) {
        log.debug("REST request to get LigneCmdClient : {}", id);
        Optional<LigneCmdClient> ligneCmdClient = ligneCmdClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ligneCmdClient);
    }

    /**
     * {@code DELETE  /ligne-cmd-clients/:id} : delete the "id" ligneCmdClient.
     *
     * @param id the id of the ligneCmdClient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ligne-cmd-clients/{id}")
    public ResponseEntity<Void> deleteLigneCmdClient(@PathVariable Long id) {
        log.debug("REST request to delete LigneCmdClient : {}", id);
        ligneCmdClientService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
