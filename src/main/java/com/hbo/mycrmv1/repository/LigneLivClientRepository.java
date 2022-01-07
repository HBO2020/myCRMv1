package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.LigneLivClient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LigneLivClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigneLivClientRepository extends JpaRepository<LigneLivClient, Long> {}
