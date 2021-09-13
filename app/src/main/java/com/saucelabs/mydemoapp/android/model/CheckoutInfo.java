package com.saucelabs.mydemoapp.android.model;

import java.io.Serializable;

public class CheckoutInfo implements Serializable {
    String name;
    String address1;
    String address2;
    String billingAddress;
    String city;
    String state;
    String zip;
    String country;
    String cardHolderName;
    String cardNumber;
    String expirationDate;
    String securityCode;
    boolean isSameShipping;

    public CheckoutInfo() {

    }

    public CheckoutInfo(String name, String address1, String address2, String billingAddress, String city, String state, String zip, String country, String cardHolderName, String cardNumber, String expirationDate, String securityCode) {
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.billingAddress = billingAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isSameShipping() {
        return isSameShipping;
    }

    public void setSameShipping(boolean sameShipping) {
        isSameShipping = sameShipping;
    }
}
