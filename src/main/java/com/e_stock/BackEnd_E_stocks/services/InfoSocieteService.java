package com.e_stock.BackEnd_E_stocks.services;

import com.e_stock.BackEnd_E_stocks.entity.InfoSociete;

public interface InfoSocieteService {
    
    InfoSociete findById(Long id);

    InfoSociete insert(InfoSociete infoSociete);

    InfoSociete update(InfoSociete infoSociete);

    void deleteById(Long id);

}
