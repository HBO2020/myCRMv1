package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.CiviliteClient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CiviliteClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CiviliteClientRepository extends JpaRepository<CiviliteClient, Long> {}
