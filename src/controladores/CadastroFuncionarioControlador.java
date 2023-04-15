package controladores;

import java.sql.Connection;

import conexoes.FabricarConexao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import modelos.entidadeDAO.CidadeDAO;
import modelos.entidadeDAO.EstadoDAO;
import modelos.entidadeDAO.TipoFuncionarioDAO;
import modelos.entidades.Cidade;
import modelos.entidades.Estado;
import modelos.entidades.TipoFuncionario;

// TELA DE LOGIN/CADASTRO
public class CadastroFuncionarioControlador {

    @FXML
    private ComboBox<TipoFuncionario> tipo;

    @FXML
    private TextField nome;

    @FXML
    private TextField cep;

    @FXML
    private TextField cpf;

    @FXML
    private ComboBox<Estado> estado;

    @FXML
    private ComboBox<Cidade> cidade;

    @FXML
    private TextField bairro;

    @FXML
    private TextField rua;

    @FXML
    private TextField numero;

    private static Connection conexao;

    static {
        String url = "jdbc:postgresql://localhost:5432/test";
        String usuario = "postgres";
        String senha = "admin";
        conexao = new FabricarConexao(url, usuario, senha).getConexao();
    }

    @FXML
    public void cancelar(ActionEvent event) {
        Window janela = (Window) ((Node) event.getSource()).getScene().getWindow();
        ((Stage) janela).close();
    }

    @FXML
    public void salvar(ActionEvent event) {

    }

    @FXML 
    public void fecharTela(MouseEvent event) {
        Window janela = (Window) ((Node) event.getSource()).getScene().getWindow();
        ((Stage) janela).close();
    }

    @FXML
    public void initialize() {
        carregarDados();
        estado.getSelectionModel().selectedItemProperty().addListener((observavel, antigoEstado, novoEstado) -> {
            if (novoEstado != null) {
                CidadeDAO cidadeDAO = new CidadeDAO(conexao);
                cidade.setValue(null);
                cidade.getItems().setAll(cidadeDAO.buscarPorEstado(novoEstado));
            }
        });
    }

    public void carregarDados() {
        EstadoDAO estadoDAO = new EstadoDAO(conexao);
        TipoFuncionarioDAO tipoFuncionarioDAO = new TipoFuncionarioDAO(conexao);
        estado.getItems().setAll(estadoDAO.buscarTodos());
        tipo.getItems().setAll(tipoFuncionarioDAO.obterTodos());
    }
    

    public TipoFuncionario getTipo() {
        return tipo.getSelectionModel().getSelectedItem();
    }

    public String getNome() {
        return nome.getText();
    }

    public String getCep() {
        return cep.getText();
    }

    public Estado getEstado() {
        return estado.getSelectionModel().getSelectedItem();
    }

    public Cidade getCidade() {
        return cidade.getSelectionModel().getSelectedItem();
    }

    public String getBairro() {
        return bairro.getText();
    }

    public String getRua() {
        return rua.getText();
    }

    public Integer getNumero() {
        return Integer.parseInt(numero.getText());
    }

}
