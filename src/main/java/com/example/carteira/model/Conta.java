package com.example.carteira.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//É o dado. Apenas descreve como a Conta é: quais campos ela tem. Não faz nada, só representa.

@Entity // diz ao Spring/JPA que essa classe representa uma tabela no banco de dados
public class Conta {

    @Id // marca o campo id como chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // o banco gera o id automaticamente (auto incremento)
    private Long id; // identificador único da conta

    private String titular; // nome do dono da conta

    private Double saldo; // valor disponível na conta

    // construtor vazio obrigatório para o JPA funcionar
    public Conta() {
    }

    // construtor com todos os campos para facilitar a criação de objetos nos testes
    public Conta(Long id, String titular, Double saldo) {
        this.id = id; // atribui o id recebido ao campo da classe
        this.titular = titular; // atribui o titular recebido ao campo da classe
        this.saldo = saldo; // atribui o saldo recebido ao campo da classe
    }

    // retorna o valor do id
    public Long getId() {
        return id;
    }

    // define o valor do id
    public void setId(Long id) {
        this.id = id;
    }

    // retorna o nome do titular
    public String getTitular() {
        return titular;
    }

    // define o nome do titular
    public void setTitular(String titular) {
        this.titular = titular;
    }

    // retorna o saldo atual
    public Double getSaldo() {
        return saldo;
    }

    // define o saldo
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
