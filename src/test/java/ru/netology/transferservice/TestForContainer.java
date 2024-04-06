package ru.netology.transferservice;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import ru.netology.transferservice.advice.ErrorResponse;
import ru.netology.transferservice.model.Amount;
import ru.netology.transferservice.model.Currency;
import ru.netology.transferservice.model.TransferResponse;
import ru.netology.transferservice.model.TransferUnit;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestForContainer {
    private static final GenericContainer<?> transferApp =
            new GenericContainer<>("transferservice-transfer_app:latest").
                    withExposedPorts(5500);

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeAll
    public static void setUp() {
        transferApp.start();
    }

    @AfterAll
    public static void setDown() {
        transferApp.stop();
    }

    @Test
    void appMakeTransferTest(){
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
        TransferResponse response = restTemplate.postForObject(
                "http://localhost:" + transferApp.getMappedPort(5500) + "/transfer",
                transferUnit,
                TransferResponse.class
        );
        Assertions.assertEquals(response.getOperationId(), "1");
    }

    @Test
    void appIncorrectTransferUnitTest() {
        TransferUnit transferUnit = new TransferUnit(
                "",
                "12/27",
                "123",
                "9876543217654321",
                new Amount(
                        5000,
                        Currency.RUR
                )
        );
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                "http://localhost:" + transferApp.getMappedPort(5500) + "/transfer",
                transferUnit,
                ErrorResponse.class
        );
        String errorMSG = "BAD_REQUEST : ru.netology.transferservice.exception.InvalidTransferDataException:" +
                " Invalid transferUnit :/Incorrect sender's card number";
        Assertions.assertEquals(Objects.requireNonNull(response.getBody()).getMessage(), errorMSG);
    }



}
