package com.e_stock.BackEnd_E_stocks.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


import org.springframework.stereotype.Service;

import com.e_stock.BackEnd_E_stocks.entity.Produit;
import com.e_stock.BackEnd_E_stocks.repository.ProduitRepository;
import com.e_stock.BackEnd_E_stocks.services.ProduitService;

import lombok.RequiredArgsConstructor;

import com.e_stock.BackEnd_E_stocks.Exceptions.EntityNotFoundException;
import com.e_stock.BackEnd_E_stocks.Exceptions.InvalidEntityException;

@Service
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;

    @Override
    public Produit findById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produit not found with ID: " + id));
    }

    @Override
    public List<Produit> findAll() {
        return produitRepository.findAll();
    }
    
    @Override
    public Produit findByDesignation(String designation) {
        return produitRepository.findByDesignation(designation);
    }

    @Override
    public Produit findByReference(String reference) {
        return produitRepository.findByReference(reference);
    }

    @Override
    public Produit insert(Produit entity) {
        if (entity.getDesignation() == null || entity.getDesignation().isEmpty()) {
            throw new InvalidEntityException("Prduit cannot be empty.");
        }

        String generatedRef = generateReferenceSequence();
        entity.setReference(generatedRef);

        entity.setTotal(entity.getQuantite() * entity.getPrixUnitaire());
        
        return produitRepository.save(entity);
    }


    private String generateReferenceSequence() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String datePart = LocalDate.now().format(formatter);
        
        // Compter les produits créés ce mois-ci pour le numéro séquentiel
        Long count = produitRepository.countByReferenceStartingWith("REF-" + datePart);
        
        // Format: REF-AAAA-MM-NNNN (4 chiffres)
        return String.format("REF-%s-%04d", datePart, count + 1);
    }

    @Override
    public Produit update(Produit Entity) {
        Produit currentProduit = produitRepository.findById(Entity.getId())
        .orElseThrow(() -> new IllegalArgumentException("Produit not found"));

        currentProduit.setDesignation(Entity.getDesignation());
        currentProduit.setQuantite(Entity.getQuantite());
        currentProduit.setPrixUnitaire(Entity.getPrixUnitaire());
        currentProduit.setTotal(Entity.getQuantite() * Entity.getPrixUnitaire());

        
        return produitRepository.save(currentProduit);
    }

    @Override
    public void deleteById(Long id) {
        produitRepository.deleteById(id);
    }

}

