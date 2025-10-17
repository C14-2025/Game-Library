package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RawgApiClientTeste {

    @Mock
    private HttpClient mockClient;

    @Mock
    private HttpResponse<String> mockResponse;

    @Mock
    private HttpResponse<String> mockResponseDetalhes;

    private RawgApiClient apiClient;

    @BeforeEach
    public void setUp() {
        apiClient = new RawgApiClient("DUMMY_API_KEY", mockClient);
    }

    @Test
    @DisplayName("Deve retornar um Jogo quando a API encontra resultados")
    public void buscarJogoPorNome_quandoEncontrado_deveRetornarOptionalComJogo() throws IOException, InterruptedException {
        String jsonBusca = """
        {
            "count": 1,
            "results": [
                {
                    "id": 3498,
                    "name": "Grand Theft Auto V",
                    "released": "2013-09-17",
                    "genres": [{"name": "Action"}],
                    "platforms": [{"platform": {"name": "PC"}}]
                }
            ]
        }
        """;

        String jsonDetalhes = """
        {
            "id": 3498,
            "developers": [{"name": "Rockstar North"}],
            "publishers": [{"name": "Rockstar Games"}]
        }
        """;

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(jsonBusca);

        when(mockResponseDetalhes.statusCode()).thenReturn(200);
        when(mockResponseDetalhes.body()).thenReturn(jsonDetalhes);

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse)
                .thenReturn(mockResponseDetalhes);

        Optional<Jogo> resultado = apiClient.buscarJogoPorNome("Grand Theft Auto V");

        assertTrue(resultado.isPresent(), "O Optional não deveria estar vazio.");
        Jogo jogo = resultado.get();
        assertEquals("Grand Theft Auto V", jogo.getNome());
        assertEquals("Action", jogo.getGenero());
        assertEquals("2013-09-17", jogo.getDataLancamento());
        assertEquals("PC", jogo.getPlataforma());
        assertEquals("Rockstar North", jogo.getDesenvolvedora());
        assertEquals("Rockstar Games", jogo.getPublicadora());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando a API não encontra resultados")
    public void buscarJogoPorNome_quandoNaoEncontrado_deveRetornarOptionalVazio() throws IOException, InterruptedException {
        String jsonResposta = """
        {
            "count": 0,
            "results": []
        }
        """;

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(jsonResposta);

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        Optional<Jogo> resultado = apiClient.buscarJogoPorNome("JogoQueNaoExiste");

        assertTrue(resultado.isEmpty(), "O Optional deveria estar vazio para jogo não encontrado.");
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando a API retorna erro de status")
    public void buscarJogoPorNome_quandoApiRetornaErro_deveLancarRuntimeException() throws IOException, InterruptedException {
        when(mockResponse.statusCode()).thenReturn(500);

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            apiClient.buscarJogoPorNome("Qualquer Jogo");
        });

        String expectedMessage = "RAWG retornou status 500";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
