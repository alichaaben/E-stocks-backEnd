package com.e_stock.BackEnd_E_stocks.controller;

import com.e_stock.BackEnd_E_stocks.dto.OperationDto;
import com.e_stock.BackEnd_E_stocks.entity.Operation;
import com.e_stock.BackEnd_E_stocks.entity.Produit;
import com.e_stock.BackEnd_E_stocks.mapper.OperationMapper;
import com.e_stock.BackEnd_E_stocks.services.OperationService;
import com.e_stock.BackEnd_E_stocks.services.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/operations")
@CrossOrigin("*")
public class OperationController {

    private final OperationService operationService;
    private final OperationMapper operationMapper;
    private final ProduitService produitService;

    @GetMapping("/{produitId}")
    public ResponseEntity<List<OperationDto>> getOperationsByProduit(@PathVariable Long produitId) {
        Produit produit = produitService.findById(produitId);
        List<Operation> operations = operationService.findByProduit(produit);
        return ResponseEntity.ok(operationMapper.map(operations));
    }

    @PostMapping
    public ResponseEntity<OperationDto> createOperation(@RequestBody OperationDto operationDto) {
        Operation operation = operationMapper.unMap(operationDto);
        Operation savedOperation = operationService.insert(operation);
        return ResponseEntity.ok(operationMapper.map(savedOperation));
    }
}
