package cz.daiton;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Game;

import java.io.IOException;
import java.io.InputStream;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        setProperties(stage);
        Game game = new Game();
        game.setUp(stage);
    }

    private static Image loadIcon() throws IOException {
        InputStream is = App.class.getResourceAsStream("icon.png");
        return new Image(is);
    }

    private static void setProperties(Stage stage) throws IOException {
        stage.setResizable(false);
        stage.setTitle("Breakout");
        stage.getIcons().add(loadIcon());
    }

    public static void main(String[] args) {
        launch();
    }
}