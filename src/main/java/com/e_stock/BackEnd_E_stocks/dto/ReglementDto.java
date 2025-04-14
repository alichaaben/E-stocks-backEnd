package com.e_stock.BackEnd_E_stocks.dto;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReglementDto {
    private Long id;
    private LocalDate date;
    private double reglement;
    private double montantRestant;
    private double montantTotal;
    private String numFact;
    private Long factureId;
}

