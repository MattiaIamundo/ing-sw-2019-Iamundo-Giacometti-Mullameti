package it.polimi.sw2019.view.ControllerClasses;

import it.polimi.sw2019.network.Socket.ClientSocket;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;



import java.io.IOException;

public class MenuController {



    private ClientSocket clientSocket;

    @FXML private Button newButton;
    @FXML private Button exitButton;

    @FXML
    public void initialize(){
        this.newButton.setDefaultButton(true);
        this.exitButton.setCancelButton(true);
    }


    public void newPlayerButtonPushed()throws IOException
    {


        Parent newPlayer=FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML/NewPlayer.fxml"));
        Scene newPlayerScene = new Scene(newPlayer);

        Stage window = (Stage)newButton.getScene().getWindow();

        window.setScene(newPlayerScene);
        window.show();
    }

    @FXML
    private void exitButtonPushed(){
        System.exit(0);
    }


    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket=clientSocket;
    }
}
