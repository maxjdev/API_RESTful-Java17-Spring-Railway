package br.com.service.exception;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public BusinessException(String m) {super(m);}
}
