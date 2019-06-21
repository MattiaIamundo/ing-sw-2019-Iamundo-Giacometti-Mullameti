package it.polimi.sw2019.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ActionController {

    @FXML
    public void moveButtonPushed(ActionEvent event) throws IOException {
        Parent table = FXMLLoader.load(getClass().getResource("direction.fxml"));
        Scene tableScene = new Scene(table);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableScene);
        window.show();
    }

    @FXML
    public void grabButtonPushed(ActionEvent event) throws IOException {
        Parent table = FXMLLoader.load(getClass().getResource("direction.fxml"));
        Scene tableScene = new Scene(table);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableScene);
        window.show();
    }

    @FXML
    public void shootButtonPushed(ActionEvent event) throws IOException {
        Parent table = FXMLLoader.load(getClass().getResource("weapon.fxml"));
        Scene tableScene = new Scene(table);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableScene);
        window.show();
    }
}
