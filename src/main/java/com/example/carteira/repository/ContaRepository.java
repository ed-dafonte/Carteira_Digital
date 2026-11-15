package com.example.carteira.repository; // define o pacote onde esta interface está localizada

import com.example.carteira.model.Conta; // importa a entidade Conta
import org.springframework.data.jpa.repository.JpaRepository; // importa a interface do Spring que já oferece os métodos de banco prontos

// JpaRepository já fornece métodos como findById, save, delete, etc.
// não precisamos escrever nada aqui — o Spring cria a implementação automaticamente
public interface ContaRepository extends JpaRepository<Conta, Long> {
    // Conta = tipo da entidade que este repositório gerencia
    // Long = tipo do id da entidade
}
