package com.app.e_business_easytask.entity;

public class Adresse {
    private long id;
    private String ort;
    private String plz;
    private String strasse;
    private String nr;

    public Adresse() {
        // Standardkonstruktor
    }
    public Adresse(long id, String ort, String plz, String strasse, String nr) {
        this.id = id;
        this.ort = ort;
        this.plz = plz;
        this.strasse = strasse;
        this.nr = nr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "id=" + id +
                ", ort='" + ort + '\'' +
                ", plz='" + plz + '\'' +
                ", strasse='" + strasse + '\'' +
                ", nr='" + nr + '\'' +
                '}';
    }
}