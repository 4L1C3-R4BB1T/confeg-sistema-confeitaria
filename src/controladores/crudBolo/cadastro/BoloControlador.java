package controladores.crudBolo.cadastro;

import java.net.URL;

import aplicacao.App;
import controladores.crudBolo.edicao.BoloEdicaoControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.entidadeDAO.BoloDAO;
import modelos.entidades.Bolo;

// Bolo para editar ou remover
public class BoloControlador {

    @FXML
    private AnchorPane modal;

    @FXML
    private ImageView foto;

    @FXML
    private Button nome;

    private Long codigo;
    private BoloDAO boloDAO = new BoloDAO(App.conexao);

    private boolean clicouEditar = false;

    private Node areaDeAlerta;


    @FXML
    public void abrirModal(ActionEvent event) {
        if (modal.isVisible()) {
            App.removerEfeitoSuave(modal);
        } else {
            App.adicionaEfeitoSuave(modal);
        }
    }

    @FXML
    public void editar(ActionEvent event) throws Exception {
        if (!clicouEditar) {
            abrirTelaEditar();
        } else {
            App.exibirAlert(areaDeAlerta, "FRACASSO", "Edição", "Edição já aberta para o bolo.");
        }
    }

    @FXML
    public void remover(ActionEvent event) {
        if (codigo != null) {
            boloDAO.remover(boloDAO.buscarPorCodigo(codigo));
        }
    }

    public void abrirTelaEditar() {
        try {
            clicouEditar = true;
            FXMLLoader carregar = new  FXMLLoader(getClass().getResource("/telas/bolos/edicao/edicao.fxml"));
            Parent raiz = carregar.load();
            BoloEdicaoControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            // Setar os dados para edição
            App.adicionarMovimento(palco, cena);
            Bolo bolo = boloDAO.buscarPorCodigo(codigo);

            if (bolo != null) {
                controlador.setSabor(bolo.getSabor());
                controlador.setPreco(bolo.getPreco());
                controlador.setPeso(bolo.getPeso());
                controlador.setFabricacao(bolo.getFabricacao());
                controlador.setVencimento(bolo.getVencimento());
                controlador.setDescricao(bolo.getDescricao());
                controlador.setBolo(bolo);
            }

            controlador.setTela(palco);
            palco.setScene(cena);
            palco.showAndWait();
            clicouEditar = false;

            if (controlador.getAlterou()) {
                App.exibirAlert(areaDeAlerta, "SUCESSO", "Edição", "Bolo alterado com sucesso.");
            } else {
                App.exibirAlert(areaDeAlerta, "FRACASSO", "Edição", "Erro interno ou Operação Cancelada.");
            }

        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome.setText(nome);
    }

    public void setFoto(long codigo) {
        String caminho = getClass().getResource("/telas/principal/bolo/images/").toExternalForm() + codigo + ".png";
        foto.setImage(new Image(caminho));
    }

    public void setAreaDeAlerta(Node areaAlerta) {
        this.areaDeAlerta = areaAlerta;
    }

}
