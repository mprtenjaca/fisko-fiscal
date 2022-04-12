package com.fisco.fiscal.fiskofiscal.service.implementation;

import com.fisco.fiscal.fiskofiscal.model.Offer;
import com.fisco.fiscal.fiskofiscal.repository.OfferRepository;
import com.fisco.fiscal.fiskofiscal.repository.OutputInvoiceRepository;
import com.fisco.fiscal.fiskofiscal.service.OfferService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class OfferServiceImpl implements OfferService {

    private OfferRepository offerRepository;
    private JdbcTemplate jdbc;
    private SimpleJdbcInsert offerInsert;

    public OfferServiceImpl(OfferRepository offerRepository, JdbcTemplate jdbc) {
        this.offerRepository = offerRepository;
        this.jdbc = jdbc;
        this.offerInsert = new SimpleJdbcInsert(jdbc).withTableName("Offer").usingGeneratedKeyColumns("id");;
    }

    @Override
    public List<Offer> getAll() {
        return offerRepository.findAll();
    }

    @Override
    public List<Offer> getOffersByUserId(Long id) {
        return offerRepository.findByUserId(id);
    }

    @Override
    public Optional<Offer> getOfferById(Long id) {
        return offerRepository.findById(id);
    }

    @Override
    public List<Offer> findByCustomerId(Long id) {
        return offerRepository.findByCustomerId(id);
    }

    @Override
    public List<Offer> findByServiceModelId(Long id) {
        return offerRepository.findByServiceModelId(id);
    }

    @Override
    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public Optional<Offer> update(Long id, Offer offer) {
        String updateQuery = "UPDATE offers SET " +
                "offer_number = ?, " +
                "payment_method = ?, " +
                "offer_date = ?, " +
                "payment_date = ? " +
                "WHERE id = ?";

        jdbc.update(updateQuery,
                offer.getOfferNumber(),
                offer.getPaymentMethod().getValue(),
                offer.getOfferDate(),
                offer.getPaymentDate(),
                id);

        return Optional.ofNullable(offer);
    }

    @Override
    public void delete(Long id) {
        offerRepository.delete(id);
    }
}
