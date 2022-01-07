package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.ContactClient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ContactClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactClientRepository extends JpaRepository<ContactClient, Long> {}
