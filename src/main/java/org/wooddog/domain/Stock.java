package org.wooddog.domain;

import java.util.Date;

/**
* Created by IntelliJ IDEA.
* User: DENCBR
* Date: 15-08-11
* Time: 18:49
* To change this template use File | Settings | File Templates.
*/
public class Stock {
    private int id;
    private String company;
    private String value;
    private String diff;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "company='" + company + '\'' +
                ", value='" + value + '\'' +
                ", diff='" + diff + '\'' +
                '}';
    }
}
