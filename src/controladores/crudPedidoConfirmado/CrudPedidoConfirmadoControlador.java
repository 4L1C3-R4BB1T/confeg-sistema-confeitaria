package controladores.crudPedidoConfirmado;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelos.consultas.ConsultaPersonalizada;
import modelos.consultas.entitidades.PedidoConfirmado;
import modelos.interfaces.AproveitarFuncao;

// Crud 
public class CrudPedidoConfirmadoControlador {

    @FXML
    private TableView<PedidoConfirmado> areaConfirmados;

    @FXML
    private TableColumn<PedidoConfirmado, Long> colunaCodigo;

    @FXML
    private TableColumn<PedidoConfirmado, String> colunaCliente;

    @FXML
    private TableColumn<PedidoConfirmado, Long> colunaPedido;

    @FXML
    private TableColumn<PedidoConfirmado, String> colunaData;

    @FXML
    private TableColumn<PedidoConfirmado, Label> colunaPago;

    @FXML
    private TableColumn<PedidoConfirmado, HBox> colunaBotoes;

    @FXML
    private HBox areaDeAlerta;

    private Stage tela;


    @FXML
    public void fechar(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void initialize() {
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaCliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaPedido.setCellValueFactory(new PropertyValueFactory<>("codigoPedido"));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colunaPago.setCellValueFactory(new PropertyValueFactory<>("pago"));
        colunaBotoes.setCellValueFactory(new PropertyValueFactory<>("botoes"));
        carregarPedidosConfirmados();
      
    }

    public void carregarPedidosConfirmados() {
        areaConfirmados.getItems().clear();
        ConsultaPersonalizada
            .obterPedidosConfirmados()
            .forEach( pedidoConfirmado -> {
                
                try {
                    System.out.println(pedidoConfirmado);
                    FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/confirmacao/botoes.fxml"));
                    Node elemento = carregar.load();
                    PedidoConfirmadoControlador controlador = carregar.getController();
                    controlador.setAreaDeAlerta(areaDeAlerta);
                    controlador.setPedidoConfirmado(pedidoConfirmado);
                    controlador.setAtualizarPedidosConfirmados(this::carregarPedidosConfirmados);
                    HBox botoes = new HBox();
                    botoes.getChildren().add(elemento);
                    pedidoConfirmado.setBotoes(botoes);
                    areaConfirmados.getItems().add(pedidoConfirmado);
                } catch (Exception erro) {
                    erro.printStackTrace();
                }
            });
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
