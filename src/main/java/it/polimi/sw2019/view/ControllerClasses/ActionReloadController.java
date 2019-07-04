package it.polimi.sw2019.view.ControllerClasses;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**Class ActionReloadController: the controller behind Action&Reload.fxml file
 * @author Merita Mullameti
 */
public class ActionReloadController {

    @FXML
    public void actionButtonPushed(ActionEvent event) throws IOException {
        Parent table = FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML_File/Action.fxml"));
        Scene tableScene = new Scene(table);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableScene);
        window.show();
    }


    @FXML
    public void reloadButtonPushed(ActionEvent event) throws IOException {
        Parent table = FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML_File/powerup.fxml"));
        Scene tableScene = new Scene(table);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableScene);
        window.show();
    }
}
