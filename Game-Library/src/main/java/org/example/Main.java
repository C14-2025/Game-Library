package org.example;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("RAWG_API_KEY");
        RawgApiClient api = new RawgApiClient(apiKey);
        BibliotecaJogo biblioteca = new BibliotecaJogo();

        Scanner entrada = new Scanner(System.in);
        biblioteca.importarDeJson("jogo.json");
        System.out.println("\n-------------------------------------------");
        System.out.println("=== Bem-vindo à Biblioteca de Jogos ===");
        System.out.println("Jogos carregados da última sessão: " + biblioteca.obterTamanho());

        boolean flag = true;
        while(flag){
            System.out.println("\nOpções da iblioteca:");
            System.out.println("1. Buscar e adicionar jogo à lista");
            System.out.println("2. Listar jogos");
            System.out.println("3. Remover jogo");
            System.out.println("4. Salvar e sair");
            System.out.print("Escolha uma opção: ");

            int opcao = entrada.nextInt();
            entrada.nextLine();  //Quebra de linha

            switch (opcao){
                case 1:
                    System.out.print("\nNome do jogo: ");
                    String nome = entrada.nextLine();

                    try {
                        biblioteca.buscarEAdicionarJogo(nome, api);
                        System.out.println("Jogo adicionado com sucesso: ");
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 2:
                    if (biblioteca.listaVazia()) {
                        System.out.println("\nNenhum jogo cadastrado.");
                    } else {
                        System.out.println("\n=== Lista de Jogos ===");
                        for (Jogo jogo : biblioteca.listarJogos()) {
                            System.out.println(
                                    "- Nome: " + jogo.getNome() +
                                            " | Gênero: " + jogo.getGenero() +
                                            " | Lançado em: " + jogo.getDataLancamento() +
                                            " | Plataforma: " + jogo.getPlataforma() +
                                            " | Desenvolvedora: " + jogo.getDesenvolvedora() +
                                            " | Publicadora: " + jogo.getPublicadora()
                            );
                        }
                    }
                    break;

                case 3:
                    System.out.print("\nDigite o nome do jogo a remover: ");
                    String removerNome = entrada.nextLine();
                    try {
                        biblioteca.removerJogo(removerNome);
                        System.out.println("Jogo removido com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 4:
                    try (FileWriter writer = new FileWriter("jogo.json")) {
                        writer.write(biblioteca.exportarParaJson());
                        System.out.println("\nAlterações salvas em " + "jogo.json");
                    } catch (IOException e) {
                        System.out.println("\nErro ao salvar: " + e.getMessage());
                    }
                    flag = false;
                    break;

                default:
                    System.out.println("\nOpção inválida. Tente novamente.");
            }
        }
        entrada.close();
        System.out.println("\nAté logo!");
        System.out.println("-------------------------------------------");
    }
}