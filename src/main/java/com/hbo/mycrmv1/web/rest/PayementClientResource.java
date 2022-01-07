package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.PayementClient;
import com.hbo.mycrmv1.repository.PayementClientRepository;
import com.hbo.mycrmv1.service.PayementClientService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.PayementClient}.
 */
@RestController
@RequestMapping("/api")
public class PayementClientResource {

    private final Logger log = LoggerFactory.getLogger(PayementClientResource.class);

    private static final String ENTITY_NAME = "payementClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayementClientService payementClientService;

    private final PayementClientRepository payementClientRepository;

    public PayementClientResource(PayementClientService payementClientService, PayementClientRepository payementClientRepository) {
        this.payementClientService = payementClientService;
        this.payementClientRepository = payementClientRepository;
    }

    /**
     * {@code POST  /payement-clients} : Create a new payementClient.
     *
     * @param payementClient the payementClient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payementClient, or with status {@code 400 (Bad Request)} if the payementClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payement-clients")
    public ResponseEntity<PayementClient> createPayementClient(@RequestBody PayementClient payementClient) throws URISyntaxException {
        log.debug("REST request to save PayementClient : {}", payementClient);
        if (payementClient.getId() != null) {
            throw new BadRequestAlertException("A new payementClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayementClient result = payementClientService.save(payementClient);
        return ResponseEntity
            .created(new URI("/api/payement-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payement-clients/:id} : Updates an existing payementClient.
     *
     * @param id the id of the payementClient to save.
     * @param payementClient the payementClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payementClient,
     * or with status {@code 400 (Bad Request)} if the payementClient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payementClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payement-clients/{id}")
    public ResponseEntity<PayementClient> updatePayementClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayementClient payementClient
    ) throws URISyntaxException {
        log.debug("REST request to update PayementClient : {}, {}", id, payementClient);
        if (payementClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payementClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payementClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PayementClient result = payementClientService.save(payementClient);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payementClient.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payement-clients/:id} : Partial updates given fields of an existing payementClient, field will ignore if it is null
     *
     * @param id the id of the payementClient to save.
     * @param payementClient the payementClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payementClient,
     * or with status {@code 400 (Bad Request)} if the payementClient is not valid,
     * or with status {@code 404 (Not Found)} if the payementClient is not found,
     * or with status {@code 500 (Internal Server Error)} if the payementClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payement-clients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PayementClient> partialUpdatePayementClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayementClient payementClient
    ) throws URISyntaxException {
        log.debug("REST request to partial update PayementClient partially : {}, {}", id, payementClient);
        if (payementClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payementClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payementClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PayementClient> result = payementClientService.partialUpdate(payementClient);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payementClient.getId().toString())
        );
    }

    /**
     * {@code GET  /payement-clients} : get all the payementClients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payementClients in body.
     */
    @GetMapping("/payement-clients")
    public List<PayementClient> getAllPayementClients() {
        log.debug("REST request to get all PayementClients");
        return payementClientService.findAll();
    }

    /**
     * {@code GET  /payement-clients/:id} : get the "id" payementClient.
     *
     * @param id the id of the payementClient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payementClient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payement-clients/{id}")
    public ResponseEntity<PayementClient> getPayementClient(@PathVariable Long id) {
        log.debug("REST request to get PayementClient : {}", id);
        Optional<PayementClient> payementClient = payementClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(payementClient);
    }

    /**
     * {@code DELETE  /payement-clients/:id} : delete the "id" payementClient.
     *
     * @param id the id of the payementClient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payement-clients/{id}")
    public ResponseEntity<Void> deletePayementClient(@PathVariable Long id) {
        log.debug("REST request to delete PayementClient : {}", id);
        payementClientService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
