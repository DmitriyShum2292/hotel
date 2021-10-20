package com.example.hotels.dto;

import java.util.Objects;

public class Payment {

    private String serviceName;
    private String description;
    private double amount;
    private long citizenCardNumber;
    private String redirectUrl;

    public Payment() {
    }

    public Payment(String serviceName, String description, double amount, long citizenCardNumber, String redirectUrl) {
        this.serviceName = serviceName;
        this.description = description;
        this.amount = amount;
        this.citizenCardNumber = citizenCardNumber;
        this.redirectUrl = redirectUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getCitizenCardNumber() {
        return citizenCardNumber;
    }

    public void setCitizenCardNumber(long citizenCardNumber) {
        this.citizenCardNumber = citizenCardNumber;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Double.compare(payment.amount, amount) == 0 && Objects.equals(serviceName, payment.serviceName) && Objects.equals(description, payment.description) && Objects.equals(citizenCardNumber, payment.citizenCardNumber) && Objects.equals(redirectUrl, payment.redirectUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName, description, amount, citizenCardNumber, redirectUrl);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "serviceName='" + serviceName + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", citizenCardNumber='" + citizenCardNumber + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                '}';
    }
}
