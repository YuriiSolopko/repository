package com.js.taxi.service;

import com.js.taxi.dao.OperatorDao;
import com.js.taxi.domain.Operator;
import com.js.taxi.exception.AuthenticationException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jura on 01.04.2015.
 */
@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    public static final Logger log = Logger.getLogger(AuthenticationServiceImpl.class);

    @Autowired(required = true)
    private OperatorDao operatorDao;

    public AuthenticationServiceImpl() {
    }

    public void create(Operator operator) {
        operatorDao.create(operator);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean authenticate(String login, String pass) throws AuthenticationException {
        if (operatorDao.authenticate(login, pass)) {
            return true;
        } else {
            throw new AuthenticationException("Invalid login or password");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExpiredPass(String login) {
        return operatorDao.isExpiredPass(login);
    }

    @Override
    @Transactional(readOnly = true)
    public Operator readOperatorByLogin(String login) {
        return operatorDao.readByLogin(login);
    }

    @Override
    public boolean updateOperatorPassword(String operatorLogin, String oldPassword, String newPassword, String confirmNewPassword) throws AuthenticationException {

        Operator operator = operatorDao.readByLogin(operatorLogin);

        if (!oldPassword.equals(operator.getPassword())) {
            throw new AuthenticationException("Old password is incorrect");
        }
        Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");
        Matcher matcher = pattern.matcher(newPassword);
        if (!matcher.matches() || newPassword.length() < 8) {
            throw new AuthenticationException("Password must be at least 8 symbols and contain small, capital letters and figures");
        }
        if(!newPassword.equals(confirmNewPassword)) {
            throw new AuthenticationException("Password confirm error");
        }

        operator.setPassword(newPassword);
        Calendar instance = Calendar.getInstance();
        Calendar expireDate = new GregorianCalendar(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH) + 3, instance.get(Calendar.DAY_OF_MONTH));
        operator.setPassExpireDate(expireDate);
        try {
            operatorDao.update(operator);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }
}
