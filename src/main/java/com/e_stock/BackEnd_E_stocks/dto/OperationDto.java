package com.e_stock.BackEnd_E_stocks.dto;

import java.time.LocalDate;

import com.e_stock.BackEnd_E_stocks.entity.TypeOperation;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationDto {
    private Long id;
    private LocalDate date;
    private TypeOperation type;
    private String reference;
    private String designation;
    private int quantite;
    private double prix;
    private double total;
    private ProduitDto produit;
    private Long factureId;
}
