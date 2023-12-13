package com.app.e_business_easytask.entity;

public class Benutzer {
    private long id;
    private String vorname;
    private String nachname;
    private String email;
    private String passwort;
    private String telefonnummer;
    private long adresse_id;

    private Adresse adresse;
    private String user_type;

    public Benutzer() {
        // Standardkonstruktor
    }

    public Benutzer(long id, String vorname, String nachname, String email, String passwort, String telefonnummer, long adresse_id, Adresse adresse, String user_type) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.passwort = passwort;
        this.telefonnummer = telefonnummer;
        this.adresse_id = adresse_id;
        this.adresse = adresse;
        this.user_type = user_type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public long getAdresse_id() {
        return adresse_id;
    }

    public void setAdresse_id(long adresse_id) {
        this.adresse_id = adresse_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "id=" + id +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", email='" + email + '\'' +
                ", passwort='" + passwort + '\'' +
                ", telefonnummer='" + telefonnummer + '\'' +
                ", adresse_id=" + adresse_id +
                ", user_type='" + user_type + '\'' +
                ", adresse=" + adresse +
                '}';
    }
}
