package ru.netology.transferservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class TransferUnit {
    private String cardFromNumber;
    private String cardFromValidTill;
    private String cardFromCVV;
    private String cardToNumber;
    private Amount amount;
    private String date;
    private String time;

    public TransferUnit(
            String cardFromNumber,
            String cardFromValidTill,
            String cardFromCVV,
            String cardToNumber,
            Amount amount
    ) {
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransferUnit that)) return false;
        return Objects.equals(
                getCardFromNumber(), that.getCardFromNumber()) &&
                Objects.equals(getCardFromValidTill(), that.getCardFromValidTill()) &&
                Objects.equals(getCardFromCVV(), that.getCardFromCVV()) &&
                Objects.equals(getCardToNumber(), that.getCardToNumber()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getTime(), that.getTime()
                );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getCardFromNumber(),
                getCardFromValidTill(),
                getCardFromCVV(),
                getCardToNumber(),
                getAmount(),
                getDate(),
                getTime()
        );
    }

    @Override
    public String toString() {
        return "TransferUnit{" +
                "cardFromNumber='" + cardFromNumber + '\'' +
                ", cardToNumber='" + cardToNumber + '\'' +
                ", amount=" + amount +
                '}';
    }
}
