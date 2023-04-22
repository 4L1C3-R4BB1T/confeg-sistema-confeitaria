package controladores.crudPedidoConfirmado;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class CrudPedidoConfirmadoControlador {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<?> areaConfirmados;

    @FXML
    private TableColumn<?, ?> colunaCodigo;

    @FXML
    private TableColumn<?, ?> colunaFuncionario;

    @FXML
    private TableColumn<?, ?> colunaPedido;

    @FXML
    private TableColumn<?, ?> colunaData;

    @FXML
    private TableColumn<?, ?> colunaPago;

    @FXML
    private TableColumn<?, ?> colunaBotoes;

    @FXML
    private HBox areaDeAlerta;

    @FXML
    public void fechar(MouseEvent event) {

    }

    @FXML
    public void initialize() {
       

    }
}
