package com.fisco.fiscal.fiskofiscal.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class GenericModel {
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
}
