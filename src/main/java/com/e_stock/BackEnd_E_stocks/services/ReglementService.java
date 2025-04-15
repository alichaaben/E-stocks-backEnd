package com.e_stock.BackEnd_E_stocks.services;

import java.util.List;

import com.e_stock.BackEnd_E_stocks.entity.Facture;
import com.e_stock.BackEnd_E_stocks.entity.Reglement;

public interface ReglementService {

    List<Reglement> findByFactureId(Facture facture);

    Reglement insert(Reglement reglement);

    Reglement insertByIdFact(Reglement reglement);

    Reglement updateReglement(Reglement reglement);

    void deleteById(Long id);

}
