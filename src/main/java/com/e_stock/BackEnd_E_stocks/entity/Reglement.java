package com.e_stock.BackEnd_E_stocks.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reglement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private double reglement;
    private double montantRestant;
    private double montantTotal;
    private String numFact;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_facture")
    private Facture facture;
}