package com.example.hotels.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Payment {

    private long id;
    private double amount;
    private Date date;
    private long user_id;
    private boolean paid;
    private String description;


    public Payment() {
    }

    public Payment(double amount, Date date, long user_id,
                   boolean paid, String description) {
        this.amount = amount;
        this.date = date;
        this.user_id = user_id;
        this.paid = paid;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id && Double.compare(payment.amount, amount) == 0 && user_id == payment.user_id && paid == payment.paid && Objects.equals(date, payment.date) && Objects.equals(description, payment.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, date, user_id, paid, description);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", user_id=" + user_id +
                ", paid=" + paid +
                ", description='" + description + '\'' +
                '}';
    }
}
