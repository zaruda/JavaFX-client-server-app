package sample;


import Network.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;


public class Main extends Application {
    private static Socket clientSock;
    private static ObjectOutputStream outStream;
    private static ObjectInputStream inStream;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.getIcons().add(new Image("Icons/System-icon.png"));
        Parent intro = FXMLLoader.load(getClass().getResource("/fxml/intro.fxml"));
        primaryStage.setTitle("Таможенный калькулятор");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(intro));
        primaryStage.show();

    }

    public static void main(String[] args)
    {
        launch(args);
    }

}

