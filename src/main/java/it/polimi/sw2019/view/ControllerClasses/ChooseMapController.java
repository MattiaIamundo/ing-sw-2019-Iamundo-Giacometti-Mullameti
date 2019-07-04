package it.polimi.sw2019.view.ControllerClasses;


import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.network.Socket.ClientSocket;
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
    private ClientSocket clientSocket ;

    private Table table =new Table();
    private String map =new String();

   @FXML private RadioButton firstMapButton;
   @FXML private RadioButton secondMapButton;
   @FXML private RadioButton thirdMapButton;
   @FXML private RadioButton fourthMapButton;
   @FXML private ToggleGroup mapGroup;

   @FXML private Button startButton;
    public void setClientSocket(ClientSocket clientSocket){
        this.clientSocket=clientSocket;
    }


    @FXML
    public void initialize(){
       this.startButton.setDefaultButton(true);
       this.startButton.setVisible(false);
       this.firstMapButton.setStyle("-fx-background-image: url(/it/polimi/sw2019/Images/0map.png); ");
       this.secondMapButton.setStyle("-fx-background-image: url(/it/polimi/sw2019/Images/1map.png);");
       this.thirdMapButton.setStyle("-fx-background-image: url(/it/polimi/sw2019/Images/2map.png);");
       this.fourthMapButton.setStyle("-fx-background-image: url(/it/polimi/sw2019/Images/3map.png);");
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
        clientSocket.setInfo("map",map);
        if(clientSocket.getOk()){
            System.out.println("entra");
            this.startButton.setVisible(true);
        }else if(!(clientSocket.getOk())){
            this.startButton.setVisible(false);
        }


    }




    @FXML
    public void startButtonPushed()  {
        System.out.println("6map");
        System.out.println("1");
        clientSocket.getContSelect().waitForPing(clientSocket.getPlayerView());


        /*Parent table = FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML_File/Table.fxml"));
        Scene tableScene = new Scene(table);

        Stage window = (Stage) startButton.getScene().getWindow();

        window.setScene(tableScene);
        window.show();*/
    }


}