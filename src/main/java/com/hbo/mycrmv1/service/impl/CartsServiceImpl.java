package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.Carts;
import com.hbo.mycrmv1.repository.CartsRepository;
import com.hbo.mycrmv1.service.CartsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Carts}.
 */
@Service
@Transactional
public class CartsServiceImpl implements CartsService {

    private final Logger log = LoggerFactory.getLogger(CartsServiceImpl.class);

    private final CartsRepository cartsRepository;

    public CartsServiceImpl(CartsRepository cartsRepository) {
        this.cartsRepository = cartsRepository;
    }

    @Override
    public Carts save(Carts carts) {
        log.debug("Request to save Carts : {}", carts);
        return cartsRepository.save(carts);
    }

    @Override
    public Optional<Carts> partialUpdate(Carts carts) {
        log.debug("Request to partially update Carts : {}", carts);

        return cartsRepository
            .findById(carts.getId())
            .map(existingCarts -> {
                if (carts.getCartIsEmpty() != null) {
                    existingCarts.setCartIsEmpty(carts.getCartIsEmpty());
                }
                if (carts.getCartUserEmail() != null) {
                    existingCarts.setCartUserEmail(carts.getCartUserEmail());
                }
                if (carts.getCartListProduct() != null) {
                    existingCarts.setCartListProduct(carts.getCartListProduct());
                }

                return existingCarts;
            })
            .map(cartsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Carts> findAll() {
        log.debug("Request to get all Carts");
        return cartsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Carts> findOne(Long id) {
        log.debug("Request to get Carts : {}", id);
        return cartsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Carts : {}", id);
        cartsRepository.deleteById(id);
    }
}
