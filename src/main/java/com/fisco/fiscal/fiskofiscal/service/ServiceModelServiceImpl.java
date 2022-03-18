package com.fisco.fiscal.fiskofiscal.service;

import com.fisco.fiscal.fiskofiscal.dto.ServiceModelDTO;
import com.fisco.fiscal.fiskofiscal.model.ServiceModel;
import com.fisco.fiscal.fiskofiscal.repository.ServiceModelRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceModelServiceImpl implements ServiceModelService {

    private ServiceModelRepository serviceModelRepository;
    private JdbcTemplate jdbc;
    private SimpleJdbcInsert serviceModelInsert;

    public ServiceModelServiceImpl(ServiceModelRepository serviceModelRepository, JdbcTemplate jdbc) {
        this.serviceModelRepository = serviceModelRepository;
        this.jdbc = jdbc;
        this.serviceModelInsert = new SimpleJdbcInsert(jdbc).withTableName("ServiceModel").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<ServiceModelDTO> getAll() {
        return serviceModelRepository.findAll().stream().map(this::mapServiceModelToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ServiceModelDTO> getServiceModelById(Long id) {
        return serviceModelRepository.findById(id).map(this::mapServiceModelToDTO);
    }

    @Override
    public List<ServiceModelDTO> getServiceModelsByUserId(Long id) {
        return serviceModelRepository.findByUserId(id).stream().map(this::mapServiceModelToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ServiceModel> getById(Long id) {
        return serviceModelRepository.findById(id);
    }

    @Override
    public Optional<ServiceModelDTO> update(Long id, ServiceModel serviceModel) {

        String updateQuery = "UPDATE service_models SET service_name = ?, service_number = ? WHERE id = ?";
        jdbc.update(updateQuery, serviceModel.getServiceName(), serviceModel.getServiceNumber(), id);

        return Optional.ofNullable(serviceModel).map(this::mapServiceModelToDTO);
    }

    @Override
    public ServiceModel save(ServiceModel serviceModel) {
        return serviceModelRepository.save(serviceModel);
    }

    @Override
    public void delete(Long id) {
        serviceModelRepository.delete(id);
    }

    private ServiceModelDTO mapServiceModelToDTO(final ServiceModel serviceModel){
        return new ServiceModelDTO(
                serviceModel.getId(),
                serviceModel.getServiceNumber(),
                serviceModel.getServiceName()
        );
    }

}
