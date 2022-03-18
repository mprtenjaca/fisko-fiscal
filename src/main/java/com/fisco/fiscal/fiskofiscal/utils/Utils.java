package com.fisco.fiscal.fiskofiscal.utils;

import com.fisco.fiscal.fiskofiscal.dto.CustomerDTO;
import com.fisco.fiscal.fiskofiscal.model.Customer;

public interface Utils {
    default CustomerDTO mapCustomerToDTO(final Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getOib(),
                customer.getCity(),
                customer.getPostalCode(),
                customer.getCountry(),
                customer.getPhoneNumber(),
                customer.getFax()
        );
    }
}
