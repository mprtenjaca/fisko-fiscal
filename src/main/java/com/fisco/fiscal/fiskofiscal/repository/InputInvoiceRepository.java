package com.fisco.fiscal.fiskofiscal.repository;

import com.fisco.fiscal.fiskofiscal.model.InputInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InputInvoiceRepository extends JpaRepository<InputInvoice, Long> {

    List<InputInvoice> findByUserId(Long id);

    @Modifying
    @Query("DELETE FROM InputInvoice o WHERE o.id = :id")
    void delete(@Param("id") Long id);
}
