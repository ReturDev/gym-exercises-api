package com.returdev.gym_exercises_api.manager.message;

public interface MessageManager {

    String getMessage(String resourceMsgKey);

    String getMessageWithParams(String resourceMsgKey, Object[] params);

}
