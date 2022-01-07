package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.Carts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Carts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartsRepository extends JpaRepository<Carts, Long> {}
