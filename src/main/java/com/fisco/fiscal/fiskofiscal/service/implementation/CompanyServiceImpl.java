package com.fisco.fiscal.fiskofiscal.service.implementation;

import com.fisco.fiscal.fiskofiscal.dto.CompanyDTO;
import com.fisco.fiscal.fiskofiscal.model.Company;
import com.fisco.fiscal.fiskofiscal.repository.CompanyRepository;
import com.fisco.fiscal.fiskofiscal.service.CompanyService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private JdbcTemplate jdbc;
    private SimpleJdbcInsert companyInsert;

    public CompanyServiceImpl(CompanyRepository companyRepository, JdbcTemplate jdbc) {
        this.companyRepository = companyRepository;
        this.jdbc = jdbc;
        this.companyInsert = new SimpleJdbcInsert(jdbc).withTableName("Company").usingGeneratedKeyColumns("id");;
    }

    @Override
    public List<CompanyDTO> getCompanies() {
        return companyRepository.findAll().stream().map(this::mapCompanyToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<CompanyDTO> getCompanyById(Long id) {
        return companyRepository.findById(id).map(this::mapCompanyToDTO);
    }

    @Override
    public Optional<CompanyDTO> getCompanyByUserId(Long id) {
        return companyRepository.findByUserId(id).map(this::mapCompanyToDTO);
    }

    @Override
    public Optional<Company> save(Company company) {
        return Optional.of(companyRepository.save(company));
    }

    @Override
    public Optional<CompanyDTO> update(Long id, Company company) {
        String updateQuery = "UPDATE company SET " +
                "name = ?, " +
                "oib = ?, " +
                "email = ?," +
                "address = ?, " +
                "city = ?, " +
                "postal_code = ?, " +
                "phone_number = ?, " +
                "tax_rate = ?, " +
                "isvatsystem = ?, " +
                "reference = ?, " +
                "website = ?, " +
                "custom_reference = ? " +
                "WHERE id = ?";

        jdbc.update(updateQuery,
                company.getName(),
                company.getOib(),
                company.getEmail(),
                company.getAddress(),
                company.getCity(),
                company.getPostalCode(),
                company.getPhoneNumber(),
                company.getTaxRate(),
                company.getIsVATsystem(),
                company.getReference(),
                company.getWebsite(),
                company.getCustomReference(),
                id);

        return Optional.ofNullable(company).map(this::mapCompanyToDTO);
    }

    @Override
    public void delete(Long id) {
        companyRepository.delete(id);
    }

    public CompanyDTO mapCompanyToDTO(Company company){
        return new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getOib(),
                company.getEmail(),
                company.getAddress(),
                company.getCity(),
                company.getPostalCode(),
                company.getPhoneNumber(),
                company.getTaxRate(),
                company.getIsVATsystem(),
                company.getReference(),
                company.getWebsite(),
                company.getCustomReference()
        );
    }
}
