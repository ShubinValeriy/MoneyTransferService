package ru.netology.transferservice.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.transferservice.model.ConfirmOperationInfo;
import ru.netology.transferservice.model.TransferResponse;
import ru.netology.transferservice.model.TransferUnit;
import ru.netology.transferservice.repository.TransferUnitsRepository;
import ru.netology.transferservice.service.MoneyTransferService;


@RestController
@CrossOrigin(origins = "https://serp-ya.github.io/card-transfer/", allowedHeaders = "*")
public class MoneyTransferController {
    private final MoneyTransferService transferService;

    public MoneyTransferController(MoneyTransferService transferService) {
        this.transferService = transferService;
        TransferUnitsRepository repository = new TransferUnitsRepository();
    }

    @PostMapping("transfer")
    public TransferResponse makeTransfer(@RequestBody TransferUnit transferUnit) {
        System.out.println("ok ");
        return transferService.makeTransfer(transferUnit);
    }
    @PostMapping("confirmOperation")
    public TransferResponse confirmTransfer(@RequestBody ConfirmOperationInfo info) {
        return transferService.confirmTransfer(info);
    }
}
