package controladores.crudBolo;

import java.util.ArrayList;
import java.util.List;

import aplicacao.App;
import controladores.crudBolo.cadastro.CadastroBoloController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CrudBoloControlador {

    @FXML 
    public HBox areaDeAlerta;

    private Stage tela;

    private List<Stage> telas = new ArrayList<>();

    private boolean clicouAdicionar = false;
    
    @FXML
    public void adicionarBolo(ActionEvent event) {
        if (!clicouAdicionar) {
            carregarCadastro();
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
    public void minimizar(MouseEvent event) {
        if (tela != null) {
            tela.setIconified(true);
        }
    }

    @FXML
    public void initialize() {

    }

    public void carregarCadastro() {
        try {
            clicouAdicionar = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/bolos/cadastro/cadastro.fxml"));
            Parent raiz = carregar.load();
            System.out.println(raiz);
            CadastroBoloController controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            App.adicionarMovimento(palco, cena);    
            controlador.setTela(palco);
            palco.setScene(cena);
            telas.add(palco);
            palco.showAndWait();
            telas.remove(palco);
            clicouAdicionar = false;
        } catch (Exception erro) {
            System.out.println("Erro na linha 40 CrudBoloControlador");
            erro.printStackTrace();
        }
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
