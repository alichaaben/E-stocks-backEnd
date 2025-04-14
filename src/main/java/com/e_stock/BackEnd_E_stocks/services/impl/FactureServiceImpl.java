package com.e_stock.BackEnd_E_stocks.services.impl;

import org.springframework.stereotype.Service;

import com.e_stock.BackEnd_E_stocks.entity.Facture;
import com.e_stock.BackEnd_E_stocks.entity.Operation;
import com.e_stock.BackEnd_E_stocks.entity.Produit;
import com.e_stock.BackEnd_E_stocks.entity.Reglement;
import com.e_stock.BackEnd_E_stocks.entity.TypeFacture;
import com.e_stock.BackEnd_E_stocks.entity.TypeOperation;
import com.e_stock.BackEnd_E_stocks.repository.FactureRepository;
import com.e_stock.BackEnd_E_stocks.repository.ProduitRepository;
import com.e_stock.BackEnd_E_stocks.services.FactureService;
import com.e_stock.BackEnd_E_stocks.services.ProduitService;
import com.e_stock.BackEnd_E_stocks.services.ReglementService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FactureServiceImpl implements FactureService {

    private final FactureRepository factureRepository;
    private final ProduitService produitService;
    private final ProduitRepository produitRepository;
    private final ReglementService reglementService;

    @Override
    public Facture findById(Long id) {
        return factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture not found"));
    }

    @Override
    @Transactional
    public Facture insert(Facture facture) {
        List<Operation> operations = facture.getOperations();
        facture.setOperations(new ArrayList<>());

        // 1. Sauvegarder la facture sans opérations
        Facture savedFacture = factureRepository.save(facture);

        List<Operation> savedOperations = new ArrayList<>();

        // 2. Traiter les opérations (si FOURNISSEUR)
        if (facture.getType() == TypeFacture.FOURNISSEUR) {
            for (Operation op : operations) {
                Produit produitFacture = op.getProduit();
                Produit produitEnBase = produitRepository.findByDesignation(produitFacture.getDesignation());

                Produit produitFinal;

                if (produitEnBase != null) {
                    // Produit existe déjà => MAJ quantité + total
                    int nouvelleQuantite = produitEnBase.getQuantite() + produitFacture.getQuantite();
                    produitEnBase.setQuantite(nouvelleQuantite);
                    produitEnBase.setTotal(nouvelleQuantite * produitEnBase.getPrixUnitaire());
                    produitFinal = produitRepository.save(produitEnBase);
                } else {
                    // Nouveau produit
                    Produit nouveauProduit = Produit.builder()
                            .designation(produitFacture.getDesignation())
                            .quantite(produitFacture.getQuantite())
                            .prixUnitaire(produitFacture.getPrixUnitaire())
                            .total(produitFacture.getQuantite() * produitFacture.getPrixUnitaire())
                            .build();
                    produitFinal = produitService.insert(nouveauProduit);
                }

                // Créer opération associée
                Operation nouvelleOperation = Operation.builder()
                        .date(LocalDate.now())
                        .type(TypeOperation.ENTREE)
                        .reference(produitFinal.getReference())
                        .designation(produitFacture.getDesignation())
                        .quantite(produitFacture.getQuantite())
                        .prix(produitFacture.getPrixUnitaire())
                        .total(produitFacture.getTotal())
                        .produit(produitFinal)
                        .facture(savedFacture)
                        .build();

                savedOperations.add(nouvelleOperation);
            }

        }

        // 3. Sauvegarder les opérations
        savedFacture.setOperations(savedOperations);



 // 4. Créer le règlement associé à la facture
        double montantTotal = savedFacture.getTotalTTC();
        double montantRegle = savedFacture.getReglement();
        double montantRestant = montantTotal - montantRegle;
        savedFacture.setMontantRestant(montantRestant);

        if (Math.abs(montantRestant) < 0.01) {
            savedFacture.setStatus("payée");
        } else {
            savedFacture.setStatus("impayée");
        }


        // Nouveau Reglement
        Reglement reglement = Reglement.builder()
                .date(LocalDate.now())
                .montantTotal(montantTotal)
                .reglement(montantRegle)
                .montantRestant(montantRestant)
                .numFact(savedFacture.getNumeroFacture())
                .facture(savedFacture)
                .build();

        reglementService.insert(reglement);




        return factureRepository.save(savedFacture);
    }
}
