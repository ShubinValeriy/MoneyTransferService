package ru.netology.transferservice.model;


import org.apache.commons.lang.StringUtils;

public class TransferCardChecker {
    // Сервис проверки номера карты на корректность
    public boolean checkCardNumber (String cardNumber){
        return cardNumber != null &&
                cardNumber.length() == 16 &&
                StringUtils.isNumeric(cardNumber);
    }

    // Сервис проверки CVV номера карты на корректность
    public boolean checkCardCVV (String cardCVV){
        return cardCVV != null &&
                cardCVV.length() == 3 &&
                StringUtils.isNumeric(cardCVV);
    }
    // Сервис проверки срока действия карты на корректность
    public boolean checkCardValidTill (String cardValidTill){
        String regex = "^\\d{2}/\\d{2}$";
        return cardValidTill.matches(regex);
    }

    // Сервис проверки вылидности суммы перевода
    public boolean checkAmount (Amount amount){
        return amount != null && amount.getValue() >= 0 && amount.getCurrency() != null;
    }

    // Сервис проверки вылидности кода подтверждения
    public boolean checkConfirmCode (String confirmCode){
       return confirmCode != null &&
                confirmCode.length() == 4 &&
                StringUtils.isNumeric(confirmCode);
    }

    // Сервис проверки вылидности ID операции
    public boolean checkOperationID (String operationID){
        return operationID != null &&
                !operationID.isEmpty() &&
                StringUtils.isNumeric(operationID);
    }


}
