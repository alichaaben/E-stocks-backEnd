package com.e_stock.BackEnd_E_stocks.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e_stock.BackEnd_E_stocks.dto.ProduitDto;
import com.e_stock.BackEnd_E_stocks.entity.Produit;
import com.e_stock.BackEnd_E_stocks.mapper.ProduitMapper;
import com.e_stock.BackEnd_E_stocks.services.ProduitService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produit")
@CrossOrigin("*")
public class ProduitController {

    private final ProduitService produitService;
    private final ProduitMapper produitMapper;



    //@PreAuthorize("hasAuthority('ROLE_Employe','ROLE_Admin')")
    @GetMapping("/{id}")
    public ResponseEntity<ProduitDto> findById(@PathVariable Long id) {
        Produit entity = produitService.findById(id);
        ProduitDto produitDto = produitMapper.map(entity);
        return ResponseEntity.ok(produitDto);
    }

    @GetMapping()
    public ResponseEntity<List<ProduitDto>> findAll() {
        List<Produit> entities = produitService.findAll();
        List<ProduitDto> produitDtos = produitMapper.map(entities);
        return ResponseEntity.ok(produitDtos);
    }

    @GetMapping("/desg")
    public ResponseEntity<ProduitDto> filtreByDesignation(@RequestParam String designation) {
        Produit entity = produitService.findByDesignation(designation);
        ProduitDto uproduitDto = produitMapper.map(entity);
        return ResponseEntity.ok(uproduitDto);
    }

    @GetMapping("/ref")
    public ResponseEntity<ProduitDto> filtreByReference(@RequestParam String reference) {
        Produit entity = produitService.findByReference(reference);
        ProduitDto uproduitDto = produitMapper.map(entity);
        return ResponseEntity.ok(uproduitDto);
    }

    @PostMapping()
    public ResponseEntity<ProduitDto> insert(@RequestBody ProduitDto dto) {

        Produit produit = produitMapper.unMap(dto);
        Produit entity = produitService.insert(produit);
        ProduitDto pdto = produitMapper.map(entity);


        return ResponseEntity.ok(pdto);
    }

    @PutMapping()
    public ResponseEntity<ProduitDto> update(@RequestBody ProduitDto dto) {

        Produit currentProduit = produitService.findById(dto.getId());
        produitMapper.updateEntityFromDto(currentProduit, dto);
        Produit updatedProduit = produitService.update(currentProduit);
        ProduitDto responseDto = produitMapper.map(updatedProduit);

        return ResponseEntity.ok(responseDto);

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        produitService.deleteById(id);
    }

}
