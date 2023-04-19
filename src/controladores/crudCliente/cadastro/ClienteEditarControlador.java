package controladores.crudCliente.cadastro;

import java.util.stream.Stream;

import aplicacao.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelos.entidadeDAO.CidadeDAO;
import modelos.entidadeDAO.ClienteDAO;
import modelos.entidadeDAO.EnderecoDAO;
import modelos.entidadeDAO.EstadoDAO;
import modelos.entidades.Cidade;
import modelos.entidades.Cliente;
import modelos.entidades.Endereco;
import modelos.entidades.Estado;
import modelos.validacao.ValidaFormulario;

public class ClienteEditarControlador {

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

    @FXML
    private TextField telefone;

    @FXML
    private Label erroNome;

    @FXML
    private Label erroCpf;

    @FXML
    private Label erroCep;

    @FXML
    private Label erroEstado;

    @FXML
    private Label erroBairro;

    @FXML
    private Label erroCidade;

    @FXML
    private Label erroRua;

    @FXML
    private Label erroNumero;

    @FXML
    private Label erroTelefone;

    private Stage tela;

    private EstadoDAO estadoDAO = new EstadoDAO(App.conexao);

    private CidadeDAO cidadeDAO = new CidadeDAO(App.conexao);

    private EnderecoDAO enderecoDAO = new EnderecoDAO(App.conexao);

    private ClienteDAO clienteDAO = new ClienteDAO(App.conexao);

    private ValidaFormulario vf = new ValidaFormulario();

    private volatile boolean threadPodeParar = false;
    private volatile boolean threadPodeValidar = false;

    private boolean cadastrou = false;
    private boolean erro = false;

    private Cliente cliente;

    private Endereco endereco;

    @FXML
    public void fechar(ActionEvent event) {
        encerrar();
    }

    @FXML
    public void encerrar2(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void salvar(ActionEvent event) throws Exception {
        if (validarCampos()) {
            App.conexao.setAutoCommit(false);
            try {
    
                endereco.setCep(getCep());
                endereco.setEstado(getEstado());
                endereco.setCidade(getCidade());
                endereco.setBairro(getBairro());
                endereco.setRua(getRua());
                endereco.setNumero(Integer.parseInt(getNumero()));
                cliente.setNome(getNome());
                cliente.setTelefone(getTelephone());
                clienteDAO.alterar(cliente);
                enderecoDAO.alterar(endereco);
                App.conexao.commit();
                cadastrou = true;
                encerrar();
            } catch (Exception erro) {
                erro.printStackTrace();
                App.conexao.rollback();
                this.erro = true;
                encerrar();
            }
        }
        threadPodeValidar = true;
    }

    @FXML
    public void initialize() {
        carregarSelecao();
        estado.getSelectionModel().selectedItemProperty().addListener((observable, antigoValor, novoValor) -> {
            if (novoValor != null) {
                cidade.setValue(null);
                cidade.getItems().setAll(cidadeDAO.buscarPorEstado(novoValor));
            }
        });

        new Thread(() -> {
            while (!threadPodeParar) {
                if (threadPodeValidar) {
                    Platform.runLater(() -> validarCampos());
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public boolean validarCampos() {
        return Stream.of(
            vf.validarCampo(erroNome, getNome(), "Preencha o Nome"),
            vf.validarCep(erroCep, getCep()),
            vf.validarComboBox(erroCidade, getCidade(), "Selecione a Cidade"),
            vf.validarComboBox(erroEstado, getEstado(), "Selecione a Estado"),
            vf.validarCampo(erroBairro, getBairro(), "Preencha o Bairro"),
            vf.validarCampo(erroRua, getRua(), "Preencha o Rua"),
            vf.validarValorNumerico(erroNumero, getNumero()),
            vf.validarValorNumerico(erroTelefone, getTelephone())
        ).allMatch(valor -> valor == true);
    }

    public void carregarSelecao() {
        estado.getItems().setAll(estadoDAO.buscarTodos());
    }

    public void encerrar() {
        if (tela != null) {
            threadPodeParar = true;
            tela.close();
        }
    }

    public void setCliente(Cliente cliente) {
        nome.setText(cliente.getNome());
        cpf.setText(cliente.getCpf());
        cep.setText(cliente.getEndereco().getCep());
        estado.setValue(cliente.getEndereco().getEstado());
        cidade.setValue(cliente.getEndereco().getCidade());
        bairro.setText(cliente.getEndereco().getBairro());
        rua.setText(cliente.getEndereco().getRua());
        numero.setText(String.valueOf(cliente.getEndereco().getNumero()));
        telefone.setText(cliente.getTelefone().trim());
        this.cliente = cliente;
        this.endereco = cliente.getEndereco();
    }

    public boolean getCadastrou() {
        return cadastrou;
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }

    public String getNome() {
        return nome.getText();
    }

    public String getCpf() {
        return cpf.getText();
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

    public String getNumero() {
        return numero.getText();
    }

    public String getTelephone() {
        return telefone.getText().trim();
    }

    public boolean getErro() {
        return erro;
    }
}
