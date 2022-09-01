package com.fisco.fiscal.fiskofiscal.enums;

public enum PaymentMethod {
    GOTOVINA("GOTOVINA"),
    KARTICA("KARTICA"),
    TRANSAKCIJSKI_RAČUN("TRANSAKCIJSKI_RAČUN"),
    ČEK("ČEK"),
    OSTALO("OSTALO");

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
