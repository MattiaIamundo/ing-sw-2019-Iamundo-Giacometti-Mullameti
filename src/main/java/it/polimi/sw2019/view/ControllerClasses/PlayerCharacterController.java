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
import java.util.List;

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
    @FXML private Button getCharacterButton;
    private String stringCharacter;



    @FXML
    public void initialize(){

        this.nextButton.setDefaultButton(true);
        this.nextButton.setVisible(false);
        this.yellowButton.setDisable(true);
        this.blueButton.setDisable(true);
        this.grayButton.setDisable(true);
        this.greenButton.setDisable(true);
        this.purpleButton.setDisable(true);


    }


    public void enableButtons(List<String> colorList){

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


    @FXML
    public void getCharactersButtonClicked(){
        enableButtons(clientSocket.getColorList());
    }
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

    @FXML
    public void nextButtonPushed() {

        if(!(clientSocket.getYouAreFirstPlayer())) {

            clientSocket.getContSelect().waitingForSkull(clientSocket.getPlayerView());
        }else {

            clientSocket.getContSelect().waitForPing(clientSocket.getPlayerView());
        }


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
