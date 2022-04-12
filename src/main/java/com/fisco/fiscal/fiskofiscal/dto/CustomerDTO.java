package com.fisco.fiscal.fiskofiscal.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String companyName;
    private String address;
    private String email;
    private String oib;
    private String city;
    private String postalCode;
    private String country;
    private String phoneNumber;
    private String fax;
    private Date createdAt;

}
