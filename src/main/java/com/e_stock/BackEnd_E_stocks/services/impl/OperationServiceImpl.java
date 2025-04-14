package com.e_stock.BackEnd_E_stocks.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.e_stock.BackEnd_E_stocks.entity.Operation;
import com.e_stock.BackEnd_E_stocks.entity.Produit;
import com.e_stock.BackEnd_E_stocks.repository.OperationRepository;
import com.e_stock.BackEnd_E_stocks.services.OperationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    @Override
    public List<Operation> findByProduit(Produit produit) {
        return operationRepository.findByProduit(produit);
    }

    @Override
    @Transactional
    public Operation insert(Operation operation) {
        // Ensure facture is not null
        if (operation.getFacture() == null) {
            throw new IllegalArgumentException("Operation must be associated with a facture");
        }
        return operationRepository.save(operation);
    }
}
