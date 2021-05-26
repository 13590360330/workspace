package com.javabasic.domain;

import java.io.Serializable;

public class Person implements Serializable {

    public String name;
    public String url;
    public String page;
    public String isNonProfit;
    public address address;
    public link[] links;

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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getIsNonProfit() {
        return isNonProfit;
    }

    public void setIsNonProfit(String isNonProfit) {
        this.isNonProfit = isNonProfit;
    }

    public com.javabasic.domain.address getAddress() {
        return address;
    }

    public void setAddress(com.javabasic.domain.address address) {
        this.address = address;
    }

    public link[] getLinks() {
        return links;
    }

    public void setLinks(link[] links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", page='" + page + '\'' +
                ", isNonProfit='" + isNonProfit + '\'' +
                ", address='" + address + '\'' +
                ", links='" + links + '\'' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
