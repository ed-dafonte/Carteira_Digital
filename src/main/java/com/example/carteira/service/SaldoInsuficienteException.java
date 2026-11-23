package com.example.carteira.service; // define o pacote onde esta classe está localizada

// exceção personalizada criada para representar o erro de saldo insuficiente numa transferência
// como essa situação é específica do nosso negócio, criamos nossa própria exceção em vez de usar uma genérica
public class SaldoInsuficienteException extends RuntimeException { // herda de RuntimeException para ser uma exceção não verificada

    public SaldoInsuficienteException(String mensagem) { // construtor que recebe a mensagem de erro
        super(mensagem); // passa a mensagem para a classe pai (RuntimeException)
    }
}
