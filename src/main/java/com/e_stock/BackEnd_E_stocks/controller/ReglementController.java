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
@RequestMapping("/reglements")
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

    @PostMapping("/add")
    public ResponseEntity<ReglementDto> ajouterReglement(@RequestBody ReglementDto dto) {
        Reglement reglement = reglementMapper.unMap(dto);
        Facture facture = factureService.findById(dto.getFactureId());
        reglement.setFacture(facture);
    
        Reglement saved = reglementService.insertByIdFact(reglement);
        return ResponseEntity.ok(reglementMapper.map(saved));
    }
    



}
