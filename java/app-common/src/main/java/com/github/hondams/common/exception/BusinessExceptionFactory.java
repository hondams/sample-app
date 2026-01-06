package com.github.hondams.common.exception;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessExceptionFactory {

    private final MessageSource messageSource;

    public BusinessException createBusinessException(String messageId, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = this.messageSource.getMessage(messageId, args, locale);
        return new BusinessException(messageId, args, message);
    }

    public BusinessException createBusinessException(Throwable cause, String messageId,
            Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = this.messageSource.getMessage(messageId, args, locale);
        return new BusinessException(messageId, args, message, cause);
    }
}
