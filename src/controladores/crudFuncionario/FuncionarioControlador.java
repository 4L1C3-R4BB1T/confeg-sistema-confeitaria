package controladores.crudFuncionario;

import aplicacao.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.entidadeDAO.ConfirmacaoPedidoDAO;
import modelos.entidadeDAO.EnderecoDAO;
import modelos.entidadeDAO.FuncionarioDAO;
import modelos.entidadeDAO.PedidoBoloDAO;
import modelos.entidadeDAO.PedidoCompraDAO;
import modelos.entidadeDAO.PedidoCompraIngredientesDAO;
import modelos.entidadeDAO.PedidoDAO;
import modelos.entidades.ConfirmacaoPedido;
import modelos.entidades.Funcionario;
import modelos.interfaces.AproveitarFuncao;

// Bolo para editar ou remover
public class FuncionarioControlador {

    @FXML
    private AnchorPane modal;

    @FXML
    private ImageView foto;

    @FXML
    private Button nome;

    private Long codigo;

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO(App.conexao);

    private boolean clicouEditar = false;

    private Node areaDeAlerta;

    private AproveitarFuncao atualizarAreaDeFuncionarios ;

    private static int geradorImagem = 0;
    private static int quantidadeImagem = 2;

    @FXML
    public void abrirModal(ActionEvent event) {
        if (modal.isVisible()) {
            App.removerEfeitoSuave(modal);
        } else {
            App.adicionaEfeitoSuave(modal);
        }
    }

    @FXML
    public void editar(ActionEvent event) throws Exception {
        if (!clicouEditar) {
            abrirTelaEditar();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "EDIÇÃO", "Edição já aberta para o Funcionário.");
        }
    }

    @FXML
    public void remover(ActionEvent event) throws Exception {
        if (codigo != null) {
            App.conexao.setAutoCommit(false);
           try {
                Funcionario funcionario = funcionarioDAO.buscarPorCodigo(codigo);

                PedidoDAO pedidoDAO = new PedidoDAO(App.conexao);
                PedidoBoloDAO pedidoBoloDAO = new PedidoBoloDAO(App.conexao);
                ConfirmacaoPedidoDAO confirmacaoPedidoDAO = new ConfirmacaoPedidoDAO(App.conexao);

                // remover todos os pedidos que estão associados ao funcionario a ser removido
                pedidoDAO.buscarPorFuncionario(funcionario).forEach(pedido -> {
                    // remover confirmação do pedido
                    ConfirmacaoPedido confirmacaoPedido = confirmacaoPedidoDAO.buscarPorPedido(pedido);
                    if (confirmacaoPedido != null) {
                        confirmacaoPedidoDAO.remover(confirmacaoPedido);
                    }
                    // remover pedidobolo do pedido
                    pedidoBoloDAO.buscarPorPedido(pedido).forEach(pedidoBolo ->
                        pedidoBoloDAO.remover(pedidoBolo)
                    );
                    // remover pedido
                    pedidoDAO.remover(pedido);
                });

                PedidoCompraDAO pedidoCompraDAO = new PedidoCompraDAO(App.conexao);
                PedidoCompraIngredientesDAO pedidoCompraIngredientesDAO = new PedidoCompraIngredientesDAO(App.conexao);
                pedidoCompraDAO.buscarPorFuncionario(funcionario).forEach(pedidoCompra -> {
                    // remover pedidocompraingrediente do pedidocompra
                    pedidoCompraIngredientesDAO.buscarPorPedidoCompra(pedidoCompra).forEach(PedidoCompraIngrediente -> 
                        pedidoCompraIngredientesDAO.remover(PedidoCompraIngrediente)
                    );
                    // remover pedidocompra
                    pedidoCompraDAO.remover(pedidoCompra);
                });

                // remover funcionario
                funcionarioDAO.remover(funcionario);

                // remover endereco assoaciado ao funcionario
                EnderecoDAO enderecoDAO = new EnderecoDAO(App.conexao);
                enderecoDAO.remover(enderecoDAO.buscarPorCodigo(funcionario.getEndereco().getCodigo()));

                atualizarAreaDeFuncionarios.usar();
                App.conexao.commit();
                App.exibirAlert(areaDeAlerta, "SUCESSO", "DELEÇÃO", "O Funcionário com ID: " + codigo + " foi removido.");
           } catch (Exception erro) {
                erro.printStackTrace();
                App.conexao.rollback();
                App.exibirAlert(areaDeAlerta, "FRACASSO", "DELEÇÃO", "Não foi possível remover");
           }
        }
    }

    public void abrirTelaEditar() {
        try {
            clicouEditar = true;
            FXMLLoader carregar = new  FXMLLoader(getClass().getResource("/telas/funcionarios/edicao/edicao.fxml"));
            Parent raiz = carregar.load();
            FuncionarioEditarControlador controlador = carregar.getController();
            Funcionario funcionario = funcionarioDAO.buscarPorCodigo(codigo);
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            App.adicionarMovimento(palco, cena);
            controlador.setTela(palco);
            controlador.setFuncionario(funcionario);
            palco.setScene(cena);
            palco.showAndWait();
            clicouEditar = false;
            if (controlador.dadosForamSalvos()) {
                atualizarAreaDeFuncionarios.usar();
                App.exibirAlert(areaDeAlerta, "SUCESSO", "EDIÇÃO", "Funcionário alterado com sucesso.");
            } else if (controlador.getErro()) {
                App.exibirAlert(areaDeAlerta, "FRACASSO", "EDIÇÃO", "Não foi possível alterar.");
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void carregarImagem() {
        geradorImagem = geradorImagem % quantidadeImagem;
        String caminho = getClass().getResource("/telas/funcionarios/subtela/"+ ++geradorImagem + ".png").toExternalForm();
        Image imagem = new Image(caminho);
        foto.setImage(imagem);
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome.setText(nome);
    }

    public void setAreaDeAlerta(Node areaAlerta) {
        this.areaDeAlerta = areaAlerta;
    }

    public void setAtualizarAreaDeFuncionarios(AproveitarFuncao funcao) {
       this.atualizarAreaDeFuncionarios = funcao;
    }

}
