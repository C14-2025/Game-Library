package org.example;

import java.util.Optional;

public interface JogoApiService {
    Optional<Jogo> buscarJogoPorNome(String nome); //Busca um jogo pelo nome e devolve o primeiro resultado mapeado
    //ou vazio caso não encontrado
}
