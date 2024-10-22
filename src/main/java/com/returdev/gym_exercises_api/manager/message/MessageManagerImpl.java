package com.returdev.gym_exercises_api.manager.message;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Implementation of the MessageManager interface for retrieving messages
 * from resource bundles, supporting internationalization.
 *
 * <p>
 * This class utilizes Spring's MessageSource to fetch messages based on
 * the provided keys and current locale. It allows for both simple message
 * retrieval and formatted messages with parameters.
 * </p>
 */
@Component
public class MessageManagerImpl implements MessageManager {

    private final MessageSource messageSource;

    /**
     * Constructs a MessageManagerImpl with the specified MessageSource.
     *
     * @param messageSource the MessageSource to be used for fetching messages
     */
    public MessageManagerImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String resourceMsgKey) {
        return getMessageWithParams(resourceMsgKey, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessageWithParams(String resourceMsgKey, Object[] params) {
        return messageSource.getMessage(
                resourceMsgKey,
                params,
                LocaleContextHolder.getLocale()
        );
    }
}


