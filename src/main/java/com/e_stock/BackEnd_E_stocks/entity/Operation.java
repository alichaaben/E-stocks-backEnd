package com.e_stock.BackEnd_E_stocks.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TypeOperation type;

    private String reference;
    private String designation;
    private int quantite;
    private double prix;
    private double total;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_produit", nullable = false)
    private Produit produit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_facture", nullable = false)
    private Facture facture;

}

