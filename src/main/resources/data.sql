-- este arquivo é executado automaticamente pelo Spring ao iniciar a aplicação
-- ele insere dados iniciais no banco H2 para testes manuais pelo navegador

-- insere a conta 1 com titular Ana e saldo de 1500.00
INSERT INTO conta (id, titular, saldo) VALUES (1, 'Ana Silva', 1500.00);

-- insere a conta 2 com titular Carlos e saldo de 3200.50
INSERT INTO conta (id, titular, saldo) VALUES (2, 'Carlos Souza', 3200.50);
