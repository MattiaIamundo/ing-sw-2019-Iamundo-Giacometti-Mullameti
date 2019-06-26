package it.polimi.sw2019.view.ControllerClasses;


import it.polimi.sw2019.model.Table;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;



import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;



import java.io.IOException;

public class ChooseMapController {

    private Table table =new Table();
    private String map =new String();

   @FXML private RadioButton firstMapButton;
   @FXML private RadioButton secondMapButton;
   @FXML private RadioButton thirdMapButton;
   @FXML private RadioButton fourthMapButton;
   @FXML private ToggleGroup mapGroup;

   @FXML private Button startButton;


    @FXML
    public void initialize(){
       this.startButton.setDefaultButton(true);
       this.startButton.setVisible(false);
    }



    @FXML
    public String chooseMap(){

        if(this.mapGroup.getSelectedToggle().equals(this.firstMapButton)) {
            map="zero";
        }else if(this.mapGroup.getSelectedToggle().equals(this.secondMapButton)) {
            map="one";
        }else if(this.mapGroup.getSelectedToggle().equals(this.thirdMapButton)) {
            map="two";
        } else if(this.mapGroup.getSelectedToggle().equals(this.fourthMapButton)) {
            map="three";
        }


        this.startButton.setVisible(true);
        return map;

    }




    @FXML
    public void startButtonPushed() throws IOException {

        Parent table = FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML/Table.fxml"));
        Scene tableScene = new Scene(table);

        Stage window = (Stage) startButton.getScene().getWindow();

        window.setScene(tableScene);
        window.show();
    }


}