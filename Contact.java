package com.example.model;

import androidx.annotation.NonNull;

public class Contact {
    private int ma;
    private String ten;
    private String phone;

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Contact() {
    }

    public Contact(int ma, String ten, String phone) {
        this.ma = ma;
        this.ten = ten;
        this.phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        return this.ma +" - "+ this.ten + " - " + this.phone;
    }
}
