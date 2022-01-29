package com.algaworks.algafood.domain.exception;

//uncheck excp.
public class EntidadeEmUsoException extends RuntimeException{

    public EntidadeEmUsoException(String mensagem){
        super(mensagem);
    }
}
