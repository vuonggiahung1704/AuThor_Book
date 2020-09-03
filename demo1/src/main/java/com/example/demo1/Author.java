package com.example.demo1;

import java.io.Serializable;

public class Author implements Serializable {
    private String maTG;
    private String tenTG;
    private String email;
    private String diaChi;

    public Author(String maTG, String tenTG, String email, String diaChi) {
        this.maTG = maTG;
        this.tenTG = tenTG;
        this.email = email;
        this.diaChi = diaChi;
    }

    public Author(String maTG) {
        this.maTG = maTG;
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getTenTG() {
        return tenTG;
    }

    public void setTenTG(String tenTG) {
        this.tenTG = tenTG;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
