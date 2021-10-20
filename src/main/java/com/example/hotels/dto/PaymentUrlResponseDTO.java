package com.example.hotels.dto;

import java.util.Objects;

public class PaymentUrlResponseDTO {

    private boolean success;
    private String message;
    private String object;

    public PaymentUrlResponseDTO() {
    }

    public PaymentUrlResponseDTO(boolean success, String message, String object) {
        this.success = success;
        this.message = message;
        this.object = object;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentUrlResponseDTO that = (PaymentUrlResponseDTO) o;
        return success == that.success && Objects.equals(message, that.message) && Objects.equals(object, that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, object);
    }

    @Override
    public String toString() {
        return "PaymentUrlResponseDTO{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", object='" + object + '\'' +
                '}';
    }
}
