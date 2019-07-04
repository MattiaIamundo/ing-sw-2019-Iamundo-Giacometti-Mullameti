package it.polimi.sw2019.view.ControllerClasses;
import it.polimi.sw2019.events.NotifyClient;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.client_event.Cevent.DirectionChooseEv;
import it.polimi.sw2019.events.client_event.Cevent.StartGameEv;
import it.polimi.sw2019.events.server_event.VCevent.MoveEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.network.Socket.ClientSocket;
import it.polimi.sw2019.view.Observer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableController implements Observer<NotifyClient> {



    private ClientSocket clientSocket;
    private List<Player> Players = new ArrayList<Player>(5);
    private List<Weapon> Weapon = new ArrayList<Weapon>(9);
    private List<Ammo> Ammo = new ArrayList<Ammo>(9);
    private List<Button> PlayerButtons = new ArrayList<Button>(5);
    private List<ImageView> WeaponImages = new ArrayList<>(9);
    private List<ImageView> AmmoImages = new ArrayList<ImageView>(9);
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

    @FXML private Button firstPlayer;
    @FXML private Button secondPlayer;
    @FXML private Button thirdPlayer;
    @FXML private Button fourthPlayer;
    @FXML private Button fifthPlayer;

    @FXML private AnchorPane thisPlayerBoard;
    @FXML private AnchorPane firstPlayerBoard;
    @FXML private AnchorPane secondPlayerBoard;
    @FXML private AnchorPane thirdPlayerBoard;
    @FXML private AnchorPane fourthPlayerBoard;

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

/*
        this.XImage=this.yellowStatue.getX();

        this.YImage=this.yellowStatue.getY();

        System.out.println(this.yellowStatue.getX());
        addPlayerButtonsToList();
        addWeaponsToList();
        setPlayerButtons(Players);
        setWeapons(Weapon);


 */



    }

    //************************************PLAYERBOARDS*******************************************
    private void addPlayerButtonsToList(){
        PlayerButtons.add(firstPlayer);
        PlayerButtons.add(secondPlayer);
        PlayerButtons.add(thirdPlayer);
        PlayerButtons.add(fourthPlayer);
        PlayerButtons.add(fifthPlayer);

    }
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
    private void addWeaponsToList(){
        WeaponImages.add(weaponNorth1);
        WeaponImages.add(weaponNorth2);
        WeaponImages.add(weaponNorth3);
        WeaponImages.add(weaponEast1);
        WeaponImages.add(weaponEast2);
        WeaponImages.add(weaponEast3);
        WeaponImages.add(weaponWest1);
        WeaponImages.add(weaponWest2);
        WeaponImages.add(weaponWest3);


    }

    private void setWeapons(List<Weapon> Weapon){
        String stringImage;
        String string;
        /*
        for (int i = 0 ; i < 9 ; i++){
            stringImage = Weapon.get(i).getName();
            string = new StringBuilder().append("-fx-background-image: url(/it/polimi/sw2019/Images/").append(stringImage).append(".png);").toString();
            //this.WeaponImages.get(i).setImage();
            this.WeaponImages.get(i).setImage( new Image(string));
        }

         */
        stringImage = Weapon.get(0).getName();
        weaponNorth1.setImage( new Image("it/polimi/sw2019/Images/"+ stringImage + ".png"));
        stringImage = Weapon.get(1).getName();
        weaponNorth2.setImage( new Image("it/polimi/sw2019/Images/"+ stringImage + ".png"));
        stringImage = Weapon.get(2).getName();
        weaponNorth3.setImage( new Image("it/polimi/sw2019/Images/"+ stringImage + ".png"));
        stringImage = Weapon.get(3).getName();
        weaponWest1.setImage( new Image("it/polimi/sw2019/Images/"+ stringImage + ".png"));
        stringImage = Weapon.get(4).getName();
        weaponWest2.setImage( new Image("it/polimi/sw2019/Images/"+ stringImage + ".png"));
        stringImage = Weapon.get(5).getName();
        weaponWest3.setImage( new Image("it/polimi/sw2019/Images/"+ stringImage + ".png"));
        stringImage = Weapon.get(6).getName();
        weaponEast1.setImage( new Image("it/polimi/sw2019/Images/"+ stringImage + ".png"));
        stringImage = Weapon.get(7).getName();
        weaponEast2.setImage( new Image("it/polimi/sw2019/Images/"+ stringImage + ".png"));
        stringImage = Weapon.get(8).getName();
        weaponEast3.setImage( new Image("it/polimi/sw2019/Images/"+ stringImage + ".png"));





        /*
        for (Weapon weapon : Weapon){

            for (ImageView weaponImages : WeaponImages) {
                System.out.println( weapon.getName() );
                weaponImages.setStyle(String.format("-fx-background-image: url(/it/polimi/sw2019/Images/%s.png);", weapon.getName()));

            }
        }

         */
    }
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

    public void setPlayerButtons(List<Player>players ){

        for(Player player : players) {
            for (Button playerButton : PlayerButtons) {
                playerButton.setText(player.getNickname());
                playerButton.setStyle(String.format("-fx-background-image: url(/it/polimi/sw2019/Images/%s.png);", player.getCharacter()));
            }
        }

    }

    private void setAmmo(List<Ammo> Ammo){
        for (Ammo ammo : Ammo){
            for (ImageView ammoImages : AmmoImages) {
                //weaponImages.setStyle(String.format("-fx-background-image: url(/it/polimi/sw2019/Images/%s.png);", ammo.));

            }
        }
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void handleEvent(StartGameEv startGameEv) {
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
        //setPlayerButtons(this.Players);
    }

    public void update(NotifyClient notifyClient) {

    }

    public void handleEvent(DirectionChooseEv directionChooseEv) {
        //set the possibility to give player the possibility to
    }
}