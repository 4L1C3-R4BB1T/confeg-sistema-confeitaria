package controladores.crudPedidoIngrediente;


import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import aplicacao.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelos.consultas.ConsultaPersonalizada;
import modelos.consultas.entitidades.TotalComprasFuncionario;
import modelos.entidadeDAO.FuncionarioDAO;
import modelos.entidadeDAO.IngredienteDAO;
import modelos.entidadeDAO.PedidoCompraDAO;
import modelos.entidadeDAO.PedidoCompraIngredientesDAO;
import modelos.entidades.Funcionario;
import modelos.entidades.Ingrediente;
import modelos.entidades.PedidoCompra;
import modelos.entidades.PedidoCompraIngrediente;
import modelos.validacao.ValidaFormulario;

public class PedirIngredienteControlador {

    @FXML
    private ComboBox<Ingrediente> ingredientes;

    @FXML
    private TextArea observacao;

    @FXML
    private TextField quantidade;

    @FXML
    private TableView<PedidoCompraIngrediente> tabelaPedidos;

    @FXML
    private TableColumn<PedidoCompraIngrediente, Ingrediente> colunaIngrediente;

    @FXML
    private TableColumn<PedidoCompraIngrediente, Long> colunaQuantidade;

    @FXML
    private ComboBox<Funcionario> funcionarios;

    @FXML
    private Label areaErroData;

    @FXML
    private HBox areaDeAlerta;

    @FXML
    private DatePicker dataPedido;

    private Stage tela;

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO(App.conexao);
    private IngredienteDAO ingredienteDAO = new IngredienteDAO(App.conexao);
    private PedidoCompraIngredientesDAO pedidoCompraIngredientesDAO = new PedidoCompraIngredientesDAO(App.conexao);
    private PedidoCompraDAO pedidoCompraDAO = new PedidoCompraDAO(App.conexao);

    private ValidaFormulario validaFormulario = new ValidaFormulario();

    private PedidoCompra pedidoCompra = new PedidoCompra();
    private List<PedidoCompraIngrediente> carrinho = new ArrayList<>();

    private boolean sucesso = false;
    private boolean fracasso = false;

    @FXML
    public void adicionarPedido(MouseEvent event) {
        if (podeAdicionar()) {
            PedidoCompraIngrediente compra = new PedidoCompraIngrediente(pedidoCompra, getIngrediente(), getQuantidade());
            carrinho.add(compra);
            tabelaPedidos.getItems().add(compra);
            limparPedido();
        }
    }

    @FXML
    public void removerPedido(MouseEvent event) {
        if (podeRemover()) {
            carrinho.remove(getPedidoCompraIngrediente());
            tabelaPedidos.getItems().remove(getPedidoCompraIngrediente());
        }
    }

    @FXML
    public void cancelar(ActionEvent event) {
        encerrar();
    }

    @FXML
    public void confirmar(ActionEvent event) throws Exception {
        if (podeSalvar()) {
            if (carrinho.size() <= 0) {
                App.exibirAlert(areaDeAlerta, "FRACASSO", "ERRO", "É necessário ter no mínimo 1 ingrediente.");
                return;
            }
            
            App.conexao.setAutoCommit(false);

            try {
                pedidoCompra.setDataPedido(Date.valueOf(getDataPedido()));
                pedidoCompra.setFuncionario(getFuncionario());
                pedidoCompra.setObservacao(getObservacao());
                pedidoCompra.setCodigo(pedidoCompraDAO.inserir(pedidoCompra));

                for (PedidoCompraIngrediente pedido: carrinho) {
                    pedidoCompraIngredientesDAO.inserir(pedido);
                }
                App.conexao.commit();
                sucesso = true;
                encerrar();
            } catch (Exception erro) {
                erro.printStackTrace();
                App.conexao.rollback();
                fracasso = true;
            }

        }
    }

    @FXML
    public void fecharTelas(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void initialize() {
        carregarElementos();
        colunaIngrediente.setCellValueFactory(new PropertyValueFactory<>("ingrediente"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
    }

    public void limparPedido() {
        quantidade.setText("");
        ingredientes.setValue(null);
    }

    public boolean podeAdicionar() {
        if (getIngrediente() == null) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INFORMAÇÃO", "Selecione o Ingrediente.");
            return false;
        } else if (quantidade.getText().intern() == "") {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INFORMAÇÃO", "Preencha o campo quantidade.");
            return false;
        } else if (!validaFormulario.validarNumero(quantidade.getText())) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INFORMAÇÃO", "Quantidade não é um número.");
            return false;
        } else if (getQuantidade() <= 0) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INFORMAÇÃO", "Quantidade não pode ser menor ou zero.");
            return false;
        } else {
            App.exibirAlert(areaDeAlerta, "SUCESSO", "INFORMAÇÃO", "Ingrediente adicionado ao carrinho.");
        }
        return true;
    }

    public boolean podeRemover() {
        if (getPedidoCompraIngrediente() == null) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INFORMAÇÃO", "Selecione o Ingrediente.");
            return false;
        }
        App.exibirAlert(areaDeAlerta, "SUCESSO", "INFORMAÇÃO", "Ingrediente removido.");
        return true;
    }

    public boolean podeSalvar() {

        if (getFuncionario() == null) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INFORMAÇÃO", "Selecione o Funcionário.");
            return false;
        }
        
        if (pedidoCompraDAO.buscarPendentesPorFuncionario(getFuncionario()).size() >= 2) {
            App.exibirAlert(areaDeAlerta, "FRACASSO", "REGRA", "Um funcionário pode ter no máximo 2 pedidos em aberto.");
            return false;
        }

        if (!validarRegraNegocio()) {
            App.exibirAlert(areaDeAlerta, "FRACASSO", "REGRA", "Crédito de 500 reais mensal ultrapassado.");
            return false;
        }

        if (getDataPedido() == null) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INFORMAÇÃO", "Insira a Data do Pedido.");
            return false;
        } 

        LocalDate dataDeHoje = LocalDate.now();
        String formatado = dataDeHoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        if (getDataPedido().isBefore(dataDeHoje)) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INFORMAÇÃO", "A data é menor que a data hoje: " + formatado);
            return false;
        }

        return true;

    }

    public boolean validarRegraNegocio() {
        final double LIMITE_MES = 500;
        LocalDate localDate = LocalDate.now();
        int mes = localDate.getMonth().getValue();
        int ano = localDate.getYear();
        TotalComprasFuncionario tcf = ConsultaPersonalizada.totalComprasFuncionarioMes(ano, mes, getFuncionario());
        double totalCarrinho = carrinho.stream().map( x -> x.getIngrediente().getPreco() * x.getQuantidade()).reduce(0D, (x, y) -> x + y);
        return ((tcf == null ? 0 : tcf.getTotal()) + totalCarrinho) <= LIMITE_MES;
    }

    public void carregarElementos() {
        funcionarios.getItems().setAll(funcionarioDAO.buscarTodos());
        ingredientes.getItems().setAll(ingredienteDAO.buscarTodos());
    }

    public void encerrar() {
        if (tela != null) {
            tela.close();
        }
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }

    public Ingrediente getIngrediente() {
        return ingredientes.getSelectionModel().getSelectedItem();
    }

    public Funcionario getFuncionario() {
        return funcionarios.getSelectionModel().getSelectedItem();
    }

    public Long getQuantidade() {
        return Long.parseLong(quantidade.getText());
    }

    public PedidoCompraIngrediente getPedidoCompraIngrediente() {
        return tabelaPedidos.getSelectionModel().getSelectedItem();
    }

    public LocalDate getDataPedido() {
        return dataPedido.getValue();
    }

    public String getObservacao() {
        return observacao.getText();
    }

    public boolean getSucesso() {
        return sucesso;
    }

    public boolean getFracasso() {
        return fracasso;
    }
}
