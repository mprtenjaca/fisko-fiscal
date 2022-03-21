package com.fisco.fiscal.fiskofiscal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fisco.fiscal.fiskofiscal.enums.MeasureUnit;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "service_details")
public class ServiceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serviceDescription;
    @Enumerated(EnumType.STRING)
    private MeasureUnit measureUnit;
    private Integer amount;
    private Integer discount;
    private Integer taxRate;
    private BigDecimal price;
    private BigDecimal finalPrice;

//    @OneToOne(mappedBy = "serviceDetails")
//    @JoinColumn(name = "output_invoice_id")
//    @JsonIgnore
//    private OutputInvoice outputInvoice;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "output_invoice_id", referencedColumnName = "id")
//    private OutputInvoice outputInvoice;

    @OneToOne(mappedBy = "serviceDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private Offer offer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
