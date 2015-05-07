package com.js.taxi.service;

import com.js.taxi.exception.AuthorizationException;

/**
 * Created by Jura on 02.04.2015.
 */
public interface AuthorizationService {

    public boolean register(String login, String id, String pass) throws AuthorizationException;
}
