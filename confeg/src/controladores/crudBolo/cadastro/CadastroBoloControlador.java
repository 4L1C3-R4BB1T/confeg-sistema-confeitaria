package controladores.crudBolo.cadastro;

import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Stream;

import aplicacao.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelos.entidadeDAO.BoloDAO;
import modelos.entidadeDAO.SaborDAO;
import modelos.entidades.Bolo;
import modelos.entidades.Sabor;
import modelos.validacao.ValidaFormulario;

public class CadastroBoloControlador {

    @FXML
    private ComboBox<Sabor> sabores;

    @FXML
    private TextField preco;

    @FXML
    private TextField peso;

    @FXML
    private DatePicker fabricao;

    @FXML
    private DatePicker vencimento;

    @FXML
    private TextArea descricao;

    @FXML 
    private Label erroSabor;

    @FXML 
    private Label erroPreco;

    @FXML 
    private Label erroPeso;
    
    @FXML 
    private Label erroFabricacao;

    @FXML 
    private Label erroVencimento;

    @FXML 
    private Label erroDescricao;

    @FXML 
    private HBox areaDeAlerta;

    private Stage tela;

    private SaborDAO saborDAO = new SaborDAO(App.conexao);

    private BoloDAO boloDAO = new BoloDAO(App.conexao);

    private ValidaFormulario vf = new ValidaFormulario();

    private volatile boolean threadPodeValidar = false;

    private volatile boolean pararThreadValidar = false;

    private boolean cadastrou = false;
    private boolean erro = false;

    @FXML
    public void cancelar(ActionEvent event) {
        encerrarTela();
    }

    @FXML 
    public void fechar(MouseEvent event) {
        encerrarTela();
    }

    @FXML
    public void salvar(ActionEvent event) throws Exception {
        threadPodeValidar = true;
        if(!podeCadastrar()) return; // Abortar operação
        App.conexao.setAutoCommit(false);
        try {
            Bolo bolo = new Bolo(
                getSabor(),
                getDescricao(),
                Double.parseDouble(getPeso()),
                Double.parseDouble(getPreco()),
                Date.valueOf(getFabricao()),
                Date.valueOf(getVencimento())
            );
            boloDAO.inserir(bolo);
            App.conexao.commit();
            cadastrou = true;
            encerrarTela();
        } catch (Exception erro) {
            App.conexao.rollback();
            erro.printStackTrace();
            this.erro = true;
            encerrarTela();
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
            validarDatas(),
            vf.validarCampo(erroDescricao, getDescricao(), "Preencha a Descrição")
        ).allMatch( bool -> bool != false);
    }

    public boolean validarDatas() {
        erroFabricacao.setStyle("-fx-text-fill: red;");
        erroVencimento.setStyle("-fx-text-fill: red;");
        erroFabricacao.setText("");
        erroVencimento.setText("");
        boolean teveErro = false;

        if (getFabricao() == null){
            erroFabricacao.setText("* Preencha a Fabricação.");
            teveErro = true;
        }

        if (getVencimento() == null) {
            erroVencimento.setText("* Preencha a Validade.");
            teveErro = true;
        } 
        
        if (getFabricao() != null && getFabricao().isBefore(LocalDate.now())) {
            erroFabricacao.setText("Fabricação inferior a data atual");
            teveErro = true;
        }

        if (getVencimento() != null && getVencimento().isBefore(LocalDate.now())) {
            erroVencimento.setText("Vencimento inferior a data atual");
            teveErro = true;
        } 

        if (getVencimento() != null && getVencimento().isBefore(getFabricao())) {
            erroVencimento.setText("Vencimento inferior a data de fabricação");
            teveErro = true;
        } 

        return !teveErro;
    }

    public boolean getCadastrou() {
        return cadastrou;
    }

    public void encerrarTela() {
        if (tela != null) {
            pararThreadValidar = true;
            tela.close();
        }
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

    public LocalDate getFabricao() {
        return fabricao.getValue();
    }

    public LocalDate getVencimento() {
        return vencimento.getValue();
    }

    public String getDescricao() {
        return descricao.getText();
    }

    public Sabor getSabor() {
        return sabores.getSelectionModel().getSelectedItem();
    }

    public boolean getErro() {
        return erro;
    }
    
}
