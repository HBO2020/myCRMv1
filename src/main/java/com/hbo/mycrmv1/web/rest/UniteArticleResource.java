package com.hbo.mycrmv1.web.rest;

import com.hbo.mycrmv1.domain.UniteArticle;
import com.hbo.mycrmv1.repository.UniteArticleRepository;
import com.hbo.mycrmv1.service.UniteArticleService;
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
 * REST controller for managing {@link com.hbo.mycrmv1.domain.UniteArticle}.
 */
@RestController
@RequestMapping("/api")
public class UniteArticleResource {

    private final Logger log = LoggerFactory.getLogger(UniteArticleResource.class);

    private static final String ENTITY_NAME = "uniteArticle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UniteArticleService uniteArticleService;

    private final UniteArticleRepository uniteArticleRepository;

    public UniteArticleResource(UniteArticleService uniteArticleService, UniteArticleRepository uniteArticleRepository) {
        this.uniteArticleService = uniteArticleService;
        this.uniteArticleRepository = uniteArticleRepository;
    }

    /**
     * {@code POST  /unite-articles} : Create a new uniteArticle.
     *
     * @param uniteArticle the uniteArticle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uniteArticle, or with status {@code 400 (Bad Request)} if the uniteArticle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unite-articles")
    public ResponseEntity<UniteArticle> createUniteArticle(@RequestBody UniteArticle uniteArticle) throws URISyntaxException {
        log.debug("REST request to save UniteArticle : {}", uniteArticle);
        if (uniteArticle.getId() != null) {
            throw new BadRequestAlertException("A new uniteArticle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UniteArticle result = uniteArticleService.save(uniteArticle);
        return ResponseEntity
            .created(new URI("/api/unite-articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unite-articles/:id} : Updates an existing uniteArticle.
     *
     * @param id the id of the uniteArticle to save.
     * @param uniteArticle the uniteArticle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uniteArticle,
     * or with status {@code 400 (Bad Request)} if the uniteArticle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uniteArticle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unite-articles/{id}")
    public ResponseEntity<UniteArticle> updateUniteArticle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UniteArticle uniteArticle
    ) throws URISyntaxException {
        log.debug("REST request to update UniteArticle : {}, {}", id, uniteArticle);
        if (uniteArticle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uniteArticle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uniteArticleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UniteArticle result = uniteArticleService.save(uniteArticle);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uniteArticle.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /unite-articles/:id} : Partial updates given fields of an existing uniteArticle, field will ignore if it is null
     *
     * @param id the id of the uniteArticle to save.
     * @param uniteArticle the uniteArticle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uniteArticle,
     * or with status {@code 400 (Bad Request)} if the uniteArticle is not valid,
     * or with status {@code 404 (Not Found)} if the uniteArticle is not found,
     * or with status {@code 500 (Internal Server Error)} if the uniteArticle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/unite-articles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UniteArticle> partialUpdateUniteArticle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UniteArticle uniteArticle
    ) throws URISyntaxException {
        log.debug("REST request to partial update UniteArticle partially : {}, {}", id, uniteArticle);
        if (uniteArticle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uniteArticle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uniteArticleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UniteArticle> result = uniteArticleService.partialUpdate(uniteArticle);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uniteArticle.getId().toString())
        );
    }

    /**
     * {@code GET  /unite-articles} : get all the uniteArticles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uniteArticles in body.
     */
    @GetMapping("/unite-articles")
    public List<UniteArticle> getAllUniteArticles() {
        log.debug("REST request to get all UniteArticles");
        return uniteArticleService.findAll();
    }

    /**
     * {@code GET  /unite-articles/:id} : get the "id" uniteArticle.
     *
     * @param id the id of the uniteArticle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uniteArticle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unite-articles/{id}")
    public ResponseEntity<UniteArticle> getUniteArticle(@PathVariable Long id) {
        log.debug("REST request to get UniteArticle : {}", id);
        Optional<UniteArticle> uniteArticle = uniteArticleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uniteArticle);
    }

    /**
     * {@code DELETE  /unite-articles/:id} : delete the "id" uniteArticle.
     *
     * @param id the id of the uniteArticle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unite-articles/{id}")
    public ResponseEntity<Void> deleteUniteArticle(@PathVariable Long id) {
        log.debug("REST request to delete UniteArticle : {}", id);
        uniteArticleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
