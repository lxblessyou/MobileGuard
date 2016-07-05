package com.home.user.mobileguard.bean;

/**
 * Created by user on 16-7-4.
 */
public class ContactBean {
    private String name;
    private String safeNum;

    public ContactBean() {
    }

    public ContactBean(String name, String safeNum) {
        this.name = name;
        this.safeNum = safeNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSafeNum() {
        return safeNum;
    }

    public void setSafeNum(String safeNum) {
        this.safeNum = safeNum;
    }

    @Override
    public String toString() {
        return "ContactBean{" +
                "name='" + name + '\'' +
                ", safeNum='" + safeNum + '\'' +
                '}';
    }
}
