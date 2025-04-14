package com.e_stock.BackEnd_E_stocks.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.e_stock.BackEnd_E_stocks.dto.ReglementDto;
import com.e_stock.BackEnd_E_stocks.entity.Reglement;


@Mapper(componentModel = "spring")
public interface ReglementMapper {

        // Mapping Reglement -> ReglementDto
    @Mapping(source = "date", target = "date")
    @Mapping(source = "reglement", target = "reglement")
    @Mapping(source = "montantRestant", target = "montantRestant")
    @Mapping(source = "montantTotal", target = "montantTotal")
    @Mapping(source = "numFact", target = "numFact")
    @Mapping(source = "facture.id", target = "factureId")
    ReglementDto map(Reglement entity);


    // Mapping List<Reglement> -> List<ReglementDto>
    List<ReglementDto> map(List<Reglement> entities);

    // Mapping ReglementDto -> User
    @Mapping(source = "date", target = "date")
    @Mapping(source = "reglement", target = "reglement")
    @Mapping(source = "montantRestant", target = "montantRestant")
    @Mapping(source = "montantTotal", target = "montantTotal")
    @Mapping(source = "numFact", target = "numFact")
    @Mapping(source = "factureId", target = "facture", ignore = true)
    Reglement unMap(ReglementDto dto);

    // Mapping ReglementDto -> Reglement pour mise à jour
    @Mapping(source = "date", target = "date")
    @Mapping(source = "reglement", target = "reglement")
    @Mapping(source = "montantRestant", target = "montantRestant")
    @Mapping(source = "montantTotal", target = "montantTotal")
    @Mapping(source = "numFact", target = "numFact")
    @Mapping(source = "factureId", target = "facture", ignore = true)
    void updateEntityFromDto(@MappingTarget Reglement entity, ReglementDto dto);

}
