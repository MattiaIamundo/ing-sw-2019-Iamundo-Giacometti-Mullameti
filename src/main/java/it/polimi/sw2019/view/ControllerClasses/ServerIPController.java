package it.polimi.sw2019.view.ControllerClasses;

import it.polimi.sw2019.view.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ServerIPController {


    private GUI gui;
    @FXML private Button okButton;
    @FXML private TextField ipField;

    public void setGui(GUI gui){
        this.gui=gui;
    }
    @FXML
    public void handleOkButton(){

        returnIP(ipField.getText());
        gui.requestMenu("ok");
    }
    public void returnIP(String string){
        gui.setServerIP(string);
    }
}
