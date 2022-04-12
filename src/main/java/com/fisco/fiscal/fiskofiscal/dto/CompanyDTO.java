package com.fisco.fiscal.fiskofiscal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {
    private Long id;
    private String name;
    private String oib;
    private String email;
    private String address;
    private String city;
    private String postalCode;
    private String phoneNumber;
    private Integer taxRate;
    private Boolean isVATsystem;
    private String reference;
    private String website;
    private String customReference;
}
