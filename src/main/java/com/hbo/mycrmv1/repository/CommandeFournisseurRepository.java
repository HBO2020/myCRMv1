package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.CommandeFournisseur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CommandeFournisseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Long> {}
