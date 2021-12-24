package com.lmc.geradortamanhoarquivo.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Arquivo {
    private String nome;
    private ArquivoEnum extensao;
    private long tamanho;
}
