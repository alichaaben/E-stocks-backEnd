package com.e_stock.BackEnd_E_stocks.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateFacture;

    @Column(unique = true)
    private String numeroFacture;

    // Info de Fournisseur :
    private String nom;
    private String adresse;
    private String telephone;

    @Enumerated(EnumType.STRING)
    private TypeFacture type;

    private double montantTotal;
    private double montantRestant;
    private String status;
    
    @Transient
    private Double reglement;

    private double totalHT;// THT = Total
    private double remise;//==> remise
    private double TVA; // ==> TVA = 19% de (TotalHT - remise) toujour
    private double totalTTC;//==> TotalTTC = TotalHT - remise + TVA

    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL)
    private List<Operation> operations;

    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL)
    private List<Reglement> reglements;
}
