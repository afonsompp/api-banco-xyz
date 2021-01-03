package br.com.bancoxyz.error.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg, Object id) {
        super(msg+ id);
    }
}
