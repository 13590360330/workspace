package com.sparksql;

import java.io.Serializable;

public class HappyPerson implements Serializable {
    private String name;
    private String favouriteBeverage;

    public HappyPerson() {
    }

    public HappyPerson(String n, String b) {
        this.name = n;
        this.favouriteBeverage = b;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavouriteBeverage() {
        return favouriteBeverage;
    }

    public void setFavouriteBeverage(String favouriteBeverage) {
        this.favouriteBeverage = favouriteBeverage;
    }
}
