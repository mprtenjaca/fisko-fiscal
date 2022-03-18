package com.fisco.fiscal.fiskofiscal.controller;

import com.fisco.fiscal.fiskofiscal.dto.ServiceModelDTO;
import com.fisco.fiscal.fiskofiscal.model.OutputInvoice;
import com.fisco.fiscal.fiskofiscal.model.ServiceModel;
import com.fisco.fiscal.fiskofiscal.service.OutputInvoiceService;
import com.fisco.fiscal.fiskofiscal.service.ServiceModelService;
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
@AllArgsConstructor
@RequestMapping("/api/service")
@CrossOrigin("*")
public class ServiceModelController {

    private ServiceModelService serviceModelService;
    private OutputInvoiceService outputInvoiceService;


    @GetMapping
    public List<ServiceModelDTO> getServiceModels() {return  serviceModelService.getAll();}

    @GetMapping("/admin/{id}")
    public List<ServiceModelDTO> getServiceModelsByUserId(@PathVariable("id") final Long id){
        return serviceModelService.getServiceModelsByUserId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceModelDTO> getCustomerById(@PathVariable("id") final Long id){
        Optional<ServiceModelDTO> serviceModelDTO = serviceModelService.getServiceModelById(id);
        if(serviceModelDTO.isPresent())
            return new ResponseEntity<>(serviceModelDTO.get(), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ServiceModel> saveCustomer(@RequestBody ServiceModel serviceModel){
        return new ResponseEntity<>(serviceModelService.save(serviceModel), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceModelDTO> update(@PathVariable Long id, @RequestBody final ServiceModel serviceModel){
        Optional<ServiceModelDTO> serviceModelDTO = serviceModelService.update(id, serviceModel);
        if(serviceModelDTO.isPresent())
            return new ResponseEntity<>(serviceModelDTO.get(), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Long id){
        try {
            List<OutputInvoice> outputInvoicesList = outputInvoiceService.findByServiceModelId(id);
            for(OutputInvoice outputInvoice: outputInvoicesList){
                outputInvoice.setServiceModel(null);
            }

            serviceModelService.delete(id);
        }catch (EmptyResultDataAccessException e){
            log.error("Error while trying to delete Service Model by ID", e.getMessage());
        }
    }
}
