package com.example.carteira.controller; // define o pacote onde esta classe está localizada

import com.example.carteira.service.ContaService; // importa o serviço que contém as regras de negócio
import com.example.carteira.service.SaldoInsuficienteException; // importa a exceção personalizada de saldo insuficiente
import org.springframework.beans.factory.annotation.Autowired; // importa a anotação de injeção automática
import org.springframework.http.ResponseEntity; // importa a classe que representa uma resposta HTTP (com status e corpo)
import org.springframework.web.bind.annotation.ExceptionHandler; // importa a anotação que captura exceções lançadas pelos métodos
import org.springframework.web.bind.annotation.GetMapping; // importa a anotação para mapear requisições GET
import org.springframework.web.bind.annotation.PathVariable; // importa a anotação que lê variáveis da URL
import org.springframework.web.bind.annotation.PostMapping; // importa a anotação para mapear requisições POST
import org.springframework.web.bind.annotation.RequestParam; // importa a anotação que lê parâmetros enviados na requisição
import org.springframework.web.bind.annotation.RestController; // importa a anotação que marca esta classe como um controller REST

@RestController // diz ao Spring que esta classe recebe requisições HTTP e devolve respostas em formato texto/JSON
public class ContaController {

    @Autowired // o Spring injeta automaticamente uma instância do serviço aqui
    private ContaService contaService;

    @GetMapping("/contas/{id}/saldo") // mapeia requisições GET para a URL /contas/{id}/saldo
    public ResponseEntity<Double> consultarSaldo(@PathVariable Long id) { // @PathVariable lê o {id} da URL

        Double saldo = contaService.consultarSaldo(id); // chama o serviço para buscar o saldo da conta

        return ResponseEntity.ok(saldo); // retorna o saldo com status 200 OK
    }

    @PostMapping("/contas/{id}/depositar") // mapeia requisições POST para a URL /contas/{id}/depositar
    public ResponseEntity<Void> depositar(@PathVariable Long id, // lê o {id} da URL
                                          @RequestParam Double valor) { // lê o parâmetro "valor" enviado na requisição

        contaService.depositar(id, valor); // chama o serviço para realizar o depósito

        return ResponseEntity.ok().build(); // retorna status 200 OK sem corpo
    }

    @PostMapping("/contas/transferir") // mapeia requisições POST para a URL /contas/transferir
    public ResponseEntity<Void> transferir(@RequestParam Long idOrigem, // lê o parâmetro "idOrigem" da requisição
                                           @RequestParam Long idDestino, // lê o parâmetro "idDestino" da requisição
                                           @RequestParam Double valor) { // lê o parâmetro "valor" da requisição

        contaService.transferir(idOrigem, idDestino, valor); // chama o serviço para realizar a transferência

        return ResponseEntity.ok().build(); // retorna status 200 OK sem corpo
    }

    @ExceptionHandler(IllegalArgumentException.class) // captura qualquer IllegalArgumentException lançada neste controller
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) { // recebe a exceção como parâmetro

        return ResponseEntity.badRequest().body(e.getMessage()); // retorna status 400 Bad Request com a mensagem de erro
    }

    @ExceptionHandler(SaldoInsuficienteException.class) // captura qualquer SaldoInsuficienteException lançada neste controller
    public ResponseEntity<String> handleSaldoInsuficiente(SaldoInsuficienteException e) { // recebe a exceção como parâmetro

        return ResponseEntity.badRequest().body(e.getMessage()); // retorna status 400 Bad Request com a mensagem de erro
    }
}
