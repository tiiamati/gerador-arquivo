package com.lmc.geradortamanhoarquivo.service;

import com.lmc.geradortamanhoarquivo.domain.Arquivo;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class GeradorTamanhoArquivoService {

    private static final String CAMINHO = "src/main/resources/";

    public Resource criarArquivo(Arquivo arquivo) throws MalformedURLException {
        try {
            String stringBuilder = getCaminho(arquivo);

            this.criaArquivo(arquivo, stringBuilder);
            this.insereTamanhoArquivo(arquivo, stringBuilder);

            return new UrlResource(Paths.get(stringBuilder).toUri());

        } catch (IOException exception) {
            System.out.println("Não foi possível criar o arquivo ".concat(arquivo.getNome()));
            return null;
        }
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
                .append(arquivo.getExtensao().value);

        return stringBuilder.toString();
    }

    private void criaArquivo(Arquivo arquivo, String stringBuilder) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(stringBuilder);
        String example = "This is an example";
        byte[] bytes = example.getBytes(StandardCharsets.UTF_8);

        fileOutputStream.write(bytes, 0, bytes.length);
        fileOutputStream.close();
    }

    private void insereTamanhoArquivo(Arquivo arquivo, String stringBuilder) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(stringBuilder, "rw");
        raf.setLength(arquivo.getTamanho());
        raf.close();
    }
}
