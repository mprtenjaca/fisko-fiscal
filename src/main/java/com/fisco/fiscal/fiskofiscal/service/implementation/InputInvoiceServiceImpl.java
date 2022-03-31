package com.fisco.fiscal.fiskofiscal.service.implementation;

import com.fisco.fiscal.fiskofiscal.model.InputInvoice;
import com.fisco.fiscal.fiskofiscal.repository.InputInvoiceRepository;
import com.fisco.fiscal.fiskofiscal.service.InputInvoiceService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class InputInvoiceServiceImpl implements InputInvoiceService {

    private InputInvoiceRepository inputInvoiceRepository;
    private JdbcTemplate jdbc;
    private SimpleJdbcInsert inputInvoiceInsert;

    public InputInvoiceServiceImpl(InputInvoiceRepository inputInvoiceRepository, JdbcTemplate jdbc) {
        this.inputInvoiceRepository = inputInvoiceRepository;
        this.jdbc = jdbc;
        this.inputInvoiceInsert = new SimpleJdbcInsert(jdbc).withTableName("InputInvoice").usingGeneratedKeyColumns("id");;
    }

    @Override
    public List<InputInvoice> getAll() {
        return inputInvoiceRepository.findAll();
    }

    @Override
    public Optional<InputInvoice> getInputInvoiceById(Long id) {
        return inputInvoiceRepository.findById(id);
    }

    @Override
    public List<InputInvoice> getInputInvoicesByUserId(Long id) {
        return inputInvoiceRepository.findByUserId(id);
    }


    @Override
    public InputInvoice save(InputInvoice outputInvoice) {
        return inputInvoiceRepository.save(outputInvoice);
    }

    @Override
    public Optional<InputInvoice> update(Long id, InputInvoice inputInvoice) {

        String updateQuery = "UPDATE input_invoices SET " +
                "expense_type = ?, " +
                "invoice_issuer = ?, " +
                "city = ?, " +
                "street_address = ?, " +
                "street_number = ?, " +
                "postal_code = ?, " +
                "oib = ?, " +
                "description = ?, " +
                "reference = ?, " +
                "payment_method = ?, " +
                "tax_rate = ?, " +
                "pretax = ?, " +
                "price = ?, " +
                "final_price = ?, " +
                "invoice_date = ?, " +
                "payment_deadline = ?, " +
                "payment_date = ? " +
                "WHERE id = ?";

        jdbc.update(updateQuery,
                inputInvoice.getExpenseType(),
                inputInvoice.getIssuer(),
                inputInvoice.getCity(),
                inputInvoice.getStreetAddress(),
                inputInvoice.getStreetNumber(),
                inputInvoice.getPostalCode(),
                inputInvoice.getOib(),
                inputInvoice.getDescription(),
                inputInvoice.getReference(),
                inputInvoice.getPaymentMethod(),
                inputInvoice.getTaxRate(),
                inputInvoice.getPretax(),
                inputInvoice.getPrice(),
                inputInvoice.getFinalPrice(),
                inputInvoice.getInvoiceDate(),
                inputInvoice.getPaymentDeadline(),
                inputInvoice.getPaymentDate(),
                id);

        return Optional.ofNullable(inputInvoice);
    }

    @Override
    public void delete(Long id) {
        inputInvoiceRepository.delete(id);
    }
}
