package com.e_stock.BackEnd_E_stocks.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import com.e_stock.BackEnd_E_stocks.dto.OperationDto;
import com.e_stock.BackEnd_E_stocks.entity.Operation;
import com.e_stock.BackEnd_E_stocks.entity.TypeOperation;
import java.util.List;

@Mapper(componentModel = "spring", uses = { ProduitMapper.class })
public interface OperationMapper {

    @Mapping(source = "type", target = "type", qualifiedByName = "operationTypeToString")
    @Mapping(source = "facture.id", target = "factureId")
    OperationDto map(Operation entity);

    @Mapping(source = "type", target = "type", qualifiedByName = "stringToOperationType")
    @Mapping(source = "factureId", target = "facture", ignore = true)
    Operation unMap(OperationDto dto);

    List<OperationDto> map(List<Operation> entities);

    @Named("operationTypeToString")
    default String operationTypeToString(TypeOperation type) {
        return type != null ? type.name() : null;
    }

    @Named("stringToOperationType")
    default TypeOperation  stringToOperationType(String type) {
        return type != null ? TypeOperation.valueOf(type) : null;
    }
}
