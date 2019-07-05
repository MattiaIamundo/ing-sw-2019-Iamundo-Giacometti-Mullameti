package it.polimi.sw2019.view.ControllerClasses;

import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventClient;
import it.polimi.sw2019.events.NotifyClient;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.client_event.Cevent.DirectionChooseEv;
import it.polimi.sw2019.events.client_event.Cevent.NotifyEndMoveEv;
import it.polimi.sw2019.events.client_event.Cevent.StartGameEv;
import it.polimi.sw2019.events.client_event.MVevent.NotifyMoveEv;
import it.polimi.sw2019.events.client_event.NotYourTurnEv;
import it.polimi.sw2019.events.client_event.StartTurnEv;
import it.polimi.sw2019.events.client_event.YourTurnEv;
import it.polimi.sw2019.events.server_event.VCevent.EndEv;
import it.polimi.sw2019.events.server_event.VCevent.MoveEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.network.Socket.ClientSocket;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Class TableController: the controller behind Table.fxml file
 * @author Merita Mullameti
 */

public class TableController extends Observable<ActionEv> implements Observer<NotifyClient> {



    private boolean yourTurn=false;
    private ClientSocket clientSocket;
    private ExecutorEventClient executorEventClient = new ExecutorEventClient();
    private String typeMap;
    private List<Player> Players = new ArrayList<Player>(5);
    private List<Weapon> Weapon = new ArrayList<Weapon>(9);
    private List<Ammo> Ammo = new ArrayList<Ammo>(9);


    @FXML private AnchorPane Rooms;
    @FXML private AnchorPane table ;
    @FXML private ImageView map;
    @FXML private Label info;

    //************************************PLAYERBOARDS*******************************************

    @FXML private Button firstPlayer;
    @FXML private Button secondPlayer;
    @FXML private Button thirdPlayer;
    @FXML private Button fourthPlayer;
    @FXML private Button fifthPlayer;

    @FXML private AnchorPane firstPlayerBoard;
    @FXML private AnchorPane secondPlayerBoard;
    @FXML private AnchorPane thirdPlayerBoard;
    @FXML private AnchorPane fourthPlayerBoard;
    @FXML private AnchorPane fifthPlayerBoard;

    @FXML private ImageView firstStatue;
    @FXML private ImageView secondStatue;
    @FXML private ImageView thirdStatue;
    @FXML private ImageView fourthStatue;
    @FXML private ImageView fifthStatue;

    @FXML private ImageView firstBoard;
    @FXML private ImageView secondBoard;
    @FXML private ImageView thirdBoard;
    @FXML private ImageView fourthBoard;
    @FXML private ImageView fifthBoard;
    //****************************************YOUR-TURN-BUTTONS*********************************

    @FXML private Button powerUpButton;
    @FXML private Button moveButton;
    @FXML private Button grabButton;
    @FXML private Button shootButton;
    @FXML private Button reloadButton;
    @FXML private Button endButton;

    //****************************************WEAPON********************************************

    @FXML private ImageView weaponNorth1;
    @FXML private ImageView weaponNorth2;
    @FXML private ImageView weaponNorth3;
    @FXML private ImageView weaponWest1;
    @FXML private ImageView weaponWest2;
    @FXML private ImageView weaponWest3;
    @FXML private ImageView weaponEast1;
    @FXML private ImageView weaponEast2;
    @FXML private ImageView weaponEast3;


    //****************************************POWERUP********************************************

    @FXML private ImageView powerUpDeck;

    //******************************************AMMO*********************************************
    @FXML private ImageView ammo00;
    @FXML private ImageView ammo02;
    @FXML private ImageView ammo10;
    @FXML private ImageView ammo11;
    @FXML private ImageView ammo12;
    @FXML private ImageView ammo20;
    @FXML private ImageView ammo21;
    @FXML private ImageView ammo31;
    @FXML private ImageView ammo32;


    /**
     * This method initializes the window of the PlayerCharacter.fxml
     */
    @FXML
    public void initialize() {
        info.setText("");
        if(yourTurn){
            moveButton.setDisable(false);
            info.setText("It`s your turn!");
        }else{
            moveButton.setDisable(true);
        }

    }

    //************************************PLAYERBOARDS*******************************************
    /**
     * This method handles the event on the fifthPlayerButton and shows his playerBoard
     */
    @FXML
    public void fifthPlayerButtonPushed() {
        if (fifthPlayerBoard.isVisible()) {
            this.fifthPlayerBoard.setVisible(false);

        } else {
            this.fifthPlayerBoard.setVisible(true);
        }


    }

    /**
     * This method handles the event on the firstPlayerButton and shows his playerBoard
     */
    @FXML
    public void firstPlayerButtonPushed() {

        if (firstPlayerBoard.isVisible()) {
            this.firstPlayerBoard.setVisible(false);

        } else {
            this.firstPlayerBoard.setVisible(true);
        }
    }

    /**
     * This method handles the event on the secondPlayerButton and shows his playerBoard
     */
    @FXML
    public void secondPlayerButtonPushed() {
        if (secondPlayerBoard.isVisible()) {
            this.secondPlayerBoard.setVisible(false);

        } else {
            this.secondPlayerBoard.setVisible(true);
        }
    }

    /**
     * This method handles the event on the thirdPlayerButton and shows his playerBoard
     */
    @FXML
    public void thirdPlayerButtonPushed() {
        if (thirdPlayerBoard.isVisible()) {
            this.thirdPlayerBoard.setVisible(false);

        } else {
            this.thirdPlayerBoard.setVisible(true);
        }
    }

    /**
     * This method handles the event on the fourthPlayerButton and shows his playerBoard
     */
    @FXML
    public void fourthPlayerButtonPushed() {
        if (fourthPlayerBoard.isVisible()) {
            this.fourthPlayerBoard.setVisible(false);

        } else {
            this.fourthPlayerBoard.setVisible(true);
        }
    }

    //************************************WEAPONS*******************************************


    /**
     * This method sets the weapons on the table
     * @param Weapon arraylist of weapon
     */
    private void setWeapons(List<Weapon> Weapon){
        String stringImage;

        stringImage = Weapon.get(0).getName();
        weaponNorth1.setImage( new Image("it/polimi/sw2019/Images/Cards/"+ stringImage + ".png"));
        stringImage = Weapon.get(1).getName();
        weaponNorth2.setImage( new Image("it/polimi/sw2019/Images/Cards/"+ stringImage + ".png"));
        stringImage = Weapon.get(2).getName();
        weaponNorth3.setImage( new Image("it/polimi/sw2019/Images/Cards/"+ stringImage + ".png"));
        stringImage = Weapon.get(3).getName();
        weaponWest1.setImage( new Image("it/polimi/sw2019/Images/Cards/"+ stringImage + ".png"));
        stringImage = Weapon.get(4).getName();
        weaponWest2.setImage( new Image("it/polimi/sw2019/Images/Cards/"+ stringImage + ".png"));
        stringImage = Weapon.get(5).getName();
        weaponWest3.setImage( new Image("it/polimi/sw2019/Images/Cards/"+ stringImage + ".png"));
        stringImage = Weapon.get(6).getName();
        weaponEast1.setImage( new Image("it/polimi/sw2019/Images/Cards/"+ stringImage + ".png"));
        stringImage = Weapon.get(7).getName();
        weaponEast2.setImage( new Image("it/polimi/sw2019/Images/Cards/"+ stringImage + ".png"));
        stringImage = Weapon.get(8).getName();
        weaponEast3.setImage( new Image("it/polimi/sw2019/Images/Cards/"+ stringImage + ".png"));
    }
    /**
     * This method handles the event on the powerUpDeck and opens the window PowerUP
     */
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


    /**
     * This method handles the event on the yourTurnButton and opens the window Action&Reload
     */
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

    /**
     * This method sets the Player's characteristics on the table
     * @param Players arraylist of player
     */
    public void setPlayerButtons(List<Player>Players ){

        firstPlayer.setText(Players.get(0).getNickname());
        firstPlayer.setStyle(String.format("-fx-background-image: url(/it/polimi/sw2019/Images/%s.png);", Players.get(0).getCharacter()));
        firstBoard.setImage( new Image("it/polimi/sw2019/Images/"+ Players.get(0).getCharacter() + "Board.png"));
        firstStatue.setImage(new Image("it/polimi/sw2019/Images/"+ Players.get(0).getCharacter() + "Statue.png"));

        secondPlayer.setText(Players.get(1).getNickname());
        secondPlayer.setStyle(String.format("-fx-background-image: url(/it/polimi/sw2019/Images/%s.png);", Players.get(1).getCharacter()));
        secondBoard.setImage( new Image("it/polimi/sw2019/Images/"+ Players.get(1).getCharacter() + "Board.png"));
        secondStatue.setImage( new Image("it/polimi/sw2019/Images/"+ Players.get(1).getCharacter() + "Statue.png"));

        thirdPlayer.setText(Players.get(2).getNickname());
        thirdPlayer.setStyle(String.format("-fx-background-image: url(/it/polimi/sw2019/Images/%s.png);", Players.get(2).getCharacter()));
        thirdBoard.setImage( new Image("it/polimi/sw2019/Images/"+ Players.get(2).getCharacter() + "Board.png"));
        thirdStatue.setImage( new Image("it/polimi/sw2019/Images/"+ Players.get(2).getCharacter() + "Statue.png"));

        if((Players.size())>3) {
            fourthPlayer.setText(Players.get(3).getNickname());
            fourthPlayer.setStyle(String.format("-fx-background-image: url(/it/polimi/sw2019/Images/%s.png);", Players.get(3).getCharacter()));
            fourthBoard.setImage(new Image("it/polimi/sw2019/Images/" + Players.get(3).getCharacter() + "Board.png"));
            fourthStatue.setImage( new Image("it/polimi/sw2019/Images/"+ Players.get(3).getCharacter() + "Statue.png"));

        }else{
            fourthPlayer.setVisible(false);
            fourthStatue.setVisible(false);
        }

        if((Players.size())>4){
            fifthPlayer.setText(Players.get(4).getNickname());
            fifthPlayer.setStyle(String.format("-fx-background-image: url(/it/polimi/sw2019/Images/%s.png);", Players.get(4).getCharacter()));
            fifthBoard.setImage( new Image("it/polimi/sw2019/Images/"+ Players.get(4).getCharacter() + "Board.png"));
            fifthStatue.setImage( new Image("it/polimi/sw2019/Images/"+ Players.get(4).getCharacter() + "Statue.png"));

        }else{
            fifthPlayer.setVisible(false);
            fifthStatue.setVisible(false);
        }

        map.setImage( new Image("it/polimi/sw2019/Images/"+ typeMap + ".png"));
    }

    /**
     * This method sets the ammo cards on the map
     * @param Ammo arraylist of ammo
     */
    private void setAmmo(List<Ammo> Ammo){
        if(this.typeMap.equals("zero")){
            this.ammo00.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(0).getImageName()+".png"));
            this.ammo02.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(1).getImageName()+".png"));
            this.ammo02.setLayoutX(40.0);
            this.ammo02.setLayoutY(105.0);
            this.ammo10.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(2).getImageName()+".png"));
            this.ammo11.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(3).getImageName()+".png"));
            this.ammo12.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(4).getImageName()+".png"));
            this.ammo12.setLayoutX(183.0);
            this.ammo12.setLayoutY(26.0);
            this.ammo20.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(5).getImageName()+".png"));
            this.ammo20.setLayoutX(365.0);
            this.ammo20.setLayoutY(415.0);
            this.ammo21.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(6).getImageName()+".png"));
            this.ammo21.setLayoutX(355.0);
            this.ammo21.setLayoutY(275.0);
            this.ammo31.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(7).getImageName()+".png"));
            this.ammo31.setLayoutX(495.0);
            this.ammo31.setLayoutY(275.0);
            this.ammo32.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(8).getImageName()+".png"));

        }else if(this.typeMap.equals("one")){

            this.ammo00.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(0).getImageName()+".png"));
            this.ammo02.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(1).getImageName()+".png"));
            this.ammo02.setLayoutX(42.0);
            this.ammo02.setLayoutY(108.0);
            this.ammo10.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(2).getImageName()+".png"));
            this.ammo11.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(3).getImageName()+".png"));
            this.ammo12.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(4).getImageName()+".png"));
            this.ammo12.setLayoutX(183.0);
            this.ammo12.setLayoutY(26.0);
            this.ammo20.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(5).getImageName()+".png"));
            this.ammo20.setLayoutX(335.0);
            this.ammo20.setLayoutY(415.0);
            this.ammo21.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(5).getImageName()+".png"));
            this.ammo21.setLayoutX(355.0);
            this.ammo21.setLayoutY(250.0);
            this.ammo31.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(6).getImageName()+".png"));
            this.ammo31.setLayoutX(570.0);
            this.ammo31.setLayoutY(250.0);
            this.ammo32.setVisible(false);

        }else if(this.typeMap.equals("two")){
            this.ammo00.setVisible(false);
            this.ammo02.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(0).getImageName()+".png"));
            this.ammo02.setLayoutX(40.0);
            this.ammo02.setLayoutY(27.0);
            this.ammo10.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(1).getImageName()+".png"));
            this.ammo11.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(2).getImageName()+".png"));
            this.ammo12.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(3).getImageName()+".png"));
            this.ammo12.setLayoutX(185.0);
            this.ammo12.setLayoutY(103.0);
            this.ammo20.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(5).getImageName()+".png"));
            this.ammo20.setLayoutX(365.0);
            this.ammo20.setLayoutY(415.0);
            this.ammo21.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(6).getImageName()+".png"));
            this.ammo21.setLayoutX(355.0);
            this.ammo21.setLayoutY(275.0);
            this.ammo31.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(7).getImageName()+".png"));
            this.ammo31.setLayoutX(495.0);
            this.ammo31.setLayoutY(275.0);
            this.ammo32.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(7).getImageName()+".png"));
        }else if(this.typeMap.equals("three")){
            this.ammo00.setVisible(false);
            this.ammo02.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(0).getImageName()+".png"));
            this.ammo02.setLayoutX(40.0);
            this.ammo02.setLayoutY(27.0);
            this.ammo10.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(1).getImageName()+".png"));
            this.ammo11.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(2).getImageName()+".png"));
            this.ammo12.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(3).getImageName()+".png"));
            this.ammo12.setLayoutX(185.0);
            this.ammo12.setLayoutY(103.0);
            this.ammo20.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(4).getImageName()+".png"));
            this.ammo20.setLayoutX(335.0);
            this.ammo20.setLayoutY(415.0);
            this.ammo21.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(5).getImageName()+".png"));
            this.ammo21.setLayoutX(355.0);
            this.ammo21.setLayoutY(250.0);
            this.ammo31.setImage(new Image("it/polimi/sw2019/Images/Ammo/"+Ammo.get(6).getImageName()+".png"));
            this.ammo31.setLayoutX(570.0);
            this.ammo31.setLayoutY(250.0);
            this.ammo32.setVisible(false);
        }
    }

    /**
     * This method sets the clientSocket on this class
     * @param clientSocket
     */
    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     *
     * @param startGameEv
     */
    public void handleEvent(StartGameEv startGameEv) {
        this.typeMap = startGameEv.getGameboard().getNrMap();
        System.out.println(startGameEv.getPlayers().get(0).getNickname());
        System.out.println(startGameEv.getPlayers().get(1).getNickname());
        System.out.println(startGameEv.getPlayers().get(2).getNickname());
        this.Players = startGameEv.getPlayers();
        try{
            this.Weapon.add(((SpaceGeneration)startGameEv.getGameboard().getMap().getSpace(2,2)).takeWeapon(0));
            this.Weapon.add(((SpaceGeneration)startGameEv.getGameboard().getMap().getSpace(2,2)).takeWeapon(1));
            this.Weapon.add(((SpaceGeneration)startGameEv.getGameboard().getMap().getSpace(2,2)).takeWeapon(2));
            this.Weapon.add(((SpaceGeneration)startGameEv.getGameboard().getMap().getSpace(3,0)).takeWeapon(0));
            this.Weapon.add(((SpaceGeneration)startGameEv.getGameboard().getMap().getSpace(3,0)).takeWeapon(1));
            this.Weapon.add(((SpaceGeneration)startGameEv.getGameboard().getMap().getSpace(3,0)).takeWeapon(2));
            this.Weapon.add(((SpaceGeneration)startGameEv.getGameboard().getMap().getSpace(0,1)).takeWeapon(0));
            this.Weapon.add(((SpaceGeneration)startGameEv.getGameboard().getMap().getSpace(0,1)).takeWeapon(1));
            this.Weapon.add(((SpaceGeneration)startGameEv.getGameboard().getMap().getSpace(0,1)).takeWeapon(2));


        } catch (InvalidSpaceException ex) {
            System.out.println("handle event exception in table controller");
        }

        if ( startGameEv.getGameboard().getNrMap().equals("zero") ) {
            try{
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(0,0)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(0,2)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(1,0)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(1,1)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(1,2)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(2,0)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(2,1)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(3,1)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(3,2)).takeAmmo()   );
            } catch (InvalidSpaceException ex) {
                //
            }
        }
        else if ( startGameEv.getGameboard().getNrMap().equals("one") ) {
            try{
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(0,0)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(0,2)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(1,0)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(1,1)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(1,2)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(2,0)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(2,1)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(3,1)).takeAmmo()   );
            } catch (InvalidSpaceException ex) {
                //
            }
        }
        else if ( startGameEv.getGameboard().getNrMap().equals("two") ) {
            try{
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(0,2)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(1,0)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(1,1)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(1,2)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(2,0)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(2,1)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(3,1)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(3,2)).takeAmmo()   );
            } catch (InvalidSpaceException ex) {
                //
            }
        }
        else {
            try{
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(0,2)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(1,0)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(1,1)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(1,2)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(2,0)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(2,1)).takeAmmo()   );
                this.Ammo.add(  ((SpaceAmmo) startGameEv.getGameboard().getMap().getSpace(3,1)).takeAmmo()   );
            } catch (InvalidSpaceException ex) {
                //
            }

        }
        //addWeaponsToList();
        Platform.runLater( () -> setWeapons(this.Weapon));
        Platform.runLater( () -> setPlayerButtons(this.Players));
        Platform.runLater( () -> setAmmo(this.Ammo));

        //setPlayerButtons(this.Players);
    }

    public void update(NotifyClient notifyClient) {
        notifyClient.visit(this.executorEventClient, this);
    }

    public void handleEvent(DirectionChooseEv directionChooseEv) {
        //set the possibility to give player the possibility to
    }

    public void handleEvent(NotifyMoveEv notifyMoveEv) {
        //
    }

    public void handleEvent(StartTurnEv startTurnEv) {
        //
    }

    public void handleEvent(NotifyEndMoveEv notifyEndMoveEv) {
        //
    }

    public void handleEvent(YourTurnEv yourTurnEv) {
        //
    }

    public void handleEvent(NotYourTurnEv notYourTurnEv) {

    }

    //***********************************Your-TURN-BUTTONS************************************************
    @FXML
    public void moveButtonPushed(){
        try {
            Parent table = FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML_File/Direction.fxml"));
            Scene tableScene = new Scene(table);
            Stage window = (Stage) moveButton.getScene().getWindow();
            window.setScene(tableScene);
            window.setResizable(false);
            window.show();
            MoveEv moveEv = new MoveEv(null);
            this.clientSocket.getViewContEvent().sendActionEv(moveEv);
        }catch (IOException e){

        }
    }

    @FXML
    public void grabButtonPushed(){
        try {
            Parent table = FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML_File/Direction.fxml"));
            Scene tableScene = new Scene(table);

            Stage window = (Stage) grabButton.getScene().getWindow();

            window.setScene(tableScene);
            window.setResizable(false);
            window.show();
        }catch (IOException e){

        }
    }

    @FXML
    public void shootButtonPushed() {
        try {
            Parent table = FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML_File/weapon.fxml"));
            Scene tableScene = new Scene(table);

            Stage window = (Stage) shootButton.getScene().getWindow();

            window.setScene(tableScene);
            window.setResizable(false);
            window.show();
        }catch (IOException e){

        }
    }


    @FXML
    public void reloadButtonPushed()  {
        try {
            Parent table = FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML_File/PowerUp.fxml"));
            Scene tableScene = new Scene(table);

            Stage window = (Stage) powerUpButton.getScene().getWindow();

            window.setScene(tableScene);
            window.setResizable(false);
            window.show();
        }catch(IOException e){

        }
    }
    @FXML
    public void endButtonPushed(ActionEvent event) throws IOException {

        EndEv endEv = new EndEv();
        this.clientSocket.getViewContEvent().sendActionEv(endEv);
    }
}