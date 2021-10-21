package com.example.hotels.dto;

import java.util.Objects;

public class CompleteRequestDTO {

    private String message;
    private long citizenCardId;
    private String transactionId;
    private long amount;
    private String success;

    public CompleteRequestDTO() {
    }

    public CompleteRequestDTO(String message, long citizenCardId, String transactionId, long amount, String success) {
        this.message = message;
        this.citizenCardId = citizenCardId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCitizenCardId() {
        return citizenCardId;
    }

    public void setCitizenCardId(long citizenCardId) {
        this.citizenCardId = citizenCardId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompleteRequestDTO that = (CompleteRequestDTO) o;
        return citizenCardId == that.citizenCardId && Double.compare(that.amount, amount) == 0 && Objects.equals(message, that.message) && Objects.equals(transactionId, that.transactionId) && Objects.equals(success, that.success);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, citizenCardId, transactionId, amount, success);
    }

    @Override
    public String toString() {
        return "CompleteRequestDTO{" +
                "message='" + message + '\'' +
                ", citizenCardId=" + citizenCardId +
                ", transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", success='" + success + '\'' +
                '}';
    }
}
