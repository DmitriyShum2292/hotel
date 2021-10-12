package com.example.hotels.dto;

import java.util.Date;
import java.util.UUID;

public class ResidentReponseDTO {

    private Long cardNumber;

    private String firstName;

    private String lastName;

    private String gender;

    private Date birthDate;

    private Object parentId;

    private String maritalStatus;

    private UUID photoId;

    private Object address;

    private boolean active;

    private String message;

    public ResidentReponseDTO() {
    }

    public ResidentReponseDTO(Long cardNumber, String firstName, String lastName, String gender, Date birthDate,
                              Object parentId, String maritalStatus, UUID photoId, Object address, boolean active,String message) {
        this.cardNumber = cardNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.parentId = parentId;
        this.maritalStatus = maritalStatus;
        this.photoId = photoId;
        this.address = address;
        this.active = active;
        this.message = message;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public UUID getPhotoId() {
        return photoId;
    }

    public void setPhotoId(UUID photoId) {
        this.photoId = photoId;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResidentReponseDTO{" +
                "cardNumber=" + cardNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", parentId=" + parentId +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", photoId=" + photoId +
                ", address=" + address +
                ", active=" + active +
                '}';
    }
}
