package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.LivraisonCl;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LivraisonCl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LivraisonClRepository extends JpaRepository<LivraisonCl, Long> {}
