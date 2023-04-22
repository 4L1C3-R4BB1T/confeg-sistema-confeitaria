package controladores.crudPedidoConfirmado;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CrudPedidoConfirmadoControlador {

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
