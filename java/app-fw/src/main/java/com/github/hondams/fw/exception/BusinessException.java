package com.github.hondams.fw.exception;

import java.io.Serial;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String messageId;

    private final Object[] args;

    public BusinessException(String messageId, Object[] args, String message) {
        super(message);
        this.messageId = messageId;
        this.args = args;
    }

    public BusinessException(String messageId, Object[] args, String message, Throwable cause) {
        super(message, cause);
        this.messageId = messageId;
        this.args = args;
    }
}
