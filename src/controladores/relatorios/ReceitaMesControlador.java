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
import modelos.consultas.entitidades.ReceitaMesConsulta;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReceitaMesControlador {

    @FXML
    private TableView<ReceitaMesConsulta> areaDePedidos;

    @FXML
    private TableColumn<ReceitaMesConsulta, Integer> ano;

    @FXML
    private TableColumn<ReceitaMesConsulta, Integer> mes;

    @FXML
    private TableColumn<ReceitaMesConsulta, Long> pedidos;

    @FXML
    private TableColumn<ReceitaMesConsulta, Double> receita;

    @FXML
    private HBox areaDeAlerta;

    private Stage tela;

    @FXML
    public void fechar(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void imprimir(ActionEvent event) throws JRException {
        JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/receitaMes.jasper"));
        JasperPrint jp = JasperFillManager.fillReport(jr, null, App.conexao);
        JasperViewer jv = new JasperViewer(jp, false);
        jv.setVisible(true);
    }

    @FXML
    public void initialize() { 
        ano.setCellValueFactory(new PropertyValueFactory<>("ano"));
        mes.setCellValueFactory(new PropertyValueFactory<>("mes"));
        pedidos.setCellValueFactory(new PropertyValueFactory<>("concluidos"));
        receita.setCellValueFactory(new PropertyValueFactory<>("receita"));
        carregarReceitaPorMes();
    }

    public void carregarReceitaPorMes() {
        areaDePedidos.getItems().clear();
        ConsultaPersonalizada
            .obterReceitaMesConsultas()
            .forEach( receitaMes -> areaDePedidos.getItems().add(receitaMes));
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
