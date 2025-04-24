package com.e_stock.BackEnd_E_stocks.services.impl;



import org.springframework.stereotype.Service;

import com.e_stock.BackEnd_E_stocks.entity.InfoSociete;
import com.e_stock.BackEnd_E_stocks.repository.InfoSocieteRepository;
import com.e_stock.BackEnd_E_stocks.services.InfoSocieteService;

import lombok.RequiredArgsConstructor;

import com.e_stock.BackEnd_E_stocks.Exceptions.EntityNotFoundException;
import com.e_stock.BackEnd_E_stocks.Exceptions.InvalidEntityException;

@Service
@RequiredArgsConstructor
public class InfoSocieteServiceImpl implements InfoSocieteService {

    private final InfoSocieteRepository infoSocieteRepository;

    @Override
    public InfoSociete findById(Long id) {
        return infoSocieteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("societe not found with ID: " + id));
    }

    @Override
    public InfoSociete insert(InfoSociete entity) {
        if (entity.getNom() == null || entity.getNom().isEmpty()) {
            throw new InvalidEntityException("societe cannot be empty.");
        }
        return infoSocieteRepository.save(entity);
    }



    @Override
    public InfoSociete update(InfoSociete Entity) {
        InfoSociete currentSoc = infoSocieteRepository.findById(Entity.getId())
        .orElseThrow(() -> new IllegalArgumentException("Societe not found"));

        currentSoc.setNom(Entity.getNom());
        currentSoc.setAdresse(Entity.getAdresse());
        currentSoc.setNumFiscal(Entity.getNumFiscal());
        currentSoc.setTelephone(Entity.getTelephone());

        
        return infoSocieteRepository.save(currentSoc);
    }

    @Override
    public void deleteById(Long id) {
        infoSocieteRepository.deleteById(id);
    }

}
