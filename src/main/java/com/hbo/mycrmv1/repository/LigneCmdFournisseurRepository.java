package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.LigneCmdFournisseur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LigneCmdFournisseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigneCmdFournisseurRepository extends JpaRepository<LigneCmdFournisseur, Long> {}
