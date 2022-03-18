package com.fisco.fiscal.fiskofiscal.service;

import com.fisco.fiscal.fiskofiscal.dto.CustomerDTO;
import com.fisco.fiscal.fiskofiscal.model.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface JdbcCustomerRepository {
    Optional<CustomerDTO> update(Long id, Customer customer);
}
