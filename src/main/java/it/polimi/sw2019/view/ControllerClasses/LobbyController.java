package it.polimi.sw2019.view.ControllerClasses;

import it.polimi.sw2019.network.Socket.ClientSocket;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class LobbyController {
    @FXML
    private Button startButton;

    private ClientSocket clientSocket;

    @FXML
    public void initialize(){
        this.startButton.setDefaultButton(true);
        this.startButton.setVisible(false);
    }

    public void setClientSocket(ClientSocket clientSocket){
        this.clientSocket=clientSocket;
    }


    @FXML
    public void startButtonClicked(){

    }
}
