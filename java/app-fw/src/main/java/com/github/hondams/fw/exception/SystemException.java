package com.github.hondams.fw.exception;

import java.io.Serial;
import lombok.Getter;

@Getter
public class SystemException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String errorCode;

    public SystemException(String errorCode) {
        this.errorCode = errorCode;
    }

    public SystemException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public SystemException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public SystemException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
}
