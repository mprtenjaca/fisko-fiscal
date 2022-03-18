package com.fisco.fiscal.fiskofiscal.service;

import com.fisco.fiscal.fiskofiscal.model.OutputInvoice;

import java.util.List;
import java.util.Optional;

public interface OutputInvoiceService {
    List<OutputInvoice> getAll();
    List<OutputInvoice> getOutputInvoicesByUserId(Long id);
    Optional<OutputInvoice> getOutputInvoiceById(Long id);
    List<OutputInvoice> findByCustomerId(Long id);
    List<OutputInvoice> findByServiceModelId(Long id);
    OutputInvoice save(OutputInvoice outputInvoice);
    Optional<OutputInvoice> update(Long id, OutputInvoice outputInvoice);
    void delete(Long id);
}
