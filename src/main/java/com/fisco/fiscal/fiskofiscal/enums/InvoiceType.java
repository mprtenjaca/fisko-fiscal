package com.fisco.fiscal.fiskofiscal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum InvoiceType {
    R("R"),
    R1("R1"),
    R2("R2"),
    AVANSNI("AVANSNI"),
    OSTALO("OSTALO");

    private String value;

    @Override
    public String toString() {
        return this.getValue();
    }

}
