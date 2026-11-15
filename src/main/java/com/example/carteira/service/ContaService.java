package com.example.carteira.service; // define o pacote onde esta classe está localizada

import com.example.carteira.repository.ContaRepository; // importa o repositório para acessar o banco
import org.springframework.beans.factory.annotation.Autowired; // importa a anotação de injeção automática
import org.springframework.stereotype.Service; // importa a anotação que marca esta classe como serviço

@Service // diz ao Spring que esta classe é um serviço e deve ser gerenciada por ele
public class ContaService {

    @Autowired // o Spring injeta automaticamente uma instância do repositório aqui
    private ContaRepository contaRepository;

    // método ainda sem implementação — será preenchido no commit GREEN
    public Double consultarSaldo(Long id) {
        return null;
    }
}
