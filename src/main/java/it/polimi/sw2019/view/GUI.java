package it.polimi.sw2019.view;



import it.polimi.sw2019.view.ControllerClasses.*;
import it.polimi.sw2019.events.server_event.VCevent.VCColor;
import it.polimi.sw2019.events.server_event.VCevent.VCLogin;
import it.polimi.sw2019.nethandler.ViewContEvent;
import it.polimi.sw2019.network.Socket.ClientSocket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.IOException;
import java.util.List;

public class GUI extends Application implements UIinterface {


    private Stage stage;
    private ClientSocket clientSocket;
    private String string;
    private PlayerView pv ;
    private ViewContEvent vce ;

    public GUI(){
    }


    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/it/polimi/sw2019/FXML/Menu.fxml"));
        Parent menu = loader.load();
        Scene scene=new Scene(menu);
        stage.setScene(scene);
        stage.setTitle("Adrenaline");
        stage.setResizable(false);
        stage.getScene().setRoot(menu);
        stage.show();
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });

        this.stage=stage;
        this.clientSocket=new ClientSocket("127.0.0.1" , false);
        clientSocket.setView( this);
        MenuController menuController = loader.getController();
        menuController.setClientSocket(clientSocket);

    }


    @Override
    public void requestNickname(boolean isTheFirstTime, List<String> nicknameInTheGame) {

        NewPlayerController newPlayerController = new NewPlayerController();


        newPlayerController.isValid("true");


        if( isTheFirstTime ) {


            string = newPlayerController.submitButtonClicked();
            VCLogin vcLogin = new VCLogin(string);
            pv.sendNickname(vce, vcLogin);
        }
        else {

            string = newPlayerController.submitButtonClicked();
            VCLogin vcLogin = new VCLogin(string);
            pv.sendNickname(vce, vcLogin);
        }


    }

    @Override
    public void requestColor(boolean firstTime, boolean duplicated, List<String> colorlist) {


        PlayerCharacterController playerCharacterController= new PlayerCharacterController();

        string = playerCharacterController.chooseCharacter();
        VCColor vcColor = new VCColor(string);
        pv.sendColor(vce,vcColor);

    }

    @Override
    public void requestSkull(boolean firstTime) {


        ChooseSkullController chooseSkullController = new ChooseSkullController();

        if ( firstTime ) {

            string = chooseSkullController.chooseNrSkulls();

        }
        else {


        }


        pv.sendSkull(vce,string);
    }


    @Override
    public void reconnection() {

    }

    @Override
    public void sendOk() {

    }

    @Override
    public void requestMap(boolean firstTime) {
        ChooseMapController chooseMapController = new ChooseMapController();

        if ( firstTime ) {

            string = chooseMapController.chooseMap();

        }
        else {


        }


        pv.sendSkull(vce,string);

    }

    public static void main(String[]args){
        launch(args);
    }

}

