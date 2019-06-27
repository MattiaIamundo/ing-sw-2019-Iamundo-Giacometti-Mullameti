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

public class ChooseSkullController {

        private ClientSocket clientSocket ;
        private Table table =new Table();

        private String skulls =new String();

        @FXML
        private RadioButton fiveSkullsButton;
        @FXML private RadioButton sixSkullsButton;
        @FXML private RadioButton sevenSkullsButton;
        @FXML private RadioButton eightSkullsButton;

        @FXML private ToggleGroup skullsGroup;
        @FXML private Button nextButton;


        @FXML
        public void initialize(){
            this.nextButton.setDefaultButton(true);
            this.nextButton.setVisible(false);
        }


        @FXML
        public String chooseNrSkulls(){

            if(this.skullsGroup.getSelectedToggle().equals(this.fiveSkullsButton)) {skulls="five"; }
            else if(this.skullsGroup.getSelectedToggle().equals(this.sixSkullsButton)) {skulls="six";}
            else if(this.skullsGroup.getSelectedToggle().equals(this.sevenSkullsButton)) {skulls="seven";}
            else if(this.skullsGroup.getSelectedToggle().equals(this.eightSkullsButton)) {skulls="eight";}

            this.nextButton.setVisible(true);

            clientSocket.setInfo("skull",skulls);
            return skulls;
        }

        public void setClientSocket(ClientSocket clientSocket){
            this.clientSocket=clientSocket;
        }

        @FXML
        public void nextButtonPushed() throws IOException {

            clientSocket.getContSelect().waitingForMap(clientSocket.getPlayerView());
            /*Parent table = FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML/ChooseMap.fxml"));
            Scene tableScene = new Scene(table);

            Stage window = (Stage) nextButton.getScene().getWindow();

            window.setScene(tableScene);
            window.show();*/
        }


}

