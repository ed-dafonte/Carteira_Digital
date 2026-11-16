package com.example.carteira.repository;

import com.example.carteira.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;


// É o acesso ao banco. Só sabe buscar, salvar e deletar contas. Não conhece regra de negócio nenhuma.

// JpaRepository já fornece métodos como findById, save, delete, etc.
// não precisamos escrever nada aqui — o Spring cria a implementação automaticamente
public interface ContaRepository extends JpaRepository<Conta, Long> {
    // Conta = tipo da entidade que este repositório gerencia
    // Long = tipo do id da entidade
}
