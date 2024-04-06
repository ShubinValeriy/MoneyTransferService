package ru.netology.transferservice.service;

import org.springframework.stereotype.Service;
import ru.netology.transferservice.exception.ErrorTransferConfirmException;
import ru.netology.transferservice.exception.InvalidTransferDataException;
import ru.netology.transferservice.logger.Logger;
import ru.netology.transferservice.logger.SimpleLogger;
import ru.netology.transferservice.model.ConfirmOperationInfo;
import ru.netology.transferservice.model.TransferCardChecker;
import ru.netology.transferservice.model.TransferResponse;
import ru.netology.transferservice.model.TransferUnit;
import ru.netology.transferservice.model.generator.ConfirmCodeGenerator;
import ru.netology.transferservice.model.generator.SimpleConfirmCodeGenerator;
import ru.netology.transferservice.repository.TransferStatus;
import ru.netology.transferservice.repository.TransferUnitsRepository;


import java.time.LocalDate;
import java.time.LocalTime;


@Service
public class MoneyTransferService {
    private final TransferUnitsRepository repository;
    private final Logger logger;
    private final ConfirmCodeGenerator codeGenerator;

    public MoneyTransferService(TransferUnitsRepository repository) {
        this.repository = repository;
        this.logger = SimpleLogger.getInstance();
        this.codeGenerator = new SimpleConfirmCodeGenerator();
    }

    public TransferResponse makeTransfer(TransferUnit transferUnit) {
        StringBuilder errorMSG = new StringBuilder();
        TransferCardChecker cardChecker = new TransferCardChecker();
        boolean check = true;
        //проверка номера карты с которой отправляем средства
        if (
             !cardChecker.checkCardNumber(transferUnit.getCardFromNumber())
        ) {
            errorMSG.append("/Incorrect sender's card number");
            check = false;
        }
        //проверка CVV карты с которой отправляем средства
        if (
                !cardChecker.checkCardCVV(transferUnit.getCardFromCVV())
        ) {
            errorMSG.append("/Incorrect sender's card CVV");
            check = false;
        }
        //проверка срока действия карты с которой отправляем средства
        if (
                !cardChecker.checkCardValidTill(transferUnit.getCardFromValidTill())
        ) {
            errorMSG.append("/Incorrect sender's card Data Valid Till");
            check = false;
        }
        //проверка номера карты куда отправляем средства
        if (
                !cardChecker.checkCardNumber(transferUnit.getCardToNumber())
        ) {
            errorMSG.append("/Incorrect recipient card number");
            check = false;
        }
        //проверка валидности отправляемых средств
        if (!cardChecker.checkAmount(transferUnit.getAmount())){
            errorMSG.append("/Incorrect transfer Amount");
            check = false;
        }
        if (!check) {
            throw new InvalidTransferDataException("Invalid transferUnit :" + errorMSG);
        }
        transferUnit.setDate(LocalDate.now().toString());
        transferUnit.setTime(LocalTime.now().toString());
        String id = repository.addTransferUnit(transferUnit);
        logger.log("Add Transfer Unit: " + transferUnit);
        return new TransferResponse(id);
    }


    public TransferResponse confirmTransfer(ConfirmOperationInfo confirmOperationInfo){
        StringBuilder errorMSG = new StringBuilder();
        TransferCardChecker cardChecker = new TransferCardChecker();
        boolean check = true;
        //проверка валидности Кода подтверждения
        if (!cardChecker.checkConfirmCode(confirmOperationInfo.getCode())){
            errorMSG.append("/Incorrect confirm CODE");
            check = false;
        }
        //проверка валидности ID операции
        if (!cardChecker.checkOperationID(confirmOperationInfo.getOperationId())){
            errorMSG.append("/Incorrect operation ID");
            check = false;
        }
        if (!check) {
            throw new InvalidTransferDataException("Invalid confirmOperationInfo :" + errorMSG);
        }

        // Проверка что есть хоть одна запись в репозитории
        if (repository.isEmpty()){
            throw new ErrorTransferConfirmException(
                    "Error base transfer units is empty: " + confirmOperationInfo
            );
        }

        if(confirmOperationInfo.getCode().equals(codeGenerator.getConfirmCode(confirmOperationInfo.getOperationId()))){
            if (repository.getTransferStatus(confirmOperationInfo.getOperationId()).equals(TransferStatus.CONFIRM)){
                throw new ErrorTransferConfirmException(
                        "Error to confirm the operation! This transferUnit was confirm earlier: " + confirmOperationInfo
                );
            }
            TransferUnit transferUnit = repository.setConfirmTransferStatus(confirmOperationInfo.getOperationId());
            if (transferUnit == null){
                throw new ErrorTransferConfirmException(
                        "Error to confirm the operation! Unknown transferUnit for confirm " +
                        "operation info: " + confirmOperationInfo
                );
            }
            logger.log("Confirm Transfer Unit: " + transferUnit);
        }else {
            TransferUnit transferUnit = repository.setErrorTransferStatus(confirmOperationInfo.getOperationId());
            if (transferUnit == null){
                throw new ErrorTransferConfirmException(
                        "Error to set ERROR status the operation! Unknown transferUnit for confirm " +
                                "operation info: " + confirmOperationInfo
                );
            }
            logger.log("Error Transfer Unit: " + transferUnit);
        }
        return new TransferResponse(confirmOperationInfo.getOperationId());
    }
}
