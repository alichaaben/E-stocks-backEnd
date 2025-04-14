package com.e_stock.BackEnd_E_stocks.services;

import com.e_stock.BackEnd_E_stocks.entity.Facture;

public interface FactureService {

    Facture findById(Long id);

    // Facture findByNumero(String numeroFacture);

    // List<Facture> findAll();

    // List<Facture> findByFournisseur(Long fournisseurId);

    // List<Facture> findByType(TypeFacture type);

    Facture insert(Facture facture);

    // Facture update(Facture facture);

    // void deleteById(Long id);

    // double calculateMontantRestant(Long factureId);


}
