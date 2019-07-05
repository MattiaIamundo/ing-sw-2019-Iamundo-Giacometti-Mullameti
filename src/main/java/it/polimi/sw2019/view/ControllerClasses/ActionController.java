package it.polimi.sw2019.view.ControllerClasses;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
/**Class ActionController: the controller behind Action.fxml file
 * @author Merita Mullameti
 */
public class ActionController {


    @FXML private Button moveButton;

    @FXML
    public void moveButtonPushed(ActionEvent event) throws IOException {
        Parent table = FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML_File/Direction.fxml"));
        Scene tableScene = new Scene(table);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableScene);
        window.show();
    }

    @FXML
    public void grabButtonPushed(ActionEvent event) throws IOException {
        Parent table = FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML_File/Direction.fxml"));
        Scene tableScene = new Scene(table);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableScene);
        window.show();
    }

    @FXML
    public void shootButtonPushed(ActionEvent event) throws IOException {
        Parent table = FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML_File/weapon.fxml"));
        Scene tableScene = new Scene(table);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableScene);
        window.show();
    }


}
