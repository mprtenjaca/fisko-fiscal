package com.fisco.fiscal.fiskofiscal.repository;

import com.fisco.fiscal.fiskofiscal.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CustomerServiceRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByUserId(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Customer c WHERE c.id = :id")
    void delete(@Param("id") Long id);
}
