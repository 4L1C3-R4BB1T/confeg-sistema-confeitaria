DROP TABLE IF EXISTS pedido_compra_ingrediente CASCADE;
DROP TABLE IF EXISTS pedido_compra CASCADE;
DROP TABLE IF EXISTS ingrediente CASCADE;
DROP TABLE IF EXISTS confimacao_pedido CASCADE;
DROP TABLE IF EXISTS pedido_adicional CASCADE;
DROP TABLE IF EXISTS pedido_bolo CASCADE;
DROP TABLE IF EXISTS pedido CASCADE;
DROP TABLE IF EXISTS metodo_pagamento CASCADE;
DROP TABLE IF EXISTS adicional CASCADE;
DROP TABLE IF EXISTS bolo CASCADE;
DROP TABLE IF EXISTS sabor CASCADE;
DROP TABLE IF EXISTS cliente CASCADE;
DROP TABLE IF EXISTS funcionario CASCADE;
DROP TABLE IF EXISTS endereco CASCADE;
DROP TABLE IF EXISTS tipo_funcionario CASCADE;
DROP TABLE IF EXISTS cidade CASCADE;
DROP TABLE IF EXISTS estado CASCADE;

/*--------------------------------------------------------------*/

CREATE TABLE estado (
    cod_estado      SERIAL      NOT NULL,   
    nome_estado     VARCHAR(75) NOT NULL,
    sigla_estado    CHAR(2)     NOT NULL,
    CONSTRAINT pk_estado
        PRIMARY KEY (cod_estado)
);

CREATE TABLE cidade (
    cod_cidade      SERIAL          NOT NULL,
    nome_cidade     VARCHAR(120)    NOT NULL,
    cod_estado      INTEGER         NOT NULL,
    CONSTRAINT pk_cidade
        PRIMARY KEY (cod_cidade),
    CONSTRAINT fk_cidade_estado 
        FOREIGN KEY (cod_estado)
        REFERENCES estado(cod_estado)
);

CREATE TABLE endereco (
    cod_endereco    SERIAL      NOT NULL,
    cep_endereco    CHAR(8)     NOT NULL,
    cod_estado      INTEGER     NOT NULL,
    cod_cidade      INTEGER     NOT NULL,
    bairro_endereco VARCHAR(30) NOT NULL,
    rua_endereco    VARCHAR(30) NOT NULL,
    numero_endereco INTEGER     NOT NULL,
    CONSTRAINT pk_endereco
        PRIMARY KEY (cod_endereco),
    CONSTRAINT fk_endereco_estado
        FOREIGN KEY (cod_estado)
        REFERENCES estado(cod_estado),
    CONSTRAINT fk_endereco_cidade
        FOREIGN KEY (cod_cidade)
        REFERENCES cidade(cod_cidade)
);

CREATE TABLE tipo_funcionario (
    cod_tipo_funcionario          SERIAL      NOT NULL,
    descricao_tipo_funcionario    VARCHAR(30) NOT NULL,
    CONSTRAINT pk_tipo_funcionario
        PRIMARY KEY (cod_tipo_funcionario)
);

CREATE TABLE cliente (
    cod_cliente         SERIAL      NOT NULL,
    nome_cliente        VARCHAR(60) NOT NULL,
    cpf_cliente         CHAR(11)    NOT NULL,
    telefone_cliente    CHAR(15)    NOT NULL,
    cod_endereco        INTEGER     NOT NULL,
    CONSTRAINT pk_cliente
        PRIMARY KEY (cod_cliente),
    CONSTRAINT fk_cliente_endereco
        FOREIGN KEY (cod_endereco)
        REFERENCES endereco(cod_endereco)
);

CREATE TABLE funcionario (
    cod_funcionario         SERIAL      NOT NULL,
    nome_funcionario        VARCHAR(60) NOT NULL,
    cpf_funcionario         CHAR(11)    NOT NULL,
    telefone_funcionario    CHAR(15)    NOT NULL,
    cod_tipo_funcionario    INTEGER     NOT NULL, 
    cod_endereco            INTEGER     NOT NULL, 
    email                   VARCHAR(30) NOT NULL,
    senha                   VARCHAR(15) NOT NULL,
    CONSTRAINT pk_funcionario
        PRIMARY KEY (cod_funcionario),
    CONSTRAINT fk_funcionario_tipo_funcionario
        FOREIGN KEY (cod_tipo_funcionario)
        REFERENCES tipo_funcionario(cod_tipo_funcionario),
    CONSTRAINT fk_funcionario_endereco
        FOREIGN KEY (cod_endereco)
        REFERENCES endereco(cod_endereco)
);

CREATE TABLE sabor (
    cod_sabor       SERIAL      NOT NULL,
    descricao_sabor VARCHAR(20) NOT NULL,
    CONSTRAINT pk_sabor
        PRIMARY KEY (cod_sabor)
);

CREATE TABLE bolo (
    cod_bolo                SERIAL          NOT NULL,
    cod_sabor               INTEGER         NOT NULL,
    descricao_bolo          VARCHAR(200)    NOT NULL,
    peso_bolo               NUMERIC(10,2)   NOT NULL,
    preco_bolo              NUMERIC(10,2)   NOT NULL,
    data_fabricacao_bolo    DATE            NOT NULL,
    data_vencimento_bolo    DATE            NOT NULL,
    CONSTRAINT pk_bolo
        PRIMARY KEY (cod_bolo),
    CONSTRAINT fk_bolo_sabor
        FOREIGN KEY (cod_sabor)
        REFERENCES sabor(cod_sabor)
);

CREATE TABLE adicional (
    cod_adicional       SERIAL          NOT NULL,
    descricao_adicional VARCHAR(60)     NOT NULL,
    preco_adicional     NUMERIC(10,2)   NOT NULL,
    CONSTRAINT pk_adicional
        PRIMARY KEY (cod_adicional)
);

CREATE TABLE metodo_pagamento (
    cod_metodo_pagamento        SERIAL      NOT NULL,
    descricao_metodo_pagamento  VARCHAR(30) NOT NULL,
    CONSTRAINT pk_metodo_pagamento
        PRIMARY KEY (cod_metodo_pagamento)
);

CREATE TABLE pedido (
    cod_pedido              SERIAL      NOT NULL,
    cod_cliente             INTEGER     NOT NULL,
    cod_funcionario         INTEGER     NOT NULL,
    data_pedido             DATE        NOT NULL,
    cod_metodo_pagamento    INTEGER     NOT NULL,
    status_pedido           VARCHAR(10) NOT NULL,
    observacao_pedido       VARCHAR(60) NULL,
    CONSTRAINT pk_pedido
        PRIMARY KEY (cod_pedido),
    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (cod_cliente)
        REFERENCES cliente(cod_cliente),
    CONSTRAINT fk_pedido_funcionario
        FOREIGN KEY (cod_funcionario)
        REFERENCES funcionario(cod_funcionario),
    CONSTRAINT fk_pedido_metodo_pagamento
        FOREIGN KEY (cod_metodo_pagamento)
        REFERENCES metodo_pagamento(cod_metodo_pagamento)
);

CREATE TABLE pedido_bolo (
    cod_pedido      INTEGER NOT NULL,
    cod_bolo        INTEGER NOT NULL,
    quantidade_bolo INTEGER NOT NULL,
    CONSTRAINT pk_pedido_item
        PRIMARY KEY (cod_pedido, cod_bolo),
    CONSTRAINT fk_pedido_bolo_pedido
        FOREIGN KEY (cod_pedido)
        REFERENCES pedido(cod_pedido),
    CONSTRAINT fk_pedido_bolo_bolo
        FOREIGN KEY (cod_bolo)
        REFERENCES bolo(cod_bolo)
);

CREATE TABLE pedido_adicional (
    cod_pedido              INTEGER NOT NULL,
    cod_bolo                INTEGER NOT NULL,
    cod_adicional           INTEGER NOT NULL,
    quantidade_adicional    INTEGER NOT NULL,
    CONSTRAINT pk_pedido_adicional
        PRIMARY KEY (cod_pedido, cod_bolo, cod_adicional),
    CONSTRAINT fk_pedido_adicional_pedido_bolo
        FOREIGN KEY (cod_pedido, cod_bolo)
        REFERENCES pedido_bolo (cod_pedido, cod_bolo),
    CONSTRAINT fk_pedido_adicional_adicional
        FOREIGN KEY (cod_adicional)
        REFERENCES adicional(cod_adicional)        
);

CREATE TABLE confimacao_pedido (
    cod_cliente             INTEGER NOT NULL,
    cod_pedido              INTEGER NOT NULL,
    data_confirmacao_pedido DATE    NOT NULL,
    CONSTRAINT pk_confirmacao_pedido
        PRIMARY KEY (cod_cliente, cod_pedido),
    CONSTRAINT fk_confimacao_pedido_cliente
        FOREIGN KEY (cod_cliente)
        REFERENCES cliente(cod_cliente),
    CONSTRAINT fk_confimacao_pedido_pedido
        FOREIGN KEY (cod_pedido)
        REFERENCES pedido(cod_pedido)
);

CREATE TABLE ingrediente (
    cod_ingrediente         SERIAL      NOT NULL,
    descricao_ingrediente   VARCHAR(30) NOT NULL,
    CONSTRAINT pk_ingrediente
        PRIMARY KEY (cod_ingrediente)
);

CREATE TABLE pedido_compra (
    cod_pedido_compra       SERIAL      NOT NULL,
    cod_funcionario         INTEGER     NOT NULL,
    data_pedido_compra      DATE        NOT NULL,
    status_pedido_compra    VARCHAR(10) NOT NULL,
    CONSTRAINT pk_pedido_compra
        PRIMARY KEY (cod_pedido_compra),
    CONSTRAINT fk_pedido_compra_ingrediente_funcionario
        FOREIGN KEY (cod_funcionario)
        REFERENCES funcionario(cod_funcionario)
);

CREATE TABLE pedido_compra_ingrediente (
    cod_pedido_compra       INTEGER NOT NULL,
    cod_ingrediente         INTEGER NOT NULL,
    quantidade_ingrediente  INTEGER NOT NULL,
    CONSTRAINT pk_pedido_compra_ingrediente 
        PRIMARY KEY (cod_pedido_compra, cod_ingrediente),
    CONSTRAINT fk_pedido_compra_ingrediente_pedido_compra
        FOREIGN KEY (cod_pedido_compra)
        REFERENCES pedido_compra(cod_pedido_compra),
    CONSTRAINT fk_pedido_compra_ingrediente_ingrediente
        FOREIGN KEY (cod_ingrediente)
        REFERENCES ingrediente(cod_ingrediente)
);
