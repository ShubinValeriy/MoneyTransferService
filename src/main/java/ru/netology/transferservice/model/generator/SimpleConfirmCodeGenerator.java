package ru.netology.transferservice.model.generator;

public class SimpleConfirmCodeGenerator implements ConfirmCodeGenerator {
    @Override
    public String getConfirmCode(String id) {
        return "1234";
    }
}
