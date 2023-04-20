package controladores.principal;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aplicacao.App;
import controladores.crudBolo.CrudBoloControlador;
import controladores.crudCliente.CrudClienteControlador;
import controladores.crudConfirmarPedido.ConfirmarPedidoControlador;
import controladores.crudConfirmarPedido.CrudConfirmarPedidoControlador;
import controladores.crudFuncionario.CrudFuncionarioControlador;
import controladores.crudPedidos.ListarPedidosControlador;
import controladores.crudPedidos.RegistrarPedidoControlador;
import controladores.login.LoginControlador;
import controladores.principal.bolo.BoloControlador;
import controladores.principal.perfil.PerfilControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.entidadeDAO.BoloDAO;
import modelos.entidades.Funcionario;

// TELA PRINCIPAL DA APLICAÇÃO
public class PrincipalControlador {

    @FXML
    private AnchorPane menuPedidos;

    @FXML
    private AnchorPane menuUsuario;

    @FXML 
    private ImageView menuUsuarioSeta;

    @FXML
    private HBox areaBotaoMenu;

    @FXML 
    private Button areaFuncionario;

    @FXML 
    private FlowPane areaBolo;

    @FXML 
    private HBox areaDeAlerta;

    // Botoes do menu
    @FXML
    private Button administrador;
    @FXML 
    private Button principal;
    @FXML 
    private Button pedidos; 
    @FXML
    private Button bolos;
    @FXML 
    private Button clientes;

    private Button[] botoes;

    @FXML
    private ImageView areaImagem;


    private Stage tela;
    
    private List<Stage> telas = new ArrayList<>();

    private BoloDAO boloDAO = new BoloDAO(App.conexao);

    private Funcionario conectado;

    // Verificar se clicou nos botoes para que não haja duplicidade
    private boolean clicouPerfil = false;
    private boolean clicouBolo = false;
    private boolean clicouCliente = false;
    private boolean clicouFuncionario = false;
    private boolean clicouListarPedido = false;
    private boolean clicouBotaoPedir = false;
    private boolean clicouBotaoConfirmarPedido = false;

    @FXML
    public void abrirMenuPedidos(ActionEvent event) {
        removerBotaoAtivo();
        if (menuPedidos.isVisible()) {
            App.removerEfeitoSuave(menuPedidos);
        } else {
            adicionarAtivoNoBotao(pedidos);
            App.adicionaEfeitoSuave(menuPedidos);
        }
    }

    @FXML
    public void abrirMenuUsuario(ActionEvent event) {
        if (menuUsuario.isVisible()) {
            App.removerEfeitoSuave(menuUsuario);
            String caminhoSetaBaixo = PrincipalControlador.class.getResource("/telas/principal/images/seta_baixo.png").toExternalForm();
            menuUsuarioSeta.setImage(new Image(caminhoSetaBaixo));
        } else {
            App.adicionaEfeitoSuave(menuUsuario);
            String caminhoSetaCima = PrincipalControlador.class.getResource("/telas/principal/images/seta_cima.png").toExternalForm();
            menuUsuarioSeta.setImage(new Image(caminhoSetaCima));
        }
    }

    @FXML
    public void fecharTela(MouseEvent event) {
        fecharTodasTelas();
    }

    @FXML 
    public void minimizarTela(MouseEvent event) {
        tela.setIconified(true);
    }

 

    @FXML
    public void verPerfil(MouseEvent event) throws Exception {
        if (!clicouPerfil) {
            carregarTelaPerfil(conectado);
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }

    @FXML
    public void irTelaBolo(ActionEvent event) throws Exception {
        removerBotaoAtivo();
        adicionarAtivoNoBotao(bolos);
       if (!clicouBolo) {
            carregarTelaCrudBolo();
       } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
       }
    }

    @FXML
    public void irParaTelaFuncionarios(ActionEvent event) throws Exception {
        removerBotaoAtivo();
        adicionarAtivoNoBotao(administrador);
        if (!clicouFuncionario) {
            carregarTelaCrudFuncionario();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }

    @FXML 
    public void irParaTelaPrincipal(ActionEvent event) {
        removerBotaoAtivo();
        adicionarAtivoNoBotao(principal);
    }

    @FXML 
    public void irParaTelaClientes(ActionEvent event) throws Exception {
        removerBotaoAtivo();
        adicionarAtivoNoBotao(clientes);
        if (!clicouCliente) {
            carregarTelaCrudCliente();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
       }

    }

    @FXML
    public void deslogar(MouseEvent event) {
        carregarTelaLogin();
    }

    @FXML
    public void initialize() {
        atualizarAreaBolo();
        botoes = new Button[] { administrador, principal, pedidos, bolos, clientes };
    }

    // Pedidos menu
    @FXML 
    public void listarPedidos(MouseEvent event) throws Exception {
        if (!clicouListarPedido) {
            carregarTelaListarPedidos();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }

    @FXML 
    public void pedir(MouseEvent event) throws Exception {
        if(!clicouBotaoPedir) {
            carregarTelaDePedido();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }

    @FXML
    public void abrirTelaConfirmar(MouseEvent event) throws Exception {
        if (!clicouBotaoConfirmarPedido) {
            carregarTelaConfirmarPedido();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }

    public void limparModalMenuAbertos() {
        menuPedidos.setVisible(false);
    }
    
    public void removerBotaoAtivo() {
        Arrays.stream(botoes).forEach(botao -> {
            if (botao != null) {
                botao.getStyleClass().remove("ativo");
            }
        });
    }

    public void adicionarAtivoNoBotao(Button botao) {
        if (botao != null) {
            limparModalMenuAbertos();
            botao.getStyleClass().add("ativo");
        }
    }

    public void atualizarAreaBolo() {
        areaBolo.getChildren().clear();
        boloDAO.buscarTodos().stream()
            .map( bolo -> {
                try {
                    FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/principal/bolo/bolo.fxml"));
                    Node node = carregar.load();
                    BoloControlador controlador = carregar.getController();
                    controlador.setImagem(getClass().getResource("/telas/principal/bolo/images/").toExternalForm() + bolo.getSabor().getCodigo() + ".png");
                    controlador.setDescricao(bolo.getDescricao());
                    controlador.setFabricao("Fab: " + bolo.getFabricacao().toString());
                    controlador.setPeso("Peso: " + bolo.getPeso().toString() + " kg");
                    controlador.setPreco("Preço: R$ " + bolo.getPreco().toString());
                    controlador.setValidade("Val: " + bolo.getVencimento().toString());
                    controlador.setBolo(bolo);
                    controlador.setAreaDeAlerta(areaDeAlerta);
                    return node;
                } catch (Exception erro) {
                    return null;
                }
            })
            .forEach( bolo -> areaBolo.getChildren().add(bolo));
    }

    public void carregarTelaPerfil(Funcionario funcionario) {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/principal/perfil/perfil.fxml"));
            Parent raiz = carregar.load();
            PerfilControlador controlador = carregar.getController();
            controlador.setConectado(funcionario);
            Scene cena = new Scene(raiz);
            Stage palco = new Stage();
            controlador.setTela(palco);
            telas.add(palco);
            palco.setScene(cena);
            clicouPerfil = true;
            palco.initStyle(StageStyle.UNDECORATED);
            App.adicionarMovimento(palco, cena);
            palco.showAndWait();
            telas.remove(palco);
            clicouPerfil = false;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void carregarTelaLogin() {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/login/login.fxml"));
            Parent raiz = carregar.load();
            LoginControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            controlador.setPalco(palco);
            palco.setScene(cena);
            fecharTodasTelas();
            App.adicionarMovimento(palco, cena);
            palco.show();
        } catch (Exception erro) {
            erro.printStackTrace();;
        }
    }

    public void carregarTelaCrudBolo() {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/bolos/bolo.fxml"));
            Parent raiz = carregar.load();
            CrudBoloControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            controlador.setTela(palco);
            palco.setScene(cena);
            telas.add(palco);
            clicouBolo = true;
            App.adicionarMovimento(palco, cena);
            palco.showAndWait();
            telas.remove(palco);
            clicouBolo = false;
            atualizarAreaBolo();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void carregarTelaCrudCliente() {
        try {
            clicouCliente = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/clientes/cliente.fxml"));
            Parent raiz = carregar.load();
            CrudClienteControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            controlador.setTela(palco);
            palco.setScene(cena);
            telas.add(palco);
            App.adicionarMovimento(palco, cena);
            palco.showAndWait();
            telas.remove(palco);
            clicouCliente = false;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void carregarTelaCrudFuncionario() {
        try {
            clicouFuncionario = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/funcionarios/funcionarios.fxml"));
            Parent raiz = carregar.load();
            CrudFuncionarioControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            controlador.setTela(palco);
            palco.setScene(cena);
            telas.add(palco);
            App.adicionarMovimento(palco, cena);
            palco.showAndWait();
            telas.remove(palco);
            clicouFuncionario = false;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void carregarTelaListarPedidos() {
        try {
            clicouListarPedido = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/pedidos/pedidos.fxml"));
            Parent raiz = carregar.load();
            ListarPedidosControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            controlador.setTela(palco);
            palco.setScene(cena);
            telas.add(palco);
            App.adicionarMovimento(palco, cena);
            palco.showAndWait();
            telas.remove(palco);
            clicouListarPedido = false;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void carregarTelaDePedido() {
        try {
            clicouBotaoPedir = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/pedidos/cadastro/registrarPedido.fxml"));
            Parent raiz = carregar.load();
            RegistrarPedidoControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(cena);
            App.adicionarMovimento(palco, cena);
            controlador.setTela(palco);
            palco.showAndWait();
            clicouBotaoPedir = false;
            if (controlador.getRegistrouPedido()) {
                App.exibirAlert(areaDeAlerta, "SUCESSO", "PEDIDO", "Pedido registrado com sucesso");
            } else if (controlador.getErro()) {
                App.exibirAlert(areaDeAlerta, "FRACASSO", "PEDIDO", "Não foi possível registrar pedido.");
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }


    public void carregarTelaConfirmarPedido() {
        try {

            clicouBotaoConfirmarPedido = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/confirmacao/cadastro/confirmarPedido.fxml"));
            Parent raiz = carregar.load();
            ConfirmarPedidoControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(cena);
            App.adicionarMovimento(palco, cena);
            controlador.setTela(palco);
            palco.showAndWait();
            clicouBotaoConfirmarPedido = false;

        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void fecharTodasTelas() {
        telas.forEach( tela -> {
            if (tela != null) {
                tela.hide();
                tela.close();
            }
        });
    }

    public void setConectado(Funcionario funcionario) {
        if (funcionario != null) {
            conectado = funcionario;
            areaFuncionario.setText(conectado.getNome());
            if (funcionario.getTipo().getDescricao().intern() == "Gerente") {
                administrador.setVisible(true);
            }
        } else {
            throw new RuntimeException("O funcionário não foi conectado a tela PrincipalControlador");
        }
    }

    public void setTela(Stage tela) {
        if (tela != null) {
            this.tela = tela;
            telas.add(tela);
        }
    }

}
