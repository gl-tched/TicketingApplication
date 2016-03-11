package com.kewishfagoe.android.ticketingapplication.database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by Kemble on 3/10/2016.
 */
public class User {
    private int user_id;
    private Date regDatum;
    private String f_naam;
    private String v_naam;
    private String adres;
    private int telefoon;
    private String email;
    private String username;
    private String password;
    private int userLevel;

    public User(String username, String password, int userLevel) {
        this.username = username;
        this.password = password;
        this.userLevel = userLevel;
    }

    public User(int user_id, Date regDatum, String f_naam, String v_naam, String adres, int telefoon, String email, String username, String password, int userLevel) {
        this.user_id = user_id;
        this.regDatum = regDatum;
        this.f_naam = f_naam;
        this.v_naam = v_naam;
        this.adres = adres;
        this.telefoon = telefoon;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userLevel = userLevel;
    }

    public static final String getPasswordHash(final String s) throws NoSuchAlgorithmException {
        final String SHA256 = "SHA-256";
        // Create SHA256 Hash
        MessageDigest digest = java.security.MessageDigest
                .getInstance(SHA256);
        digest.update(s.getBytes());
        byte messageDigest[] = digest.digest();

        // Create Hex String
        StringBuilder hexString = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            String h = Integer.toHexString(0xFF & aMessageDigest);
            while (h.length() < 2)
                h = "0" + h;
            hexString.append(h);
        }

        return hexString.toString();
    }

    public boolean comparePassword(String password) throws NoSuchAlgorithmException {
        return User.getPasswordHash(password).equals(this.password);
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getRegDatum() {
        return regDatum;
    }

    public void setRegDatum(Date regDatum) {
        this.regDatum = regDatum;
    }

    public String getF_naam() {
        return f_naam;
    }

    public void setF_naam(String f_naam) {
        this.f_naam = f_naam;
    }

    public String getV_naam() {
        return v_naam;
    }

    public void setV_naam(String v_naam) {
        this.v_naam = v_naam;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public int getTelefoon() {
        return telefoon;
    }

    public void setTelefoon(int telefoon) {
        this.telefoon = telefoon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }
}
