CREATE TABLE usuarios (
    codigo serial not null,
    email varchar not null,
    senha varchar not null,
    constraint pk_usuarios
        primary key (codigo)
);

INSERT INTO usuarios (email, senha) VALUES ('admin@admin', '123');