package com.fisco.fiscal.fiskofiscal.service;

import com.fisco.fiscal.fiskofiscal.dto.CustomerDTO;
import com.fisco.fiscal.fiskofiscal.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDTO> getAll();
    List<CustomerDTO> getCustomersByUserId(Long id);
    Optional<CustomerDTO> getCustomerById(Long id);
    Optional<Customer> getById(Long id);
    Customer save(Customer customer);
    void delete(Long id);
}
