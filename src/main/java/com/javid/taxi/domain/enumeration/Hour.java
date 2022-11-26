package com.javid.taxi.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Hour {

    _0, _1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _21, _22, _23;

    Hour() {
    }

    @JsonValue
    public Integer value() {
        return this.ordinal();
    }

    @Override
    public String toString() {
        return super.toString().replace("_", "");
    }
}
