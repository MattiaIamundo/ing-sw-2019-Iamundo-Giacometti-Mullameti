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
/**Class ChooseSkullController: the controller behind ChooseSkull.fxml file
 * @author Merita Mullameti
 */
public class ChooseSkullController {

        private ClientSocket clientSocket ;
        private String skulls ;

        @FXML private RadioButton fiveSkullsButton;
        @FXML private RadioButton sixSkullsButton;
        @FXML private RadioButton sevenSkullsButton;
        @FXML private RadioButton eightSkullsButton;

        @FXML private ToggleGroup skullsGroup;
        @FXML private Button nextButton;


        /**
        * This method initializes the window of the ChooseSkull.fxml
        */
        @FXML
        public void initialize(){
            this.nextButton.setDefaultButton(true);
            this.nextButton.setVisible(false);
        }

        /**
        * This method handles the event on the ToggleGroup of Skulls-RadioButton
        */
        @FXML
        public void chooseNrSkulls(){

            if(this.skullsGroup.getSelectedToggle().equals(this.fiveSkullsButton)) {skulls="five"; }
            else if(this.skullsGroup.getSelectedToggle().equals(this.sixSkullsButton)) {skulls="six";}
            else if(this.skullsGroup.getSelectedToggle().equals(this.sevenSkullsButton)) {skulls="seven";}
            else if(this.skullsGroup.getSelectedToggle().equals(this.eightSkullsButton)) {skulls="eight";}


            clientSocket.setInfo("skull",skulls);

            if(clientSocket.getOk()){
                System.out.println("entra");
                this.nextButton.setVisible(true);
            }else if(!(clientSocket.getOk())){
                this.nextButton.setVisible(false);
            }

        }

        /**
        * This method handles the event on the nextButton and sends a " message " in clientSocket
        */
        @FXML
        public void nextButtonPushed(){
            clientSocket.getContSelect().waitingForMap(clientSocket.getPlayerView());
        }

        /**
         * This method sets the clientSocket on this class
         * @param clientSocket
         */
        public void setClientSocket(ClientSocket clientSocket){ this.clientSocket=clientSocket; }

}

