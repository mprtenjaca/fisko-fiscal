package com.fisco.fiscal.fiskofiscal.service;

import com.fisco.fiscal.fiskofiscal.dto.ServiceModelDTO;
import com.fisco.fiscal.fiskofiscal.model.ServiceModel;

import java.util.List;
import java.util.Optional;

public interface ServiceModelService {
    List<ServiceModelDTO> getAll();
    Optional<ServiceModelDTO> getServiceModelById(Long id);
    List<ServiceModelDTO> getServiceModelsByUserId(Long id);
    Optional<ServiceModel> getById(Long id);
    Optional<ServiceModelDTO> update(Long id, ServiceModel serviceModel);
    ServiceModel save(ServiceModel serviceModel);
    void delete(Long id);
}
