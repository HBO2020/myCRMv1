package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.CiviliteClient;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CiviliteClient}.
 */
public interface CiviliteClientService {
    /**
     * Save a civiliteClient.
     *
     * @param civiliteClient the entity to save.
     * @return the persisted entity.
     */
    CiviliteClient save(CiviliteClient civiliteClient);

    /**
     * Partially updates a civiliteClient.
     *
     * @param civiliteClient the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CiviliteClient> partialUpdate(CiviliteClient civiliteClient);

    /**
     * Get all the civiliteClients.
     *
     * @return the list of entities.
     */
    List<CiviliteClient> findAll();

    /**
     * Get the "id" civiliteClient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CiviliteClient> findOne(Long id);

    /**
     * Delete the "id" civiliteClient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
