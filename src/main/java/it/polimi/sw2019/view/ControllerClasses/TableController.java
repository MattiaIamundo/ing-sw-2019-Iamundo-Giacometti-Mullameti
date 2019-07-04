package it.polimi.sw2019.view.ControllerClasses;


import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.client_event.Cevent.StartGameEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.network.Socket.ClientSocket;
import it.polimi.sw2019.view.Observer;
import javafx.application.Platform;
import it.polimi.sw2019.network.Socket.ClientSocket;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableController implements Observer<NotifyReturn> {



    private ClientSocket clientSocket;
    private List<Player> players = new ArrayList<>(5);
    private Table tablegame;
    private ExecutorEventImp executorEventImp = new ExecutorEventImp();
    private static final Logger logger = Logger.getLogger( TableController.class.getName() );

    @FXML private ImageView yellowStatue;
    @FXML private ImageView blueStatue;
    @FXML private ImageView grayStatue;
    @FXML private ImageView greenStatue;
    @FXML private ImageView purpleStatue;
    @FXML private AnchorPane Rooms;
    @FXML private AnchorPane table ;
    @FXML private Button XButtonNorth;
    @FXML private Button XButtonWest;
    @FXML private Button XButtonEast;
    private double XImage;
    private double YImage;

    //************************************PLAYERBOARDS*******************************************
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

    //****************************************WEAPON********************************************

    @FXML private ImageView WeaponNorth1;
    @FXML private ImageView WeaponNorth2;
    @FXML private ImageView WeaponNorth3;
    @FXML private ImageView WeaponWest1;
    @FXML private ImageView WeaponWest2;
    @FXML private ImageView WeaponWest3;
    @FXML private ImageView WeaponEast1;
    @FXML private ImageView WeaponEast2;
    @FXML private ImageView WeaponEast3;
    @FXML private AnchorPane weaponBoardNorth;
    @FXML private AnchorPane weaponBoardWest;
    @FXML private AnchorPane weaponBoardEast;
    @FXML private Button descriptionWeapon_1;
    @FXML private Button descriptionWeapon_2;
    @FXML private Button descriptionWeapon_3;
    @FXML private TextArea descriptionWeapon;

    //****************************************POWERUP********************************************

    @FXML private ImageView powerUpDeck;
    @FXML private AnchorPane powerUpBoard;
    @FXML private Button descriptionPowerUp_1;
    @FXML private Button descriptionPowerUp_2;
    @FXML private Button descriptionPowerUp_3;
    @FXML private Button descriptionPowerUp;
    @FXML private Button yourTurn;



    @FXML
    private ImageView ammoCard;

    private double Xcard;
    private double Ycard;


    @FXML
    public void initialize() {
        this.thisPlayerBoard.setVisible(false);
        this.firstPlayerBoard.setVisible(false);
        this.secondPlayerBoard.setVisible(false);
        this.thirdPlayerBoard.setVisible(false);
        this.fourthPlayerBoard.setVisible(false);
        this.weaponBoardNorth.setVisible(false);
        this.weaponBoardEast.setVisible(false);
        this.weaponBoardWest.setVisible(false);
        this.XImage=this.yellowStatue.getX();

        this.YImage=this.yellowStatue.getY();

        System.out.println(this.yellowStatue.getX());



    }

    //************************************PLAYERBOARDS*******************************************

    @FXML
    public void thisPlayerButtonPushed() {
        if (thisPlayerBoard.isVisible()) {
            this.thisPlayerBoard.setVisible(false);

        } else {
            this.thisPlayerBoard.setVisible(true);
            System.out.println(GridPane.getColumnIndex(yellowStatue));
        }


    }

    @FXML
    public void firstPlayerButtonPushed() {

        if (firstPlayerBoard.isVisible()) {
            this.firstPlayerBoard.setVisible(false);

        } else {
            this.firstPlayerBoard.setVisible(true);
        }
    }

    @FXML
    public void secondPlayerButtonPushed() {
        if (secondPlayerBoard.isVisible()) {
            this.secondPlayerBoard.setVisible(false);

        } else {
            this.secondPlayerBoard.setVisible(true);
        }
    }

    @FXML
    public void thirdPlayerButtonPushed() {
        if (thirdPlayerBoard.isVisible()) {
            this.thirdPlayerBoard.setVisible(false);

        } else {
            this.thirdPlayerBoard.setVisible(true);
        }
    }

    @FXML
    public void fourthPlayerButtonPushed() {
        if (fourthPlayerBoard.isVisible()) {
            this.fourthPlayerBoard.setVisible(false);

        } else {
            this.fourthPlayerBoard.setVisible(true);
        }
    }

    //************************************WEAPONS*******************************************
    @FXML
    public void seeWeaponsNorth(){
        if (!(weaponBoardNorth.isVisible())){
            this.weaponBoardNorth.setVisible(true);

        }

    }

    public void seeWeaponsEast(){
        if (!(weaponBoardEast.isVisible())){
            this.weaponBoardEast.setVisible(true);

        }

    }

    public void seeWeaponsWest(){
        if (!(weaponBoardWest.isVisible())){
            this.weaponBoardWest.setVisible(true);

        }
    }

    public void XButtonNorthClicked(){
        if(weaponBoardNorth.isVisible()){
            this.weaponBoardNorth.setVisible(false);
        }

    }
    public void XButtonWestClicked(){
        if(weaponBoardWest.isVisible()){
            this.weaponBoardWest.setVisible(false);
        }

    }
    public void XButtonEastClicked(){
        if(weaponBoardEast.isVisible()){
            this.weaponBoardEast.setVisible(false);
        }

    }



    @FXML
    public void descriptionButtonPushed() {
        this.descriptionWeapon.setText("meri");
        if (descriptionWeapon.isVisible()) {
            this.descriptionWeapon.setVisible(false);

        } else {
            this.descriptionWeapon.setVisible(true);
        }
    }

    @FXML
    public void powerUpButtonPushed(ActionEvent event) throws IOException {
        if (powerUpBoard.isVisible()) {
            this.powerUpBoard.setVisible(false);

        } else {
            this.powerUpBoard.setVisible(true);
        }

    }




    @FXML
    public void choosePowerUp(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("PowerUp.fxml"));
            AnchorPane PowerUp = (AnchorPane) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(PowerUp));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void yourTurnButtonPushed() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Action&Reload.fxml"));
            AnchorPane playerTurn = (AnchorPane) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(playerTurn));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleAmmo(String direction) {

        if (direction.equals("North")) {
            double newX = XImage + 0;
            double newY = YImage + 100;
            yellowStatue.setX(newX);
            yellowStatue.setY(newY);
        } else if (direction.equals("South")) {
            double newX = XImage + 0;
            double newY = YImage - 100;
            yellowStatue.setX(newX);
            yellowStatue.setY(newY);

        } else if (direction.equals("East") ) {
            double newX = XImage + 100;
            double newY = YImage + 0;
            yellowStatue.setX(newX);
            yellowStatue.setY(newY);

        } else if (direction.equals("West")) {
            double newX = XImage - 100;
            double newY = YImage + 0;
            yellowStatue.setX(newX);
            yellowStatue.setY(newY);
        }
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void update(NotifyReturn notifyReturn) {
        notifyReturn.updateObject( this.executorEventImp, null );
    }

    public void handleEvent(StartGameEv startGameEv) {
        this.players = startGameEv.getPlayers();
        this.tablegame = startGameEv.getGameboard();
        //Platform.runLater( () -> this.clientSocket.notifyGUI("Refresh") );
    }
}
