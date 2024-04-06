package ru.netology.transferservice.logger;

public interface Logger {
    void log(String msg);

    static Logger getInstance() {
        return null;
    }
}
