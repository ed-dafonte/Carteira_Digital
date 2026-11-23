package com.example.carteira.controller; // define o pacote onde esta classe de teste está localizada

import com.example.carteira.service.ContaService; // importa o serviço que será simulado (mock)
import com.example.carteira.service.SaldoInsuficienteException; // importa a exceção personalizada
import org.junit.jupiter.api.Test; // importa a anotação que marca um método como teste
import org.springframework.beans.factory.annotation.Autowired; // importa a anotação de injeção automática
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; // importa a anotação que sobe apenas o controller para teste, sem o banco
import org.springframework.boot.test.mock.mockito.MockBean; // importa a anotação que cria um mock gerenciado pelo Spring
import org.springframework.test.web.servlet.MockMvc; // importa o MockMvc que simula requisições HTTP sem subir o servidor

import static org.mockito.Mockito.doNothing; // importa o método que define que um mock não faz nada quando chamado
import static org.mockito.Mockito.doThrow; // importa o método que define que um mock deve lançar uma exceção
import static org.mockito.Mockito.when; // importa o método que define o comportamento do mock
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // importa o método que cria uma requisição GET
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // importa o método que cria uma requisição POST
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // importa o método que verifica o status HTTP da resposta

@WebMvcTest(ContaController.class) // sobe apenas o ContaController para teste — sem banco, sem serviço real
class ContaControllerTest {

    @Autowired // o Spring injeta automaticamente o MockMvc aqui
    private MockMvc mockMvc; // ferramenta que simula requisições HTTP nos testes

    @MockBean // cria um mock do ContaService gerenciado pelo Spring — substitui o serviço real durante os testes
    private ContaService contaService;

    @Test // teste 7
    void deveRetornar200AoConsultarSaldo() throws Exception { // throws Exception porque o MockMvc pode lançar exceções

        when(contaService.consultarSaldo(1L)) // quando o serviço for chamado com id 1...
                .thenReturn(500.0); // ...retorna 500.0

        mockMvc.perform(get("/contas/1/saldo")) // simula uma requisição GET para /contas/1/saldo
                .andExpect(status().isOk()); // verifica se o status retornado é 200 OK
    }

    @Test // teste 8
    void deveRetornar200AoDepositarValorValido() throws Exception {

        doNothing().when(contaService).depositar(1L, 200.0); // define que o serviço não faz nada (sem erro) ao depositar

        mockMvc.perform(post("/contas/1/depositar") // simula uma requisição POST para /contas/1/depositar
                        .param("valor", "200.0")) // envia o parâmetro "valor" com o valor 200.0
                .andExpect(status().isOk()); // verifica se o status retornado é 200 OK
    }

    @Test // teste 9
    void deveRetornar400AoDepositarValorInvalido() throws Exception {

        doThrow(new IllegalArgumentException("Valor inválido")) // define que o serviço vai lançar IllegalArgumentException...
                .when(contaService).depositar(1L, 0.0); // ...quando chamado com valor 0.0

        mockMvc.perform(post("/contas/1/depositar") // simula uma requisição POST para /contas/1/depositar
                        .param("valor", "0.0")) // envia o parâmetro "valor" com o valor 0.0
                .andExpect(status().isBadRequest()); // verifica se o status retornado é 400 Bad Request
    }

    @Test // teste 10
    void deveRetornar200AoTransferirComSaldoSuficiente() throws Exception {

        doNothing().when(contaService).transferir(1L, 2L, 200.0); // define que o serviço não faz nada (sem erro) ao transferir

        mockMvc.perform(post("/contas/transferir") // simula uma requisição POST para /contas/transferir
                        .param("idOrigem", "1") // envia o parâmetro "idOrigem"
                        .param("idDestino", "2") // envia o parâmetro "idDestino"
                        .param("valor", "200.0")) // envia o parâmetro "valor"
                .andExpect(status().isOk()); // verifica se o status retornado é 200 OK
    }

    @Test // teste 11
    void deveRetornar400AoTransferirComSaldoInsuficiente() throws Exception {

        doThrow(new SaldoInsuficienteException("Saldo insuficiente")) // define que o serviço vai lançar SaldoInsuficienteException...
                .when(contaService).transferir(1L, 2L, 999.0); // ...quando chamado com valor 999.0

        mockMvc.perform(post("/contas/transferir") // simula uma requisição POST para /contas/transferir
                        .param("idOrigem", "1") // envia o parâmetro "idOrigem"
                        .param("idDestino", "2") // envia o parâmetro "idDestino"
                        .param("valor", "999.0")) // envia o parâmetro "valor"
                .andExpect(status().isBadRequest()); // verifica se o status retornado é 400 Bad Request
    }
}
