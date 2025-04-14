package com.e_stock.BackEnd_E_stocks.services;

import java.util.List;

import com.e_stock.BackEnd_E_stocks.entity.Operation;
import com.e_stock.BackEnd_E_stocks.entity.Produit;

public interface OperationService {
    
    List<Operation> findByProduit(Produit produit);

    Operation insert(Operation operation);

}
