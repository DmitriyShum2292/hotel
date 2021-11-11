package com.example.hotels.dto;

import java.util.Objects;
import java.util.UUID;

public class CompleteRequestDTO {

    private String message;
    private long citizenCardId;
    private UUID transactionId;
    private Boolean success;

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

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompleteRequestDTO that = (CompleteRequestDTO) o;
        return citizenCardId == that.citizenCardId && Objects.equals(message, that.message) && Objects.equals(transactionId, that.transactionId) && Objects.equals(success, that.success);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, citizenCardId, transactionId, success);
    }

    @Override
    public String toString() {
        return "CompleteRequestDTO{" +
                "message='" + message + '\'' +
                ", citizenCardId=" + citizenCardId +
                ", transactionId=" + transactionId +
                ", success=" + success +
                '}';
    }
}
