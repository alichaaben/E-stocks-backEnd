package com.e_stock.BackEnd_E_stocks.controller;

import com.e_stock.BackEnd_E_stocks.dto.ReglementDto;
import com.e_stock.BackEnd_E_stocks.entity.Facture;
import com.e_stock.BackEnd_E_stocks.entity.Reglement;
import com.e_stock.BackEnd_E_stocks.mapper.ReglementMapper;
import com.e_stock.BackEnd_E_stocks.services.FactureService;
import com.e_stock.BackEnd_E_stocks.services.ReglementService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reglement")
@CrossOrigin("*")
public class ReglementController {

    private final ReglementService reglementService;
    private final ReglementMapper reglementMapper;
    private final FactureService factureService;

    @GetMapping("/{factureId}")
    public ResponseEntity<List<ReglementDto>> getReglementByFacture(@PathVariable Long factureId) {
        Facture facture = factureService.findById(factureId);
        List<Reglement> reglement = reglementService.findByFactureId(facture);
        return ResponseEntity.ok(reglementMapper.map(reglement));
    }

    @PostMapping
    public ResponseEntity<ReglementDto> createOperation(@RequestBody ReglementDto reglementDto) {
        Reglement reglement = reglementMapper.unMap(reglementDto);
        Reglement savedReglement = reglementService.insert(reglement);
        return ResponseEntity.ok(reglementMapper.map(savedReglement));
    }

}
