package com.example.carteira.service;

import com.example.carteira.model.Conta;
import com.example.carteira.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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

    @Test // marca este método como um caso de teste
    void deveDepositarValorValidoEAtualizarOSaldo() {

        // --- ARRANGE (preparação) ---

        Conta conta = new Conta(1L, "João", 500.0); // cria uma conta fictícia com saldo inicial de 500

        when(contaRepository.findById(1L)) // define que quando buscar pelo id 1...
                .thenReturn(Optional.of(conta)); // ...retorna a conta fictícia

        // --- ACT (execução) ---

        contaService.depositar(1L, 200.0); // chama o método depositar com id 1 e valor 200

        // --- ASSERT (verificação) ---

        assertEquals(700.0, conta.getSaldo()); // verifica se o saldo foi atualizado de 500 para 700

        verify(contaRepository).save(conta); // verifica se o repositório foi chamado para salvar a conta atualizada
    }
}
