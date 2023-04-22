package controladores.principal;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aplicacao.App;
import controladores.crudBolo.CrudBoloControlador;
import controladores.crudCliente.CrudClienteControlador;
import controladores.crudConfirmarPedido.ConfirmarPedidoControlador;
import controladores.crudFuncionario.CrudFuncionarioControlador;
import controladores.crudPedidoConfirmado.CrudPedidoConfirmadoControlador;
import controladores.crudPedidoIngrediente.CrudPedidoIngrediente;
import controladores.crudPedidoIngrediente.PedirIngredienteControlador;
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
    private AnchorPane modalListar;

    @FXML
    private AnchorPane modalPedir;

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

    // BOTÕES DO MENU PEDIDO
    @FXML
    private HBox botaoListar;

    @FXML
    private HBox botaoPedir;

    @FXML
    private HBox botaoConfirmar;

    private HBox[] botoesPedido;

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
    private boolean clicouBotaoListarPedido = false;
    private boolean clicouBotaoPedirBolo = false;
    private boolean clicouBotaoConfirmarPedido = false;
    private boolean clicouBotaoListarIngrediente = false;
    private boolean clicouBotaoPedirIngrediente = false;
    private boolean clicouBotaoListarConfirmacao = false;

    @FXML
    public void abrirMenuPedidos(ActionEvent event) {
        removerBotaoPedidoAtivo();
        removerBotaoAtivo();
        if (menuPedidos.isVisible()) {
            fecharModaisPedido();
            App.removerEfeitoSuave(menuPedidos);
        } else {
            adicionarAtivoNoBotao(pedidos);
            App.adicionaEfeitoSuave(menuPedidos);
        }
    }

    // Colocar os modais 
    public void fecharModaisPedido() {
        removerBotaoPedidoAtivo();
        modalPedir.setVisible(false);
        modalListar.setVisible(false);
    }

    public void fecharModaisPedidoESub() {
        fecharModaisPedido();
        menuPedidos.setVisible(false);
    }

    @FXML
    public void abrirMenuUsuario(ActionEvent event) {
        if (menuUsuario.isVisible()) {
            App.removerEfeitoSuave(menuUsuario);
            String caminhoSetaBaixo = PrincipalControlador.class.getResource("/telas/_assets/img/seta_baixo.png").toExternalForm();
            menuUsuarioSeta.setImage(new Image(caminhoSetaBaixo));
        } else {
            App.adicionaEfeitoSuave(menuUsuario);
            String caminhoSetaCima = PrincipalControlador.class.getResource("/telas/_assets/img/seta_cima.png").toExternalForm();
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
    public void verPerfil(MouseEvent event) {
        if (!clicouPerfil) {
            carregarTelaPerfil(conectado);
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }

    @FXML
    public void irTelaBolo(ActionEvent event) {
        removerBotaoAtivo();
        adicionarAtivoNoBotao(bolos);
       if (!clicouBolo) {
            carregarTelaCrudBolo();
       } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
       }
    }

    @FXML
    public void irParaTelaFuncionarios(ActionEvent event) {
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
    public void irParaTelaClientes(ActionEvent event) {
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
        botoesPedido = new HBox[] { botaoListar, botaoPedir, botaoConfirmar };
    }

    // Listar Modal
    @FXML 
    public void abrirModalListar(MouseEvent event) {
        removerBotaoPedidoAtivo();
        if (modalListar.isVisible()) {
            App.removerEfeitoSuave(modalListar);
        } else {
            fecharModaisPedido();
            adicionarAtivoNoBotaoPedido(botaoListar);
            App.adicionaEfeitoSuave(modalListar);
        }
        
    }

    @FXML
    public void abrirListaPedido(MouseEvent event)  {
        if (!clicouBotaoListarPedido) {
            fecharModaisPedidoESub();
            carregarTelaListarPedidos();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }

    @FXML
    public void abrirListaConfirmacao(MouseEvent event) {
        fecharModaisPedidoESub();
        if (!clicouBotaoListarConfirmacao) {
            carregarTelaListarConfirmados();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }


    @FXML
    public void abrirListaIngrediente(MouseEvent event)  {
        fecharModaisPedidoESub();
        if (!clicouBotaoListarIngrediente) {
            carregarTelaListarPedidoIngredientes();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }


    // Pedir Modal
    @FXML 
    public void abrirModalPedir(MouseEvent event) {
        removerBotaoPedidoAtivo();
        if(modalPedir.isVisible()) {
            App.removerEfeitoSuave(modalPedir);
        } else {
            fecharModaisPedido();
            adicionarAtivoNoBotaoPedido(botaoPedir);
            App.adicionaEfeitoSuave(modalPedir);
        }
    }

    @FXML
    public void abrirPedirBolo(MouseEvent event) {
        fecharModaisPedidoESub();
        if (!clicouBotaoPedirBolo) {
            carregarTelaDePedido();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }

    @FXML
    public void abrirPedirIngrediente(MouseEvent event) {
        fecharModaisPedidoESub();
        if (!clicouBotaoPedirIngrediente) {
            carregarTelaPedirIngredientes();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
 
    }

    @FXML
    public void abrirTelaConfirmar(MouseEvent event) {
        removerBotaoPedidoAtivo();
        if (!clicouBotaoConfirmarPedido) {
            fecharModaisPedidoESub();
            adicionarAtivoNoBotaoPedido(botaoConfirmar);
            carregarTelaConfirmarPedido();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }

    public void limparModalMenuAbertos() {
        menuPedidos.setVisible(false);
        
    }


    public void removerBotaoPedidoAtivo() {
        Arrays.stream(botoesPedido).forEach(botao -> {
            if (botao != null) {
                botao.getStyleClass().remove("pedido-ativo");
            }
        });
    }

    public void adicionarAtivoNoBotaoPedido(HBox botao) {
        if (botao != null) {
            removerBotaoAtivo();
            botao.getStyleClass().add("pedido-ativo");
        }
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
            fecharModaisPedido();
            limparModalMenuAbertos();
            botao.getStyleClass().add("ativo");
        }
    }

    public void atualizarAreaBolo() {
        areaBolo.getChildren().clear();
        boloDAO.buscarTodos().stream()
            .map( bolo -> {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/principal/bolo/bolo.fxml"));
                    Node node = carregar.load();
                    BoloControlador controlador = carregar.getController();
                    controlador.setImagem(getClass().getResource("/telas/principal/bolo/img/").toExternalForm() + bolo.getSabor().getCodigo() + ".png");
                    controlador.setDescricao(bolo.getDescricao());
                    controlador.setFabricao("Fab: " + sdf.format(bolo.getFabricacao()));
                    controlador.setPeso("Peso: " + bolo.getPeso().toString() + " kg");
                    controlador.setPreco("Preço: R$ " + bolo.getPreco().toString());
                    controlador.setValidade("Val: " + sdf.format(bolo.getVencimento()));
                    controlador.setBolo(bolo);
                    controlador.setAreaDeAlerta(areaDeAlerta);
                    return node;
                } catch (Exception erro) {
                    erro.printStackTrace();
                    return null;
                }
            })
            .forEach( bolo -> {
                if (bolo != null) {
                    areaBolo.getChildren().add(bolo);
                }
            });
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
            removerBotaoAtivo();
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
            removerBotaoAtivo();
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
            removerBotaoAtivo();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void carregarTelaListarPedidos() {
        try {
            clicouBotaoListarPedido = true;
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
            clicouBotaoListarPedido = false;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void carregarTelaDePedido() {
        try {
            clicouBotaoPedirBolo = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/pedidos/cadastro/registrarPedido.fxml"));
            Parent raiz = carregar.load();
            RegistrarPedidoControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(cena);
            App.adicionarMovimento(palco, cena);
            controlador.setTela(palco);
            palco.showAndWait();
            clicouBotaoPedirBolo = false;
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
            limparModalMenuAbertos();
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

            if (controlador.getSucesso() && controlador.getDesconto()) {
                App.exibirAlert(areaDeAlerta, "SUCESSO", "PEDIDO", "Pedido confirmado e 2% desconto aplicado.");
            } else if (controlador.getSucesso()) {
                App.exibirAlert(areaDeAlerta, "SUCESSO", "PEDIDO", "Pedido confirmado.");
            } else if (controlador.getErro()) {
                App.exibirAlert(areaDeAlerta, "FRACASSO", "ERRO", "Não foi possível confirmar o pedido.");
            }

        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void carregarTelaListarPedidoIngredientes() {
        try {
            clicouBotaoListarIngrediente = true;    
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/ingredientes/pedidos.fxml"));
            Parent conteudo = carregar.load();
            CrudPedidoIngrediente controlador = carregar.getController();
            Scene scene = new Scene(conteudo);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(scene);
            controlador.setTela(palco);
            App.adicionarMovimento(palco, scene);
            palco.showAndWait();
            clicouBotaoListarIngrediente = false;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void carregarTelaPedirIngredientes() {
        try {
            clicouBotaoPedirIngrediente = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/ingredientes/cadastro/registrarPedido.fxml"));
            Parent conteudo = carregar.load();
            PedirIngredienteControlador controlador = carregar.getController();
            Scene scene = new Scene(conteudo);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(scene);
            controlador.setTela(palco);
            App.adicionarMovimento(palco, scene);
            palco.showAndWait();
            if (controlador.getSucesso()) {
                App.exibirAlert(areaDeAlerta, "SUCESSO", "PEDIDO", "Pedido Ingrediente registrado com sucesso");
            } else if (controlador.getFracasso()) {
                App.exibirAlert(areaDeAlerta, "FRACASSO", "PEDIDO", "Não foi possível registrar Pedido de Ingrediente.");
            }
            clicouBotaoPedirIngrediente = false;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }


    public void carregarTelaListarConfirmados() {
        try {
            clicouBotaoListarConfirmacao = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/confirmacao/confirmar.fxml"));
            Parent conteudo = carregar.load();
            CrudPedidoConfirmadoControlador controlador = carregar.getController();
            Scene scene = new Scene(conteudo);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(scene);
            controlador.setTela(palco);
            App.adicionarMovimento(palco, scene);
            palco.showAndWait();
            /* 
            if (controlador.getSucesso()) {
                App.exibirAlert(areaDeAlerta, "SUCESSO", "PEDIDO", "Pedido Ingrediente registrado com sucesso");
            } else if (controlador.getFracasso()) {
                App.exibirAlert(areaDeAlerta, "FRACASSO", "PEDIDO", "Não foi possível registrar Pedido de Ingrediente.");
            }
            */
            clicouBotaoListarConfirmacao = false;
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
