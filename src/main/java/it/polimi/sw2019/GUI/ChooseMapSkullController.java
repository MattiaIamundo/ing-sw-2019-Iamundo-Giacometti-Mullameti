package it.polimi.sw2019.GUI;

import it.polimi.sw2019.model.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;



import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;



import java.io.IOException;

public class ChooseMapSkullController {

    private Table table =new Table();
    private String map =new String();
    private String skulls =new String();

   @FXML private RadioButton fiveSkullsButton;
   @FXML private RadioButton sixSkullsButton;
   @FXML private RadioButton sevenSkullsButton;
   @FXML private RadioButton eightSkullsButton;
   @FXML private RadioButton firstMapButton;
   @FXML private RadioButton secondMapButton;
   @FXML private RadioButton thirdMapButton;
   @FXML private RadioButton fourthMapButton;
   @FXML private ToggleGroup mapGroup;
   @FXML private ToggleGroup skullsGroup;
   @FXML private Button startButton;


    @FXML
    public void initialize(){
       this.startButton.setDefaultButton(true);
       this.startButton.setVisible(false);
    }


    @FXML
    public void chooseNrSkulls(){

        if(this.skullsGroup.getSelectedToggle().equals(this.fiveSkullsButton)) {skulls="five"; }
        else if(this.skullsGroup.getSelectedToggle().equals(this.sixSkullsButton)) {skulls="six";}
        else if(this.skullsGroup.getSelectedToggle().equals(this.sevenSkullsButton)) {skulls="seven";}
        else if(this.skullsGroup.getSelectedToggle().equals(this.eightSkullsButton)) {skulls="eight";}

        visibleStartButton();

    }

    @FXML
    public void chooseMap(){

        if(this.mapGroup.getSelectedToggle().equals(this.firstMapButton)) {
            map="zero";
        }else if(this.mapGroup.getSelectedToggle().equals(this.secondMapButton)) {
            map="one";
        }else if(this.mapGroup.getSelectedToggle().equals(this.thirdMapButton)) {
            map="two";
        } else if(this.mapGroup.getSelectedToggle().equals(this.fourthMapButton)) {
            map="three";
        }


        visibleStartButton();

    }




    public void visibleStartButton(){
        if((this.mapGroup.getSelectedToggle().isSelected())&&(this.skullsGroup.getSelectedToggle().isSelected())){
            this.startButton.setVisible(true);
        }
    }

    @FXML
    public void startButtonPushed(ActionEvent event) throws IOException {

        table.setNrSkulls(skulls);
        table.setNrMap(map);
        System.out.println(table.getNrMap());
        System.out.println(table.getNrSkulls());
        Parent table = FXMLLoader.load(getClass().getResource("Table.fxml"));
        Scene tableScene = new Scene(table);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableScene);
        window.show();
    }


}