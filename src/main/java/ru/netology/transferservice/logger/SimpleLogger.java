package ru.netology.transferservice.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;


public class SimpleLogger implements Logger{
    private static SimpleLogger logger;
    private FileWriter logFile;

    private SimpleLogger() {
        try {
            logFile = new FileWriter("logFile.txt", true);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SimpleLogger getInstance() {
        if (logger == null) logger = new SimpleLogger();
        return logger;
    }

    @Override
    public void log(String msg) {
        try {
            logFile.append("[")
                    .append(LocalDateTime.now().toString())
                    .append("] ")
                    .append(msg)
                    .append("\n");
            logFile.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
