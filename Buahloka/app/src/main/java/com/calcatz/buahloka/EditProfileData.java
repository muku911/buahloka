package com.calcatz.buahloka;

/**
 * Created by HusniMuni on 26/08/2017.
 */

public class EditProfileData {
    private String name, address, phone, saldo;

    public EditProfileData() {
    }

    public EditProfileData(String name, String address, String phone, String saldo) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.saldo = saldo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
