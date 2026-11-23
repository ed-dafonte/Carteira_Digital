package com.example.carteira; // define o pacote raiz da aplicação

import org.springframework.boot.SpringApplication; // importa a classe que inicializa a aplicação Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication; // importa a anotação que configura automaticamente o Spring Boot

@SpringBootApplication // marca esta classe como ponto de entrada da aplicação — o Spring vai escanear todos os pacotes abaixo deste
public class CarteiraApplication {

    public static void main(String[] args) { // método principal — é aqui que a aplicação começa
        SpringApplication.run(CarteiraApplication.class, args); // inicia o servidor com todas as configurações do Spring Boot
    }
}
