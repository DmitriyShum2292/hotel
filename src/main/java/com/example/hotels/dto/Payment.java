package com.example.hotels.dto;

import java.util.Objects;

public class Payment {


    private double amount;
    private long citizenCardNumber;
    private String description;
    private String serviceName;

    public Payment() {
    }

    public Payment(double amount, long citizenCardNumber, String description, String serviceName) {
        this.amount = amount;
        this.citizenCardNumber = citizenCardNumber;
        this.description = description;
        this.serviceName = serviceName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Double.compare(payment.amount, amount) == 0 && citizenCardNumber == payment.citizenCardNumber && description.equals(payment.description) && serviceName.equals(payment.serviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, citizenCardNumber, description, serviceName);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", citizenCardNumber=" + citizenCardNumber +
                ", description='" + description + '\'' +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}
