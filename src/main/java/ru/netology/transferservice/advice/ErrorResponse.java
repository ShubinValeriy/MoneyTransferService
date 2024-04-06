package ru.netology.transferservice.advice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ErrorResponse {
    private String message;
    private Integer id;

    public ErrorResponse(String errorMSG, Integer errorID) {
        this.message = errorMSG;
        this.id = errorID;
    }

    public ErrorResponse() {}

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errorMSG='" + message + '\'' +
                ", errorID=" + id +
                '}';
    }


}
