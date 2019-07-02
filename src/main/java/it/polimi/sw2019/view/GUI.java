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
import java.util.Scanner;

public class GUI extends Application implements UIinterface {


    private MenuController menuController;
    private Stage stage;
    private ClientSocket clientSocket;
    private String string;
    private PlayerView pv ;
    private ViewContEvent vce ;

    public GUI() {


    }


    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/it/polimi/sw2019/FXML_File/Menu.fxml"));
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
        this.clientSocket=new ClientSocket("127.0.0.1");
        clientSocket.setUI(this);
        MenuController menuController = loader.getController();
        menuController.setClientSocket(clientSocket);



    }


    @Override
    public void requestNickname(String string) {

        if(string.equals("ok")) {

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/it/polimi/sw2019/FXML_File/NewPlayer.fxml"));
                Parent newPlayer = loader.load();

                NewPlayerController newPlayerController = loader.getController();
                newPlayerController.setClientSocket(clientSocket);

                Stage mainStage = stage;
                mainStage.getScene().setRoot(newPlayer);
            } catch (IOException e) {
            }
        }else{
            System.out.println("Error");
        }
    }

    @Override
    public void requestColor(String string) {

        if(string.equals("ok")){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/it/polimi/sw2019/FXML_File/PlayerCharacter.fxml"));
                Parent playerCharacter = loader.load();

                PlayerCharacterController playerCharacterController = loader.getController();
                playerCharacterController.setClientSocket(clientSocket);

                Stage mainStage = stage;
                mainStage.getScene().setRoot(playerCharacter);
            }catch (IOException e){}
        }else{
            System.out.println("ko");
        }

    }

    @Override
    public void requestSkull(String string) {

        if(string.equals("ok")){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/it/polimi/sw2019/FXML_File/ChooseSkull.fxml"));
                Parent chooseSkull = loader.load();

                ChooseSkullController chooseSkullController = loader.getController();
                chooseSkullController.setClientSocket(clientSocket);

                Stage mainStage = stage;
                mainStage.getScene().setRoot(chooseSkull);
            }catch (IOException e){}
        }else{
            System.out.println("ko");
        }

    }


    @Override
    public void reconnection() {

    }

    @Override
    public void sendOk() {


    }

    @Override
    public void requestMap(String string) {

        if (string.equals("ok")){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/it/polimi/sw2019/FXML_File/ChooseMap.fxml"));
                Parent chooseMap = loader.load();

                ChooseMapController chooseMapController = loader.getController();
                chooseMapController.setClientSocket(clientSocket);

                Stage mainStage = stage;
                mainStage.getScene().setRoot(chooseMap);
            } catch (IOException e) {
            }
        }else {
            System.out.println("ko");
        }
    }

    @Override
    public void requestLobby(String string) {
        if (string.equals("ok")){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/it/polimi/sw2019/FXML_File/Lobby.fxml"));
                Parent lobby = loader.load();

                LobbyController lobbyController = loader.getController();
                lobbyController.setClientSocket(clientSocket);

                Stage mainStage = stage;
                mainStage.getScene().setRoot(lobby);
            } catch (IOException e) {
            }
        }else {
            System.out.println("ko");
        }
    }


    @Override
    public void requestTable(String string) {
        if (string.equals("ok")){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/it/polimi/sw2019/FXML_File/Table.fxml"));
                Parent lobby = loader.load();

                TableController tableController = loader.getController();
                tableController.setClientSocket(clientSocket);

                Stage mainStage = stage;
                mainStage.getScene().setRoot(lobby);
            } catch (IOException e) {
            }
        }else {
            System.out.println("ko");
        }
    }
    public static void main(String[]args){
        launch(args);
    }

}

