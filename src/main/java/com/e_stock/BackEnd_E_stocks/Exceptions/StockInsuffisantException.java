package com.e_stock.BackEnd_E_stocks.Exceptions;


public class StockInsuffisantException extends RuntimeException {
    public StockInsuffisantException(String message) {
        super(message);
    }
}
