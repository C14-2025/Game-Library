package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Test
    public void testeRemoverJogoListaVazia(){
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        assertThrows(IllegalStateException.class,
                () -> bibliotecaJogo.removerJogo("teste"));
    }

    @Test
    public void testeRemoverJogoInexistente(){
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        bibliotecaJogo.adicionarJogo((new Jogo("Persona 4 Golden", "14/06/2012", "RPG de Turno", 100)));
        assertThrows(NoSuchElementException.class,
                () -> bibliotecaJogo.removerJogo("teste"));
    }

    @Test
    public void testeListarJogos() {
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        Jogo jogo1 = new Jogo("Astro Bot", "06/07/2024", "Plataforma", 30);
        Jogo jogo2 = new Jogo("Expedition 33", "24/04/2025", "RPG", 100);
      
        bibliotecaJogo.adicionarJogo(jogo1);
        bibliotecaJogo.adicionarJogo(jogo2);
        List<Jogo> lista = bibliotecaJogo.listarJogos();
        assertEquals(2, lista.size());
        assertTrue(lista.contains(jogo1));
        assertTrue(lista.contains(jogo2));
    }

    @Test
    public void testeAdicionarJogoDuplicadoLancaExcecao() {
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        bibliotecaJogo.adicionarJogo(new Jogo("Skyrim", "11/11/2011", "RPG", 300));
        assertThrows(IllegalArgumentException.class,
                () -> bibliotecaJogo.adicionarJogo(new Jogo("SKYRIM", "11/11/2011", "RPG", 300)));
    }

    @Test
    public void testeBibliotecaRecemCriadaVazia() {
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        assertEquals(0, bibliotecaJogo.obterTamanho());
        assertTrue(bibliotecaJogo.listaVazia());
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
    //Bissacot
    @Test
    public void testeAdicionarVariosJogos() {
        // Criando uma biblioteca e 2 jogos
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        Jogo jogo1 = new Jogo("Elden Ring", "25/02/2022", "RPG de Ação", 150);
        Jogo jogo2 = new Jogo("God of War", "20/04/2018", "Ação/Aventura", 40);

        // Adicionando jogos a biblioteca
        bibliotecaJogo.adicionarJogo(jogo1);
        bibliotecaJogo.adicionarJogo(jogo2);

        // Verificando se o tamanho e o conteúdo da lista estão corretos
        assertEquals(2, bibliotecaJogo.obterTamanho());
        List<Jogo> listaDeJogos = bibliotecaJogo.listarJogos();
        assertTrue(listaDeJogos.contains(jogo1));
        assertTrue(listaDeJogos.contains(jogo2));
    }

}
