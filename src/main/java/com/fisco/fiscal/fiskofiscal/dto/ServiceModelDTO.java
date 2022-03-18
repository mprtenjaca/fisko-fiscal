package com.fisco.fiscal.fiskofiscal.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ServiceModelDTO {

    private Long id;
    private Integer serviceNumber;
    private String serviceName;

    public ServiceModelDTO(Long id, Integer serviceNumber, String serviceName) {
        this.id = id;
        this.serviceNumber = serviceNumber;
        this.serviceName = serviceName;
    }
}
