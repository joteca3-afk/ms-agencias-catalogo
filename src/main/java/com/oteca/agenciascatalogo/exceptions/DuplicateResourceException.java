package com.oteca.agenciascatalogo.exceptions;

public class DuplicateResourceException extends RuntimeException {
    private final String campo;

    public DuplicateResourceException(String campo, String message) {
        super(message);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}
