package it.polimi.sw2019.view.ControllerClasses;

import it.polimi.sw2019.network.Socket.ClientSocket;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
/**Class LobbyController: the controller behind Lobby.fxml file
 * @author Merita Mullameti
 */
public class LobbyController {


    private ClientSocket clientSocket;

    @FXML private Button startButton;
    @FXML private ProgressBar pingPong;


    /**
     * This method initializes the window of the Lobby.fxml
     */

    @FXML
    public void initialize(){
        this.startButton.setDefaultButton(true);
        this.startButton.setVisible(true);

    }

    /**
     * This method handles the event on the startButton
     */
    @FXML
    public void startButtonClicked(){ }

    /**
     * This method sets the clientSocket on this class
     * @param clientSocket
     */
    public void setClientSocket(ClientSocket clientSocket){
        this.clientSocket=clientSocket;
    }

}
