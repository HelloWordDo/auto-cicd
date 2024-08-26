package com.arkmon.autocicd.events;

import org.springframework.context.ApplicationEvent;

/**
 * @author X.J
 * @date 2021/2/4
 */
public class UserRegisterEvent extends ApplicationEvent {

    private String username;

    public UserRegisterEvent(Object source) {
        super(source);
    }

    public UserRegisterEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
