package it.polimi.sw2019.view;



import it.polimi.sw2019.view.ControllerClasses.*;
import it.polimi.sw2019.network.Socket.ClientSocket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.IOException;

public class GUI extends Application implements UIinterface {

    private TableController tableController;
    private Stage stage;
    private ClientSocket clientSocket;


    private String ip;

    public void setServerIP(String ip){
        this.ip = ip;


    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/it/polimi/sw2019/FXML_File/ServerIP.fxml"));
        Parent serverIP = loader.load();
        Scene scene=new Scene(serverIP);
        stage.setScene(scene);
        stage.setTitle("Adrenaline");
        stage.setResizable(false);
        stage.getScene().setRoot(serverIP);
        stage.show();
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
        ServerIPController serverIPController = loader.getController();
        serverIPController.setGui(this);
        this.stage=stage;

        this.clientSocket=new ClientSocket(ip);
        clientSocket.setUI(this);





    }



    @Override
    public void requestMenu(String string) {

        if(string.equals("ok")){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/it/polimi/sw2019/FXML_File/Menu.fxml"));
                Parent menu = loader.load();

                MenuController menuController = loader.getController();
                menuController.setClientSocket(clientSocket);

                Stage mainStage = stage;
                mainStage.getScene().setRoot(menu);
            }catch (IOException e){}
        }else{
            System.out.println("ko");
        }

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
            }catch (IOException e){
                //
            }
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
                Parent table = loader.load();

                this.tableController = loader.getController();
                this.tableController.setClientSocket(clientSocket);
                this.clientSocket.setTableController(this.tableController);

                Stage mainStage = stage;
                mainStage.getScene().setRoot(table);

                EventForGui eventForGui = new EventForGui(this.clientSocket);
                eventForGui.run();

                //EventForServer eventForServer = new EventForServer();
                //eventForServer.run();

            } catch (IOException e) {
                //
            }
        }else {
            System.out.println("ko");
        }
    }

    @Override
    public void requestRefresh(String string) {
        if (string.equals("ok")){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/it/polimi/sw2019/FXML_File/Table.fxml"));
                Parent table = loader.load();

                this.tableController = loader.getController();

                Stage mainStage = stage;
                mainStage.getScene().setRoot(table);


            } catch (IOException e) {
                //
            }
        }else {
            System.out.println("ko");
        }
    }




    public static void main(String[]args){
        launch(args);
    }

}

