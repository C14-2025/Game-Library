package org.example;

public class Jogo {
    private String nome;
    private String dataLancamento;
    private String genero;
    private String plataforma;
    private String desenvolvedora;
    private String publicadora;

    public Jogo(String nome, String dataLancamento, String genero, String plataforma, String desenvolvedora, String publicadora) {
        this.nome = nome;
        this.dataLancamento = dataLancamento;
        this.genero = genero;
        this.plataforma = plataforma;
        this.desenvolvedora = desenvolvedora;
        this.publicadora = publicadora;
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

    public String getPlataforma() {
        return plataforma;
    }

    public String getDesenvolvedora() {
        return desenvolvedora;
    }

    public String getPublicadora() {
        return publicadora;
    }
}
