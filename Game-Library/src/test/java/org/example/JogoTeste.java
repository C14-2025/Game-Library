package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JogoTeste {

    @Test
    public void testeGetNomeCorretamente(){
        Jogo jogo = new Jogo("MGS Delta: Snake Eater", "28/08/2025", "Espionagem", 24);
        String nome = jogo.getNome();
        assertEquals("MGS Delta: Snake Eater", nome);
    }

    @Test
    public void testeGetDuracao(){
        Jogo jogo = new Jogo("Elden Ring", "25/02/2022", "RPG de Ação", 100);
        int duracao = jogo.getDuracao();
        assertEquals(100, duracao);
    }

    @Test
    public void testeGetGeneroComEntradaNula(){
        Jogo jogo = new Jogo("Silent Hill 2", "24/09/2001", null, 10);
        String genero = jogo.getGenero();
        assertNull(genero);
    }

    @Test
    public void testeCriacaoComDuracaoNegativa(){
        Jogo jogo = new Jogo("F-ZERO 99", "14/09/2023", "Corrida", -1);
        int duracao = jogo.getDuracao();
        assertEquals(-1, duracao);
    }
}
