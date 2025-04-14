package com.e_stock.BackEnd_E_stocks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_stock.BackEnd_E_stocks.entity.Operation;
import com.e_stock.BackEnd_E_stocks.entity.Produit;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByProduit(Produit produit);
}
