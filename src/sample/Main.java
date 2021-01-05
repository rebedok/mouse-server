package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.Search;
import net.ServerDesk;

public class Main extends Application {
    static ServerDesk serverDesk;
    static Search search;

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setTitle("Mouse");
        stage.setScene(new Scene(root,stage.getWidth(),stage.getHeight()));
        stage.show();
        stage.setOnCloseRequest(e -> {Platform.exit(); System.exit(0);});
        startNet();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void startNet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               search = new Search();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new ServerDesk();
            }
        }).start();
    }
}