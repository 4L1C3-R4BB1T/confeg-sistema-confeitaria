package controladores.crudBolo.cadastro;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import aplicacao.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelos.entidadeDAO.SaborDAO;
import modelos.entidades.Bolo;
import modelos.entidades.Sabor;
import modelos.validacao.ValidaFormulario;

public class CadastroBoloController {

    @FXML
    private ComboBox<Sabor> sabores;

    @FXML
    private TextField preco;

    @FXML
    private TextField peso;

    @FXML
    private TextField fabricao;

    @FXML
    private TextField vencimento;

    @FXML
    private TextArea descricao;

    @FXML 
    private Label erroSabor;
    @FXML 
    private Label erroPreco;
    @FXML 
    private Label erroPeso;
    @FXML 
    private Label erroFabricao;
    @FXML 
    private Label erroVencimento;
    @FXML 
    private Label erroDescricao;

    private Stage tela;

    private SaborDAO saborDAO = new SaborDAO(App.conexao);

    private ValidaFormulario vf = new ValidaFormulario();

    private volatile boolean threadPodeValidar = false;

    private volatile boolean pararThreadValidar = false;

    @FXML
    public void cancelar(ActionEvent event) {
        if (tela != null) {
            pararThreadValidar = true;
            tela.close();
        }
    }

    @FXML 
    public void fechar(MouseEvent event) {
        if (tela != null) {
            pararThreadValidar = true;
            tela.close();
        }
    }

    @FXML
    public void salvar(ActionEvent event) throws Exception {
        threadPodeValidar = true;
        if(!podeCadastrar()) return; // Abortar operação
        App.conexao.setAutoCommit(false);

        try {

            /*
             * 
             *   private Long codigo;
                private Sabor sabor;
                private String descricao;
                private Double peso;
                private Double preco;
                private Date fabricacao;
                private Date vencimento;
             * 
             * 
             */

            Bolo bolo = new Bolo(
                getSabor(),
                getDescricao(),
                Double.parseDouble(getPeso()),
                Double.parseDouble(getPreco()),
                Date.valueOf(getFabricao()),
                Date.valueOf(getVencimento())
            );

            System.out.println(bolo.getVencimento());



            App.conexao.commit();
        } catch (Exception erro) {
            App.conexao.rollback();
        }

    }

    @FXML
    public void initialize() {
        sabores.getItems().setAll(saborDAO.buscarTodos());
        new Thread(() -> {
            while (!pararThreadValidar) {
                if (threadPodeValidar) {
                    try {
                        Platform.runLater(() -> podeCadastrar());
                        Thread.sleep(200);
                    } catch (Exception erro) {}
                } 
            }

        }).start();
    }

    public boolean podeCadastrar() {
        return Stream.of(
            vf.validarComboBox(erroSabor, getSabor(), "Selecione o Sabor"),
            vf.validarValorNumerico(erroPreco, getPreco()),
            vf.validarValorNumerico(erroPeso, getPeso()),
            vf.validarData(erroFabricao, getFabricao(), "Preencha a data de Fabricação"),
            vf.validarData(erroVencimento, getVencimento(), "Preencha a data de Vencimento"),
            vf.validarCampo(erroDescricao, getDescricao(), "Preencha a Descrição")
        ).allMatch( bool -> bool != false);
    }

    public void setPararThreadValidar(boolean b) {
        pararThreadValidar = b;
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }

    public String getPreco() {
        return preco.getText();
    }

    public String getPeso() {
        return peso.getText();
    }

    public String getFabricao() {
        return fabricao.getText();
    }

    public String getVencimento() {
        return vencimento.getText();
    }

    public String getDescricao() {
        return descricao.getText();
    }

    public Sabor getSabor() {
        return sabores.getSelectionModel().getSelectedItem();
    }
}
