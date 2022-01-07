package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.CiviliteFournisseur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CiviliteFournisseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CiviliteFournisseurRepository extends JpaRepository<CiviliteFournisseur, Long> {}
