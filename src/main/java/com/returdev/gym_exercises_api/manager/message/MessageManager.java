package com.returdev.gym_exercises_api.manager.message;

/**
 * Interface for managing messages and localization in the application.
 *
 * <p>
 * This interface provides methods for retrieving messages from resource bundles,
 * allowing for internationalization and parameterized messages.
 * </p>
 */
public interface MessageManager {

    /**
     * Retrieves a message associated with the specified resource key.
     *
     * @param resourceMsgKey the key of the message to retrieve
     * @return the message corresponding to the provided key
     */
    String getMessage(String resourceMsgKey);

    /**
     * Retrieves a message associated with the specified resource key and formats it
     * with the provided parameters.
     *
     * @param resourceMsgKey the key of the message to retrieve
     * @param params         an array of parameters to format the message
     * @return the formatted message corresponding to the provided key
     */
    String getMessageWithParams(String resourceMsgKey, Object[] params);
}

