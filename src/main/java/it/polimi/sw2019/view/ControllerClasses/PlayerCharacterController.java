package it.polimi.sw2019.view.ControllerClasses;

import it.polimi.sw2019.network.Socket.ClientSocket;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

/**Class PlayerCharacterController: the controller behind PlayerCharacter.fxml file
 * @author Merita Mullameti
 */
public class PlayerCharacterController {

    private ClientSocket clientSocket ;
    private String stringCharacter;

    @FXML private RadioButton yellowButton;
    @FXML private RadioButton blueButton;
    @FXML private RadioButton grayButton;
    @FXML private RadioButton greenButton;
    @FXML private RadioButton purpleButton;
    @FXML private ToggleGroup characterGroup;
    @FXML private Button nextButton;
    @FXML private Button getCharacterButton;


    /**
     * This method initializes the window of the PlayerCharacter.fxml
     */
    @FXML
    public void initialize(){

        this.yellowButton.setStyle("-fx-background-image: url(/it/polimi/sw2019/Images/yellow.png); ");
        this.blueButton.setStyle("-fx-background-image: url(/it/polimi/sw2019/Images/blue.png);");
        this.grayButton.setStyle("-fx-background-image: url(/it/polimi/sw2019/Images/gray.png);");
        this.greenButton.setStyle("-fx-background-image: url(/it/polimi/sw2019/Images/green.png);");
        this.purpleButton.setStyle("-fx-background-image: url(/it/polimi/sw2019/Images/purple.png);");

    }

    /**
     * This method enables the still-available characters buttons
     * @param colorList list of available characters
     */
    private void enableButtons(List<String> colorList){

        this.getCharacterButton.setVisible(false);
        for (String color : colorList) {

            switch (color) {
                case "yellow":

                    this.yellowButton.setDisable(false);
                    break;
                case "blue":
                    this.blueButton.setDisable(false);
                    break;
                case "gray":
                    this.grayButton.setDisable(false);
                    break;
                case "green":
                    this.greenButton.setDisable(false);
                    break;
                case "purple":
                    this.purpleButton.setDisable(false);
                    break;
            }

        }


    }

    /**
     * This method handles the event on the getCharactersButton
     * calls the enableButtons method to enable the right buttons
     */
    @FXML
    public void getCharactersButtonClicked(){
        enableButtons(clientSocket.getColorList());
    }
    /**
     * This method handles the event on the ToggleGroup of Character-RadioButton
     */
    @FXML
    public void chooseCharacter(){
        if(this.characterGroup.getSelectedToggle().equals(this.yellowButton)) {stringCharacter="yellow";}
        if(this.characterGroup.getSelectedToggle().equals(this.blueButton)) {stringCharacter="blue";}
        if(this.characterGroup.getSelectedToggle().equals(this.grayButton)) {stringCharacter="gray";}
        if(this.characterGroup.getSelectedToggle().equals(this.greenButton)) {stringCharacter="green";}
        if(this.characterGroup.getSelectedToggle().equals(this.purpleButton)) {stringCharacter="purple";}
        this.clientSocket.setInfo("character",stringCharacter);

        if(clientSocket.getOk()){

            System.out.println("entra");
            this.nextButton.setVisible(true);
        }else if(!(clientSocket.getOk())){
            this.nextButton.setVisible(false);
        }


    }

    /**
     * This method handles the event on the nextButton and sends a " message " in clientSocket
     * If this is the first player the button asks to choose the nr of skulls ; if not waits to start the game
     */
    @FXML
    public void nextButtonPushed() {

        if(!(clientSocket.getYouAreFirstPlayer())) {

            clientSocket.getContSelect().waitingForSkull(clientSocket.getPlayerView());
        }else {

            clientSocket.getContSelect().waitForPing(clientSocket.getPlayerView());
        }

    }
    /**
     * This method sets the clientSocket on this class
     * @param clientSocket
     */
    public void setClientSocket(ClientSocket clientSocket){ this.clientSocket=clientSocket; }
}
