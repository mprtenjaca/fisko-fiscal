package com.fisco.fiscal.fiskofiscal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fisco.fiscal.fiskofiscal.enums.InvoiceType;
import com.fisco.fiscal.fiskofiscal.enums.PaymentMethod;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OutputInvoiceDTO {

    private Integer invoiceNumber;
    private InvoiceType invoiceType;
    private PaymentMethod paymentMethod;
    private LocalDateTime dateAndTime;
    private LocalDate deliveryDate;
}
