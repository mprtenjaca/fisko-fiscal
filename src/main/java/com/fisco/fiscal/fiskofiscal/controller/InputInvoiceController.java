package com.fisco.fiscal.fiskofiscal.controller;

import com.fisco.fiscal.fiskofiscal.model.InputInvoice;
import com.fisco.fiscal.fiskofiscal.service.InputInvoiceService;
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
@RequestMapping("api/input-invoice")
@AllArgsConstructor
@CrossOrigin("*")
public class InputInvoiceController {

    private final InputInvoiceService inputInvoiceService;

    @GetMapping
    public List<InputInvoice> getInputInvoices(){
        return inputInvoiceService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputInvoice> getInputInvoiceById(@PathVariable("id") final Long id){
        Optional<InputInvoice> optionalInputInvoice = inputInvoiceService.getInputInvoiceById(id);
        return optionalInputInvoice
                .map(inputInvoice -> new ResponseEntity<>(inputInvoice, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @GetMapping("/admin/{id}")
    public List<InputInvoice> getInputInvoicesByUserId(@PathVariable("id") final Long id){
        return inputInvoiceService.getInputInvoicesByUserId(id);
    }

    @PostMapping(consumes = {"application/json"})
    public InputInvoice save(@RequestBody final InputInvoice inputInvoice){
        return inputInvoiceService.save(inputInvoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InputInvoice> update(@PathVariable Long id, @RequestBody final InputInvoice inputInvoice){

        Optional<InputInvoice> inputInvoiceOptional = inputInvoiceService.update(id, inputInvoice);

        return inputInvoiceOptional
                .map(invoice -> new ResponseEntity<>(invoice, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Long id){
        try {
            inputInvoiceService.delete(id);
        }catch (EmptyResultDataAccessException e){
            log.error("Error while trying to delete InputInvoice by ID {}", e.getMessage());
        }
    }


}
