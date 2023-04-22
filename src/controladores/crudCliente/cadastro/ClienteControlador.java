package controladores.crudCliente.cadastro;

import aplicacao.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.entidadeDAO.ClienteDAO;
import modelos.entidades.Cliente;
import modelos.interfaces.AproveitarFuncao;

// Bolo para editar ou remover
public class ClienteControlador {

    @FXML
    private AnchorPane modal;

    @FXML
    private ImageView foto;

    @FXML
    private Button nome;

    private Long codigo;
    private ClienteDAO clienteDAO = new ClienteDAO(App.conexao);

    private boolean clicouEditar = false;

    private Node areaDeAlerta;

    private AproveitarFuncao atualizarAreaDeClientes;


    @FXML
    public void abrirModal(ActionEvent event) {
        if (modal.isVisible()) {
            App.removerEfeitoSuave(modal);
        } else {
            App.adicionaEfeitoSuave(modal);
        }
    }

    @FXML
    public void editar(ActionEvent event)  {
        if (!clicouEditar) {
            abrirTelaEditar();
        } else {
            App.exibirAlert(areaDeAlerta, "FRACASSO", "Edição", "Edição já aberta para o Cliente.");
        }
    }

    @FXML
    public void remover(ActionEvent event) throws Exception {
        if (codigo != null) {
            App.conexao.setAutoCommit(false);
           try {
                clienteDAO.remover(clienteDAO.buscarPorCodigo(codigo));
                atualizarAreaDeClientes.usar();
                App.conexao.commit();
                App.exibirAlert(areaDeAlerta, "SUCESSO", "DELEÇÃO", "O cliente com ID: " + codigo + " foi removido.");
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
            FXMLLoader carregar = new  FXMLLoader(getClass().getResource("/telas/clientes/edicao/edicao.fxml"));
            Parent raiz = carregar.load();
            ClienteEditarControlador controlador = carregar.getController();
            Cliente cliente = clienteDAO.buscarPorCodigo(codigo);
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            App.adicionarMovimento(palco, cena);
            controlador.setTela(palco);
            controlador.setCliente(cliente);
            palco.setScene(cena);
            palco.showAndWait();
            clicouEditar = false;

            if (controlador.getCadastrou()) {
                atualizarAreaDeClientes.usar();
                App.exibirAlert(areaDeAlerta, "SUCESSO", "Edição", "Bolo alterado com sucesso.");
            } else if (controlador.getErro()){
                App.exibirAlert(areaDeAlerta, "FRACASSO", "Edição", "Erro interno ou Operação Cancelada.");
            }
        

        } catch (Exception erro) {
            erro.printStackTrace();
        }
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

    public void setAtualizarAreaDeClientes(AproveitarFuncao funcao) {
       this.atualizarAreaDeClientes = funcao;
    }

}
