package com.e_stock.BackEnd_E_stocks.dto;

import lombok.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FactureDto {
    private Long id;
    private Date dateFacture;
    private String numeroFacture;
    private String nom;
    private String adresse;
    private String telephone;
    private String type;
    private double montantTotal;
    private double reglement;
    private double montantRestant;
    private String status;
    private double totalHT;
    private double remise;
    private double TVA;
    private double totalTTC;
    private List<OperationDto> operations;
    private Long infoSociete;
}