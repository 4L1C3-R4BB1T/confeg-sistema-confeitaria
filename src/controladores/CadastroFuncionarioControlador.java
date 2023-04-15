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
import modelos.entidades.Cidade;
import modelos.entidades.Estado;

public class CadastroFuncionarioControlador {

    @FXML
    private ComboBox<?> tipo;

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
        carregarSelecoes();
    }

    public void carregarSelecoes() {
        EstadoDAO estadoDAO = new EstadoDAO(conexao);
        CidadeDAO cidadeDAO = new CidadeDAO(conexao);
        estado.getItems().setAll(estadoDAO.buscarTodos());
        cidade.getItems().setAll(cidadeDAO.buscarTodos());
    }

}
