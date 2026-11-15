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

    public Double consultarSaldo(Long id) { // recebe o id da conta que se quer consultar

        Conta conta = contaRepository.findById(id) // busca a conta no banco pelo id
                .orElseThrow(() -> new RuntimeException("Conta não encontrada")); // se não existir, lança um erro

        return conta.getSaldo(); // retorna o saldo da conta encontrada
    }
}
