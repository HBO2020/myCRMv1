package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.LigneCmdClient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LigneCmdClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigneCmdClientRepository extends JpaRepository<LigneCmdClient, Long> {}
