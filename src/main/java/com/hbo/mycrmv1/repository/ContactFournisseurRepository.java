package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.ContactFournisseur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ContactFournisseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactFournisseurRepository extends JpaRepository<ContactFournisseur, Long> {}
