package controladores.crudFuncionario;


import aplicacao.App;
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
import modelos.entidadeDAO.FuncionarioDAO;
import modelos.entidades.Funcionario;
import modelos.interfaces.AproveitarFuncao;

// Bolo para editar ou remover
public class FuncionarioControlador {

    @FXML
    private AnchorPane modal;

    @FXML
    private ImageView foto;

    @FXML
    private Button nome;

    private Long codigo;

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO(App.conexao);

    private boolean clicouEditar = false;

    private Node areaDeAlerta;

    private AproveitarFuncao atualizarAreaDeFuncionarios ;

    private static int geradorImagem = 0;
    private static int quantidadeImagem = 2;


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
            App.exibirAlert(areaDeAlerta, "FRACASSO", "Edição", "Edição já aberta para o Funcionário.");
        }
    }

    @FXML
    public void remover(ActionEvent event) throws Exception {
        if (codigo != null) {
            App.conexao.setAutoCommit(false);
           try {
                funcionarioDAO.remover(funcionarioDAO.buscarPorCodigo(codigo));
                atualizarAreaDeFuncionarios.usar();
                App.conexao.commit();
                App.exibirAlert(areaDeAlerta, "SUCESSO", "DELEÇÃO", "O Funcionário com ID: " + codigo + " foi removido.");
           } catch (Exception erro) {
                erro.printStackTrace();
                App.conexao.rollback();
                App.exibirAlert(areaDeAlerta, "FRACASSO", "DELEÇÃO", "Não foi possível remover");
           }
        }
    }


    public void abrirTelaEditar() {

        try {
            clicouEditar = true;
            FXMLLoader carregar = new  FXMLLoader(getClass().getResource("/telas/funcionarios/edicao/edicao.fxml"));
            Parent raiz = carregar.load();
            FuncionarioEditarControlador controlador = carregar.getController();
            Funcionario funcionario = funcionarioDAO.buscarPorCodigo(codigo);
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            App.adicionarMovimento(palco, cena);
            controlador.setTela(palco);
            controlador.setFuncionario(funcionario);
            palco.setScene(cena);
            palco.showAndWait();
            clicouEditar = false;
            if (controlador.dadosForamSalvos()) {
                atualizarAreaDeFuncionarios.usar();
                App.exibirAlert(areaDeAlerta, "SUCESSO", "Edição", "Funcionário alterado com sucesso.");
            } else if (controlador.getErro()) {
                App.exibirAlert(areaDeAlerta, "FRACASSO", "Edição", "Não foi possível alterar.");
            }
        

        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void carregarImagem() {
        geradorImagem = geradorImagem % quantidadeImagem;
        String caminho = getClass().getResource("/telas/funcionarios/subtela/"+ ++geradorImagem + ".png").toExternalForm();
        Image imagem = new Image(caminho);
        foto.setImage(imagem);
        
    }



    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome.setText(nome);
    }

    public void setAreaDeAlerta(Node areaAlerta) {
        this.areaDeAlerta = areaAlerta;
    }

    public void setAtualizarAreaDeFuncionarios(AproveitarFuncao funcao) {
       this.atualizarAreaDeFuncionarios = funcao;
    }

}
