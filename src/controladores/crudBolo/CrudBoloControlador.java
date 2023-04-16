package controladores.crudBolo;

import java.util.ArrayList;
import java.util.List;

import aplicacao.App;
import controladores.crudBolo.cadastro.BoloControlador;
import controladores.crudBolo.cadastro.CadastroBoloControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.entidadeDAO.BoloDAO;

public class CrudBoloControlador {

    @FXML 
    public HBox areaDeAlerta;

    @FXML
    public FlowPane areaDebolos;

    private Stage tela;

    private List<Stage> telas = new ArrayList<>();

    private boolean clicouAdicionar = false;

    private BoloDAO boloDAO = new BoloDAO(App.conexao);
    
    @FXML
    public void adicionarBolo(ActionEvent event) throws  Exception {
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
    public void initialize() {
        atualizarAreaDeBolos();
    }

    public void carregarCadastro() {
        try {
            clicouAdicionar = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/bolos/cadastro/cadastro.fxml"));
            Parent raiz = carregar.load();
            System.out.println(raiz);
            CadastroBoloControlador controlador = carregar.getController();
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
                atualizarAreaDeBolos();
                App.exibirAlert(areaDeAlerta, "SUCESSO", "ADICIONAR", "O Bolo foi cadastro com sucesso.");
            } else {
                App.exibirAlert(areaDeAlerta, "FRACASSO", "ADICIONAR", "Operação abortada");
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }


    public void atualizarAreaDeBolos() {
        areaDebolos.getChildren().clear();
        boloDAO.buscarTodos()
            .forEach( bolo -> {
                try {
                    FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/bolos/subbolo/subbolo.fxml"));
                    Parent raiz = carregar.load();
                    BoloControlador controlador = carregar.getController();
                    controlador.setCodigo(bolo.getCodigo());
                    controlador.setFoto(bolo.getSabor().getCodigo());
                    controlador.setAreaDeAlerta(areaDeAlerta);
                    controlador.setNome(bolo.getSabor().getDescricao().substring(0, 5) + bolo.getCodigo());
                    areaDebolos.getChildren().add(raiz);
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
