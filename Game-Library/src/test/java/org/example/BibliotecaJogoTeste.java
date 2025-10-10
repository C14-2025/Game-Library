package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BibliotecaJogoTeste {

    private BibliotecaJogo bibliotecaJogo;

    @Mock
    private JogoApiService apiMock;

    @BeforeEach
    void setUp() {
        bibliotecaJogo = new BibliotecaJogo();
    }

    @Test
    @DisplayName("Deve buscar e adicionar um jogo com sucesso via API")
    void testeBuscareAdicionarJogoComMock() {
        Jogo jogo = new Jogo("TesteGame", "20/09/2025", "Teste", "TesteStation", "TesteSoft", "TesteCorp");
        when(apiMock.buscarJogoPorNome("TesteGame")).thenReturn(Optional.of(jogo));

        bibliotecaJogo.buscarEAdicionarJogo("TesteGame", apiMock);

        assertEquals(1, bibliotecaJogo.obterTamanho());
        assertEquals("TesteGame", bibliotecaJogo.listarJogos().getFirst().getNome());
        verify(apiMock, times(1)).buscarJogoPorNome("TesteGame");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar jogo que API não encontra")
    void testBuscarEAdicionar_notFound() {
        when(apiMock.buscarJogoPorNome("Teste")).thenReturn(Optional.empty());

        assertEquals(0, bibliotecaJogo.obterTamanho());

        assertThrows(NoSuchElementException.class,
                () -> bibliotecaJogo.buscarEAdicionarJogo("Teste", apiMock));

        verify(apiMock, times(1)).buscarJogoPorNome("Teste");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar adicionar jogo duplicado via API")
    void testAdicionarJogoDuplicadoViaApi() {
        Jogo jogo = new Jogo("Skyrim", "2011", "RPG", "PC", "Bethesda", "Bethesda");
        when(apiMock.buscarJogoPorNome("Skyrim")).thenReturn(Optional.of(jogo));

        bibliotecaJogo.buscarEAdicionarJogo("Skyrim", apiMock);
        assertEquals(1, bibliotecaJogo.obterTamanho());

        assertThrows(IllegalArgumentException.class,
                () -> bibliotecaJogo.buscarEAdicionarJogo("Skyrim", apiMock));
    }

    @Test
    @DisplayName("Deve retornar falso para listaVazia quando contém um jogo")
    void testeListaNaoEstaVazia() {
        Jogo jogo = new Jogo("Metal Gear Solid Delta: Snake Eater", "28/08/2025", "Espionagem", "PC", "Konami", "Konami");
        when(apiMock.buscarJogoPorNome(anyString())).thenReturn(Optional.of(jogo));
        bibliotecaJogo.buscarEAdicionarJogo("Metal Gear Solid Delta: Snake Eater", apiMock);

        assertEquals(1, bibliotecaJogo.obterTamanho());
        assertFalse(bibliotecaJogo.listaVazia());
    }

    @Test
    @DisplayName("Deve remover um jogo com sucesso")
    void testeRemoverJogo() {
        Jogo jogo = new Jogo("Persona 4 Golden", "14/06/2012", "RPG de Turno", "PS Vita", "Atlus", "Sega");
        bibliotecaJogo.adicionarJogo(jogo);
        assertEquals(1, bibliotecaJogo.obterTamanho());

        bibliotecaJogo.removerJogo("persona 4 golden");

        assertEquals(0, bibliotecaJogo.obterTamanho());
        assertTrue(bibliotecaJogo.listaVazia());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar remover de uma lista vazia")
    void testeRemoverJogoListaVazia() {
        assertEquals(0, bibliotecaJogo.obterTamanho());
        assertThrows(IllegalStateException.class,
                () -> bibliotecaJogo.removerJogo("teste"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar remover um jogo inexistente")
    void testeRemoverJogoInexistente() {
        Jogo jogo = new Jogo("Persona 4 Golden", "14/06/2012", "RPG de Turno", "PS Vita", "Atlus", "Sega");
        bibliotecaJogo.adicionarJogo(jogo);

        assertEquals(1, bibliotecaJogo.obterTamanho());
        assertThrows(NoSuchElementException.class,
                () -> bibliotecaJogo.removerJogo("teste"));
    }

    @Test
    @DisplayName("Deve listar corretamente os jogos adicionados")
    void testeListarJogos() {
        Jogo jogo1 = new Jogo("Astro Bot", "06/07/2024", "Plataforma", "PS5", "Playstation", "Playstation");
        Jogo jogo2 = new Jogo("Expedition 33", "24/04/2025", "RPG", "PC", "Sandfall Interactive", "Kepler Interactive");

        bibliotecaJogo.adicionarJogo(jogo1);
        bibliotecaJogo.adicionarJogo(jogo2);

        List<Jogo> lista = bibliotecaJogo.listarJogos();

        assertEquals(2, lista.size());
        assertTrue(lista.contains(jogo1));
        assertTrue(lista.contains(jogo2));
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar jogo duplicado diretamente")
    void testeAdicionarJogoDuplicadoLancaExcecao() {
        Jogo jogo = new Jogo("Skyrim", "11/11/2011", "RPG", "PC", "Bethesda", "Bethesda");
        bibliotecaJogo.adicionarJogo(jogo);
        assertEquals(1, bibliotecaJogo.obterTamanho());

        assertThrows(IllegalArgumentException.class, () ->
                bibliotecaJogo.adicionarJogo(jogo));
        
        assertEquals(1, bibliotecaJogo.obterTamanho());
    }

    @Test
    @DisplayName("Deve estar vazia quando recém-criada")
    void testeBibliotecaRecemCriadaVazia() {
        assertEquals(0, bibliotecaJogo.obterTamanho());
        assertTrue(bibliotecaJogo.listaVazia());
    }

    @Test
    @DisplayName("Deve exportar uma lista vazia para um JSON array vazio")
    void testeExportarParaJson_listaVazia() {
        String json = bibliotecaJogo.exportarParaJson();
        assertEquals("[]", json.trim());
    }

    @Test
    @DisplayName("Deve exportar uma lista com jogos para um JSON populado")
    void testeExportarParaJson_comJogos() {
        bibliotecaJogo.adicionarJogo(new Jogo("Minecraft", "2011", "Sandbox", "PC", "Mojang", "Microsoft"));

        String json = bibliotecaJogo.exportarParaJson();
        assertTrue(json.contains("Minecraft"));
        assertTrue(json.contains("2011"));
        assertTrue(json.contains("Sandbox"));
    }

    @Test
    @DisplayName("Deve importar jogos de um arquivo JSON válido")
    void testeImportarDeJson_comArquivoValido() throws IOException {
        String json = """
                [
                  {
                    "nome": "The Witcher 3",
                    "dataLancamento": "2015",
                    "genero": "RPG",
                    "plataforma": "PC",
                    "desenvolvedora": "CD Projekt",
                    "publicadora": "CD Projekt"
                  }
                ]
                """;

        Path arquivoTemp = Files.createTempFile("jogos", ".json");
        try (FileWriter writer = new FileWriter(arquivoTemp.toFile())) {
            writer.write(json);
        }

        bibliotecaJogo.importarDeJson(arquivoTemp.toString());

        assertEquals(1, bibliotecaJogo.obterTamanho());
        assertEquals("The Witcher 3", bibliotecaJogo.listarJogos().getFirst().getNome());

        Files.deleteIfExists(arquivoTemp);
    }

    @Test
    @DisplayName("Deve permanecer vazia ao tentar importar de arquivo inexistente")
    void testImportarDeJson_arquivoInexistente_comMock() {
        
        BibliotecaJogo bibliotecaSpy = Mockito.spy(new BibliotecaJogo());

        doThrow(new RuntimeException("Arquivo não encontrado"))
                .when(bibliotecaSpy).importarDeJson("nao_existe.json");

        
        try {
            bibliotecaSpy.importarDeJson("nao_existe.json");
        } catch (Exception e) {
        }

        assertTrue(bibliotecaSpy.listaVazia());
    }

    @Test
    void testeAdicionarVariosJogos_comMock() {
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        Jogo jogo1 = mock(Jogo.class);
        Jogo jogo2 = mock(Jogo.class);

        when(jogo1.getNome()).thenReturn("Elden Ring");
        when(jogo1.getAnoLancamento()).thenReturn("25/02/2022");
        when(jogo1.getGenero()).thenReturn("RPG de Ação");

        when(jogo2.getNome()).thenReturn("God of War");
        when(jogo2.getAnoLancamento()).thenReturn("20/04/2018");
        when(jogo2.getGenero()).thenReturn("Ação/Aventura");

        bibliotecaJogo.adicionarJogo(jogo1);
        bibliotecaJogo.adicionarJogo(jogo2);

        assertEquals(2, bibliotecaJogo.obterTamanho());

        List<Jogo> lista = bibliotecaJogo.listarJogos();
        assertTrue(lista.contains(jogo1));
        assertTrue(lista.contains(jogo2));

        verify(jogo1, atLeastOnce()).getNome();
        verify(jogo2, atLeastOnce()).getNome();
    }
}
