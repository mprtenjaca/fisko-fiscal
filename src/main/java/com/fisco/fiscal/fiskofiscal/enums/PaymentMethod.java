package com.fisco.fiscal.fiskofiscal.enums;

import lombok.AllArgsConstructor;

public enum PaymentMethod {
    CASH("CASH"),
    CARD("CARD"),
    CHECK("CHECK"),
    OTHER("OTHER");

    private String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
