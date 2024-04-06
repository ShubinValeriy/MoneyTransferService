package ru.netology.transferservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class Amount {
    private Float value;
    private Currency currency;
    public Amount(Integer value, Currency currency) {
        this.value = (float) value/100;
        this.currency = currency;
    }

    public void setValue(Integer value) {
        this.value = (float) value/100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Amount amount)) return false;
        return Objects.equals(getValue(), amount.getValue()) && Objects.equals(getCurrency(), amount.getCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getCurrency());
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
