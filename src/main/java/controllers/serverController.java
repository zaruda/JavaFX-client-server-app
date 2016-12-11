package controllers;

import Network.StartServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by zaruda on 08.12.2016.
 */
public class serverController implements Initializable{

    private StartServer server=new StartServer();
    @FXML
    private Label serverStatus;

    @FXML
    void startServer(ActionEvent event) {
        server.startServer();
        serverStatus.setText("ON");
    }

    @FXML
    void stopServer(ActionEvent event) {
        server.stopServer();
        serverStatus.setText("OFF");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serverStatus.setText("ON");
    }
}
