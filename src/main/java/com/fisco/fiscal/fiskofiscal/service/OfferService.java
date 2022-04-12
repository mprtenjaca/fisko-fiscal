package com.fisco.fiscal.fiskofiscal.service;

import com.fisco.fiscal.fiskofiscal.model.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferService {
    List<Offer> getAll();
    List<Offer> getOffersByUserId(Long id);
    Optional<Offer> getOfferById(Long id);
    List<Offer> findByCustomerId(Long id);
    List<Offer> findByServiceModelId(Long id);
    Offer save(Offer offer);
    Optional<Offer> update(Long id, Offer offer);
    void delete(Long id);
}
