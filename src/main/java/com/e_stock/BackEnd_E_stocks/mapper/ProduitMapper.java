package com.e_stock.BackEnd_E_stocks.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.*;

import com.e_stock.BackEnd_E_stocks.dto.ProduitDto;
import com.e_stock.BackEnd_E_stocks.entity.Produit;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    // Mapping Produit -> ProduitDto
    @Mapping(source = "reference", target = "reference")
    @Mapping(source = "designation", target = "designation")
    @Mapping(source = "quantite", target = "quantite")
    @Mapping(source = "prixUnitaire", target = "prixUnitaire")
    @Mapping(source = "total", target = "total")
    ProduitDto map(Produit entity);

    // Mapping List<Produit> -> List<ProduitDto>
    List<ProduitDto> map(List<Produit> entities);

    // Mapping ProduitDto -> User
    @Mapping(source = "reference", target = "reference")
    @Mapping(source = "designation", target = "designation")
    @Mapping(source = "quantite", target = "quantite")
    @Mapping(source = "prixUnitaire", target = "prixUnitaire")
    @Mapping(source = "total", target = "total")
    @Mapping(target = "operations", ignore = true)
    Produit unMap(ProduitDto dto);

    // Mapping ProduitDto -> Produit pour mise à jour
    @Mapping(source = "reference", target = "reference")
    @Mapping(source = "designation", target = "designation")
    @Mapping(source = "quantite", target = "quantite")
    @Mapping(source = "prixUnitaire", target = "prixUnitaire")
    @Mapping(source = "total", target = "total")
    @Mapping(target = "operations", ignore = true)
    void updateEntityFromDto(@MappingTarget Produit entity, ProduitDto dto);

}