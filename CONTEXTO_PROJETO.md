# Projeto Final — Testes Automatizados I: Carteira Digital (e-Wallet)

## Visão Geral

API REST em Java/Spring Boot simulando uma carteira digital simples. O objetivo central **não é a aplicação em si**, mas a suíte de testes e a demonstração do ciclo TDD via histórico de commits no GitHub.

---

## Domínio

**Entidade:** `Conta`
| Campo | Tipo |
|--------|--------|
| id | Long |
| titular | String |
| saldo | Double |

**Arquitetura:** 3 camadas — `Repository` → `Service` → `Controller`

### Por que 3 camadas?
- **`model`** — é o dado. Apenas descreve como a `Conta` é: quais campos ela tem. Não faz nada, só representa.
- **`repository`** — é o acesso ao banco. Só sabe buscar, salvar e deletar contas. Não conhece regra de negócio nenhuma.
- **`service`** — é a regra de negócio. É aqui que fica a lógica: "não pode depositar valor negativo", "saldo insuficiente". Ele usa o `repository` para buscar os dados, aplica as regras, e devolve o resultado.

A ideia é simples: **cada camada faz uma coisa só**. Se amanhã você trocar o banco de dados, só muda o `repository`. Se mudar uma regra de negócio, só mexe no `service`. Nada fica misturado.

---

## Regras de Negócio

| Operação | Regra |
|---|---|
| Consultar Saldo | Retorna o saldo atual da conta |
| Realizar Depósito | Valor deve ser > 0, senão lança `IllegalArgumentException` |
| Realizar Transferência | Saldo de origem deve ser suficiente, senão lança `SaldoInsuficienteException` (exceção personalizada) |

---

## Testes Essenciais

### Unitários — `ContaService` (JUnit 5 + Mockito)

| # | Método | Cenário | Status |
|---|--------|---------|--------|
| 1 | `consultarSaldo` | Deve retornar o saldo quando a conta existe | ✅ GREEN |
| 2 | `depositar` | Deve depositar valor válido e atualizar o saldo | ✅ GREEN |
| 3 | `depositar` | Deve lançar `IllegalArgumentException` ao depositar valor zero | ✅ GREEN |
| 4 | `depositar` | Deve lançar `IllegalArgumentException` ao depositar valor negativo | ✅ GREEN |
| 5 | `transferir` | Deve transferir valor quando o saldo é suficiente | ✅ GREEN |
| 6 | `transferir` | Deve lançar `SaldoInsuficienteException` quando o saldo é insuficiente | ✅ GREEN |

### Integração — `ContaController` (MockMvc)

| # | Endpoint | Cenário | Status esperado | Status |
|---|----------|---------|-----------------|--------|
| 7 | `GET /contas/{id}/saldo` | Conta existe | `200 OK` | ⏳ pendente |
| 8 | `POST /contas/{id}/depositar` | Valor válido | `200 OK` | ⏳ pendente |
| 9 | `POST /contas/{id}/depositar` | Valor inválido | `400 Bad Request` | ⏳ pendente |
| 10 | `POST /contas/transferir` | Saldo suficiente | `200 OK` | ⏳ pendente |
| 11 | `POST /contas/transferir` | Saldo insuficiente | `400 Bad Request` | ⏳ pendente |

> ⏸️ **Selenium e JaCoCo fora do escopo por enquanto. Serão implementados em etapa futura.**

---

## Critério Obrigatório: TDD + Git

O histórico de commits **deve** evidenciar o ciclo Red → Green → Refactor:

| Commit | O que deve conter |
|--------|-------------------|
| **RED** | Classe de teste com a validação escrita — sem implementação (teste falha ou não compila) |
| **GREEN** | Implementação mínima do método para o teste passar |
| **REFACTOR** | Limpeza/melhoria do código, testes continuam passando |

> ⚠️ Projetos entregues em um único commit perdem pontos na avaliação do TDD.

---

## Estrutura de Arquivos Criados

```
src/
  main/java/com/example/carteira/
    model/
      Conta.java                              ✅ criado
    repository/
      ContaRepository.java                    ✅ criado
    service/
      ContaService.java                       ✅ criado (consultarSaldo + depositar + transferir implementados)
      SaldoInsuficienteException.java         ✅ criado

  test/java/com/example/carteira/
    service/
      ContaServiceTest.java                   ✅ criado (6 testes — todos GREEN)
```

**Ainda falta criar:**
- `controller/ContaController.java` — necessário para os testes de integração 7 a 11
- `test/.../ContaControllerTest.java` — testes de integração com MockMvc

---

## Decisões Técnicas Registradas

- **Sem Lombok** — getters, setters e construtores escritos na mão, para ficar explícito e fácil de entender.
- **Sem interfaces desnecessárias** — `ContaService` direto, sem `ContaServiceInterface`.
- **Sem DTOs** — a própria entidade `Conta` é usada nos endpoints.
- **Banco H2 em memória** — sem PostgreSQL, Docker ou configuração externa. Dados existem só enquanto a aplicação está rodando.
- **`@Autowired` no campo** — escolha didática para manter o código simples. A alternativa com `private final` + construtor é a melhor prática do mercado, mas exige entender construtores antes.
- **`@BeforeEach`** — usado no arquivo de testes para recriar o objeto `Conta` antes de cada teste, evitando que um teste contamine o outro.
- **Todo código comentado linha a linha** — em português, simples e direto, como se o leitor estivesse aprendendo Java.

---

## Stack

- Java 17 / Spring Boot 3.x
- JUnit 5
- Mockito
- MockMvc (Spring Test)
- H2 (banco em memória)
- GitHub (histórico de commits obrigatório)

---

## Diretriz: Manter o Código o Mais Simples Possível

> O projeto deve ser escrito como se fosse por um iniciante em Java. Sem over-engineering.

- **Sem Lombok, sem DTOs, sem interfaces desnecessárias** — só o que o enunciado pede.
- **H2 em memória** — zero configuração de banco de dados externo.
- **Estrutura de pacotes flat e óbvia** — `model`, `repository`, `service`, `controller`, cada um com um arquivo só.
- **Nomes em português** seguindo o próprio enunciado (`titular`, `saldo`, `depositar`).
- **Testes com um método por cenário**, nome descritivo em português.
- **Tratamento de exceção direto no controller** com um `@ExceptionHandler` simples, sem criar uma infraestrutura de erros elaborada.
- **Todo código deve ser comentado linha a linha** — cada linha com um comentário curto e simples explicando o que ela faz, em português.
