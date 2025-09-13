package org.example;

import org.junit.Test;

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

    @Test(expected = IllegalArgumentException.class)
    public void testeAdicionarJogoDuplicadoLancaExcecao() {
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        bibliotecaJogo.adicionarJogo(new Jogo("Skyrim", "11/11/2011", "RPG", 300));
        bibliotecaJogo.adicionarJogo(new Jogo("SKYRIM", "11/11/2011", "RPG", 300));
        }
    
    @Test
    public void testeBibliotecaRecemCriadaVazia() {
        BibliotecaJogo bibliotecaJogo = new BibliotecaJogo();
        assertEquals(0, bibliotecaJogo.obterTamanho());
        assertTrue(bibliotecaJogo.listaVazia());
    }

}
