package controladores.crudConfirmarPedido;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CrudConfirmarPedidoControlador {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<?> areaDePedidos;

    @FXML
    private TableColumn<?, ?> codigo;

    @FXML
    private TableColumn<?, ?> funcionario;

    @FXML
    private TableColumn<?, ?> dataPedido;

    @FXML
    private TableColumn<?, ?> status;

    @FXML
    private TableColumn<?, ?> areaBotao;

    @FXML
    private HBox areaDeAlerta;

    private Stage tela;

    @FXML
    public void fechar(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void initialize() {
   

    }

    public void encerrar() {
        if (tela != null) {
            tela.close();
        }
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }
    
}
