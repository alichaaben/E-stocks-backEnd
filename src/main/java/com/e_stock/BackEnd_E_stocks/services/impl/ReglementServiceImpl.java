package com.e_stock.BackEnd_E_stocks.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.e_stock.BackEnd_E_stocks.entity.Facture;
import com.e_stock.BackEnd_E_stocks.entity.Reglement;
import com.e_stock.BackEnd_E_stocks.repository.ReglementRepository;
import com.e_stock.BackEnd_E_stocks.services.ReglementService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReglementServiceImpl implements ReglementService {

        private final ReglementRepository reglementRepository;

    @Override
    public List<Reglement> findByFactureId(Facture fact) {
        return reglementRepository.findByFacture(fact);
    }

    @Override
    @Transactional
    public Reglement insert(Reglement reglement) {
        
        if (reglement.getNumFact() == null) {
            throw new IllegalArgumentException("The payment must be associated with an invoice");
        }
        return reglementRepository.save(reglement);
    }

    @Override
    @Transactional
    public Reglement insertByIdFact(Reglement reglement) {

        
        
        if (reglement.getFacture() == null) {
            throw new IllegalArgumentException("The payment must be associated with an invoice");
        }
        return reglementRepository.save(reglement);
    }

}
