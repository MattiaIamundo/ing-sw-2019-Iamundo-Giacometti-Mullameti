package it.polimi.sw2019.view.ControllerClasses;

import it.polimi.sw2019.network.Socket.ClientSocket;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;


public class LobbyController {
    @FXML
    private Button startButton;

    @FXML private ProgressBar pingPong;
    private ClientSocket clientSocket;
    double i = 0.0;

    @FXML
    public void initialize(){
        this.startButton.setDefaultButton(true);
        this.startButton.setVisible(true);
        pingPong.setProgress(0);
    }

    public void setClientSocket(ClientSocket clientSocket){
        this.clientSocket=clientSocket;
    }


    @FXML
    public void startButtonClicked(){

        while(i<1.0) {
            clientSocket.getContSelect().waitForPing(clientSocket.getPlayerView());

            pingPong.setProgress(i);
            i = i + 0.1;
        }

    }
}
