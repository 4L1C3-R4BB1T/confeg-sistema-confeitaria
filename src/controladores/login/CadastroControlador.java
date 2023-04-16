package controladores.login;

import java.sql.Connection;
import java.text.Normalizer;
import java.util.Map;
import java.util.stream.Stream;

import conexoes.FabricarConexao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
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

// TELA DE LOGIN/CADASTRO
public class CadastroControlador {

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

    /// Labels para inserir mensagem de erro na tela de cadastro da tela login
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

    private static Connection conexao;

    static {
        String url = "jdbc:postgresql://localhost:5432/test";
        String usuario = "postgres";
        String senha = "admin";
        conexao = new FabricarConexao(url, usuario, senha).getConexao();
    }

    private static ValidaFormulario vf = new ValidaFormulario();

    private volatile boolean clicouBotaoPodeCadastrar = false;

    private volatile boolean encerrarThreadValidacao = false;

    private volatile boolean salvo = false;

    @FXML
    public void cancelar(ActionEvent event) {
        Window janela = (Window) ((Node) event.getSource()).getScene().getWindow();
        ((Stage) janela).close();
    }

    @FXML
    public void salvar(ActionEvent event) throws Exception {
        clicouBotaoPodeCadastrar = true;
        if (podeCadastrar()) {
            conexao.setAutoCommit(false);
            try {
                EnderecoDAO enderecoDAO = new EnderecoDAO(conexao);
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
                Endereco endereco = new Endereco(getEstado(), getCidade(), getCep(), getBairro(), getRua(), getNumero());
                endereco.setCodigo(enderecoDAO.inserir(endereco));
                Funcionario funcionario = new Funcionario(getNome(), getCpf(), getTipo(), endereco, getCep(), getBairro());
                
                long codigo = funcionarioDAO.inserir(funcionario);
                funcionario.setCodigo(codigo);

                String email = funcionarioDAO.gerarEmail(funcionario);
                String senha = "confeg123";

                funcionario.setEmail(email);
                funcionario.setSenha(senha);
                funcionarioDAO.alterar(funcionario);
                
                conexao.commit();
                salvo = true;
                Window janela = (Window) ((Node) event.getSource()).getScene().getWindow();
                ((Stage) janela).close();
            } catch (Exception erro) {
                conexao.rollback();
                erro.printStackTrace();
            }
        } else {
            System.out.println("Não pode");
        }
    }

    @FXML 
    public void fecharTela(MouseEvent event) {
        Window janela = (Window) ((Node) event.getSource()).getScene().getWindow();
        ((Stage) janela).close();
    }

    @FXML
    public void initialize() {
        carregarDados();
        estado.getSelectionModel().selectedItemProperty().addListener((observavel, antigoEstado, novoEstado) -> {
            if (novoEstado != null) {
                CidadeDAO cidadeDAO = new CidadeDAO(conexao);
                cidade.setValue(null);
                cidade.getItems().setAll(cidadeDAO.buscarPorEstado(novoEstado));
            }
        });

        new Thread(() -> {
            while (!encerrarThreadValidacao) {
                if (clicouBotaoPodeCadastrar) {
                    Platform.runLater(() -> {
                        vf.validarTipo(exibirErroNoTipo, getTipo());
                        vf.validarCPF(exibirErroNoCpf, getCpf());
                        vf.validarCep(exibirErroNoCep, getCep());
                        vf.validarEstado(exibirErroNoEstado, getEstado());
                        vf.validarCidade(exibirErroNoCidade, getEstado(), getCidade());
                        vf.validarBairro(exibirErroNoBairro, getBairro());
                        vf.validarRua(exibirErroNoRua, getRua());
                        vf.validarNum(exibirErroNoNumero, numero.getText());
                        vf.validarNome(exibirErroNoNome, getNome());
                    });
                    try {
                        Thread.sleep(200);
                    } catch (Exception erro) {}
                }
            }
        }).start();
    }

    public void carregarDados() {
        EstadoDAO estadoDAO = new EstadoDAO(conexao);
        TipoFuncionarioDAO tipoFuncionarioDAO = new TipoFuncionarioDAO(conexao);
        estado.getItems().setAll(estadoDAO.buscarTodos());
        tipo.getItems().setAll(tipoFuncionarioDAO.buscarTodos());
    }

    public boolean podeCadastrar() {
        return Stream.of(
            vf.validarTipo(exibirErroNoTipo, getTipo()),
            vf.validarCPF(exibirErroNoCpf, getCpf()),
            vf.validarCep(exibirErroNoCep, getCep()),
            vf.validarEstado(exibirErroNoEstado, getEstado()),
            vf.validarCidade(exibirErroNoCidade, getEstado(), getCidade()),
            vf.validarBairro(exibirErroNoBairro, getBairro()),
            vf.validarRua(exibirErroNoRua, getRua()),
            vf.validarNum(exibirErroNoNumero, numero.getText()),
            vf.validarNome(exibirErroNoNome, getNome())
       ).allMatch( valor -> valor == true);
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

}
