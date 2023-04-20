package controladores.crudBolo.edicao;

import java.sql.Date;
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

public class BoloEdicaoControlador {

    @FXML
    private ComboBox<Sabor> sabores;

    @FXML
    private TextField preco;

    @FXML
    private TextField peso;

    @FXML
    private TextField fabricacao;

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
    private Label erroLabel;

    @FXML
    private Label erroDescricao;

    private SaborDAO saborDAO = new SaborDAO(App.conexao);

    private BoloDAO boloDAO = new BoloDAO(App.conexao);

    private Bolo bolo;

    private Stage tela;

    private ValidaFormulario vf = new ValidaFormulario();

    private volatile boolean podeValidar = false;

    private volatile boolean encerrarThread = false;
    
    private boolean alterou = false;
    private boolean erro = false;


    @FXML
    public void cancelar(ActionEvent event) {
        encerrar();

    }

    @FXML
    public void fechar(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void salvar(ActionEvent event) throws Exception {
        if (validarEdicao()) {
            App.conexao.setAutoCommit(false);
            try {
                bolo.setDescricao(getDescricao());
                bolo.setPeso(Double.parseDouble(getPeso()));
                bolo.setSabor(getSabor());
                bolo.setDescricao(getDescricao());
                bolo.setPreco(Double.parseDouble(getPreco()));
                boloDAO.alterar(bolo);
                App.conexao.commit();
                alterou = true;
            } catch (Exception erro) {
                App.conexao.rollback();
                erro.printStackTrace();
                this.erro = true;
            }
            encerrar();
        } 
        podeValidar = true;
    }

    @FXML
    public void initialize() {
       carregarSabores();
       new Thread(() -> {
            while (!encerrarThread) {
                if (podeValidar) {
                    Platform.runLater(() -> validarEdicao());
                    try {
                        Thread.sleep(200);
                    } catch (Exception erro) {}
                }
            }
       }).start();
    }

    public void carregarSabores() {
        sabores.getItems().addAll(saborDAO.buscarTodos());
    }

    public void encerrar() {
        if (tela != null) {
            encerrarThread = true;
            tela.close();
        }
    }

    public boolean validarEdicao() {
        return Stream.of( 
            vf.validarComboBox(erroSabor, getSabor(), "Selecione o Sabor"),
            vf.validarValorNumerico(erroPreco, getPreco()),
            vf.validarCampo(erroDescricao, getDescricao(), "Preencha a Descrição"),
            vf.validarValorNumerico(erroPeso, getPeso())
        ).allMatch( bool -> bool == true);
    }

    public void setBolo(Bolo bolo) {
        this.bolo = bolo;
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }

    public Sabor getSabor() {
        return sabores.getSelectionModel().getSelectedItem();
    }

    public void setSabor(Sabor sabor) {
        sabores.setValue(sabor);
    }

    public void setPreco(Double preco) {
        this.preco.setText(String.valueOf(preco));
    }   

    public String getPreco() {
        return preco.getText();
    }

    public void setFabricacao(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fabricacao.setText(sdf.format(date));
    }

    public Date getFabricacao() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return new Date(sdf.parse(fabricacao.getText()).getTime());
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public void setVencimento(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        vencimento.setText(sdf.format(date));
    }

    public Date getVencimento()  {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return new Date(sdf.parse(vencimento.getText()).getTime());
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public void setDescricao(String descricao) {
        this.descricao.setText(descricao);
    }

    public String getDescricao() {
        return descricao.getText();
    }

    public void setPeso(Double peso) {
        this.peso.setText(String.valueOf(peso));
    }

    public String getPeso() {
        return this.peso.getText();
    }

    public boolean getAlterou() {
        return alterou;
    }

    public boolean getErro() {
        return erro;
    }

}
