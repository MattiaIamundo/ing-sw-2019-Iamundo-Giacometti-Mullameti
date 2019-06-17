package it.polimi.sw2019.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;



import java.io.IOException;

public class MenuController {


    @FXML private Button newButton;
    @FXML private Button exitButton;

    @FXML
    public void initialize(){
        this.newButton.setDefaultButton(true);
        this.exitButton.setCancelButton(true);
    }


    public void newPlayerButtonPushed(ActionEvent event)throws IOException
    {


        Parent newPlayer=FXMLLoader.load(getClass().getResource("NewPlayer.fxml"));
        Scene newPlayerScene = new Scene(newPlayer);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newPlayerScene);
        window.show();
    }

    @FXML
    private void exitButtonPushed(){
        System.exit(0);
    }



}
