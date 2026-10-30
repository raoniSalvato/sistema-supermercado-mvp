package br.ufes.dcomp.supermercado.exception;

public class CupomInvalidoException extends RuntimeException{
    public CupomInvalidoException(String mensagem){
        super(mensagem);
    }
}
