import controladores.LoginControlador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.*;

public class App extends Application {

    @Override 
    public void start(Stage palco) throws Exception {
        FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/login/login.fxml"));
        Parent raiz = carregar.load();
        LoginControlador controlador = carregar.getController();
        controlador.setPalco(palco);
        Scene cena = new Scene(raiz);
        palco.setScene(cena);
        palco.initStyle(StageStyle.UNDECORATED);
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}