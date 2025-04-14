package com.e_stock.BackEnd_E_stocks.repository;

import com.e_stock.BackEnd_E_stocks.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    
    @Query("SELECT COUNT(p) FROM Produit p WHERE p.reference LIKE :prefix%")
    Long countByReferenceStartingWith(@Param("prefix") String prefix);

    Produit findByDesignation(String designation);
    
    Produit findByReference(String reference);
}
