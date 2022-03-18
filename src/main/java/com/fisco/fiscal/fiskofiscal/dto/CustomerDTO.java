package com.fisco.fiscal.fiskofiscal.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CustomerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String oib;
    private String city;
    private String postalCode;
    private String country;
    private String phoneNumber;
    private String fax;

    public CustomerDTO(Long id, String firstName, String lastName, String address, String email, String oib, String city, String postalCode, String country, String phoneNumber, String fax) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.oib = oib;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.fax = fax;
    }


}
