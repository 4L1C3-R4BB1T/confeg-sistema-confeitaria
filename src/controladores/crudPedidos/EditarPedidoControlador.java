package controladores.crudPedidos;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import aplicacao.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelos.entidadeDAO.BoloDAO;
import modelos.entidadeDAO.ClienteDAO;
import modelos.entidadeDAO.FuncionarioDAO;
import modelos.entidadeDAO.MetodoPagamentoDAO;
import modelos.entidadeDAO.PedidoBoloDAO;
import modelos.entidadeDAO.PedidoDAO;
import modelos.entidades.Bolo;
import modelos.entidades.Cliente;
import modelos.entidades.Funcionario;
import modelos.entidades.MetodoPagamento;
import modelos.entidades.Pedido;
import modelos.entidades.PedidoBolo;
import modelos.validacao.ValidaFormulario;

public class EditarPedidoControlador {

    @FXML 
    private Label areaErroData;

    @FXML
    private ComboBox<Cliente> clientes;

    @FXML
    private ComboBox<Funcionario> funcionarios;

    @FXML
    private ComboBox<Bolo> bolos;

    @FXML
    private TextField dataPedido;

    @FXML
    private TextArea observacao;

    @FXML
    private TextField quantidade;

    @FXML
    private ComboBox<MetodoPagamento> metodoPagamento;

    @FXML
    private TableView<PedidoBolo> tabela;

    @FXML
    private TableColumn<PedidoBolo, Bolo> colunaBolo;

    @FXML
    private TableColumn<PedidoBolo, Long> colunaQuantidade;

    @FXML
    private HBox areaDeAlerta;

    ClienteDAO clienteDAO = new ClienteDAO(App.conexao);
    FuncionarioDAO funcionarioDAO = new FuncionarioDAO(App.conexao);
    BoloDAO boloDAO = new BoloDAO(App.conexao);
    MetodoPagamentoDAO metodoPagamentoDAO = new MetodoPagamentoDAO(App.conexao);
    PedidoBoloDAO pedidoBoloDAO = new PedidoBoloDAO(App.conexao);
    PedidoDAO pedidoDAO = new PedidoDAO(App.conexao);

    private Pedido pedido;
    private List<PedidoBolo> pedidoBolos = new ArrayList<>();

    private Stage tela;

    private ValidaFormulario vf = new ValidaFormulario();
    
    private boolean registrou = false;

    @FXML
    public void cancelar(ActionEvent event) {
        encerrar();
    }

    @FXML
    public void confirmar(ActionEvent event) throws Exception {
        if (pedidoBolos.size() == 0)  {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INFORMAÇÃO", "É necessário ter no mínimo 1 pedido de bolo.");
            return;
        }

        if (validarCampos()) {
            App.conexao.setAutoCommit(false);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


                pedido.setCliente(getCliente());
                pedido.setFuncionario(getFuncionario());
                pedido.setMetodo(getMetodoPagamento());
                pedido.setObservacao(getObservacao());
                pedido.setDataPedido(new Date(sdf.parse(getDataPedido()).getTime()));
                pedidoDAO.alterar(pedido);

                for (PedidoBolo pb: pedidoBolos) {
                    if (pb.getCodigo() == null) {
                        pedidoBoloDAO.inserir(pb);
                    }
                }

                App.conexao.commit();
                registrou = true;
                encerrar();
            } catch (Exception erro) {
                erro.printStackTrace();
                App.conexao.rollback();
            }
        } 
    }

    @FXML
    public void fecharTelas(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void adicionarBolo(MouseEvent event) throws Exception {
        if (validarAdicaoPedidoBolo()) {
            PedidoBolo pedidoBolo = new PedidoBolo(pedido, getBolo(), Long.parseLong(getQuantidade()));
            pedidoBolos.add(pedidoBolo);
            tabela.getItems().add(pedidoBolo);
            tabela.getSelectionModel().clearSelection();
        }
    }

    @FXML
    public void removerBolo(MouseEvent event) throws Exception {
        PedidoBolo pedidoBolo = tabela.getSelectionModel().getSelectedItem();
        if (pedidoBolo != null) {
            App.conexao.setAutoCommit(false);
            try {
                // Verifica se existe no banco primeiro!
                if (pedidoBolo.getCodigo() != null) {
                    pedidoBoloDAO.remover(pedidoBolo);
                } 
                pedidoBolos.remove(pedidoBolo);
                tabela.getItems().remove(pedidoBolo);
                App.conexao.commit();
            } catch (Exception erro) {
                erro.printStackTrace();
                App.conexao.rollback();
            }
        }
    }

    @FXML
    public void initialize() {
        colunaBolo.setCellValueFactory(new PropertyValueFactory<>("bolo"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        carregarClientesFuncionariosBolosPagamento();
    }


    public void carregarClientesFuncionariosBolosPagamento() {
        clientes.getItems().setAll(clienteDAO.buscarTodos());
        funcionarios.getItems().setAll(funcionarioDAO.buscarTodos());
        bolos.getItems().setAll(boloDAO.buscarTodos());
        metodoPagamento.getItems().setAll(metodoPagamentoDAO.buscarTodos());
    }

    public boolean validarAdicaoPedidoBolo() throws Exception {
        if (getCliente() == null || getFuncionario() == null) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "ALERTA", "Selecione o Cliente e Funcionario");
            return false;
        } else if (!vf.validarNumero(quantidade.getText())) {
            App.exibirAlert(areaDeAlerta, "FRACASSO", "ALERTA", "A quantidade não é válida.");
        } else if (Long.parseLong(quantidade.getText()) <= 0) {
            App.exibirAlert(areaDeAlerta, "FRACASSO", "Quantidade", "Quantidade tem quer maior que 0");
            return false;
        } else {
            App.exibirAlert(areaDeAlerta, "SUCESSO", "ALERTA", "Item cadastrado");
        }
        return true; 
    }

    public boolean validarCampos() throws Exception {
        Object[] campos = {getCliente(), getFuncionario(), getDataPedido(), getMetodoPagamento()}; 
        if(Arrays.stream(campos).allMatch(campo -> vf.validarNuloOuVazio(campo))) {
            if (vf.validarData(areaErroData, getDataPedido(), "Preencha o campo corratemente dd/mm/yyyy")) {
                return true;
            }
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "CAMPO VAZIO", "Preencha todos os campos");
        }
        return false;
    }

    public void encerrar() {
        if (tela != null) {
            tela.close();
        }
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }


    public Cliente getCliente() {
        return clientes.getSelectionModel().getSelectedItem();
    }

    public Funcionario getFuncionario() {
        return funcionarios.getSelectionModel().getSelectedItem();
    }

    public Bolo getBolo() {
        return bolos.getSelectionModel().getSelectedItem();
    }

    public String getDataPedido() {
        return dataPedido.getText();
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento.getSelectionModel().getSelectedItem();
    }

    public String getObservacao() {
        return observacao.getText();
    }

    public String getQuantidade() {
        return quantidade.getText();
    }
    
    public boolean getRegistrouPedido() {
        return registrou;
    }

    public void setCliente(Cliente cliente) {
        clientes.setValue(cliente);
    }

    public void setFuncionario(Funcionario funcionario) {
        funcionarios.setValue(funcionario);
    }

    public void setBolo(Bolo bolo) {
        bolos.setValue(bolo);
    }

    public void setPedidoBolo(List<PedidoBolo> pedidoBolos) {
        this.pedidoBolos.addAll(pedidoBolos);
        tabela.getItems().setAll(pedidoBolos);
    }

    public void setData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dataPedido.setText(sdf.format(data));
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento.setValue(metodoPagamento);
    }

    public void setObservacao(String observacao) {
        this.observacao.setText(observacao);
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
