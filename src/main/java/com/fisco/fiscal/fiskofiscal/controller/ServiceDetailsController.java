package com.fisco.fiscal.fiskofiscal.controller;


import com.fisco.fiscal.fiskofiscal.model.ServiceDetails;
import com.fisco.fiscal.fiskofiscal.service.ServiceDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/service-details")
@CrossOrigin("*")
public class ServiceDetailsController {

    private ServiceDetailsService serviceDetailsService;

    public ServiceDetailsController(ServiceDetailsService serviceDetailsService) {
        this.serviceDetailsService = serviceDetailsService;
    }

    @GetMapping
    public List<ServiceDetails> getServiceModels() {return  serviceDetailsService.getAll();}

    @PostMapping
    public ResponseEntity<ServiceDetails> saveCustomer(@RequestBody ServiceDetails serviceDetails){
        return new ResponseEntity<>(serviceDetailsService.save(serviceDetails), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceDetails> update(@PathVariable Long id, @RequestBody final ServiceDetails serviceDetails){
        Optional<ServiceDetails> serviceDetailsOptional = serviceDetailsService.update(id, serviceDetails);
        if(serviceDetailsOptional.isPresent())
            return new ResponseEntity<>(serviceDetailsOptional.get(), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
