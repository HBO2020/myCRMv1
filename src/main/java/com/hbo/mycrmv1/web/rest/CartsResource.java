package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.Carts;
import com.hbo.mycrmv1.repository.CartsRepository;
import com.hbo.mycrmv1.service.CartsService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.Carts}.
 */
@RestController
@RequestMapping("/api")
public class CartsResource {

    private final Logger log = LoggerFactory.getLogger(CartsResource.class);

    private static final String ENTITY_NAME = "carts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartsService cartsService;

    private final CartsRepository cartsRepository;

    public CartsResource(CartsService cartsService, CartsRepository cartsRepository) {
        this.cartsService = cartsService;
        this.cartsRepository = cartsRepository;
    }

    /**
     * {@code POST  /carts} : Create a new carts.
     *
     * @param carts the carts to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carts, or with status {@code 400 (Bad Request)} if the carts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carts")
    public ResponseEntity<Carts> createCarts(@RequestBody Carts carts) throws URISyntaxException {
        log.debug("REST request to save Carts : {}", carts);
        if (carts.getId() != null) {
            throw new BadRequestAlertException("A new carts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Carts result = cartsService.save(carts);
        return ResponseEntity
            .created(new URI("/api/carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carts/:id} : Updates an existing carts.
     *
     * @param id the id of the carts to save.
     * @param carts the carts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carts,
     * or with status {@code 400 (Bad Request)} if the carts is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carts/{id}")
    public ResponseEntity<Carts> updateCarts(@PathVariable(value = "id", required = false) final Long id, @RequestBody Carts carts)
        throws URISyntaxException {
        log.debug("REST request to update Carts : {}, {}", id, carts);
        if (carts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cartsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Carts result = cartsService.save(carts);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carts.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /carts/:id} : Partial updates given fields of an existing carts, field will ignore if it is null
     *
     * @param id the id of the carts to save.
     * @param carts the carts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carts,
     * or with status {@code 400 (Bad Request)} if the carts is not valid,
     * or with status {@code 404 (Not Found)} if the carts is not found,
     * or with status {@code 500 (Internal Server Error)} if the carts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/carts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Carts> partialUpdateCarts(@PathVariable(value = "id", required = false) final Long id, @RequestBody Carts carts)
        throws URISyntaxException {
        log.debug("REST request to partial update Carts partially : {}, {}", id, carts);
        if (carts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cartsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Carts> result = cartsService.partialUpdate(carts);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carts.getId().toString())
        );
    }

    /**
     * {@code GET  /carts} : get all the carts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carts in body.
     */
    @GetMapping("/carts")
    public List<Carts> getAllCarts() {
        log.debug("REST request to get all Carts");
        return cartsService.findAll();
    }

    /**
     * {@code GET  /carts/:id} : get the "id" carts.
     *
     * @param id the id of the carts to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carts, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carts/{id}")
    public ResponseEntity<Carts> getCarts(@PathVariable Long id) {
        log.debug("REST request to get Carts : {}", id);
        Optional<Carts> carts = cartsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carts);
    }

    /**
     * {@code DELETE  /carts/:id} : delete the "id" carts.
     *
     * @param id the id of the carts to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> deleteCarts(@PathVariable Long id) {
        log.debug("REST request to delete Carts : {}", id);
        cartsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
