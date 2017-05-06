package com.github.joostvdg.dui.protocol;

public enum FeiwuMessageType {
    UNIDENTIFIED((byte)0x00),
    HELLO((byte)0x01);

    private byte identifier;

    private FeiwuMessageType(byte identifier) {
        this.identifier = identifier;
    }

    public byte getIdentifier(){
        return this.identifier;
    }

}
