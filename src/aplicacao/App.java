package aplicacao;
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
        App.adicionarMovimento(palco, cena);
        palco.setScene(cena);
        palco.initStyle(StageStyle.UNDECORATED);
        palco.show();
    }

    public static void adicionarMovimento(Stage stage, Scene scene) {
        final double[] eixos = new double[2];
        scene.setOnMousePressed( event -> {
            eixos[0] = event.getSceneX();
            eixos[1] = event.getSceneY();
        });

        scene.setOnMouseDragged( event -> {
            stage.setX(event.getScreenX() - eixos[0]);
            stage.setY(event.getScreenY() - eixos[1]);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}