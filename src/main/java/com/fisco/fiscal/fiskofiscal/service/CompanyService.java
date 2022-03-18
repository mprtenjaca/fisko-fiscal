package com.fisco.fiscal.fiskofiscal.service;

import com.fisco.fiscal.fiskofiscal.dto.CompanyDTO;
import com.fisco.fiscal.fiskofiscal.model.Company;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface CompanyService {
    List<CompanyDTO> getCompanies();
    Optional<CompanyDTO> getCompanyById(Long id);
    Optional<CompanyDTO> getCompanyByUserId(Long id);
    Optional<Company> save(Company company);
    Optional<CompanyDTO> update(Long id, Company company);
    void delete(Long id);

}
