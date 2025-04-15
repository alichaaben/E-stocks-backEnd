package com.e_stock.BackEnd_E_stocks.services.impl;

import org.springframework.stereotype.Service;

import com.e_stock.BackEnd_E_stocks.Exceptions.ProduitInexistantException;
import com.e_stock.BackEnd_E_stocks.Exceptions.StockInsuffisantException;
import com.e_stock.BackEnd_E_stocks.entity.Facture;
import com.e_stock.BackEnd_E_stocks.entity.Operation;
import com.e_stock.BackEnd_E_stocks.entity.Produit;
import com.e_stock.BackEnd_E_stocks.entity.Reglement;
import com.e_stock.BackEnd_E_stocks.entity.TypeFacture;
import com.e_stock.BackEnd_E_stocks.entity.TypeOperation;
import com.e_stock.BackEnd_E_stocks.repository.FactureRepository;
import com.e_stock.BackEnd_E_stocks.repository.ProduitRepository;
import com.e_stock.BackEnd_E_stocks.services.FactureService;
import com.e_stock.BackEnd_E_stocks.services.OperationService;
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
    private final OperationService operationService;

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
    
        Facture savedFacture = factureRepository.save(facture);
        List<Operation> savedOperations;
    
        if (facture.getType() == TypeFacture.FOURNISSEUR) {
            savedOperations = handleFournisseurOperations(operations, savedFacture);
        } else if (facture.getType() == TypeFacture.CLIENT) {
            savedOperations = handleClientOperations(operations, savedFacture);
        } else {
            throw new IllegalArgumentException("Type de facture inconnu : " + facture.getType());
        }
    
        savedFacture.setOperations(savedOperations);
        handleReglement(savedFacture);
    
        return factureRepository.save(savedFacture);
    }
    

    /************************ FOURNISSEURS ************************** */


    private List<Operation> handleFournisseurOperations(List<Operation> operations, Facture facture) {
        List<Operation> savedOperations = new ArrayList<>();
    
        for (Operation op : operations) {
            Produit produitFacture = op.getProduit();
            Produit produitEnBase = produitRepository.findByDesignation(produitFacture.getDesignation());
            Produit produitFinal;
    
            if (produitEnBase != null) {
                int nouvelleQuantite = produitEnBase.getQuantite() + produitFacture.getQuantite();
                produitEnBase.setQuantite(nouvelleQuantite);
                produitEnBase.setTotal(nouvelleQuantite * produitEnBase.getPrixUnitaire());
                produitFinal = produitRepository.save(produitEnBase);
            } else {
                Produit nouveauProduit = Produit.builder()
                        .designation(produitFacture.getDesignation())
                        .quantite(produitFacture.getQuantite())
                        .prixUnitaire(produitFacture.getPrixUnitaire())
                        .total(produitFacture.getQuantite() * produitFacture.getPrixUnitaire())
                        .build();
                produitFinal = produitService.insert(nouveauProduit);
            }
    
            Operation nouvelleOperation = Operation.builder()
                    .date(LocalDate.now())
                    .type(TypeOperation.ENTREE)
                    .reference(produitFinal.getReference())
                    .designation(produitFacture.getDesignation())
                    .quantite(produitFacture.getQuantite())
                    .prix(produitFacture.getPrixUnitaire())
                    .total(produitFacture.getTotal())
                    .produit(produitFinal)
                    .facture(facture)
                    .build();
    
            savedOperations.add(nouvelleOperation);
        }
    
        return savedOperations;
    }


    /****************************** CLIENTS ************************** */
    private List<Operation> handleClientOperations(List<Operation> operations, Facture facture) {
        List<Operation> savedOperations = new ArrayList<>();
    
        for (Operation op : operations) {
            Produit p = op.getProduit();
            Produit produitEnBase = produitRepository.findByDesignation(p.getDesignation());
    
            if (produitEnBase == null) {
                throw new ProduitInexistantException("Produit inexistant : " + p.getDesignation());
            }
    
            int nouvelleQte = produitEnBase.getQuantite() - p.getQuantite();
            if (nouvelleQte < 0) {
                throw new StockInsuffisantException("Stock insuffisant pour le produit : " + p.getDesignation());
            }
    
            produitEnBase.setQuantite(nouvelleQte);
            produitEnBase.setTotal(nouvelleQte * produitEnBase.getPrixUnitaire());
            produitRepository.save(produitEnBase);
    
            savedOperations.add(Operation.builder()
                .date(LocalDate.now())
                .type(TypeOperation.SORTIE)
                .reference(produitEnBase.getReference())
                .designation(p.getDesignation())
                .quantite(p.getQuantite())
                .prix(p.getPrixUnitaire())
                .total(p.getTotal())
                .produit(produitEnBase)
                .facture(facture)
                .build());
        }
    
        return savedOperations;
    }
    

    /*********************************Reglements*********************************** */

    private void handleReglement(Facture facture) {
        double montantTotal = facture.getTotalTTC();
        double montantRegle = facture.getReglement();
        double montantRestant = montantTotal - montantRegle;
    
        facture.setMontantRestant(montantRestant);
    
        facture.setStatus(montantRestant <= 0.01 ? "payée" : "impayée");
    
        Reglement reglement = Reglement.builder()
                .date(LocalDate.now())
                .montantTotal(montantTotal)
                .reglement(montantRegle)
                .montantRestant(montantRestant)
                .numFact(facture.getNumeroFacture())
                .facture(facture)
                .build();
    
        reglementService.insert(reglement);
    }
    

    /***************************************************************************** */


    @Override
    @Transactional
    public Facture update(Facture facture) {
        // 1. Récupérer la facture existante
        Facture existingFacture = factureRepository.findById(facture.getId())
                .orElseThrow(() -> new RuntimeException("Facture not found"));

        // 2. Mettre à jour les informations de la facture
        existingFacture.setDateFacture(facture.getDateFacture());
        existingFacture.setNumeroFacture(facture.getNumeroFacture());
        existingFacture.setTotalHT(facture.getTotalHT());
        existingFacture.setTVA(facture.getTVA());
        existingFacture.setTotalTTC(facture.getTotalTTC());
        existingFacture.setReglement(facture.getReglement());
        existingFacture.setType(facture.getType());

        // 3. Mise à jour des opérations si présentes
        List<Operation> updatedOperations = new ArrayList<>();
        for (Operation op : facture.getOperations()) {
            op.setFacture(existingFacture);
            if (op.getId() != null) {
                updatedOperations.add(op);
            }
        }

        for (Operation op : updatedOperations) {
            operationService.update(op);
        }

        // 4. Mise à jour du règlement
        List<Reglement> reglements = reglementService.findByFactureId(existingFacture);
        if (!reglements.isEmpty()) {
            Reglement reglement = reglements.get(0); // Si on gère un seul règlement par facture
            reglement.setReglement(existingFacture.getReglement());
            reglement.setDate(LocalDate.now());
            reglementService.updateReglement(reglement);
        }

        // 5. Recalculer le montant restant et le statut
        double montantRestant = existingFacture.getTotalTTC() - existingFacture.getReglement();
        existingFacture.setMontantRestant(montantRestant);

        if (Math.abs(montantRestant) < 0.01) {
            existingFacture.setStatus("payée");
        } else {
            existingFacture.setStatus("impayée");
        }

        return factureRepository.save(existingFacture);
    }

    // @Override
    // @Transactional
    // public void deleteById(Long id) {
    // Facture facture = factureRepository.findById(id)
    // .orElseThrow(() -> new RuntimeException("Facture introuvable avec l'id : " +
    // id));

    // // 1. Annuler les effets des opérations sur les produits AVANT de supprimer
    // les opérations
    // List<Operation> operations = facture.getOperations();

    // for (Operation operation : operations) {
    // Produit produit = operation.getProduit();

    // if (facture.getType() == TypeFacture.FOURNISSEUR && operation.getType() ==
    // TypeOperation.ENTREE) {
    // if (produit != null && produit.getId() != null) {
    // int nouvelleQuantite = produit.getQuantite() - operation.getQuantite();
    // produit.setQuantite(Math.max(0, nouvelleQuantite));
    // produit.setTotal(produit.getQuantite() * produit.getPrixUnitaire());
    // produitRepository.save(produit);
    // }
    // }
    // }

    // // 2. Supprimer les opérations (après les updates produit)
    // for (Operation operation : operations) {
    // operationService.deleteById(operation.getId());
    // }

    // // 3. Supprimer les règlements
    // List<Reglement> reglements = reglementService.findByFactureId(facture);
    // for (Reglement reglement : reglements) {
    // reglementService.deleteById(reglement.getId());
    // }

    // // 4. Supprimer la facture
    // factureRepository.deleteById(id);
    // }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable avec l'id : " + id));

        // 1. Supprimer les opérations associées et annuler leurs effets sur les
        // produits
        List<Operation> operations = facture.getOperations();

        for (Operation operation : operations) {
            Produit produit = operation.getProduit();

            if (facture.getType() == TypeFacture.FOURNISSEUR && operation.getType() == TypeOperation.ENTREE) {
                // Revenir en arrière : soustraire la quantité
                int ancienneQuantite = produit.getQuantite() - operation.getQuantite();
                if (ancienneQuantite <= 0) {
                    // On peut supprimer complètement le produit si sa quantité devient nulle
                    produitRepository.deleteById(produit.getId());
                } else {
                    produit.setQuantite(ancienneQuantite);
                    produit.setTotal(produit.getQuantite() * produit.getPrixUnitaire());
                    produitRepository.save(produit);
                }
            }

            // Supprimer l'opération
            operationService.deleteById(operation.getId());
        }

        // 2. Supprimer les règlements associés
        List<Reglement> reglements = reglementService.findByFactureId(facture);
        for (Reglement reglement : reglements) {
            reglementService.deleteById(reglement.getId());
        }

        // 3. Supprimer la facture
        factureRepository.deleteById(id);
    }

}
