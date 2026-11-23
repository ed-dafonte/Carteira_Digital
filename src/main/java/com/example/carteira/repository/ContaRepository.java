package com.example.carteira.repository;

import com.example.carteira.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;


// É o acesso ao banco. Só sabe buscar, salvar e deletar contas. Não conhece regra de negócio nenhuma.




public interface ContaRepository extends JpaRepository<Conta, Long> {
    // Conta = tipo da entidade que este repositório gerencia
    // Long = tipo do id da entidade
}
