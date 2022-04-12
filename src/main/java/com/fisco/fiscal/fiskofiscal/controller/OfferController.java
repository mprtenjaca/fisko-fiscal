package com.fisco.fiscal.fiskofiscal.controller;

import com.fisco.fiscal.fiskofiscal.model.Customer;
import com.fisco.fiscal.fiskofiscal.model.Offer;
import com.fisco.fiscal.fiskofiscal.model.ServiceModel;
import com.fisco.fiscal.fiskofiscal.model.User;
import com.fisco.fiscal.fiskofiscal.service.CustomerService;
import com.fisco.fiscal.fiskofiscal.service.OfferService;
import com.fisco.fiscal.fiskofiscal.service.ServiceModelService;
import com.fisco.fiscal.fiskofiscal.service.UserService;
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
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/offer")
@AllArgsConstructor
@CrossOrigin("*")
public class OfferController {

    private final OfferService offerService;
    private final CustomerService customerService;
    private final ServiceModelService serviceModelService;
    private final UserService userService;

    @GetMapping
    public List<Offer> getOffers(){
        return offerService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable("id") final Long id){
        Optional<Offer> optionalOffer = offerService.getOfferById(id);
        return optionalOffer
                .map(offer -> new ResponseEntity<>(offer, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/admin/{id}")
    public List<Offer> getOfferByUserId(@PathVariable("id") final Long id){
        return offerService.getOffersByUserId(id);
    }

    @PostMapping(consumes = {"application/json"})
    public Offer save(@RequestBody final Offer offer){
        if(!Objects.isNull(offer.getCustomer())){
            if(offer.getCustomer().getId() != null) {
                Optional<Customer> customer = customerService.getById(offer.getCustomer().getId());
                customer.ifPresent(offer::setCustomer);
            }else{
                customerService.save(offer.getCustomer());
            }
        }

        if(!Objects.isNull(offer.getServiceModel())){
            if(offer.getServiceModel().getId() != null) {
                Optional<ServiceModel> serviceModel = serviceModelService.getById(offer.getServiceModel().getId());
                serviceModel.ifPresent(offer::setServiceModel);
            }else{
                serviceModelService.save(offer.getServiceModel());
            }

            //outputInvoice.setDateAndTime(LocalDateTime.now());
        }

        return offerService.save(offer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Offer> update(@PathVariable Long id, @RequestBody final Offer offer){
        Optional<Customer> customer = customerService.getById(offer.getCustomer().getId());
        Optional<ServiceModel> serviceModel = serviceModelService.getById(offer.getServiceModel().getId());
        Optional<User> user = userService.getUserById(offer.getUser().getId());

        user.ifPresent(offer::setUser);
        customer.ifPresent(offer::setCustomer);
        serviceModel.ifPresent(offer::setServiceModel);

        Offer offerNew = offerService.save(offer);
        Optional<Offer> offerOptional = offerService.update(id, offerNew);

        return offerOptional
                .map(invoice -> new ResponseEntity<>(invoice, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Long id){
        try {
            offerService.delete(id);
        }catch (EmptyResultDataAccessException e){
            log.error("Error while trying to delete Offer by ID {}", e.getMessage());
        }
    }

}
