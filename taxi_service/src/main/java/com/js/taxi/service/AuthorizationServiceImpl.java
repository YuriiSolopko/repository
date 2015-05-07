package com.js.taxi.service;

import com.js.taxi.dao.OperatorDao;
import com.js.taxi.domain.Operator;
import com.js.taxi.exception.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jura on 02.04.2015.
 */
@Service
@Transactional
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private OperatorDao operatorDao;

    @Override
    public boolean register(String login, String id, String pass) throws AuthorizationException {
        if (operatorDao.readByLogin(login) != null) {
            throw new AuthorizationException("Login is already used");
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9-_\\.]+");
        Matcher matcher = pattern.matcher(login);
        if (!matcher.matches() || login.length() < 4) {
            throw new AuthorizationException("Incorrect login, at least 4 symbols required");
        }
        if (id.length() != 10) {
            throw new AuthorizationException("ID must contain 10 figures");
        }
        pattern = Pattern.compile("[0-9]+");
        matcher = pattern.matcher(id);
        if (!matcher.matches()) {
            throw new AuthorizationException("ID must contain only figures");
        }
        pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");
        matcher = pattern.matcher(pass);
        if (!matcher.matches() || pass.length() < 8) {
            throw new AuthorizationException("Password must be at least 8 symbols and contain small, capital letters and figures");
        }
        operatorDao.create(new Operator(login, pass, id));
        return true;
    }
}
