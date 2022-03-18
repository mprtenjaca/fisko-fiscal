package com.fisco.fiscal.fiskofiscal.repository;

import com.fisco.fiscal.fiskofiscal.model.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ServiceModelRepository extends JpaRepository<ServiceModel, Long> {
    List<ServiceModel> findByUserId(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM ServiceModel s WHERE s.id = :id")
    void delete(@Param("id") Long id);
}
