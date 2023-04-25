package controladores.crudCliente;

import java.util.ArrayList;
import java.util.List;


import aplicacao.App;
import controladores.crudCliente.cadastro.ClienteCadastrarControlador;
import controladores.crudCliente.cadastro.ClienteControlador;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.entidadeDAO.ClienteDAO;
import modelos.entidades.Cliente;

public class CrudClienteControlador {

    @FXML 
    public HBox areaDeAlerta;

    @FXML
    public FlowPane areaDeClientes;

    @FXML
    public TextField textFieldPesquisa;

    private Stage tela;

    private List<Stage> telas = new ArrayList<>();

    private boolean clicouAdicionar = false;

    private ClienteDAO clienteDAO = new ClienteDAO(App.conexao);
    
    @FXML
    public void adicionarCliente(ActionEvent event) throws  Exception {
        if (!clicouAdicionar) {
            carregarCadastro();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "ADICIONAR", "Já existe uma janela em aberto.");
        }
    }

    @FXML
    public void fechar(MouseEvent event) {
        if (tela != null) {
            fecharTelas();
            tela.close();
        }
    }

    @FXML
    public void fecharJanela(MouseEvent event) {
        if (tela != null) {
            fecharTelas();
            tela.close();
        }
    }

    @FXML
    public void minimizar(MouseEvent event) {
        if (tela != null) {
            tela.setIconified(true);
        }
    }

    @FXML
    public void atualizarPesquisa(KeyEvent event) {
        pesquisar();
    }

    @FXML 
    public void apertouEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            pesquisar();
        }
    }

    @FXML 
    public void apertouPesquisar(MouseEvent event) {
        pesquisar();
    }



    public void pesquisar() {
      Platform.runLater(() ->{
        areaDeClientes.getChildren().clear();
        String valor = textFieldPesquisa.getText();

        if (valor.intern() == "") {
            atualizarAreaDeClientes();
            return;
        }

        List<Cliente> clientes = clienteDAO.filtrar(valor);

        if (clientes.size() == 0) {
            areaDeClientes.getChildren().add(App.obterTelaVaziaCrud());
            return;
        }

        clientes.stream()
            .forEach( cliente -> {
                try {
                    FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/clientes/subtela/subtela.fxml"));
                    Parent raiz = carregar.load();
                    ClienteControlador controlador = carregar.getController();
                    controlador.setCodigo(cliente.getCodigo());
                    controlador.setAreaDeAlerta(areaDeAlerta);
                    controlador.setNome("COD " + cliente.getCodigo());
                    controlador.setAtualizarAreaDeClientes(this::atualizarAreaDeClientes);
                    areaDeClientes.getChildren().add(raiz);
                } catch(Exception erro) {
                    erro.printStackTrace();
                }
        });



      });
    }

    @FXML
    public void initialize() {
        atualizarAreaDeClientes();
    }

    public void carregarCadastro() {
        try {
            clicouAdicionar = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/clientes/cadastro/cadastro.fxml"));
            Parent raiz = carregar.load();
            ClienteCadastrarControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            App.adicionarMovimento(palco, cena);   
            controlador.setTela(palco); 
            palco.setScene(cena);
            telas.add(palco);
            palco.showAndWait();
            telas.remove(palco);
            clicouAdicionar = false;
            if (controlador.getCadastrou()) {
                atualizarAreaDeClientes();
                App.exibirAlert(areaDeAlerta, "SUCESSO", "ADICIONAR", "O Cliente foi cadastro com sucesso.");
            } else if (controlador.getErro()){
                App.exibirAlert(areaDeAlerta, "FRACASSO", "ADICIONAR", "Operação abortada");
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void atualizarAreaDeClientes() {
        areaDeClientes.getChildren().clear();
            clienteDAO.buscarTodos()
                .forEach( cliente -> {
                    try {
                        FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/clientes/subtela/subtela.fxml"));
                        Parent raiz = carregar.load();
                        ClienteControlador controlador = carregar.getController();
                        controlador.setCodigo(cliente.getCodigo());
                        controlador.setAreaDeAlerta(areaDeAlerta);
                        controlador.setNome("COD " + cliente.getCodigo());
                        controlador.setAtualizarAreaDeClientes(this::atualizarAreaDeClientes);
                        areaDeClientes.getChildren().add(raiz);
                    } catch(Exception erro) {
                        erro.printStackTrace();
                    }
            });
    }


    public void fecharTelas() {
        telas.forEach(tela -> {
            if (tela != null) {
                tela.close();
            }
        });
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }

}
