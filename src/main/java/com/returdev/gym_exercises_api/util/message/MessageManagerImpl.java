package com.returdev.gym_exercises_api.util.message;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageManagerImpl implements MessageManager{

    private final MessageSource messageSource;

    public MessageManagerImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String resourceMsgKey) {
        return getMessageWithParams(resourceMsgKey, null);
    }

    @Override
    public String getMessageWithParams(String resourceMsgKey, Object[] params) {
        return messageSource.getMessage(
                resourceMsgKey,
                params,
                LocaleContextHolder.getLocale()
        );
    }
}
