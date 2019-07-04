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

/**Class ChooseMapController: the controller behind ChooseMap.fxml file
 * @author Merita Mullameti
 */

public class ChooseMapController {


    private ClientSocket clientSocket ;
    private String map ;

   @FXML private RadioButton firstMapButton;
   @FXML private RadioButton secondMapButton;
   @FXML private RadioButton thirdMapButton;
   @FXML private RadioButton fourthMapButton;

   @FXML private ToggleGroup mapGroup;

   @FXML private Button startButton;

    /**
     * This method initializes the window of the ChooseMap.fxml
     */
    @FXML
    public void initialize(){
       this.startButton.setDefaultButton(true);
       this.startButton.setVisible(false);
       this.firstMapButton.setStyle("-fx-background-image: url(/it/polimi/sw2019/Images/0map.png); ");
       this.secondMapButton.setStyle("-fx-background-image: url(/it/polimi/sw2019/Images/1map.png);");
       this.thirdMapButton.setStyle("-fx-background-image: url(/it/polimi/sw2019/Images/2map.png);");
       this.fourthMapButton.setStyle("-fx-background-image: url(/it/polimi/sw2019/Images/3map.png);");
    }

    /**
     * This method handles the event on the ToggleGroup of Map-RadioButton
     */
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
            this.startButton.setVisible(true);
        }else if(!(clientSocket.getOk())){
            this.startButton.setVisible(false);
        }


    }

    /**
     * This method handles the event on the startButton and sends a " message " in clientSocket
     */
    @FXML
    public void startButtonPushed()  {
        clientSocket.getContSelect().waitForPing(clientSocket.getPlayerView());
    }
    /**
     * This method sets the clientSocket on this class
     * @param clientSocket
     */
    public void setClientSocket(ClientSocket clientSocket){ this.clientSocket=clientSocket; }

}