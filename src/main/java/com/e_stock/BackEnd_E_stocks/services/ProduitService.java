package com.e_stock.BackEnd_E_stocks.services;

import com.e_stock.BackEnd_E_stocks.entity.Produit;
import java.util.*;

public interface ProduitService {

    Produit findById(Long id);

    List<Produit> findAll();

    Produit findByDesignation(String designation);

    Produit findByReference(String reference);

    Produit insert(Produit Entity);

    Produit update(Produit Entity);

    void deleteById(Long id);

}
