package com.js.taxi.service;

import com.js.taxi.domain.Operator;
import com.js.taxi.exception.AuthenticationException;

/**
 * Created by Jura on 01.04.2015.
 */
public interface AuthenticationService {

    public boolean authenticate(String login, String pass) throws AuthenticationException;
    public boolean isExpiredPass(String login);
    public Operator readOperatorByLogin(String login);
    public boolean updateOperatorPassword(String operatorLogin, String oldPassword, String newPassword, String confirmNewPassword) throws AuthenticationException;
}
