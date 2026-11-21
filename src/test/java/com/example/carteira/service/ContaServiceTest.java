package com.example.carteira.service;

import com.example.carteira.model.Conta;
import com.example.carteira.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach; // importa a anotação que marca o método que roda antes de cada teste
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class) // ativa o Mockito para que os @Mock e @InjectMocks funcionem nesta classe
class ContaServiceTest {

    @Mock // cria um repositório simulado — não acessa o banco de verdade
    private ContaRepository contaRepository;

    @InjectMocks // cria uma instância do ContaService e injeta o mock do repositório dentro dele
    private ContaService contaService;

    private Conta conta; // declara a conta aqui para ser acessível em todos os testes

    @BeforeEach // este método roda automaticamente antes de cada teste, garantindo um objeto limpo
    void setUp() {
        conta = new Conta(1L, "João", 500.0); // recria a conta do zero antes de cada teste — saldo sempre começa em 500
    }

    @Test // marca este método como um caso de teste
    void deveRetornarOSaldoQuandoAContaExiste() {

        // --- ARRANGE (preparação) ---

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

    @Test // marca este método como um caso de teste
    void deveDepositarValorValidoEAtualizarOSaldo() {

        // --- ARRANGE (preparação) ---

        when(contaRepository.findById(1L)) // define que quando buscar pelo id 1...
                .thenReturn(Optional.of(conta)); // ...retorna a conta fictícia

        // --- ACT (execução) ---

        contaService.depositar(1L, 200.0); // chama o método depositar com id 1 e valor 200

        // --- ASSERT (verificação) ---

        assertEquals(700.0, conta.getSaldo()); // verifica se o saldo foi atualizado de 500 para 700

        verify(contaRepository).save(conta); // verifica se o repositório foi chamado para salvar a conta atualizada
    }

    @Test // marca este método como um caso de teste
    void deveLancarExcecaoAoDepositarValorZero() {

        // --- ASSERT + ACT juntos (verificação da exceção) ---

        // assertThrows verifica se o método lança a exceção esperada
        // o primeiro argumento é a exceção que esperamos
        // o segundo argumento é o código que deve provocar essa exceção
        assertThrows(IllegalArgumentException.class, () -> contaService.depositar(1L, 0.0));
    }

    @Test // marca este método como um caso de teste
    void deveLancarExcecaoAoDepositarValorNegativo() {

        // mesmo comportamento do teste anterior — mas agora com valor negativo
        // a regra é a mesma: qualquer valor <= 0 deve ser rejeitado
        assertThrows(IllegalArgumentException.class, () -> contaService.depositar(1L, -100.0));
    }

    @Test // marca este método como um caso de teste
    void deveTransferirValorQuandoSaldoESuficiente() {

        // --- ARRANGE (preparação) ---

        Conta contaOrigem = new Conta(1L, "João", 500.0); // conta que vai enviar o dinheiro, com saldo de 500
        Conta contaDestino = new Conta(2L, "Maria", 100.0); // conta que vai receber o dinheiro, com saldo de 100

        when(contaRepository.findById(1L)) // quando buscar pelo id 1...
                .thenReturn(Optional.of(contaOrigem)); // ...retorna a conta de origem

        when(contaRepository.findById(2L)) // quando buscar pelo id 2...
                .thenReturn(Optional.of(contaDestino)); // ...retorna a conta de destino

        // --- ACT (execução) ---

        contaService.transferir(1L, 2L, 200.0); // transfere 200 da conta de João para a de Maria

        // --- ASSERT (verificação) ---

        assertEquals(300.0, contaOrigem.getSaldo()); // João tinha 500, transferiu 200, deve restar 300

        assertEquals(300.0, contaDestino.getSaldo()); // Maria tinha 100, recebeu 200, deve ter 300

        verify(contaRepository).save(contaOrigem); // verifica se a conta de origem foi salva com o novo saldo

        verify(contaRepository).save(contaDestino); // verifica se a conta de destino foi salva com o novo saldo
    }
}
