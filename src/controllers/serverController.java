package controllers;

import Network.StartServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


/**
 * Created by zaruda on 08.12.2016.
 */
public class serverController{

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
}
