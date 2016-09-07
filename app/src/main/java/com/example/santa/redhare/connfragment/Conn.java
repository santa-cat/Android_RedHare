package com.example.santa.redhare.connfragment;

import android.graphics.drawable.Drawable;

/**
 * Created by santa on 16/8/3.
 */
public class Conn {
    private String name;
    private String du;
    private String company;
    private String station;
    private String relation;

    public Conn() {
        name = du = company = station = relation = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDu() {
        return du;
    }

    public void setDu(String du) {
        this.du = du;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
