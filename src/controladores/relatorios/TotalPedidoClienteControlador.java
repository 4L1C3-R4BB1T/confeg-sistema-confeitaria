package controladores.relatorios;

import aplicacao.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelos.consultas.ConsultaPersonalizada;
import modelos.consultas.entitidades.PedidosClienteConsulta;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class TotalPedidoClienteControlador {

    @FXML   
    private TableView<PedidosClienteConsulta> areaDePedidos;

    @FXML
    private TableColumn<PedidosClienteConsulta, String> cliente;

    @FXML
    private TableColumn<PedidosClienteConsulta, String> cpf;

    @FXML
    private TableColumn<PedidosClienteConsulta, String> telefone;

    @FXML
    private TableColumn<PedidosClienteConsulta, String> ultimoPedido;

    @FXML
    private TableColumn<PedidosClienteConsulta, Long> pedidos;

    @FXML
    private HBox areaDeAlerta;

    private Stage tela;

    @FXML
    public void fechar(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void imprimir(ActionEvent event) throws JRException {
        JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/pedidosCliente.jasper"));
        JasperPrint jp = JasperFillManager.fillReport(jr, null, App.conexao);
        JasperViewer jv = new JasperViewer(jp, false);
        jv.setVisible(true);
    }

    @FXML
    public void initialize() {
       cliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
       cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
       telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
       ultimoPedido.setCellValueFactory(new PropertyValueFactory<>("ultimoPedido"));
       pedidos.setCellValueFactory(new PropertyValueFactory<>("pedidos"));
       carregarTotalPedidoCliente();
    }

    public void carregarTotalPedidoCliente() {
        areaDePedidos.getItems().clear();
        ConsultaPersonalizada
            .obterQuantidadeDePedidosPorCliente()
            .forEach( totalPedidoCliente -> areaDePedidos.getItems().add(totalPedidoCliente));
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
