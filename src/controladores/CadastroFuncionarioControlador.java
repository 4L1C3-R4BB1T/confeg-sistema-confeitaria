package controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

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
    private ComboBox<?> estado;

    @FXML
    private ComboBox<?> cidade;

    @FXML
    private TextField bairro;

    @FXML
    private TextField rua;

    @FXML
    private TextField numero;

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
     

    }

}
