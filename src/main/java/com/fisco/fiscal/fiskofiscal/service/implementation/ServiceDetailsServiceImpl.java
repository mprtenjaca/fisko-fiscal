package com.fisco.fiscal.fiskofiscal.service.implementation;

import com.fisco.fiscal.fiskofiscal.model.ServiceDetails;
import com.fisco.fiscal.fiskofiscal.repository.ServiceDetailRepository;
import com.fisco.fiscal.fiskofiscal.service.ServiceDetailsService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceDetailsServiceImpl implements ServiceDetailsService {

    private ServiceDetailRepository serviceDetailRepository;
    private JdbcTemplate jdbc;
    private SimpleJdbcInsert customerInsert;

    public ServiceDetailsServiceImpl(ServiceDetailRepository serviceDetailRepository, JdbcTemplate jdbc) {
        this.serviceDetailRepository = serviceDetailRepository;
        this.jdbc = jdbc;
        this.customerInsert = new SimpleJdbcInsert(jdbc).withTableName("ServiceDetails").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<ServiceDetails> getAll() {
        return serviceDetailRepository.findAll();
    }

    @Override
    public Optional<ServiceDetails> getById(Long id) {
        return serviceDetailRepository.findById(id);
    }

    @Override
    public ServiceDetails save(ServiceDetails serviceDetails) {
        return serviceDetailRepository.save(serviceDetails);
    }

    @Override
    public Optional<ServiceDetails> update(Long id, ServiceDetails serviceDetails) {

        String updateQuery = "UPDATE service_details SET " +
                "amount = ?, " +
                "discount = ?, " +
                "measure_unit = ?, " +
                "price = ?, " +
                "tax_rate = ?, " +
                "service_description = ? " +
                "WHERE id = ?";
        jdbc.update(updateQuery,
                serviceDetails.getAmount(),
                serviceDetails.getDiscount(),
                serviceDetails.getMeasureUnit().getValue(),
                serviceDetails.getPrice(),
                serviceDetails.getTaxRate(),
                serviceDetails.getServiceDescription(),
                id);

        return Optional.ofNullable(serviceDetails);
    }

    @Override
    public void delete(Long id) {
        serviceDetailRepository.delete(id);
    }

}
