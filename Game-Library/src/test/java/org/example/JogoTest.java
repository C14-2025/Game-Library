package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JogoTest {

    @Test
    public void testeGetNomeCorretamente(){
        Jogo jogo = new Jogo("MGS Delta: Snake Eater", "28/08/2025", "Espionagem", "PC", "Konami", "Konami");
        String nome = jogo.getNome();
        assertEquals("MGS Delta: Snake Eater", nome);
    }

    @Test
    public void testeGetGeneroComEntradaNula(){
        Jogo jogo = new Jogo("Silent Hill 2", "24/09/2001", null, "PS2", "Konami", "Konami");
        String genero = jogo.getGenero();
        assertNull(genero);
    }
}
