package com.javabasic.domain;

import java.io.Serializable;

public class link implements Serializable{
    public String name;
    public String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "link{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
