package it.polimi.sw2019.GUI;

import it.polimi.sw2019.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;



import java.io.IOException;


public class NewPlayerController {


    private Main main = new Main();
    Player tempPlayer = new Player("",0,null,null);



    @FXML private TextField nicknameField;

    @FXML private Button submitButton;
    @FXML private RadioButton yellowButton;
    @FXML private RadioButton blueButton;
    @FXML private RadioButton grayButton;
    @FXML private RadioButton greenButton;
    @FXML private RadioButton purpleButton;
    @FXML private ToggleGroup characterGroup;
    @FXML private Button nextButton;
    @FXML private Label validInput;


    @FXML
    public void initialize(){
        this.nextButton.setDefaultButton(true);
        this.nextButton.setVisible(false);
        this.validInput.setText("");
        this.yellowButton.setDisable(true);
        this.blueButton.setDisable(true);
        this.grayButton.setDisable(true);
        this.greenButton.setDisable(true);
        this.purpleButton.setDisable(true);
    }

    @FXML
    public void submitButtonClicked(){

        if (nicknameField.getText() == null || nicknameField.getText().length() == 0) {
            this.validInput.setText( "No valid nickname!Enter another one !");
        }else{
            tempPlayer.setNickname(nicknameField.getText());
            main.addPlayers(tempPlayer);
            this.submitButton.setDisable(true);
            this.yellowButton.setDisable(false);
            this.blueButton.setDisable(false);
            this.grayButton.setDisable(false);
            this.greenButton.setDisable(false);
            this.purpleButton.setDisable(false);

        }


    }



    @FXML
    public void chooseCharacter(){

        if(this.characterGroup.getSelectedToggle().equals(this.yellowButton)) {tempPlayer.setCharacter("Yellow");}
        if(this.characterGroup.getSelectedToggle().equals(this.blueButton)) {tempPlayer.setCharacter("Blue");}
        if(this.characterGroup.getSelectedToggle().equals(this.grayButton)) {tempPlayer.setCharacter("Gray");}
        if(this.characterGroup.getSelectedToggle().equals(this.greenButton)) {tempPlayer.setCharacter("Green");}
        if(this.characterGroup.getSelectedToggle().equals(this.purpleButton)) {tempPlayer.setCharacter("Purple");}

        visibleNewButton();
    }


    
    public void visibleNewButton(){
        if((this.characterGroup.getSelectedToggle().isSelected())){
            this.nextButton.setVisible(true);
        }
    }

    
    @FXML
    public void nextButtonPushed(ActionEvent event) throws IOException {


        Parent chooseMapSkull= FXMLLoader.load(getClass().getResource("ChooseMapSkull.fxml"));
        Scene chooseMapSkullScene = new Scene(chooseMapSkull);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(chooseMapSkullScene);
        window.show();

    }






}
