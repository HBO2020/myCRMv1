package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.CommandeClient;
import com.hbo.mycrmv1.repository.CommandeClientRepository;
import com.hbo.mycrmv1.service.CommandeClientService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.CommandeClient}.
 */
@RestController
@RequestMapping("/api")
public class CommandeClientResource {

    private final Logger log = LoggerFactory.getLogger(CommandeClientResource.class);

    private static final String ENTITY_NAME = "commandeClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandeClientService commandeClientService;

    private final CommandeClientRepository commandeClientRepository;

    public CommandeClientResource(CommandeClientService commandeClientService, CommandeClientRepository commandeClientRepository) {
        this.commandeClientService = commandeClientService;
        this.commandeClientRepository = commandeClientRepository;
    }

    /**
     * {@code POST  /commande-clients} : Create a new commandeClient.
     *
     * @param commandeClient the commandeClient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandeClient, or with status {@code 400 (Bad Request)} if the commandeClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commande-clients")
    public ResponseEntity<CommandeClient> createCommandeClient(@RequestBody CommandeClient commandeClient) throws URISyntaxException {
        log.debug("REST request to save CommandeClient : {}", commandeClient);
        if (commandeClient.getId() != null) {
            throw new BadRequestAlertException("A new commandeClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandeClient result = commandeClientService.save(commandeClient);
        return ResponseEntity
            .created(new URI("/api/commande-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commande-clients/:id} : Updates an existing commandeClient.
     *
     * @param id the id of the commandeClient to save.
     * @param commandeClient the commandeClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeClient,
     * or with status {@code 400 (Bad Request)} if the commandeClient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandeClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commande-clients/{id}")
    public ResponseEntity<CommandeClient> updateCommandeClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommandeClient commandeClient
    ) throws URISyntaxException {
        log.debug("REST request to update CommandeClient : {}, {}", id, commandeClient);
        if (commandeClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commandeClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commandeClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommandeClient result = commandeClientService.save(commandeClient);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandeClient.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /commande-clients/:id} : Partial updates given fields of an existing commandeClient, field will ignore if it is null
     *
     * @param id the id of the commandeClient to save.
     * @param commandeClient the commandeClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeClient,
     * or with status {@code 400 (Bad Request)} if the commandeClient is not valid,
     * or with status {@code 404 (Not Found)} if the commandeClient is not found,
     * or with status {@code 500 (Internal Server Error)} if the commandeClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/commande-clients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommandeClient> partialUpdateCommandeClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommandeClient commandeClient
    ) throws URISyntaxException {
        log.debug("REST request to partial update CommandeClient partially : {}, {}", id, commandeClient);
        if (commandeClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commandeClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commandeClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommandeClient> result = commandeClientService.partialUpdate(commandeClient);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandeClient.getId().toString())
        );
    }

    /**
     * {@code GET  /commande-clients} : get all the commandeClients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandeClients in body.
     */
    @GetMapping("/commande-clients")
    public List<CommandeClient> getAllCommandeClients() {
        log.debug("REST request to get all CommandeClients");
        return commandeClientService.findAll();
    }

    /**
     * {@code GET  /commande-clients/:id} : get the "id" commandeClient.
     *
     * @param id the id of the commandeClient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandeClient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commande-clients/{id}")
    public ResponseEntity<CommandeClient> getCommandeClient(@PathVariable Long id) {
        log.debug("REST request to get CommandeClient : {}", id);
        Optional<CommandeClient> commandeClient = commandeClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commandeClient);
    }

    /**
     * {@code DELETE  /commande-clients/:id} : delete the "id" commandeClient.
     *
     * @param id the id of the commandeClient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commande-clients/{id}")
    public ResponseEntity<Void> deleteCommandeClient(@PathVariable Long id) {
        log.debug("REST request to delete CommandeClient : {}", id);
        commandeClientService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
