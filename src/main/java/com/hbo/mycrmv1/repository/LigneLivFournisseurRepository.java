package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.LigneLivFournisseur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LigneLivFournisseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigneLivFournisseurRepository extends JpaRepository<LigneLivFournisseur, Long> {}
