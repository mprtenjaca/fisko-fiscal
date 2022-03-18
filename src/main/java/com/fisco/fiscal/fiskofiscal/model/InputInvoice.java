package com.fisco.fiscal.fiskofiscal.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "input_invoices")
public class InputInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String expenseType;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Customer customer;
    private String description;

}
