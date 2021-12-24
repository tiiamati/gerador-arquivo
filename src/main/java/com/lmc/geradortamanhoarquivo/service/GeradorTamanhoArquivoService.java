package com.lmc.geradortamanhoarquivo.service;

import com.lmc.geradortamanhoarquivo.domain.Arquivo;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class GeradorTamanhoArquivoService {

    private static final String CAMINHO = "src/main/resources/";

    public Resource criarArquivo(Arquivo arquivo) throws MalformedURLException {
        String stringBuilder = getCaminho(arquivo);

        try {
            File file = new File(stringBuilder);
            file.createNewFile();

            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.setLength(arquivo.getTamanho());
            raf.close();

        } catch (IOException exception) {
            System.out.println("Não foi possível criar o arquivo ".concat(arquivo.getNome()));
            return null;
        }

        return new UrlResource(Paths.get(stringBuilder).toUri());
    }

    public void deletarArquivo(Arquivo arquivo) {
        String caminho = getCaminho(arquivo);

        File file = new File(caminho);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(
                () -> {
                    try {
                        Thread.sleep(2000);
                        file.delete();
                    } catch (InterruptedException e) {
                        System.out.println("Não foi possível deletar o diretorio ".concat(file.getName()));
                    }
                }
        );
    }

    private String getCaminho(Arquivo arquivo) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(CAMINHO)
                .append(arquivo.getNome())
                .append(".")
                .append(arquivo.getExtensao());

        return stringBuilder.toString();
    }
}
