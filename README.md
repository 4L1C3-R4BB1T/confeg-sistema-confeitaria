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
4. Altere os dados do banco de dados para seu usuário e senha para a população do banco de dados  
  4.1 Obs.: os dados do banco de dados podem ser alterados no arquivo **configuracoes.properties**
5. Execute o arquivo **App.java** localizado no diretório **./src/aplicacao/App.java**

---

|                                |                            |
| ------------------------------ | -------------------------- |
| 👩‍💼 Login como **Funcionário** | 🧑‍💼 Login como **Gerente**  |
| ✉️ **Email:** test            | ✉️ **Email:** admin        |
| 🔑 **Senha:** test            | 🔑 **Senha:** admin        |

---

## 💻 Funcionalidades do Sistema

O CONFEG possui as seguintes funcionalidades:

🔸 Cadastro de clientes, com nome, endereço e telefone.  
🔸 Cadastro de funcionários, com nome, endereço, telefone e tipo.  
🔸 Cadastro de bolos, com descrição, peso, preço de venda, data de fabricação e validade.  
🔸 Realização de vendas, permitindo a seleção dos produtos e cálculo do total a ser pago.  
🔸 Realizaçao de pedidos de ingredientes, permitindo a seleção dos ingredientes e cálculo do total a ser pago.  
🔸 Confirmação de pedidos realizados.
🔸 Chat interno.

---

## 🔧 Tecnologias Utilizadas 

🔸 Java  
🔸 JavaFX  
🔸 PostgreSQL  
🔸 JasperReports  
