package com.lmc.geradortamanhoarquivo.controller;

import com.lmc.geradortamanhoarquivo.domain.Arquivo;
import com.lmc.geradortamanhoarquivo.domain.ArquivoEnum;
import com.lmc.geradortamanhoarquivo.service.GeradorTamanhoArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.MalformedURLException;

@Controller
@RequestMapping("/criar-arquivo")
public class GeradorTamanhoArquivoController {

    @Autowired
    private GeradorTamanhoArquivoService service;

    @GetMapping()
    public String index() {
        return "criar-arquivo";
    }

    @PostMapping
    public ResponseEntity<Resource> add(
            @RequestParam("tamanho") long tamanho,
            @RequestParam("nome_arquivo") String nomeArquivo,
            @RequestParam("extensao_arquivo") String extensaoArquivo) {

        try {
            Arquivo arquivo = Arquivo.builder()
                    .tamanho(tamanho)
                    .nome(nomeArquivo)
                    .extensao(ArquivoEnum.valueOf(extensaoArquivo.toUpperCase()))
                    .build();

            Resource resource = service.criarArquivo(arquivo);

            service.deletarArquivo(arquivo);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return null;
        }

    }
}
