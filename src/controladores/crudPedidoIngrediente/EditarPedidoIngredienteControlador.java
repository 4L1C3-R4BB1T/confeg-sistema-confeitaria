package controladores.crudPedidoIngrediente;

import java.sql.Date;
import java.time.LocalDate;
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
import modelos.entidadeDAO.IngredienteDAO;
import modelos.entidadeDAO.PedidoCompraDAO;
import modelos.entidadeDAO.PedidoCompraIngredientesDAO;
import modelos.entidades.Funcionario;
import modelos.entidades.Ingrediente;
import modelos.entidades.PedidoCompra;
import modelos.entidades.PedidoCompraIngrediente;
import modelos.validacao.ValidaFormulario;

public class EditarPedidoIngredienteControlador {

    @FXML
    private ComboBox<Ingrediente> ingredientes;

    @FXML
    private TextArea observacao;

    @FXML
    private TextField quantidade;

    @FXML
    private TableView<PedidoCompraIngrediente> tabela;

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

    private PedidoCompra pedidoCompra;
    private IngredienteDAO ingredienteDAO = new IngredienteDAO(App.conexao);
    private PedidoCompraDAO pedidoCompraDAO = new PedidoCompraDAO(App.conexao);
    private PedidoCompraIngredientesDAO pedidoCompraIngredientesDAO = new PedidoCompraIngredientesDAO(App.conexao);
    
    private List<PedidoCompraIngrediente> carrinho = new ArrayList<>();
    private List<PedidoCompraIngrediente> removidos = new ArrayList<>();
    private Funcionario funcionario;
    private ValidaFormulario validaFormulario = new ValidaFormulario();

    private boolean sucesso = false;
    private boolean fracasso = false;
    
    @FXML
    void adicionarIngrediente(MouseEvent event) {
        if (podeAdicionarCarrinho()) {
            PedidoCompraIngrediente pi = new PedidoCompraIngrediente();
            pi.setIngrediente(getIngrediente());
            pi.setQuantidade(getQuantidade());
            pi.setPedidoCompra(pedidoCompra);
            tabela.getItems().add(pi);
            carrinho.add(pi);
        }
    }

    @FXML
    public void removerIngrediente(MouseEvent event) {
        if (podeRemoverCarrinho()) {
            PedidoCompraIngrediente pi = tabela.getSelectionModel().getSelectedItem();

            // Significa que já existe no banco de dados então vai para os removidos
            if (pedidoCompraIngredientesDAO.buscarPorCodigo(pi.getCodigo()) != null) {
                removidos.add(pi);
            } 

            carrinho.remove(pi);
            tabela.getItems().remove(pi);
        }
    }

    @FXML
    public void cancelar(ActionEvent event) {
        encerrar();
    }

    @FXML
    public void confirmar(ActionEvent event) throws Exception {
        if (podeSalvar()) {
            App.conexao.setAutoCommit(false);
            try {

                pedidoCompra.setDataPedido(Date.valueOf(getDataPedido()));

                for(PedidoCompraIngrediente pci: removidos) {
                    pedidoCompraIngredientesDAO.remover(pci);
                }
                
                for (PedidoCompraIngrediente pci: carrinho) {
                    pedidoCompraIngredientesDAO.inserir(pci);
                }

                pedidoCompraDAO.alterar(pedidoCompra);
                App.conexao.commit();
                sucesso = true;
                encerrar();
            } catch (Exception erro) {
                erro.printStackTrace();
                App.conexao.rollback();
                fracasso = true;
                encerrar();
            }
        }
    }

    @FXML
    public void fecharTelas(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void initialize() {
        colunaIngrediente.setCellValueFactory(new PropertyValueFactory<>("ingrediente"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        carregarIngredientes();
    }


    public boolean podeSalvar() {
        if (!validarDataPedido()) {
            return false;
        } else if (carrinho.size() == 0) {
            App.exibirAlert(areaDeAlerta, "FRACASSO", "PEDIDO", "É necessário ter 1 item no carrinho.");
            return false;
        } else {
            return true;
        }
    }

    public boolean podeRemoverCarrinho() {
        if (tabela.getSelectionModel().getSelectedItem() == null) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INGREDIENTE", "Selecione o Item no carrinho.");
            return false;
        } else {
            return true;
        }
    }

    public boolean podeAdicionarCarrinho() {
        if (getIngrediente() == null) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INGREDIENTE", "Selecione o Ingrediente");
            return false;
        } else if (quantidade.getText().intern() == "") {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "QUANTIDADE", "Preencha a Quantidade");
            return false;
        } else if (!validaFormulario.validarNumero(quantidade.getText())) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "QUANTIDADE", "Não é um valor númerico.");
            return false;
        } else if (getQuantidade() <= 0) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "QUANTIDADE", "Quantidade tem que ser maior que 0.");
            return false;
        } else {
            return true;
        }
    }

    public boolean validarDataPedido() {
        if (getDataPedido() == null) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "DATA PEDIDO", "Preencha a Data Pedido");
            return false;
        } else if (getDataPedido().isBefore(LocalDate.now())) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "DATA PEDIDO", "Data Pedido não pode ser anterior a data atual");
            return false;
        } else {
            return true;
        }
    }

    public void encerrar() {
        if (tela != null) {
            tela.close();
        }
    }

    public void carregarIngredientes() {
        ingredientes.getItems().setAll(ingredienteDAO.buscarTodos());
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        funcionarios.setValue(funcionario);
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setItemNaTabela(List<PedidoCompraIngrediente> carrinho) {
        tabela.getItems().setAll(carrinho);
        this.carrinho.addAll(carrinho);
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }

    public void setPedidoCompra(PedidoCompra pedidoCompra) {
        this.pedidoCompra = pedidoCompra;
        dataPedido.setValue(pedidoCompra.getDataPedido().toLocalDate());
        observacao.setText(pedidoCompra.getObservacao());
    }


    public Ingrediente getIngrediente() {
        return ingredientes.getSelectionModel().getSelectedItem();
    }

    public Long getQuantidade() {
        return Long.parseLong(quantidade.getText());
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
