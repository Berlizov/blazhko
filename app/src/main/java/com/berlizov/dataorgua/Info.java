package com.berlizov.dataorgua;

import java.io.Serializable;

/**
 * Created by 350z6_000 on 17.10.2015.
 * типо структура про набор данных
 */
public class Info implements Serializable {
    public String name;
    public String date;
    public String company;

    public String ID;

    public Info() {
    }

    public Info( String ID, String name, String company, String date) {
        this.ID = ID;
        this.name = name;
        this.date = date;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
