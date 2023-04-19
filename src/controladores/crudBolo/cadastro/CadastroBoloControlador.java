package controladores.crudBolo.cadastro;


import java.text.SimpleDateFormat;
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

    private BoloDAO boloDAO = new BoloDAO(App.conexao);

    private ValidaFormulario vf = new ValidaFormulario();

    private volatile boolean threadPodeValidar = false;

    private volatile boolean pararThreadValidar = false;

    private boolean cadastrou = false;

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Bolo bolo = new Bolo(
                getSabor(),
                getDescricao(),
                Double.parseDouble(getPeso()),
                Double.parseDouble(getPreco()),
                new java.sql.Date(sdf.parse(getFabricao()).getTime()),
                new java.sql.Date(sdf.parse(getVencimento()).getTime())
            );

            boloDAO.inserir(bolo);
            App.conexao.commit();
            cadastrou = true;
            encerrarTela();
        } catch (Exception erro) {
            App.conexao.rollback();
            erro.printStackTrace();
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
