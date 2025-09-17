package org.example;

import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class BibliotecaJogoTeste {

    @Test
    public void testeAdicionarJogo(){
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        bibliotecaJogo.adicionarJogo(new Jogo("Minecraft", "18/11/2011", "Sandbox", 999));
        int tamanho = bibliotecaJogo.obterTamanho();
        assertEquals(1,tamanho);
    }

    @Test
    public void testeListaVazia(){
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        bibliotecaJogo.adicionarJogo((new Jogo("MGS Delta: Snake Eater", "28/08/2025", "Espionagem", 24)));
        boolean condicao = bibliotecaJogo.listaVazia();
        assertFalse(condicao);
    }

    @Test
    public void testeRemoverJogo(){
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        bibliotecaJogo.adicionarJogo((new Jogo("Persona 4 Golden", "14/06/2012", "RPG de Turno", 100)));
        bibliotecaJogo.removerJogo("persona 4 golden");
        boolean condicao = bibliotecaJogo.listaVazia();
        assertTrue(condicao);
    }

    @Test(expected = java.lang.IllegalStateException.class)
    public void testeRemoverJogoListaVazia(){
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        bibliotecaJogo.removerJogo("teste");
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testeRemoverJogoInexistente(){
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        bibliotecaJogo.adicionarJogo((new Jogo("Persona 4 Golden", "14/06/2012", "RPG de Turno", 100)));
        bibliotecaJogo.removerJogo("teste");
    }

    @Test
    public void testeExportarParaJson_listaVazia(){
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        String json = bibliotecaJogo.exportarParaJson();
        assertEquals("[]", json.trim()); //A exportação de uma lista vazia deve gerar []
    }

    @Test
    public void testeExportarParaJson_comJogos(){
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        bibliotecaJogo.adicionarJogo(new Jogo("Minecraft", "2011", "Sandbox", 1000));

        String json = bibliotecaJogo.exportarParaJson();
        assertTrue(json.contains("Minecraft"));
        assertTrue(json.contains("2011"));
        assertTrue(json.contains("Sandbox"));
    }

    @Test
    public void testeImportarDeJson_comArquivoValido() throws IOException {
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();

        // JSON temporário
        String json = """
                [
                  {
                    "nome": "The Witcher 3",
                    "dataLancamento": "2015",
                    "genero": "RPG",
                    "duracao": 200
                  }
                ]
                """;

        Path arquivoTemp = Files.createTempFile("jogos", ".json"); //cria um arquivo temporário para que a string seja inserida
        try (FileWriter writer = new FileWriter(arquivoTemp.toFile())) { //tenta escrever algo no json
            writer.write(json);
        }

        bibliotecaJogo.importarDeJson(arquivoTemp.toString()); //passa o json para uma string

        assertEquals(1, bibliotecaJogo.obterTamanho());
        assertEquals("The Witcher 3", bibliotecaJogo.listarJogos().getFirst().getNome()); //Verifica o primeiro índice da lista

        // apaga o arquivo depois da execução do teste
        Files.deleteIfExists(arquivoTemp);
    }

    @Test
    public void testImportarDeJson_arquivoInexistente() {
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        bibliotecaJogo.importarDeJson("nao_existe.json");
        assertTrue(bibliotecaJogo.listaVazia()); //A lista deve permanecer vazia se o arquivo não existe
    }
}
