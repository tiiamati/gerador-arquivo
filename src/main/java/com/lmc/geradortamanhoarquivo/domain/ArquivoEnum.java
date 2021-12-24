package com.lmc.geradortamanhoarquivo.domain;

public enum ArquivoEnum {
    TXT("txt"),
    PDF("pdf"),
    PNG("png"),
    JPEG("jpeg"),
    JPG("jpg");

    public String value;

    ArquivoEnum(String value) {
        this.value = value;
    }
}
