package it.polimi.sw2019.GUI;


import it.polimi.sw2019.model.Player;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    private ArrayList<Player> playerList= new ArrayList<>();
    ObservableList<Player> playerData = FXCollections.observableArrayList(playerList);


    public Main(){
    }


    public ObservableList<Player> getPlayerData() {
        return playerData;
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("View/Menu.fxml"));
        Scene scene=new Scene(menu);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void addPlayers (Player player){
        playerList.add(player);
        System.out.println(player.getNickname());
        System.out.println(player.getCharacter());
    }


    public static void main(String[]args){
        launch(args);
    }
}
