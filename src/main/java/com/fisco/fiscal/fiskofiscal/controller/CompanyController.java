package com.fisco.fiscal.fiskofiscal.controller;

import com.fisco.fiscal.fiskofiscal.dto.CompanyDTO;
import com.fisco.fiscal.fiskofiscal.model.Company;
import com.fisco.fiscal.fiskofiscal.model.OutputInvoice;
import com.fisco.fiscal.fiskofiscal.model.User;
import com.fisco.fiscal.fiskofiscal.service.CompanyService;
import com.fisco.fiscal.fiskofiscal.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/company")
@CrossOrigin("*")
public class CompanyController {

    private CompanyService companyService;
    private UserService userService;

    @GetMapping
    public List<CompanyDTO> getCompanies(){
        return companyService.getCompanies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable("id") final Long id){
        Optional<CompanyDTO> optionalCompany = companyService.getCompanyById(id);

        if(optionalCompany.isPresent())
            return new ResponseEntity<>(optionalCompany.get(), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<CompanyDTO> getCompanyByUserId(@PathVariable("id") final Long id){
        Optional<CompanyDTO> optionalCompany = companyService.getCompanyByUserId(id);

        if(optionalCompany.isPresent())
            return new ResponseEntity<>(optionalCompany.get(), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Company> save(@RequestBody final Company company){
        Optional<User> user = userService.getUserById(company.getUser().getId());

        if(user.isPresent()){
            user.get().setCompany(company);
            company.setUser(user.get());
        }
        Optional<Company> optionalCompany = companyService.save(company);

        if(optionalCompany.isPresent())
            return new ResponseEntity<>(optionalCompany.get(), HttpStatus.CREATED);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> update(@PathVariable("id") Long id, @RequestBody Company company){
        Optional<CompanyDTO> optionalUpdatedCompany = companyService.update(id, company);
        if(optionalUpdatedCompany.isPresent()){
            return new ResponseEntity<>(optionalUpdatedCompany.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Long id){
        try {
            companyService.delete(id);
        }catch (EmptyResultDataAccessException e){
            log.error("Error while trying to delete Customer by ID", e.getMessage());
        }
    }
}
