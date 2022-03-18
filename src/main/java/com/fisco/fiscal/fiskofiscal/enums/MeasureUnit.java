package com.fisco.fiscal.fiskofiscal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MeasureUnit {
    KOM("KOM"),
    SAT("SAT"),
    GOD("GOD"),
    KM("KM"),
    LIT("LIT"),
    KG("KG"),
    KWH("KWH"),
    M("M"),
    M2("M2"),
    M3("M3"),
    T("T"),
    G("G"),
    DAN("DAN"),
    MJ("MJ"),
    NOĆ("NOĆ"),
    PAR("PAR"),
    SOBA("SOBA");

    private String value;

    @Override
    public String toString() {
        return this.getValue();
    }
}
