package com.fisco.fiscal.fiskofiscal.controller;

import com.fisco.fiscal.fiskofiscal.dto.CustomerDTO;
import com.fisco.fiscal.fiskofiscal.model.Customer;
import com.fisco.fiscal.fiskofiscal.model.OutputInvoice;
import com.fisco.fiscal.fiskofiscal.model.ServiceDetails;
import com.fisco.fiscal.fiskofiscal.model.ServiceModel;
import com.fisco.fiscal.fiskofiscal.model.User;
import com.fisco.fiscal.fiskofiscal.service.CustomerService;
import com.fisco.fiscal.fiskofiscal.service.JdbcCustomerRepository;
import com.fisco.fiscal.fiskofiscal.service.OutputInvoiceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerController {

    private CustomerService customerService;
    private OutputInvoiceService outputInvoiceService;
    private JdbcCustomerRepository jdbcCustomerRepository;

    @GetMapping
    public List<CustomerDTO> getCustomers() {
        return customerService.getAll();
    }

    @GetMapping("/admin/{id}")
    public List<CustomerDTO> getCustomersByUserId(@PathVariable("id") final Long id) {
        return customerService.getCustomersByUserId(id);
    }

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") final Long id){
        Optional<CustomerDTO> customerDTO = customerService.getCustomerById(id);
        if(customerDTO.isPresent())
            return new ResponseEntity<>(customerDTO.get(), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable Long id, @RequestBody final Customer customer){
        Optional<CustomerDTO> customerDTO = jdbcCustomerRepository.update(id, customer);
        if(customerDTO.isPresent())
            return new ResponseEntity<>(customerDTO.get(), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Long id){
        try {
            List<OutputInvoice> outputInvoicesList = outputInvoiceService.findByCustomerId(id);
            if(!outputInvoicesList.isEmpty()) {
                for(OutputInvoice outputInvoice : outputInvoicesList)
                    outputInvoiceService.delete(outputInvoice.getId());
            }
            customerService.delete(id);
        }catch (EmptyResultDataAccessException e){
            log.error("Error while trying to delete Customer by ID", e.getMessage());
        }
    }
}
