package org.yczbj.ycrefreshview.slide;

/**
 * Created by PC on 2017/12/14.
 * 作者：PC
 */

public class YCBean {


    private String oid;
    private int id;
    private String status;
    private String logo;
    private String name;
    private String round;
    private String amt;
    private String currency;
    private String fname;

    public YCBean(String oid, int id, String status, String logo, String name, String round, String amt, String currency, String fname) {
        this.oid = oid;
        this.id = id;
        this.status = status;
        this.logo = logo;
        this.name = name;
        this.round = round;
        this.amt = amt;
        this.currency = currency;
        this.fname = fname;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
}
