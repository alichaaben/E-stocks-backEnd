package com.e_stock.BackEnd_E_stocks.Exceptions;

public class ProduitInexistantException extends RuntimeException {
    public ProduitInexistantException(String message) {
        super(message);
    }

}
