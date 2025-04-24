package com.e_stock.BackEnd_E_stocks.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.*;

import com.e_stock.BackEnd_E_stocks.dto.InfoSocieteDto;
import com.e_stock.BackEnd_E_stocks.entity.InfoSociete;

@Mapper(componentModel = "spring")
public interface InfoSocieteMapper {


    // Mapping infoSoc -> infoSocDto
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "adresse", target = "adresse")
    @Mapping(source = "numFiscal", target = "numFiscal")
    @Mapping(source = "telephone", target = "telephone")
    InfoSocieteDto map(InfoSociete entity);

    // Mapping List<infoSoc> -> List<infoSocDto>
    List<InfoSocieteDto> map(List<InfoSociete> entities);

    // Mapping ProduitDto -> User
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "adresse", target = "adresse")
    @Mapping(source = "numFiscal", target = "numFiscal")
    @Mapping(source = "telephone", target = "telephone")
    InfoSociete unMap(InfoSocieteDto dto);

    // Mapping infoSocDto -> infoSoc pour mise à jour
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "adresse", target = "adresse")
    @Mapping(source = "numFiscal", target = "numFiscal")
    @Mapping(source = "telephone", target = "telephone")
    void updateEntityFromDto(@MappingTarget InfoSociete entity, InfoSocieteDto dto);



}
