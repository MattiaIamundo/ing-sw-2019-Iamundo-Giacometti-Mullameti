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

/**Class MenuController: the controller behind Menu.fxml file
 * @author Merita Mullameti
 */
public class MenuController {

    private ClientSocket clientSocket;

    @FXML private Button newButton;
    @FXML private Button exitButton;

    /**
     * This method initializes the window of the Menu.fxml
     */
    @FXML
    public void initialize(){
        this.newButton.setDefaultButton(true);
        this.exitButton.setCancelButton(true);

    }

    /**
     * This method handles the event on the newPlayerButton and sends a " message " in clientSocket
     */
    @FXML
    public void newPlayerButtonPushed() {
        clientSocket.getContSelect().waitForNicknameRequest(clientSocket.getPlayerView());
    }

    /**
     * This method handles the event on the exitButton (closes the window)
     */
    @FXML
    private void exitButtonPushed(){
        System.exit(0);
    }

    /**
     * This method sets the clientSocket on this class
     * @param clientSocket
     */
    public void setClientSocket(ClientSocket clientSocket){ this.clientSocket=clientSocket; }
}
