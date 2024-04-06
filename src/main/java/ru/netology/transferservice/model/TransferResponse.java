package ru.netology.transferservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferResponse {
    private String operationId;

    public TransferResponse(String operationId) {
        this.operationId = operationId;
    }

    public TransferResponse() {}
}
