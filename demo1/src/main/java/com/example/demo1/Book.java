package com.example.demo1;

import java.io.Serializable;

public class Book implements Serializable {
    private String maS;
    private String tenS;
    private Author author;
    private double donGia;

    public Book(String maS, String tenS, Author author, double donGia) {
        this.maS = maS;
        this.tenS = tenS;
        this.author = author;
        this.donGia = donGia;
    }

    public String getMaS() {
        return maS;
    }

    public void setMaS(String maS) {
        this.maS = maS;
    }

    public String getTenS() {
        return tenS;
    }

    public void setTenS(String tenS) {
        this.tenS = tenS;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
}
