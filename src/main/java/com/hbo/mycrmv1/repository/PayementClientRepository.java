package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.PayementClient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PayementClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayementClientRepository extends JpaRepository<PayementClient, Long> {}
