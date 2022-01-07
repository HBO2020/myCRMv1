package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.PayementFournisseur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PayementFournisseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayementFournisseurRepository extends JpaRepository<PayementFournisseur, Long> {}
