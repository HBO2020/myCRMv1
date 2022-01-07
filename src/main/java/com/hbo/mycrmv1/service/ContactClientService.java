package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.ContactClient;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ContactClient}.
 */
public interface ContactClientService {
    /**
     * Save a contactClient.
     *
     * @param contactClient the entity to save.
     * @return the persisted entity.
     */
    ContactClient save(ContactClient contactClient);

    /**
     * Partially updates a contactClient.
     *
     * @param contactClient the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContactClient> partialUpdate(ContactClient contactClient);

    /**
     * Get all the contactClients.
     *
     * @return the list of entities.
     */
    List<ContactClient> findAll();

    /**
     * Get the "id" contactClient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactClient> findOne(Long id);

    /**
     * Delete the "id" contactClient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
