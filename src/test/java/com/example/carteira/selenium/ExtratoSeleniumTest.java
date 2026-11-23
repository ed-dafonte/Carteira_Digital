package com.example.carteira.selenium; // define o pacote onde esta classe de teste está localizada

import org.junit.jupiter.api.AfterEach; // importa a anotação que marca o método que roda depois de cada teste
import org.junit.jupiter.api.BeforeEach; // importa a anotação que marca o método que roda antes de cada teste
import org.junit.jupiter.api.Test; // importa a anotação que marca um método como teste
import org.openqa.selenium.By; // importa a classe que permite localizar elementos na página (por id, nome, etc.)
import org.openqa.selenium.WebDriver; // importa a interface principal do Selenium que controla o navegador
import org.openqa.selenium.WebElement; // importa a classe que representa um elemento da página HTML
import org.openqa.selenium.chrome.ChromeDriver; // importa o driver que controla o Chrome
import org.openqa.selenium.chrome.ChromeOptions; // importa as opções de configuração do Chrome

import java.io.File; // importa a classe que representa um arquivo no sistema operacional

import static org.junit.jupiter.api.Assertions.assertEquals; // importa o método que verifica se dois valores são iguais

class ExtratoSeleniumTest {

    private WebDriver driver; // variável que representa o navegador controlado pelo Selenium

    @BeforeEach // roda antes de cada teste — abre o navegador
    void abrirNavegador() {
        ChromeOptions options = new ChromeOptions(); // cria as opções de configuração do Chrome
        options.addArguments("--no-sandbox"); // desativa restrições de segurança para funcionar em ambientes de teste
        options.addArguments("--disable-dev-shm-usage"); // evita problemas de memória em ambientes de teste

        driver = new ChromeDriver(options); // abre o Chrome com as opções configuradas
    }

    @Test // marca este método como um caso de teste
    void deveExibirResultadoAoConsultarConta() throws InterruptedException { // throws InterruptedException por causa do Thread.sleep

        // monta o caminho absoluto para o arquivo HTML criado no projeto
        File arquivo = new File("src/main/resources/static/extrato.html");

        // abre o arquivo HTML diretamente no navegador (sem precisar subir o servidor)
        driver.get("file:///" + arquivo.getAbsolutePath().replace("\\", "/"));

        WebElement campoConta = driver.findElement(By.id("idConta")); // localiza o campo de texto pelo id "idConta"

        campoConta.sendKeys("1"); // digita o número 1 no campo, simulando a ação do usuário

        WebElement botao = driver.findElement(By.id("btnConsultar")); // localiza o botão pelo id "btnConsultar"

        botao.click(); // clica no botão, simulando a ação do usuário

        WebElement resultado = driver.findElement(By.id("resultado")); // localiza o parágrafo onde o resultado aparece

        assertEquals("Consultando conta: 1", resultado.getText()); // verifica se o texto exibido é o esperado

        Thread.sleep(3000); // pausa de 3 segundos para visualizar o resultado antes de fechar o navegador
    }

    @AfterEach // roda depois de cada teste — fecha o navegador
    void fecharNavegador() {
        driver.quit(); // encerra o navegador e libera os recursos
    }
}
