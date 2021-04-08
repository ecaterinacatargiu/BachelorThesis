package com.example.wally.domain;

import javassist.Loader;

import javax.xml.crypto.dsig.SignatureMethod;
import java.io.Serializable;
import java.util.Objects;

public class TransactionKey implements Serializable {

    private SimpleUser simpleUser;


    public TransactionKey(SimpleUser simpleUser) {
        this.simpleUser = simpleUser;
    }

    public TransactionKey() {}

    public SimpleUser getSimpleUser() {
        return simpleUser;
    }

    public void setSimpleUser(SimpleUser simpleUser) {
        this.simpleUser = simpleUser;
    }


    @Override
    public String toString() {
        return "TransactionKey{" +
                "simpleUser=" + simpleUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionKey)) return false;
        TransactionKey that = (TransactionKey) o;
        return Objects.equals(simpleUser, that.simpleUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(simpleUser);
    }
}
