package com.example.hotels.model;

import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sys_user")
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @NotNull(message = "Login can't be empty")
    @Size(min = 6,max = 12,message = "login must be 6 - 12 characters")
    private String nickName;
    private String authority;
    private boolean active;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Order> orders;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Hotel hotel;
    @NotNull(message = "Password name should not be empty")
    @Size(min = 2,max = 20,message = "password must be 2 - 20 characters")
    private String password;
    private long userId;


    public User() {
    }

    public User(String nickName, String authority, boolean active, List<Order> orders,
                Hotel hotel, String password, long userId) {
        this.nickName = nickName;
        this.authority = authority;
        this.active = active;
        this.orders = orders;
        this.hotel = hotel;
        this.password = password;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && active == user.active && userId == user.userId && Objects.equals(nickName, user.nickName) && Objects.equals(authority, user.authority) && Objects.equals(orders, user.orders) && Objects.equals(hotel, user.hotel) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickName, authority, active, orders, hotel, password, userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + nickName + '\'' +
                ", authority='" + authority + '\'' +
                ", active=" + active +
                ", orders=" + orders +
                ", hotel=" + hotel +
                ", password='" + password + '\'' +
                ", userId=" + userId +
                '}';
    }
}
