package com.fisco.fiscal.fiskofiscal.service;


import com.fisco.fiscal.fiskofiscal.model.InputInvoice;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface InputInvoiceService {
    List<InputInvoice> getAll();
    List<InputInvoice> getInputInvoicesByUserId(Long id);
    Optional<InputInvoice> getInputInvoiceById(Long id);
    InputInvoice save(InputInvoice outputInvoice);
    Optional<InputInvoice> update(Long id, InputInvoice outputInvoice);
    void delete(Long id);
}
