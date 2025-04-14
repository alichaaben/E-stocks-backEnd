package com.e_stock.BackEnd_E_stocks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_stock.BackEnd_E_stocks.entity.Facture;
import com.e_stock.BackEnd_E_stocks.entity.TypeFacture;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    
    Facture findByNumeroFacture(String numeroFacture);

    List<Facture> findByType(TypeFacture type);

}
