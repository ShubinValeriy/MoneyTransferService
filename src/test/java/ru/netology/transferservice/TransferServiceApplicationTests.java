package ru.netology.transferservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.netology.transferservice.exception.InvalidTransferDataException;
import ru.netology.transferservice.model.*;
import ru.netology.transferservice.repository.TransferStatus;
import ru.netology.transferservice.repository.TransferUnitsRepository;
import ru.netology.transferservice.service.MoneyTransferService;

@SpringBootTest
class TransferServiceApplicationTests {
    static MoneyTransferService service;
    static TransferUnitsRepository repository;

    @BeforeAll
    static void init() {
        repository = Mockito.spy(TransferUnitsRepository.class);
        ;
        service = new MoneyTransferService(repository);
    }

    @Test
    void makeTransferTest() {
        TransferUnit transferUnit = new TransferUnit(
                "1234567891234567",
                "12/27",
                "123",
                "9876543217654321",
                new Amount(
                        5000,
                        Currency.RUR
                )
        );
        TransferResponse request = service.makeTransfer(transferUnit);
        Assertions.assertEquals(request.getOperationId(), "1");
    }

    @Test
    void makeTransferIncorrectData(){
        boolean exceptionThrown = false;
        TransferUnit incorrectTransferUnit = new TransferUnit(
                "0",
                "0",
                "0",
                "0",
                new Amount(
                        5000,
                        Currency.RUR
                )
        );

        try {
            service.makeTransfer(incorrectTransferUnit);;
        } catch (InvalidTransferDataException e) {
            exceptionThrown = true;
        }

        Assertions.assertTrue(exceptionThrown);
    }

    @Test
    void confirmTransferTest() {
        ConfirmOperationInfo info = Mockito.spy(ConfirmOperationInfo.class);
        Mockito.when(info.getOperationId()).thenReturn("1");
        Mockito.when(info.getCode()).thenReturn("1234");
        Mockito.when(repository.isEmpty()).thenReturn(false);
        Mockito.when(repository.getTransferStatus(info.getOperationId())).thenReturn(TransferStatus.LOAD);
        Mockito.when(repository.setConfirmTransferStatus(info.getOperationId())).
                thenReturn(
                        new TransferUnit(
                                "1234567891234567",
                                "12/27",
                                "123",
                                "9876543217654321",
                                new Amount(
                                        5000,
                                        Currency.RUR
                                )
                        )
                );
        TransferResponse response = service.confirmTransfer(info);
        Assertions.assertEquals(response.getOperationId(), "1");
    }

    @Test
    void confirmTransferIncorrectData(){
        boolean exceptionThrown = false;
        ConfirmOperationInfo info = Mockito.spy(ConfirmOperationInfo.class);
        Mockito.when(info.getOperationId()).thenReturn("");
        Mockito.when(info.getCode()).thenReturn("1234567");
        try {
            service.confirmTransfer(info);;
        } catch (InvalidTransferDataException e) {
            exceptionThrown = true;
        }

        Assertions.assertTrue(exceptionThrown);


    }

}
