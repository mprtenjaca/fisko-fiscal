package com.fisco.fiscal.fiskofiscal.service;

import com.fisco.fiscal.fiskofiscal.model.ServiceDetails;

import java.util.List;
import java.util.Optional;

public interface ServiceDetailsService {
    List<ServiceDetails> getAll();
    Optional<ServiceDetails> getById(Long id);
    ServiceDetails save(ServiceDetails serviceDetails);
    Optional<ServiceDetails> update(Long id, ServiceDetails serviceDetails);
    void delete(Long id);
}
