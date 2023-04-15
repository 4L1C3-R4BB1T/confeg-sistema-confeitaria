INSERT INTO endereco (cep_endereco, cod_estado, cod_cidade, bairro_endereco, rua_endereco, numero_endereco) VALUES
('29313685', 8, 16, 'Marbrasa', 'Rua José Paineiras', 20),
('20313384', 8, 17, 'Bairro das Flores', 'Rua ABC', 45),
('21213341', 8, 16, 'Aeroporto', 'Rua Alguma', 321),
('21234390', 19, 3658, 'Bairro Coqueiral', 'Rua das Araras', 450),
('20343817', 11, 1630, 'Bairro Castanheiras', 'Rua 1234', 86),
('12345678', 11, 1630, 'Centro', 'Rua Principal', 123),
('87654321', 8, 16, 'Jardim América', 'Avenida das Flores', 456),
('23456789', 8, 16, 'Vila Nova', 'Rua das Palmeiras', 789),
('45678901', 11, 1649, 'Jardim Botânico', 'Rua das Árvores', 567),
('89012345', 11, 1649, 'Barra da Tijuca', 'Avenida da Praia', 890),
('34567890', 11, 1649, 'Vila Madalena', 'Rua das Flores', 1234),
('01234567', 8, 78, 'Jardim Paulista', 'Rua Oscar Freire', 100),
('78901234', 8, 78, 'São Francisco', 'Avenida Beira-Mar', 200),
('23456790', 8, 78, 'Boa Viagem', 'Rua dos Navegantes', 300),
('12345678', 8, 77, 'Centro', 'Rua Principal', 123);

INSERT INTO tipo_funcionario (descricao_tipo_funcionario) VALUES
('Funcionário'),
('Gerente');

INSERT INTO cliente (nome_cliente, cpf_cliente, telefone_cliente, cod_endereco) VALUES 
('João Silva', '12345678900', '(11) 99999-9999', 1),
('Maria Santos', '23456789011', '(21) 88888-8888', 2),
('José Pereira', '34567890122', '(31) 77777-7777', 3),
('Ana Souza', '45678901233', '(41) 66666-6666', 4),
('Pedro Alves', '56789012344', '(51) 55555-5555', 5),
('Luciana Oliveira', '67890123455', '(61) 44444-4444', 6),
('Felipe Mendes', '78901234566', '(71) 33333-3333', 7),
('Larissa Costa', '89012345677', '(81) 22222-2222', 8),
('Ricardo Ferreira', '90123456788', '(91) 11111-1111', 9),
('Camila Rodrigues', '01234567899', '(32) 99999-8888', 10);

INSERT INTO funcionario (nome_funcionario, cpf_funcionario, telefone_funcionario, cod_tipo_funcionario, cod_endereco, email, senha) VALUES
('Pedro Silva', '12345678900', '(11) 99999-9999', 1, 11, 'funcionario1@confeg.com', 'funcionario123'),
('Ana Santos', '23456789011', '(21) 88888-8888', 1, 12, 'funcionario2@confeg.com', 'funcionario123'),
('José Pereira', '34567890122', '(31) 77777-7777', 1, 13, 'funcionario3@confeg.com', 'funcionario123'),
('Fernanda Alves', '45678901233', '(41) 66666-6666', 2, 14, 'gerente1@confeg.com', 'gerente123'),
('Mariana Rodrigues', '56789012344', '(51) 55555-5555', 1, 15, 'gerente2@confeg.com', 'gerente123');

INSERT INTO sabor (descricao_sabor) VALUES
('Chocolate'),
('Baunilha'),
('Cenoura'),
('Floresta Negra'),
('Red Velvet'),
('4 Leites');

INSERT INTO bolo (cod_sabor, descricao_bolo, peso_bolo, preco_bolo, data_fabricacao_bolo, data_vencimento_bolo) VALUES
(1, 'Bolo de Chocolate', 1.0, 30.0, '2023-04-14', '2023-04-16'),
(2, 'Um delicioso bolo de Baunilha', 1.5, 35.0, '2023-04-14', '2023-04-17'),
(3, 'Um delicioso bolo de Cenoura', 2.0, 40.0, '2023-04-14', '2023-04-18'),
(4, 'Um delicioso bolo Floresta Negra', 1.0, 30.0, '2023-04-14', '2023-04-16'),
(5, 'Um delicioso bolo Red Velvet', 2.0, 45.0, '2023-04-14', '2023-04-19'),
(6, 'Um delicioso bolo 4 Leites', 1.5, 35.0, '2023-04-14', '2023-04-17'),
(1, 'Um delicioso bolo de Chocolate', 1.0, 30.0, '2023-04-15', '2023-04-17'),
(2, 'Um delicioso bolo de Baunilha', 1.5, 35.0, '2023-04-15', '2023-04-18'),
(3, 'Um delicioso bolo de Cenoura', 2.0, 40.0, '2023-04-15', '2023-04-19'),
(4, 'Um delicioso bolo Floresta Negra', 1.0, 30.0, '2023-04-15', '2023-04-17');

INSERT INTO adicional (descricao_adicional, preco_adicional) VALUES
('Granulado', 1.00),
('Bombom', 2.50),
('Morango', 1.40),
('Chocolate', 5.00),
('Brigadeiro', 2.30);

INSERT INTO metodo_pagamento (descricao_metodo_pagamento) VALUES 
('Dinheiro'),
('Boleto Bancário'),
('Pix'),
('Cartão de Débito/ Crédito');

INSERT INTO pedido (cod_cliente, cod_funcionario, data_pedido, cod_metodo_pagamento, status_pedido, observacao_pedido) VALUES
(1, 3, '2022-01-14', 1, 'CONCLUIDO', null),
(2, 2, '2022-01-14', 2, 'PENDENTE', 'Com cobertura extra.'),
(3, 1, '2022-04-14', 1, 'CONCLUIDO', 'Sem chantilly.'),
(4, 5, '2022-06-14', 3, 'PENDENTE', 'Sabor Floresta Negra.'),
(5, 4, '2022-09-14', 2, 'CANCELADO', 'Peso de 2kg.'),
(6, 3, '2023-01-15', 1, 'CONCLUIDO', 'Com cobertura de chocolate.'),
(7, 2, '2023-01-15', 2, 'PENDENTE', 'Sem açúcar.'),
(8, 1, '2023-02-15', 1, 'CONCLUIDO', 'Sabor Red Velvet.'),
(9, 5, '2023-03-15', 3, 'PENDENTE', 'Com morangos.'),
(10, 4, '2023-03-15', 2, 'CANCELADO', 'Sem glúten.'),
(9, 2, '2023-03-15', 1, 'PENDENTE', null),
(2, 3, '2023-04-15', 2, 'CANCELADO', 'Com cobertura de baunilha.'),
(3, 4, '2023-04-15', 1, 'CONCLUIDO', null),
(4, 1, '2023-04-16', 3, 'PENDENTE', 'Sabor 4 Leites.'),
(5, 5, '2023-04-16', 2, 'CONCLUIDO', 'Peso de 1kg. Sem açúcar.');

INSERT INTO pedido_bolo (cod_pedido, cod_bolo, quantidade_bolo) VALUES
(1, 2, 2),
(1, 4, 1),
(1, 5, 3),
(2, 3, 1),
(2, 5, 1),
(3, 1, 2),
(4, 3, 3),
(4, 6, 1),
(5, 1, 1),
(6, 2, 1),
(6, 3, 1),
(6, 5, 2),
(7, 1, 1),
(7, 2, 1),
(8, 3, 2),
(8, 4, 1),
(9, 1, 3),
(10, 2, 2),
(11, 4, 2),
(12, 2, 1),
(12, 5, 1),
(12, 6, 1),
(13, 5, 2),
(14, 2, 2),
(14, 4, 1),
(15, 1, 1),
(15, 3, 1),
(15, 6, 3);

INSERT INTO pedido_adicional (cod_pedido, cod_bolo, cod_adicional, quantidade_adicional) VALUES
(10, 2, 2, 10),
(10, 2, 5, 10),
(9, 1, 1, 3),
(9, 1, 2, 2),
(4, 6, 4, 1),
(1, 2, 1, 2);

INSERT INTO confimacao_pedido (cod_cliente, cod_pedido, data_confirmacao_pedido) VALUES
(1, 1, '2022-01-17'),
(3, 13, '2023-04-16'),
(3, 3, '2022-04-18'),
(6, 6, '2023-01-18'),
(5, 15, '2023-04-16');

INSERT INTO ingrediente (descricao_ingrediente) VALUES
('Farinha'),
('Ovos'),
('Leite'),
('Manteiga'),
('Açúcar'),
('Morango'),
('Banana'),
('Canela');

INSERT INTO pedido_compra (cod_funcionario, data_pedido_compra, status_pedido_compra) VALUES
(1, '2023-02-01', 'PENDENTE'),
(2, '2023-02-02', 'PENDENTE'),
(3, '2023-02-03', 'CONCLUIDO'),
(4, '2023-02-04', 'CANCELADO'),
(5, '2023-02-05', 'PENDENTE'),
(1, '2023-02-06', 'CONCLUIDO');

INSERT INTO pedido_compra_ingrediente (cod_pedido_compra, cod_ingrediente, quantidade_ingrediente) VALUES
(1, 1, 3),
(1, 4, 2),
(2, 2, 10),
(3, 6, 30),
(4, 7, 12),
(4, 8, 3),
(4, 1, 4),
(5, 1, 4),
(5, 3, 10),
(6, 5, 2);
