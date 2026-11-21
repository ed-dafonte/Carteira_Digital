package com.example.carteira.service; // define o pacote onde esta classe está localizada

import com.example.carteira.model.Conta; // importa a entidade Conta para poder usá-la aqui
import com.example.carteira.repository.ContaRepository; // importa o repositório para acessar o banco
import org.springframework.beans.factory.annotation.Autowired; // importa a anotação de injeção automática
import org.springframework.stereotype.Service; // importa a anotação que marca esta classe como serviço

// É a regra de negócio. É aqui que fica a lógica: "não pode depositar valor negativo", "saldo insuficiente".
// Ele usa o repository para buscar os dados, aplica as regras, e devolve o resultado.

@Service // diz ao Spring que esta classe é um serviço e deve ser gerenciada por ele
public class ContaService {

    @Autowired // em vez de criar o repositório manualmente com "new", o Spring cria e entrega automaticamente uma instância pronta para uso
    private ContaRepository contaRepository;

    // Consulta Saldo
    public Double consultarSaldo(Long id) { // recebe o id da conta que se quer consultar

        Conta conta = contaRepository.findById(id) // busca a conta no banco pelo id
                .orElseThrow(() -> new RuntimeException("Conta não encontrada")); // se não existir, lança um erro

        return conta.getSaldo(); // retorna o saldo da conta encontrada
    }

    // Realiza Depósito
    public void depositar(Long id, Double valor) { // recebe o id da conta e o valor a depositar

        if (valor <= 0) { // verifica se o valor é zero ou negativo
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero"); // lança um erro com mensagem explicando o problema
        }

        Conta conta = contaRepository.findById(id) // busca a conta no banco pelo id
                .orElseThrow(() -> new RuntimeException("Conta não encontrada")); // lança erro se a conta não existir

        conta.setSaldo(conta.getSaldo() + valor); // soma o valor depositado ao saldo atual da conta

        contaRepository.save(conta); // salva a conta com o novo saldo no banco
    }

    // Realiza Transferência — método ainda sem implementação, será preenchido no commit GREEN
    public void transferir(Long idOrigem, Long idDestino, Double valor) {

    }
}
