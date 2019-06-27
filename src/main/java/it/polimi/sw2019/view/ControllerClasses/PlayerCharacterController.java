package it.polimi.sw2019.view.ControllerClasses;



import it.polimi.sw2019.model.Player;

import it.polimi.sw2019.network.Socket.ClientSocket;
import it.polimi.sw2019.view.GUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;




import java.io.IOException;

public class PlayerCharacterController {
    private ClientSocket clientSocket ;
    Player tempPlayer = new Player("",0,null,null);


    public void setClientSocket(ClientSocket clientSocket){
        this.clientSocket=clientSocket;
    }






    @FXML private RadioButton yellowButton;
    @FXML private RadioButton blueButton;
    @FXML private RadioButton grayButton;
    @FXML private RadioButton greenButton;
    @FXML private RadioButton purpleButton;
    @FXML private ToggleGroup characterGroup;
    @FXML private Button nextButton;

    private String stringCharacter;



    @FXML
    public void initialize(){
        this.nextButton.setDefaultButton(true);
        this.nextButton.setVisible(false);

    }




    @FXML
    public void chooseCharacter(){

        if(this.characterGroup.getSelectedToggle().equals(this.yellowButton)) {stringCharacter="Yellow";}
        if(this.characterGroup.getSelectedToggle().equals(this.blueButton)) {stringCharacter="Blue";}
        if(this.characterGroup.getSelectedToggle().equals(this.grayButton)) {stringCharacter="Gray";}
        if(this.characterGroup.getSelectedToggle().equals(this.greenButton)) {stringCharacter="Green";}
        if(this.characterGroup.getSelectedToggle().equals(this.purpleButton)) {stringCharacter="Purple";}

        this.nextButton.setVisible(true);
        this.clientSocket.setInfo("character",stringCharacter);

    }




    @FXML
    public void nextButtonPushed() {

        clientSocket.getContSelect().waitingForSkull(clientSocket.getPlayerView());

        /*try{
            Parent chooseMapSkull= FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML/ChooseSkull.fxml"));
            Scene chooseMapSkullScene = new Scene(chooseMapSkull);

            Stage window = (Stage)nextButton.getScene().getWindow();

            window.setScene(chooseMapSkullScene);
            window.show();
        }catch(IOException e){

        }*/


    }
}
