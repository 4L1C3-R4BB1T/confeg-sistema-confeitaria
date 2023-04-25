package controladores.crudFuncionario;

import java.util.ArrayList;
import java.util.List;

import aplicacao.App;
import controladores.login.CadastroControlador;
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
import modelos.entidadeDAO.FuncionarioDAO;
import modelos.entidades.Funcionario;

public class CrudFuncionarioControlador {

    @FXML 
    public HBox areaDeAlerta;

    @FXML
    public FlowPane areaDeFuncionarios;

    @FXML
    public TextField textFieldPesquisa;

    private Stage tela;

    private List<Stage> telas = new ArrayList<>();

    private boolean clicouAdicionar = false;

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO(App.conexao);
    
    @FXML
    public void adicionarFuncionario(ActionEvent event) throws  Exception {
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
       Platform.runLater(() -> {
            areaDeFuncionarios.getChildren().clear();
            String valor = textFieldPesquisa.getText();

            if (valor.intern() == "") {
            atualizarAreaDeFuncionarios();
                return;
            }

            List<Funcionario> funcionarios = funcionarioDAO.filtar(valor);

            if (funcionarios.size() == 0) {
                areaDeFuncionarios.getChildren().add(App.obterTelaVaziaCrud());
                return;
            }

            funcionarios
                .stream()
                    .forEach( funcionario -> {
                        try {
                            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/funcionarios/subtela/subtela.fxml"));
                            Parent raiz = carregar.load();
                            FuncionarioControlador controlador = carregar.getController();
                            controlador.setCodigo(funcionario.getCodigo());
                            controlador.setAreaDeAlerta(areaDeAlerta);
                            controlador.carregarImagem();
                            controlador.setNome("COD " + funcionario.getCodigo());
                            controlador.setAtualizarAreaDeFuncionarios((this::atualizarAreaDeFuncionarios));
                            areaDeFuncionarios.getChildren().add(raiz);
                        } catch(Exception erro) {
                            erro.printStackTrace();
                        }
                });
        });
    }



    @FXML
    public void initialize() {
        atualizarAreaDeFuncionarios();
    }

    public void carregarCadastro() {
        try {
            clicouAdicionar = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/login/cadastro/cadastro.fxml"));
            Parent raiz = carregar.load();
            CadastroControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            App.adicionarMovimento(palco, cena);   
            controlador.setTela(palco); 
            palco.setScene(cena);
            telas.add(palco);
            palco.showAndWait();
            telas.remove(palco);
            clicouAdicionar = false;
            if (controlador.dadosForamSalvos()) {
                atualizarAreaDeFuncionarios();
                App.exibirAlert(areaDeAlerta, "SUCESSO", "ADICIONAR", "O Funcionário foi cadastro com sucesso.");
            } else if (controlador.getErro()){
                App.exibirAlert(areaDeAlerta, "FRACASSO", "ADICIONAR", "Erro ao cadastrar Funcionário");
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void atualizarAreaDeFuncionarios() {
       areaDeFuncionarios.getChildren().clear();
            funcionarioDAO.buscarTodos()
                .forEach( funcionario -> {
                    try {
                        FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/funcionarios/subtela/subtela.fxml"));
                        Parent raiz = carregar.load();
                        FuncionarioControlador controlador = carregar.getController();
                        controlador.setCodigo(funcionario.getCodigo());
                        controlador.setAreaDeAlerta(areaDeAlerta);
                        controlador.carregarImagem();
                        controlador.setNome("COD " + funcionario.getCodigo());
                        controlador.setAtualizarAreaDeFuncionarios((this::atualizarAreaDeFuncionarios));
                        areaDeFuncionarios.getChildren().add(raiz);
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
