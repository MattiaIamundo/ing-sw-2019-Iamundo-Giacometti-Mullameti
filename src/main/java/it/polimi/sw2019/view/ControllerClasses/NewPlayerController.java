package it.polimi.sw2019.view.ControllerClasses;




import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.GUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;



import java.io.IOException;


public class NewPlayerController {


    private GUI gui = new GUI();
    Player tempPlayer = new Player("",0,null,null);

    private String validnickname ="true";


    @FXML private TextField nicknameField;

    @FXML private Button submitButton;

    @FXML private Button nextButton;
    @FXML private Label validInput;

    private String nickname;


    @FXML
    public void initialize(){
        this.submitButton.setDefaultButton(true);
        this.submitButton.setVisible(true);
        this.nextButton.setVisible(false);
        this.validInput.setText("");

    }

    public void isValid(String valid){
        this.validnickname=valid;

    }

    @FXML
    public String submitButtonClicked() {

        if (nicknameField.getText() == null ||nicknameField.getText().length() == 0 ||(validnickname.equals("false"))) {

            this.validInput.setText( "Invalid nickname!");

        }else {
                this.validInput.setText( "");
                nickname=nicknameField.getText();
                this.nextButton.setVisible(true);

            }

        return nickname ;

    }


    @FXML
    public void nextButtonClicked(){

        try{
            Parent chooseMapSkull= FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML/PlayerCharacter.fxml"));
            Scene chooseMapSkullScene = new Scene(chooseMapSkull);

            Stage window = (Stage)nextButton.getScene().getWindow();

            window.setScene(chooseMapSkullScene);
            window.setResizable(false);
            window.show();

        }catch(IOException e){

        }

    }




}
