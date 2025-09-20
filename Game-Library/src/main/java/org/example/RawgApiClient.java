package org.example;

import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

public class RawgApiClient implements JogoApiService{
    private static final String BASE = "https://api.rawg.io/api/games"; // Base da URL
    private final String apiKey;
    private final HttpClient client;
    private final Gson gson = new Gson();

    public RawgApiClient(String apiKey) {
        this(apiKey, HttpClient.newHttpClient());
    }

    // construtor para injeção
    public RawgApiClient(String apiKey, HttpClient client) {
        this.apiKey = Objects.requireNonNull(apiKey, "API key não pode ser nula.");
        this.client = Objects.requireNonNull(client, "O client não pode ser nulo.");
    }

    @Override
    public Optional<Jogo> buscarJogoPorNome(String nome) { //Implementação da interface JogoApiService
        try {
            String q = URLEncoder.encode(nome, StandardCharsets.UTF_8); // Nome do jogo a ser procurado
            String url = BASE + "?search=" + q + "&key=" + apiKey; // URL
            HttpRequest req = HttpRequest.newBuilder() // Requisição
                    .uri(URI.create(url))
                    .header("User-Agent", "biblioteca-jogos/1.0")
                    .GET()
                    .build();

            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString()); // Resposta
            int status = resp.statusCode(); // Recupera o código de status da resposta
            if (status != 200) {
                // Tratamento de exceção
                throw new RuntimeException("RAWG retornou status " + status);
            }

            JsonObject root = JsonParser.parseString(resp.body()).getAsJsonObject(); // Transforma a resposta em um documento Json
            JsonArray results = root.has("results") ? root.getAsJsonArray("results") : new JsonArray(); // Monta um array a partir do resultado
            if (results.isEmpty()) return Optional.empty(); // Caso não haja resultado algum, retorna vazio

            JsonObject g = results.get(0).getAsJsonObject(); // Variável para busca dos dados dos jogos

            String nomeApi = g.has("name") && !g.get("name").isJsonNull() ? g.get("name").getAsString() : nome; // Recupera nome

            String lancamento = g.has("released") && !g.get("released").isJsonNull() ? g.get("released").getAsString() : ""; // Recupera a data de lançamento

            String genero = ""; // Recupera o gênero do jogo
            if (g.has("genres") && g.get("genres").isJsonArray() && !g.getAsJsonArray("genres").isEmpty()) {
                JsonObject g0 = g.getAsJsonArray("genres").get(0).getAsJsonObject();
                genero = g0.has("name") ? g0.get("name").getAsString() : "";
            }

            String plataforma = ""; // Recupera a plataforma
            if (g.has("platforms") && g.get("platforms").isJsonArray() && !g.getAsJsonArray("platforms").isEmpty()) {
                JsonObject g0 = g.getAsJsonArray("platforms").get(0).getAsJsonObject().getAsJsonObject("platform");
                plataforma = g0.has("name") ? g0.get("name").getAsString() : "";
            }

            String desenvolvedora = ""; // Recupera a desenvolvedora
            if (g.has("developers") && g.get("developers").isJsonArray() && !g.getAsJsonArray("developers").isEmpty()) {
                JsonObject g0 = g.getAsJsonArray("developers").get(0).getAsJsonObject();
                desenvolvedora = g0.has("name") ? g0.get("name").getAsString() : "";
            }

            String publicadora = ""; // Recupera a publicadora
            if (g.has("publishers") && g.get("publishers").isJsonArray() && !g.getAsJsonArray("publishers").isEmpty()) {
                JsonObject g0 = g.getAsJsonArray("publishers").get(0).getAsJsonObject();
                publicadora = g0.has("name") ? g0.get("name").getAsString() : "";
            }

            Jogo jogo = new Jogo(nomeApi, lancamento, genero, plataforma, desenvolvedora, publicadora); // Cria um novo jogo
            return Optional.of(jogo); // Retorna o jogo criado
        } catch (IOException | InterruptedException e) { // Tratamento de exceção caso ocorra uma interrupção
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erro ao consultar RAWG", e);
        } catch (JsonParseException e) {
            throw new RuntimeException("JSON inválido recebido da RAWG", e);
        }
    }
}
