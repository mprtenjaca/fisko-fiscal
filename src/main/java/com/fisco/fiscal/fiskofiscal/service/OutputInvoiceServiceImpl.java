package com.fisco.fiscal.fiskofiscal.service;

import com.fisco.fiscal.fiskofiscal.model.OutputInvoice;
import com.fisco.fiscal.fiskofiscal.repository.OutputInvoiceRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutputInvoiceServiceImpl implements OutputInvoiceService {

    private OutputInvoiceRepository outputInvoiceRepository;
    private JdbcTemplate jdbc;
    private SimpleJdbcInsert outputInvoiceInsert;

    public OutputInvoiceServiceImpl(OutputInvoiceRepository outputInvoiceRepository, JdbcTemplate jdbc) {
        this.outputInvoiceRepository = outputInvoiceRepository;
        this.jdbc = jdbc;
        this.outputInvoiceInsert = new SimpleJdbcInsert(jdbc).withTableName("OutputInvoice").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<OutputInvoice> getAll() {
        return outputInvoiceRepository.findAll();
    }

    @Override
    public List<OutputInvoice> getOutputInvoicesByUserId(Long id) {
        return outputInvoiceRepository.findByUserId(id);
    }

    @Override
    public Optional<OutputInvoice> getOutputInvoiceById(Long id) {
        return outputInvoiceRepository.findOutputInvoiceById(id);
    }

    @Override
    public List<OutputInvoice> findByCustomerId(Long id) {
        return outputInvoiceRepository.findByCustomerId(id);
    }

    @Override
    public List<OutputInvoice> findByServiceModelId(Long id) {
        return outputInvoiceRepository.findByServiceModelId(id);
    }

    @Override
    public OutputInvoice save(OutputInvoice outputInvoice) {
        return outputInvoiceRepository.save(outputInvoice);
    }

    @Override
    public Optional<OutputInvoice> update(Long id, OutputInvoice outputInvoice) {
        String updateQuery = "UPDATE output_invoices SET " +
                "invoice_number = ?, " +
                "delivery_date = ?, " +
                "invoice_type = ?, " +
                "payment_method = ? " +
                "WHERE id = ?";

        jdbc.update(updateQuery,
                outputInvoice.getInvoiceNumber(),
                outputInvoice.getDeliveryDate(),
                outputInvoice.getInvoiceType().getValue(),
                outputInvoice.getPaymentMethod().getValue(),
                id);

        return Optional.ofNullable(outputInvoice);
    }

    @Override
    public void delete(Long id) {
        outputInvoiceRepository.delete(id);
    }
}
