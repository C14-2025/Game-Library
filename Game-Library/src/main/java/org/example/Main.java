package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        BibliotecaJogo biblioteca = new BibliotecaJogo();
        biblioteca.importarDeJson("jogo.json");
        System.out.println("\n-------------------------------------------");
        System.out.println("=== Bem-vindo à Biblioteca de Jogos ===");
        System.out.println("Jogos carregados da última sessão: " + biblioteca.obterTamanho());

        boolean flag = true;
        while(flag){
            System.out.println("\nOpções da iblioteca:");
            System.out.println("1. Adicionar jogo");
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
                    System.out.print("Data de lançamento: ");
                    String data = entrada.nextLine();
                    System.out.print("Gênero: ");
                    String genero = entrada.nextLine();
                    System.out.print("Plataforma: ");
                    String plataforma = entrada.nextLine();
                    System.out.print("Desenvolvedora: ");
                    String desenvolvedora = entrada.nextLine();
                    System.out.print("Publicadora: ");
                    String publicadora = entrada.nextLine();
                    entrada.nextLine();

                    Jogo novoJogo = new Jogo(nome, data, genero, plataforma, desenvolvedora, publicadora);
                    biblioteca.adicionarJogo(novoJogo);
                    System.out.println("Jogo adicionado com sucesso!");
                    break;

                case 2:
                    if (biblioteca.listaVazia()) {
                        System.out.println("\nNenhum jogo cadastrado.");
                    } else {
                        System.out.println("\n=== Lista de Jogos ===");
                        for (Jogo jogo : biblioteca.listarJogos()) {
                            System.out.println(
                                    "- " + jogo.getNome() +
                                            " | " + jogo.getGenero() +
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