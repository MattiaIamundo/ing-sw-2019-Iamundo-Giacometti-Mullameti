package it.polimi.sw2019.view.ControllerClasses;


import it.polimi.sw2019.network.Socket.ClientSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

import java.io.IOException;

public class TableController {


    private ClientSocket clientSocket;
    @FXML private Button thisPlayer;
    @FXML private Button firstPlayer;
    @FXML private Button secondPlayer;
    @FXML private Button thirdPlayer;
    @FXML private Button fourthPlayer;

    @FXML private AnchorPane thisPlayerBoard;
    @FXML private AnchorPane firstPlayerBoard;
    @FXML private AnchorPane secondPlayerBoard;
    @FXML private AnchorPane thirdPlayerBoard;
    @FXML private AnchorPane fourthPlayerBoard;

    @FXML private AnchorPane pointsBoard;
    @FXML private AnchorPane ammoBoard;
    @FXML private AnchorPane powerUpBoard;
    @FXML private AnchorPane weaponBoard;


    @FXML private Button descriptionPowerUp_1;
    @FXML private Button descriptionPowerUp_2;
    @FXML private Button descriptionPowerUp_3;
    @FXML private Button descriptionPowerUp;

    @FXML private Button descriptionWeapon_1;
    @FXML private Button descriptionWeapon_2;
    @FXML private Button descriptionWeapon_3;
    @FXML private TextArea descriptionWeapon;

    @FXML
    public void initialize(){
        this.thisPlayerBoard.setVisible(false);
        this.firstPlayerBoard.setVisible(false);
        this.secondPlayerBoard.setVisible(false);
        this.thirdPlayerBoard.setVisible(false);
        this.fourthPlayerBoard.setVisible(false);

        this.pointsBoard.setVisible(false);
        this.ammoBoard.setVisible(false);

        this.weaponBoard.setVisible(false);


    }

    @FXML
    public void thisPlayerButtonPushed() {
        if(thisPlayerBoard.isVisible()){
            this.thisPlayerBoard.setVisible(false);

        }else{
            this.thisPlayerBoard.setVisible(true);
        }
    
    }

    @FXML
    public void firstPlayerButtonPushed() {

        if(firstPlayerBoard.isVisible()){
            this.firstPlayerBoard.setVisible(false);

        }else{
            this.firstPlayerBoard.setVisible(true);
        }
    }

    @FXML
    public void secondPlayerButtonPushed() {
        if(secondPlayerBoard.isVisible()){
            this.secondPlayerBoard.setVisible(false);

        }else{
            this.secondPlayerBoard.setVisible(true);
        }
    }    

    @FXML
    public void thirdPlayerButtonPushed() {
        if(thirdPlayerBoard.isVisible()){
            this.thirdPlayerBoard.setVisible(false);

        }else{
            this.thirdPlayerBoard.setVisible(true);
        }
    }

    @FXML
    public void fourthPlayerButtonPushed (){
        if(fourthPlayerBoard.isVisible()){
            this.fourthPlayerBoard.setVisible(false);

        }else{
            this.fourthPlayerBoard.setVisible(true);
        }
    }
    

    @FXML
    public void weaponButtonPushed() {
        if(weaponBoard.isVisible()){
            this.weaponBoard.setVisible(false);

        }else{
            this.weaponBoard.setVisible(true);
        }
    }

    @FXML
    public void descriptionButtonPushed(){
        this.descriptionWeapon.setText("meri");
        if(descriptionWeapon.isVisible()){
            this.descriptionWeapon.setVisible(false);

        }else{
            this.descriptionWeapon.setVisible(true);
        }
    }

    @FXML
    public void powerUpButtonPushed(ActionEvent event) throws IOException {
        if(powerUpBoard.isVisible()){
            this.powerUpBoard.setVisible(false);

        }else{
            this.powerUpBoard.setVisible(true);
        }

    }

    @FXML
    public void ammoButtonPushed() {
        if(ammoBoard.isVisible()){
            this.ammoBoard.setVisible(false);

        }else{
            this.ammoBoard.setVisible(true);
        }

    }

    @FXML
    public void pointsButtonPushed() {
        if(pointsBoard.isVisible()){
            this.pointsBoard.setVisible(false);

        }else{
            this.pointsBoard.setVisible(true);
        }
    }

    @FXML
    public void yourTurnButtonPushed(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/it/polimi/sw2019/FXML_File/Action&Reload.fxml"));
            AnchorPane playerTurn = (AnchorPane) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(playerTurn));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void setClientSocket(ClientSocket clientSocket){
        this.clientSocket=clientSocket;
    }
}
