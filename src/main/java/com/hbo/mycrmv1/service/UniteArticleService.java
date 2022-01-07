package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.UniteArticle;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link UniteArticle}.
 */
public interface UniteArticleService {
    /**
     * Save a uniteArticle.
     *
     * @param uniteArticle the entity to save.
     * @return the persisted entity.
     */
    UniteArticle save(UniteArticle uniteArticle);

    /**
     * Partially updates a uniteArticle.
     *
     * @param uniteArticle the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UniteArticle> partialUpdate(UniteArticle uniteArticle);

    /**
     * Get all the uniteArticles.
     *
     * @return the list of entities.
     */
    List<UniteArticle> findAll();

    /**
     * Get the "id" uniteArticle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UniteArticle> findOne(Long id);

    /**
     * Delete the "id" uniteArticle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
