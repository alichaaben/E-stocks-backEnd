package com.e_stock.BackEnd_E_stocks.services.impl;

import java.time.LocalDate;
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
        if (reglement.getFacture() == null || reglement.getFacture().getId() == null) {
            throw new IllegalArgumentException("Facture ID is required");
        }

        Facture facture = reglement.getFacture();

        double montantRestant = facture.getMontantRestant() - reglement.getReglement();
        facture.setMontantRestant(montantRestant);

        if (Math.abs(montantRestant) < 0.01) {
            facture.setStatus("payée");
        } else {
            facture.setStatus("impayée");
        }

        reglement.setDate(LocalDate.now());
        reglement.setMontantTotal(facture.getTotalTTC());
        reglement.setMontantRestant(montantRestant);
        reglement.setNumFact(facture.getNumeroFacture());
        reglement.setFacture(facture);

        return reglementRepository.save(reglement);
    }

    @Override
    @Transactional
    public Reglement updateReglement(Reglement Entity) {
        Reglement currentReglement = reglementRepository.findById(Entity.getId())
            .orElseThrow(() -> new IllegalArgumentException("Reglement not found"));
    
        if (Entity.getFacture() == null || Entity.getFacture().getId() == null) {
            throw new IllegalArgumentException("Facture ID is required");
        }
    
        Facture facture = Entity.getFacture();
        
        // Calculate the difference between old and new payment amounts
        double paymentDifference = Entity.getReglement() - currentReglement.getReglement();
        
        // Update the remaining amount
        double montantRestant = facture.getMontantRestant() - paymentDifference;
        facture.setMontantRestant(montantRestant);
    
        // Update status
        if (Math.abs(montantRestant) < 0.01) {
            facture.setStatus("payée");
        } else {
            facture.setStatus("impayée");
        }
    
        // Update reglement fields
        currentReglement.setDate(Entity.getDate());
        currentReglement.setReglement(Entity.getReglement());
        currentReglement.setMontantTotal(facture.getTotalTTC());
        currentReglement.setMontantRestant(montantRestant);
        currentReglement.setNumFact(facture.getNumeroFacture());
        currentReglement.setFacture(facture);
        
        return reglementRepository.save(currentReglement);
    }

    @Override
    public void deleteById(Long id) {
        reglementRepository.deleteById(id);
    }

}
