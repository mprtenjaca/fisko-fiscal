package com.fisco.fiscal.fiskofiscal.repository;

import com.fisco.fiscal.fiskofiscal.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByUserId(Long id);

    @Modifying
    @Query("DELETE FROM Company c WHERE c.id = :id")
    void delete(@Param("id") Long id);
}
