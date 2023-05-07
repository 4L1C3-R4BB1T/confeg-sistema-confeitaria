# 🍰 CONFEG - Sistema de Gerenciamento de Confeitaria

Este é um sistema desenvolvido em Java que tem como objetivo auxiliar na gestão de uma confeitaria, permitindo o cadastro de clientes, funcionários e produtos, bem como a realização de vendas, pedidos de ingredientes e controle de estoque.

--- 

## ⚙️ Como executar o projeto

1. Clone este repositório em sua máquina local
```bash
git clone https://github.com/4L1C3-R4BB1T/confeg-sistema-confeitaria.git
```
2. Abra o projeto em sua IDE de preferência
3. Associe as bibliotecas do projeto ao classpath
4. Importe o arquivo **script_banco.sql** para a população do banco de dados  
  4.1 Obs.: os dados do banco de dados podem ser alterados no arquivo **configuracoes.properties**
5. Execute o arquivo **App.java** localizado no diretório **./src/aplicacao/App.java**

---

## 💻 Funcionalidades do Sistema

O CONFEG possui as seguintes funcionalidades:

🔸 Cadastro de clientes, incluindo nome, endereço, telefone.  
🔸 Cadastro de funcionários, nome, cargo.  
🔸 Cadastro de produtos, incluindo nome, descrição, preço de venda e quantidade em estoque.  
🔸 Realização de vendas, permitindo a seleção dos produtos e cálculo do total a ser pago.  
🔸 Realizaçao de pedidos de ingredientes, permitindo a seleção dos ingredientes e cálculo do total a ser pago.  
🔸 Controle de estoque, atualizando a quantidade de produtos após cada venda realizada.  

---

## 🔧 Tecnologias Utilizadas 

🔸 Java  
🔸 JavaFX  
🔸 PostgreSQL  
🔸 JasperReports  
