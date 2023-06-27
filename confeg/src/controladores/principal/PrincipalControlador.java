package controladores.principal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aplicacao.App;
import controladores.chat.ChatControlador;
import controladores.crudBolo.CrudBoloControlador;
import controladores.crudCliente.CrudClienteControlador;
import controladores.crudFuncionario.CrudFuncionarioControlador;
import controladores.crudPedidoConfirmado.ConfirmarPedidoControlador;
import controladores.crudPedidoConfirmado.CrudPedidoConfirmadoControlador;
import controladores.crudPedidoIngrediente.ConfirmarPedidoIngredienteControlador;
import controladores.crudPedidoIngrediente.CrudPedidoIngrediente;
import controladores.crudPedidoIngrediente.PedirIngredienteControlador;
import controladores.crudPedidos.ListarPedidosControlador;
import controladores.crudPedidos.RegistrarPedidoControlador;
import controladores.graficos.TelaSelecaoGraficoControlador;
import controladores.login.LoginControlador;
import controladores.principal.bolo.BoloControlador;
import controladores.principal.perfil.PerfilControlador;
import controladores.relatorios.TelaSelecaoRelatorioControlador;
import controladores.sobre.SobreControlador;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.entidadeDAO.BoloDAO;
import modelos.entidadeDAO.FuncionarioDAO;
import modelos.entidades.Bolo;
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
    private AnchorPane modalConfirmar;

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

    @FXML
    private Button relatorios;

    @FXML
    private Button graficos;

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

    @FXML 
    private TextField digitado;

    @FXML 
    private Button botaoChat;

    @FXML
    private AnchorPane areaPadrao;

    private Stage tela;
    
    private List<Stage> telas = new ArrayList<>();

    private BoloDAO boloDAO = new BoloDAO(App.conexao);

    private Funcionario conectado;

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO(App.conexao);

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
    private boolean clicouBotaoConfirmarCompra = false;
    private boolean clicouBotaoRelatorio = false;
    private boolean clicouBotaoGrafico = false;
    private boolean clicouBotaoChat = false;

    public void fecharAreaPadrao() {
        areaPadrao.setVisible(false);
    }

    @FXML
    public void pesquisar(MouseEvent event) {
        Platform.runLater(() -> carregarPesquisa());
    } 

    @FXML
    public void pesquisarEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Platform.runLater(() -> carregarPesquisa());
        }
    }

    @FXML
    void irParaTelaSobre(ActionEvent event) {
        Object[] componentes = App.carregarTela("/sobre/sobre.fxml");
        Stage tela = (Stage) componentes[0];
        SobreControlador controlador = (SobreControlador) componentes[1];
        controlador.setStage(tela);
        tela.show(); 
    }

    @FXML 
    public void irParaTelaGraficos(ActionEvent  event) {
        removerBotaoAtivo();
        adicionarAtivoNoBotao(graficos);
        if (!clicouBotaoGrafico) {
            carregarTelaGraficos();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A Tela já está sendo exibida.");
        }
    }

    public void carregarTelaGraficos() {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/graficos/graficos.fxml"));
            Parent elemento = carregar.load();
            TelaSelecaoGraficoControlador controlador = carregar.getController();
            Scene cena = new Scene(elemento);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(cena);
            App.adicionarMovimento(palco, cena);
            controlador.setTela(palco);
            palco.showAndWait();
            removerBotaoAtivo();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    @FXML
    public void irParaTelaRelatorios(ActionEvent event) {
        removerBotaoAtivo();
        adicionarAtivoNoBotao(relatorios);
        if(!clicouBotaoRelatorio) {
            carregarTelaRelatorios();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }

    public void carregarTelaRelatorios() {
        try {
            clicouBotaoRelatorio = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/relatorios/relatorios.fxml"));
            Parent elemento = carregar.load();
            TelaSelecaoRelatorioControlador controlador = carregar.getController();
            Scene cena = new Scene(elemento);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            App.adicionarMovimento(palco, cena);
            controlador.setTela(palco);
            palco.setScene(cena);
            palco.showAndWait();
            clicouBotaoRelatorio = false;
            removerBotaoAtivo();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

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
        modalConfirmar.setVisible(false);
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
        desconectar();
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
        areaBolo.getChildren().clear();
        areaBolo.getChildren().add(App.obterTelaCarregamento());
        new Thread(() -> {
            try {
                Thread.sleep(500);
                Platform.runLater(() -> atualizarAreaBolo());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
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
        desconectar();
        carregarTelaLogin();
    }

    public void desconectar() {
        conectado.setConectado(false);
        funcionarioDAO.alterarConectado(conectado);
    }

    @FXML
    public void initialize() {
        atualizarAreaBolo();
        botoes = new Button[] { administrador, principal, pedidos, bolos, clientes, relatorios, graficos, botaoChat };
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
    public void abrirModalConfirmar(MouseEvent event) {
        removerBotaoPedidoAtivo();
        if (modalConfirmar.isVisible()) {
            App.removerEfeitoSuave(modalConfirmar);
        } else {
            fecharModaisPedido();
            adicionarAtivoNoBotaoPedido(botaoConfirmar);
            App.adicionaEfeitoSuave(modalConfirmar);
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

    // Submenu confirmar
    @FXML
    public void confirmarCompra(MouseEvent event) {
        fecharModaisPedidoESub();
        if (!clicouBotaoConfirmarCompra) {
            carregarTelaConfirmarCompra();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }

    @FXML 
    public void irParaChat(ActionEvent event) {
        removerBotaoAtivo();
        if (!clicouBotaoChat) {
            adicionarAtivoNoBotao(botaoChat);
            carregarChat();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }

    public void carregarChat() {
        Object[] elementos = App.carregarTela("/chat/tela.fxml");
        Stage tela = (Stage) elementos[0];
        ChatControlador controlador = (ChatControlador) elementos[1];
        controlador.setTela(tela);
        controlador.setConectado(conectado);
        clicouBotaoChat = true;
        tela.showAndWait();
        clicouBotaoChat = false;
        botaoChat.getStyleClass().remove("ativo");
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
            pedidos.getStyleClass().add("ativo");
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

    public void carregarTelaPerfil(Funcionario funcionario) {
        Object[] elementos = App.carregarTela("/principal/perfil/perfil.fxml");
        Stage tela = (Stage) elementos[0];
        PerfilControlador controlador = (PerfilControlador) elementos[1];
        controlador.setTela(tela);
        controlador.setConectado(funcionario);
        telas.add(tela);
        clicouPerfil = true;
        tela.showAndWait();
        clicouPerfil = false;
        telas.remove(tela);
    }

    public void carregarTelaLogin() {
        Object[] elementos = App.carregarTela("/login/login.fxml");
        Stage tela = (Stage) elementos[0];
        LoginControlador controlador = (LoginControlador) elementos[1];
        controlador.setPalco(tela);
        fecharTodasTelas();
        tela.show();
    }

    public void carregarTelaCrudBolo() {
        Object[] elementos = App.carregarTela("/bolos/bolo.fxml");
        Stage tela = (Stage) elementos[0];
        CrudBoloControlador controlador = (CrudBoloControlador) elementos[1];
        controlador.setTela(tela);
        telas.add(tela);
        clicouBolo = true;
        tela.showAndWait();
        clicouBolo = false;
        telas.remove(tela);
        atualizarAreaBolo();
        removerBotaoAtivo();
    }

    public void carregarTelaCrudCliente() {
        Object[] elementos = App.carregarTela("/clientes/cliente.fxml");
        Stage tela = (Stage) elementos[0];
        CrudClienteControlador controlador = (CrudClienteControlador) elementos[1];
        controlador.setTela(tela);
        telas.add(tela);
        clicouCliente = true;
        tela.showAndWait();
        clicouCliente = false;
        telas.remove(tela);
        removerBotaoAtivo();
    }

    public void carregarTelaCrudFuncionario() {
        Object[] elementos = App.carregarTela("/funcionarios/funcionarios.fxml");
        Stage tela = (Stage) elementos[0];
        CrudFuncionarioControlador controlador = (CrudFuncionarioControlador) elementos[1];
        controlador.setTela(tela);
        telas.add(tela);
        clicouFuncionario = true;
        tela.showAndWait();
        clicouFuncionario = false;
        removerBotaoAtivo();
    }

    public void carregarTelaListarPedidos() {
        Object[] elementos = App.carregarTela("/pedidos/pedidos.fxml");
        Stage tela = (Stage) elementos[0];
        ListarPedidosControlador controlador = (ListarPedidosControlador) elementos[1];
        controlador.setTela(tela);
        clicouBotaoListarPedido = true;
        controlador.setTela(tela);
        tela.showAndWait();
        clicouBotaoListarPedido = false;
        pedidos.getStyleClass().remove("ativo");
    }

    public void carregarTelaDePedido() {
        Object[] elementos = App.carregarTela("/pedidos/cadastro/registrarPedido.fxml");
        Stage tela = (Stage) elementos[0];
        RegistrarPedidoControlador controlador = (RegistrarPedidoControlador) elementos[1];
        controlador.setTela(tela);
        clicouBotaoPedirBolo = true;
        tela.showAndWait();
        clicouBotaoPedirBolo = false;
        if (controlador.getRegistrouPedido()) 
            App.exibirAlert(areaDeAlerta, "SUCESSO", "PEDIDO", "Pedido registrado com sucesso");
        else if (controlador.getErro()) 
            App.exibirAlert(areaDeAlerta, "FRACASSO", "PEDIDO", "Não foi possível registrar pedido.");
        
        pedidos.getStyleClass().remove("ativo");
    }

    public void carregarTelaConfirmarPedido() {
        Object[] elementos = App.carregarTela("/confirmacao/cadastro/confirmarPedido.fxml");
        Stage tela = (Stage) elementos[0];
        ConfirmarPedidoControlador controlador = (ConfirmarPedidoControlador) elementos[1];
        controlador.setTela(tela);
        clicouBotaoConfirmarPedido = true;
        tela.showAndWait();
        clicouBotaoConfirmarPedido = false;

        if (controlador.getSucesso() && controlador.getDesconto()) 
            App.exibirAlert(areaDeAlerta, "SUCESSO", "PEDIDO", "Pedido confirmado e 20% desconto aplicado.");
         else if (controlador.getSucesso()) 
            App.exibirAlert(areaDeAlerta, "SUCESSO", "PEDIDO", "Pedido confirmado.");
        else if (controlador.getErro()) 
            App.exibirAlert(areaDeAlerta, "FRACASSO", "ERRO", "Não foi possível confirmar o pedido.");
        
        pedidos.getStyleClass().remove("ativo");
    }

    public void carregarTelaListarPedidoIngredientes() {
        Object[] elementos = App.carregarTela("/ingredientes/pedidos.fxml");
        Stage tela = (Stage) elementos[0];
        CrudPedidoIngrediente controlador = (CrudPedidoIngrediente) elementos[1];
        controlador.setTela(tela);
        clicouBotaoListarIngrediente = true;   
        tela.showAndWait();
        clicouBotaoListarIngrediente = false;
        pedidos.getStyleClass().remove("ativo");
    }

    public void carregarTelaPedirIngredientes() {
        Object[] elementos = App.carregarTela("/ingredientes/cadastro/registrarPedido.fxml");
        Stage tela = (Stage) elementos[0];
        PedirIngredienteControlador controlador = (PedirIngredienteControlador) elementos[1];
        controlador.setTela(tela);
        clicouBotaoPedirIngrediente = true;
        tela.showAndWait();
        clicouBotaoPedirIngrediente = false;
        if (controlador.getSucesso()) 
            App.exibirAlert(areaDeAlerta, "SUCESSO", "PEDIDO", "Pedido Ingrediente registrado com sucesso");
        else if (controlador.getFracasso()) 
            App.exibirAlert(areaDeAlerta, "FRACASSO", "PEDIDO", "Não foi possível registrar Pedido de Ingrediente.");
        
        pedidos.getStyleClass().remove("ativo");
    }

    public void carregarTelaListarConfirmados() {
        Object[] elementos = App.carregarTela("/confirmacao/confirmar.fxml");
        Stage tela = (Stage) elementos[0];
        CrudPedidoConfirmadoControlador controlador = (CrudPedidoConfirmadoControlador) elementos[1];
        clicouBotaoListarConfirmacao = true;
        controlador.setTela(tela);
        tela.showAndWait();
        clicouBotaoListarConfirmacao = false;
        pedidos.getStyleClass().remove("ativo");
    }

    public void carregarTelaConfirmarCompra() {
        Object[] elementos = App.carregarTela("/ingredientes/confirmar.fxml");
        Stage tela = (Stage) elementos[0];
        ConfirmarPedidoIngredienteControlador controlador = (ConfirmarPedidoIngredienteControlador) elementos[1];
        clicouBotaoConfirmarCompra = true;
        controlador.setTela(tela);
        tela.showAndWait();
        clicouBotaoConfirmarCompra = false;
        if (controlador.getSucesso()) 
            App.exibirAlert(areaDeAlerta, "SUCESSO", "CONFIRMAÇÃO", "Confirmação efetuada.");
        else if (controlador.getFracasso()) 
            App.exibirAlert(areaDeAlerta, "ERRO", "CONFIRMAÇÃO", "Não foi possível confirmar a compra.");

        pedidos.getStyleClass().remove("ativo");
    }

    @FXML 
    public void monitorarPesquisa(KeyEvent event){
        Platform.runLater(() -> carregarPesquisa());
    }
    
    public void carregarPesquisa() {
        areaBolo.getChildren().clear();
        String valor = digitado.getText().trim();
        if (valor.intern() == "") {
            atualizarAreaBolo();
            return;
        }

        List<Bolo> bolos = boloDAO.pesquisar(valor);

        if (bolos.size() == 0) {
            areaBolo.getChildren().add(App.obterTelaVazia());
            return;
        }

        bolos.stream()
        .map(bolo -> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/principal/bolo/bolo.fxml"));
                Node node = carregar.load();
                BoloControlador controlador = carregar.getController();
                controlador.setImagem(getClass().getResource("/telas/principal/bolo/img/").toExternalForm() + bolo.getSabor().getCodigo() + ".png");
                controlador.setDescricao(bolo.getDescricao());
                controlador.setFabricao(sdf.format(bolo.getFabricacao()));
                controlador.setPeso(bolo.getPeso().toString() + " kg");
                controlador.setPreco("R$ " + bolo.getPreco().toString());
                controlador.setValidade(sdf.format(bolo.getVencimento()));
                controlador.setBolo(bolo);
                controlador.setAreaDeAlerta(areaDeAlerta);
                return node;
            } catch (Exception erro) {
                erro.printStackTrace();
                return null;
            }
        })
        .forEach(bolo -> {
            if (bolo != null) {
                areaBolo.getChildren().add(bolo);
            }
        });
    }

    public void atualizarAreaBolo() {
        areaBolo.getChildren().clear();
        List<Bolo> bolos = boloDAO.buscarTodos();

        if (bolos.size() == 0) {
            Node vazio = App.obterTelaVazia();
            areaBolo.getChildren().add(vazio);
            return;
        }

        bolos.stream()
            .map(bolo -> {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/principal/bolo/bolo.fxml"));
                    Node node = carregar.load();
                    BoloControlador controlador = carregar.getController();
                    controlador.setImagem(getClass().getResource("/telas/principal/bolo/img/").toExternalForm() + bolo.getSabor().getCodigo() + ".png");
                    controlador.setDescricao(bolo.getDescricao());
                    controlador.setFabricao(sdf.format(bolo.getFabricacao()));
                    controlador.setPeso(bolo.getPeso().toString() + " kg");
                    controlador.setPreco("R$ " + String.format("%.2f", bolo.getPreco()));
                    controlador.setValidade(sdf.format(bolo.getVencimento()));
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
