package it.polimi.sw2019.view.ControllerClasses;

import it.polimi.sw2019.network.Socket.ClientSocket;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**Class NewPlayerController: the controller behind NewPlayer.fxml file
 * @author Merita Mullameti
 */
public class NewPlayerController {


    private ClientSocket clientSocket ;
    private String nickname;

    @FXML private Label enterNickname;
    @FXML private TextField nicknameField;
    @FXML private Button submitButton;
    @FXML private Button okButton;
    @FXML private Button nextButton;
    @FXML private Label validInput;

    /**
     * This method initializes the window of the NewPlayer.fxml
     */
    @FXML
    public void initialize(){

    }

    /**
     * This method handles the event on the submitButton and sends the typed nickname  in clientSocket
     */
    @FXML
    public void submitButtonClicked() {


        nickname=nicknameField.getText();
        this.clientSocket.setInfo("nickname" , nickname);


        if(clientSocket.getOk()){
            this.submitButton.setVisible(false);
            this.nextButton.setVisible(true);
        }else if(!(clientSocket.getOk())){

            this.enterNickname.setVisible(false);
            this.nicknameField.setVisible(false);
            this.submitButton.setVisible(false);
            this.nextButton.setVisible(false);
            this.validInput.setVisible(true);
            this.okButton.setVisible(true);
        }

    }

    /**
     * This method handles the event on the OkButton and sends a " message " in clientSocket
     */
    @FXML
    public void handleOkButton(){
        clientSocket.getContSelect().waitForNicknameRequest(clientSocket.getPlayerView());
    }

    /**
     * This method handles the event on the nextButton and sends a " message " in clientSocket
     */
    @FXML
    public void nextButtonClicked(){
        clientSocket.getContSelect().waitingForColorRequest(clientSocket.getPlayerView());
    }

    /**
     * This method sets the clientSocket on this class
     * @param clientSocket
     */
    public void setClientSocket(ClientSocket clientSocket){ this.clientSocket=clientSocket; }




}
