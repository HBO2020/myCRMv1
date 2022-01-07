package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.LivraisonFr;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LivraisonFr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LivraisonFrRepository extends JpaRepository<LivraisonFr, Long> {}
