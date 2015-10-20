package com.berlizov.dataorgua;

import java.io.Serializable;

/**
 * Created by 350z6_000 on 17.10.2015.
 * типо структура про набор данных
 */
public class Info implements Serializable {
    // имя, Детская поликриник
    public String name;
    //дата создания набора
    public String date;
    // компания которая загрузила инфу
    public String company;

    public String ID;

    public Info( String ID, String name, String company, String date) {
        this.ID = ID;
        this.name = name;
        this.date = date;
        this.company = company;
    }

}
