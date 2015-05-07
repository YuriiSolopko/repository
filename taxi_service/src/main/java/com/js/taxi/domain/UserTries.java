package com.js.taxi.domain;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Jura on 03.04.2015.
 */
public class UserTries {

    private Integer tries;

    public UserTries() {
        tries = 5;
    }

    public UserTries(Integer value) {
        tries = value;
    }

    public Integer getTries() {
        return tries;
    }

    public void setTries(Integer tries) {
        this.tries = tries;
    }
}
