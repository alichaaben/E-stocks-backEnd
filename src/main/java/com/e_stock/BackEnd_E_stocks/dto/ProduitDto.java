package com.e_stock.BackEnd_E_stocks.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProduitDto {
    private Long id;
    private String reference;
    private String designation;
    private int quantite;
    private double prixUnitaire;
    private double total;
}

