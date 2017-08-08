package com.zkn.fullstacktraining.spring.one.servlet;

/**
 * Created by zkn on 2017/8/8.
 */
public class Cookie {
    /**
     * Cookie key
     */
    private String key;
    /**
     * Cookie value
     */
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Cookie() {
    }

    public Cookie(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
