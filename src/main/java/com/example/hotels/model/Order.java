package com.example.hotels.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "hotel_order")
@DynamicUpdate
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Hotel hotel;
    @OneToOne
    private User user;
    private LocalDateTime creationDate;
    private LocalDateTime bookingDate;
    private int period;
    private long totalPrice;
    private boolean paid;

    public Order() {
    }

    public Order(Hotel hotel,  LocalDateTime bookingDate, int period) {
        this.hotel = hotel;
        this.creationDate = LocalDateTime.now();
        this.bookingDate = bookingDate;
        this.period = period;
        this.totalPrice = (long) (hotel.getDailyCost()*period);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && period == order.period && totalPrice == order.totalPrice && paid == order.paid && Objects.equals(hotel, order.hotel) && Objects.equals(user, order.user) && Objects.equals(creationDate, order.creationDate) && Objects.equals(bookingDate, order.bookingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hotel, user, creationDate, bookingDate, period, totalPrice, paid);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", hotel=" + hotel +
                ", user=" + user +
                ", creationDate=" + creationDate +
                ", bookingDate=" + bookingDate +
                ", period=" + period +
                ", totalPrice=" + totalPrice +
                ", paid=" + paid +
                '}';
    }
}
