package org.example;

public class Jogo {
    private String nome;
    private String dataLancamento;
    private String genero;
    private int duracao;

    public Jogo(String nome, String dataLancamento, String genero, int duracao) {
        this.nome = nome;
        this.dataLancamento = dataLancamento;
        this.genero = genero;
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public String getGenero() {
        return genero;
    }

    public int getDuracao() {
        return duracao;
    }
}
