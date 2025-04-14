package com.e_stock.BackEnd_E_stocks.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String reference; // Format: REF-AAAA-MM-NNNN

    private String designation;
    private int quantite;
    private double prixUnitaire;
    private double total;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<Operation> operations;

}