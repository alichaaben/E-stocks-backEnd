package com.e_stock.BackEnd_E_stocks.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoSocieteDto {
    private Long id;
    private String nom;
    private String adresse;
    private String numFiscal;
    private String telephone;
}
