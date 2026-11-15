package com.example.carteira.service; // define o pacote onde esta classe de teste está localizada

import com.example.carteira.model.Conta; // importa a entidade Conta
import com.example.carteira.repository.ContaRepository; // importa o repositório que será simulado (mock)
import org.junit.jupiter.api.Test; // importa a anotação que marca um método como teste
import org.junit.jupiter.api.extension.ExtendWith; // importa a anotação que ativa o Mockito no JUnit 5
import org.mockito.InjectMocks; // importa a anotação que injeta os mocks na classe testada
import org.mockito.Mock; // importa a anotação que cria um objeto simulado (mock)
import org.mockito.junit.jupiter.MockitoExtension; // importa a extensão do Mockito para JUnit 5

import java.util.Optional; // importa o Optional, usado pelo findById para indicar que pode ou não existir um valor

import static org.junit.jupiter.api.Assertions.assertEquals; // importa o método que verifica se dois valores são iguais
import static org.mockito.Mockito.when; // importa o método que define o comportamento do mock

@ExtendWith(MockitoExtension.class) // ativa o Mockito para que os @Mock e @InjectMocks funcionem nesta classe
class ContaServiceTest {

    @Mock // cria um repositório simulado — não acessa o banco de verdade
    private ContaRepository contaRepository;

    @InjectMocks // cria uma instância do ContaService e injeta o mock do repositório dentro dele
    private ContaService contaService;

    @Test // marca este método como um caso de teste
    void deveRetornarOSaldoQuandoAContaExiste() {

        // --- ARRANGE (preparação) ---

        Conta conta = new Conta(1L, "João", 500.0); // cria uma conta fictícia com id 1, titular João e saldo 500

        when(contaRepository.findById(1L)) // define que quando o repositório buscar pelo id 1...
                .thenReturn(Optional.of(conta)); // ...ele deve retornar a conta dentro de um Optional.
                                                 // Optional é um "envelope" que o Spring usa no findById
                                                 // para indicar que o resultado pode ou não existir.
                                                 // Optional.of(conta) significa: "existe, e é esta conta".

        // --- ACT (execução) ---

        Double saldo = contaService.consultarSaldo(1L); // chama o método que queremos testar, passando o id 1

        // --- ASSERT (verificação) ---

        assertEquals(500.0, saldo); // verifica se o saldo retornado é 500.0, como esperado
    }
}
