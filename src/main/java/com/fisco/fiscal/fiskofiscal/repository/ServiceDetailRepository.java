package com.fisco.fiscal.fiskofiscal.repository;

import com.fisco.fiscal.fiskofiscal.model.ServiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ServiceDetailRepository extends JpaRepository<ServiceDetails, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM ServiceDetails s WHERE s.id = :id")
    void delete(@Param("id") Long id);
}
