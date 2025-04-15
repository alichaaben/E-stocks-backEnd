package com.e_stock.BackEnd_E_stocks.services;

import com.e_stock.BackEnd_E_stocks.entity.Facture;

public interface FactureService {

    Facture findById(Long id);

    Facture insert(Facture facture);

    Facture update(Facture facture);

    void deleteById(Long id);

}
