package controladores.crudPedidos;

import aplicacao.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.entidades.Pedido;

public class PedidoControlador {

    private Pedido pedido;

    private boolean clicouBotaoEditar = false;

    @FXML
    public void editar(MouseEvent event) {
        System.out.println("Você clicou em editar");
    }

    @FXML
    public void remover(MouseEvent event) {
        System.out.println("Clicou em remover");
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}
