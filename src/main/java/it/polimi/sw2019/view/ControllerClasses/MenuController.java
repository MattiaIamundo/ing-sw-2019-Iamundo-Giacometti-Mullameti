package it.polimi.sw2019.view.ControllerClasses;

import it.polimi.sw2019.network.Socket.ClientSocket;
import it.polimi.sw2019.view.GUI;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;



import java.io.IOException;

public class MenuController {



    private GUI gui;
    private ClientSocket clientSocket;

    @FXML private Button newButton;
    @FXML private Button exitButton;

    @FXML
    public void initialize(){
        this.newButton.setDefaultButton(true);
        this.exitButton.setCancelButton(true);

    }

   /* public void attivaButton(){
        String string=gui.sendOk();
        if((gui.sendOk().equals("ok")){
            this.newButton.setDisable(false);
        }
    }*/

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket=clientSocket;
    }

    public void newPlayerButtonPushed()throws IOException
    {

        clientSocket.getContSelect().waitForNicknameRequest(clientSocket.getPlayerView());


        /*Parent newPlayer=FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML/NewPlayer.fxml"));
        Scene newPlayerScene = new Scene(newPlayer);

        Stage window = (Stage)newButton.getScene().getWindow();

        window.setScene(newPlayerScene);
        window.show();*/
    }

    @FXML
    private void exitButtonPushed(){
        System.exit(0);
    }

}
