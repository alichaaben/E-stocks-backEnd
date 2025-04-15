package com.e_stock.BackEnd_E_stocks.controller;

import com.e_stock.BackEnd_E_stocks.dto.FactureDto;
import com.e_stock.BackEnd_E_stocks.entity.Facture;
import com.e_stock.BackEnd_E_stocks.mapper.FactureMapper;
import com.e_stock.BackEnd_E_stocks.services.FactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/factures")
@CrossOrigin("*")
public class FactureController {

    private final FactureService factureService;
    private final FactureMapper factureMapper;

    @PostMapping
    public ResponseEntity<FactureDto> createFacture(@RequestBody FactureDto factureDto) {
        Facture facture = factureMapper.unMap(factureDto);
        Facture savedFacture = factureService.insert(facture);
        return ResponseEntity.ok(factureMapper.map(savedFacture));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FactureDto> getFactureById(@PathVariable Long id) {
        Facture facture = factureService.findById(id);
        return ResponseEntity.ok(factureMapper.map(facture));
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<FactureDto> updateFacture(@PathVariable Long id, @RequestBody FactureDto factureDto) {
    //     Facture factureToUpdate = factureMapper.unMap(factureDto);
    //     factureToUpdate.setId(id); // Assure que l'ID est bien défini
    //     Facture updatedFacture = factureService.update(factureToUpdate);
    //     return ResponseEntity.ok(factureMapper.map(updatedFacture));
    // }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        factureService.deleteById(id);
        return ResponseEntity.ok("Invoice with ID " + id + " successfully deleted.");
    }
}