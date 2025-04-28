package com.e_stock.BackEnd_E_stocks.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import com.e_stock.BackEnd_E_stocks.dto.FactureDto;
import com.e_stock.BackEnd_E_stocks.entity.Facture;
import com.e_stock.BackEnd_E_stocks.entity.TypeFacture;
import java.util.List;

@Mapper(componentModel = "spring", uses = {OperationMapper.class, ReglementMapper.class})
public interface FactureMapper {
    
    @Mapping(source = "type", target = "type", qualifiedByName = "typeFactureToString")
    @Mapping(source = "reglement", target = "reglement")
    @Mapping(source = "societe.id", target = "infoSociete")
    FactureDto map(Facture entity);
    
    @Mapping(source = "type", target = "type", qualifiedByName = "stringToTypeFacture")
    @Mapping(source = "reglement", target = "reglements", ignore = true)
    @Mapping(source = "infoSociete", target = "societe", ignore = true)
    Facture unMap(FactureDto dto);
    
    List<FactureDto> map(List<Facture> entities);
    
    @Named("typeFactureToString")
    default String typeFactureToString(TypeFacture type) {
        return type != null ? type.name() : null;
    }
    
    @Named("stringToTypeFacture")
    default TypeFacture stringToTypeFacture(String type) {
        return type != null ? TypeFacture.valueOf(type) : null;
    }
}