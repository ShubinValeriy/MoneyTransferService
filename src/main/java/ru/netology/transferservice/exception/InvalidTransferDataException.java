package ru.netology.transferservice.exception;

public class InvalidTransferDataException extends RuntimeException{
    public InvalidTransferDataException(String msg) {
        super(msg);
    }
}
