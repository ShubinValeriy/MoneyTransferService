package ru.netology.transferservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmOperationInfo {
    private String operationId;
    private String code;

    public ConfirmOperationInfo(String operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public ConfirmOperationInfo() {
    }

    @Override
    public String toString() {
        return "ConfirmOperationInfo{" +
                "operationId='" + operationId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
