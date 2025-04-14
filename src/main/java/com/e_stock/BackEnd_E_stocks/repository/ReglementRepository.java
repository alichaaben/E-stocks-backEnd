package com.e_stock.BackEnd_E_stocks.repository;

import java.util.List;

import com.e_stock.BackEnd_E_stocks.entity.Facture;
import com.e_stock.BackEnd_E_stocks.entity.Reglement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReglementRepository extends JpaRepository<Reglement, Long> {

        List<Reglement> findByFacture(Facture facture);

}
