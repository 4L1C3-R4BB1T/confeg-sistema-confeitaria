package controladores.crudFuncionario;

import java.util.stream.Stream;
import aplicacao.App;
import controladores.principal.perfil.PerfilControlador;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.entidadeDAO.CidadeDAO;
import modelos.entidadeDAO.EnderecoDAO;
import modelos.entidadeDAO.EstadoDAO;
import modelos.entidadeDAO.FuncionarioDAO;
import modelos.entidadeDAO.TipoFuncionarioDAO;
import modelos.entidades.Cidade;
import modelos.entidades.Endereco;
import modelos.entidades.Estado;
import modelos.entidades.Funcionario;
import modelos.entidades.TipoFuncionario;
import modelos.validacao.ValidaFormulario;

public class CadastroFuncionarioControlador {

    @FXML
    private ComboBox<TipoFuncionario> tipo;

    @FXML
    private TextField nome;

    @FXML
    private TextField cep;

    @FXML
    private TextField cpf;

    @FXML
    private ComboBox<Estado> estado;

    @FXML
    private ComboBox<Cidade> cidade;

    @FXML
    private TextField bairro;

    @FXML
    private TextField rua;

    @FXML
    private TextField numero;

    /// Labels para inserir mensagem de erro na tela de cadastro 
    @FXML 
    private Label exibirErroNoTipo;

    @FXML 
    private Label exibirErroNoNome;

    @FXML 
    private Label exibirErroNoCpf;

    @FXML 
    private Label exibirErroNoCep;

    @FXML 
    private Label exibirErroNoEstado;

    @FXML 
    private Label exibirErroNoCidade;

    @FXML 
    private Label exibirErroNoBairro;

    @FXML 
    private Label exibirErroNoRua;

    @FXML 
    private Label exibirErroNoNumero;

    private Stage tela;

    private static ValidaFormulario vf = new ValidaFormulario();

    private volatile boolean clicouBotaoPodeCadastrar = false;

    private volatile boolean encerrarThreadValidacao = false;

    private boolean salvo = false;
    private boolean erro = false;

    /// DAOS
    EnderecoDAO enderecoDAO = new EnderecoDAO(App.conexao);
    FuncionarioDAO funcionarioDAO = new FuncionarioDAO(App.conexao);
    CidadeDAO cidadeDAO = new CidadeDAO(App.conexao);
    EstadoDAO estadoDAO = new EstadoDAO(App.conexao);
    TipoFuncionarioDAO tipoFuncionarioDAO = new TipoFuncionarioDAO(App.conexao);

    @FXML
    public void cancelar(ActionEvent event) {
        encerrarTela();
    }

    @FXML
    public void salvar(ActionEvent event) throws Exception {
        clicouBotaoPodeCadastrar = true;
        if (podeCadastrar()) {
            App.conexao.setAutoCommit(false);
            try {
                Endereco endereco = new Endereco(getEstado(), getCidade(), vf.formatarCep(getCep()), getBairro(), getRua(), getNumero());
                endereco.setCodigo(enderecoDAO.inserir(endereco));
                Funcionario funcionario = new Funcionario(getNome(), vf.limparCPF(getCpf()), getTipo(), endereco, getCep(), getBairro());
                
                funcionario.setCodigo(funcionarioDAO.inserir(funcionario));

                funcionario.setEmail(funcionarioDAO.gerarEmail(funcionario));
                funcionario.setSenha("Y29uZmVnMTIz");
                funcionarioDAO.alterar(funcionario);

                App.conexao.commit();
                salvo = true;
                carregarTelaPerfil(funcionario);
                encerrarTela();
            } catch (Exception erro) {
                App.conexao.rollback();
                this.erro = true;
                encerrarTela();
            }
        } 
    }

    @FXML 
    public void fecharTela(MouseEvent event) {
        encerrarTela();
    }

    @FXML
    public void initialize() {
        carregarDados();
        estado.getSelectionModel().selectedItemProperty().addListener((observavel, antigoEstado, novoEstado) -> {
            if (novoEstado != null) {
                cidade.setValue(null);
                cidade.getItems().setAll(cidadeDAO.buscarPorEstado(novoEstado));
            }
        });

        new Thread(() -> {
            while (!encerrarThreadValidacao) {
                if (clicouBotaoPodeCadastrar) {
                    Platform.runLater(() -> podeCadastrar());
                    try {
                        Thread.sleep(200);
                    } catch (Exception erro) {}
                }
            }
        }).start();
    }

    public void carregarDados() {
        estado.getItems().setAll(estadoDAO.buscarTodos());
        tipo.getItems().setAll(tipoFuncionarioDAO.buscarTodos());
    }

    public boolean podeCadastrar() {
        return Stream.of(
            vf.validarComboBox(exibirErroNoTipo, getTipo(), "Selecione o Tipo do Funcionário"),
            vf.validarCPF(exibirErroNoCpf, getCpf()),
            vf.validarCep(exibirErroNoCep, getCep()),
            vf.validarComboBox(exibirErroNoEstado, getEstado(), "Selecione seu Estado"),
            vf.validarComboBox(exibirErroNoCidade, getCidade(), "Selecione a Cidade"),
            vf.validarCampo(exibirErroNoBairro, getBairro(), "Preencha o Bairro"),
            vf.validarCampo(exibirErroNoRua, getRua(), "Preencha a Rua"),
            vf.validarValorNumerico(exibirErroNoNumero, numero.getText()),
            vf.validarCampo(exibirErroNoNome, getNome(), "Preencha o Nome")
        ).allMatch( valor -> valor == true);
    }

    public void carregarTelaPerfil(Funcionario funcionario) {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/principal/perfil/perfil.fxml"));
            Parent raiz = carregar.load();
            PerfilControlador controlador = carregar.getController();
            controlador.setConectado(funcionario);
            Scene cena = new Scene(raiz);
            Stage palco = new Stage();
            controlador.setTela(palco);
            palco.setScene(cena);
            palco.initStyle(StageStyle.UNDECORATED);
            App.adicionarMovimento(palco, cena);
            palco.showAndWait();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void encerrarTela() {
        if (tela != null) {
            tela.close();
        }
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }

    public void setEncerrarThreadValidacao(boolean b) {
        encerrarThreadValidacao = true;
    }

    public boolean dadosForamSalvos() {
        return salvo;
    }

    public TipoFuncionario getTipo() {
        return tipo.getSelectionModel().getSelectedItem();
    }

    public String getNome() {
        return nome.getText();
    }

    public String getCep() {
        return cep.getText();
    }

    public Estado getEstado() {
        return estado.getSelectionModel().getSelectedItem();
    }

    public Cidade getCidade() {
        return cidade.getSelectionModel().getSelectedItem();
    }

    public String getBairro() {
        return bairro.getText();
    }

    public String getRua() {
        return rua.getText();
    }

    public Integer getNumero() {
        return Integer.parseInt(numero.getText());
    }

    public String getCpf() {
        return cpf.getText();
    }

    public boolean getErro() {
        return erro;
    }
    
}
