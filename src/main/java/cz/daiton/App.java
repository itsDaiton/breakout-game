package cz.daiton;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
        setStage(stage);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static Image loadIcon(String icon) throws IOException {
        InputStream is = App.class.getResourceAsStream(icon);
        return new Image(is);
    }

    private static void setProperties(Stage stage) throws IOException {
        stage.setResizable(false);
        stage.setTitle("Breakout");
        stage.getIcons().add(loadIcon("icon.png"));
    }

    private static void setStage(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 1000, 800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}