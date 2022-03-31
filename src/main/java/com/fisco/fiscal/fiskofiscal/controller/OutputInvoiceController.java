package com.fisco.fiscal.fiskofiscal.controller;

import com.fisco.fiscal.fiskofiscal.model.Customer;
import com.fisco.fiscal.fiskofiscal.model.OutputInvoice;
import com.fisco.fiscal.fiskofiscal.model.ServiceModel;
import com.fisco.fiscal.fiskofiscal.model.User;
import com.fisco.fiscal.fiskofiscal.service.CustomerService;
import com.fisco.fiscal.fiskofiscal.service.OutputInvoiceService;
import com.fisco.fiscal.fiskofiscal.service.ServiceModelService;
import com.fisco.fiscal.fiskofiscal.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/output-invoice")
@AllArgsConstructor
@CrossOrigin("*")
public class OutputInvoiceController {

    private static final Logger log = LoggerFactory.getLogger(OutputInvoiceController.class);

    private final OutputInvoiceService outputInvoiceService;
    private final CustomerService customerService;
    private final ServiceModelService serviceModelService;
    private final UserService userService;

    @GetMapping
    public List<OutputInvoice> getOutputInvoices(){
        return outputInvoiceService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutputInvoice> getOutputInvoiceById(@PathVariable("id") final Long id){
       Optional<OutputInvoice> optionalOutputInvoice = outputInvoiceService.getOutputInvoiceById(id);
        return optionalOutputInvoice
                .map(outputInvoice -> new ResponseEntity<>(outputInvoice, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @GetMapping("/admin/{id}")
    public List<OutputInvoice> getOutputInvoicesByUserId(@PathVariable("id") final Long id){
        return outputInvoiceService.getOutputInvoicesByUserId(id);
    }

    @PostMapping(consumes = {"application/json"})
    public OutputInvoice save(@RequestBody final OutputInvoice outputInvoice){
        if(outputInvoice.getCustomer() != null){
            if(outputInvoice.getCustomer().getId() != null) {
                Optional<Customer> customer = customerService.getById(outputInvoice.getCustomer().getId());
                customer.ifPresent(outputInvoice::setCustomer);
            }else{
                customerService.save(outputInvoice.getCustomer());
            }
        }

        if(outputInvoice.getServiceModel() != null){
            if(outputInvoice.getServiceModel().getId() != null) {
                Optional<ServiceModel> serviceModel = serviceModelService.getById(outputInvoice.getServiceModel().getId());
                serviceModel.ifPresent(outputInvoice::setServiceModel);
            }else{
                serviceModelService.save(outputInvoice.getServiceModel());
            }

            outputInvoice.setDateAndTime(LocalDateTime.now());
        }

        return outputInvoiceService.save(outputInvoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutputInvoice> update(@PathVariable Long id, @RequestBody final OutputInvoice outputInvoice){
        Optional<Customer> customer = customerService.getById(outputInvoice.getCustomer().getId());
        Optional<ServiceModel> serviceModel = serviceModelService.getById(outputInvoice.getServiceModel().getId());
        Optional<User> user = userService.getUserById(outputInvoice.getUser().getId());

        outputInvoice.setUser(userService.getUserById(outputInvoice.getUser().getId()).orElseGet(null));
        outputInvoice.setCustomer(customer.get());
        outputInvoice.setServiceModel(serviceModel.get());
        outputInvoice.setServiceModel(serviceModel.get());

        OutputInvoice outputInvoiceNew = outputInvoiceService.save(outputInvoice);
        Optional<OutputInvoice> outputInvoiceOptional = outputInvoiceService.update(id, outputInvoiceNew);

        return outputInvoiceOptional
                .map(invoice -> new ResponseEntity<>(invoice, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Long id){
        try {
            outputInvoiceService.delete(id);
        }catch (EmptyResultDataAccessException e){
            log.error("Error while trying to delete OutputInvoice by ID {}", e.getMessage());
        }
    }


}
