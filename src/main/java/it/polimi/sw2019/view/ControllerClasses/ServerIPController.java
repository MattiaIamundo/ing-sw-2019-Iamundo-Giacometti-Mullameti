package it.polimi.sw2019.view.ControllerClasses;

import it.polimi.sw2019.view.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
/**Class ServerIPController: the controller behind ServerIP.fxml file
 * @author Merita Mullameti
 */
public class ServerIPController {

    private GUI gui;

    @FXML private Button okButton;
    @FXML private TextField ipField;

    /**
     * This method handles the event on the okButton and sends a " message " in gui
     */
    @FXML
    public void handleOkButton(){
        returnIP(ipField.getText());
        gui.requestMenu("ok");
    }

    /**
     * This method  sends the ServerIP typed by the player in gui
     */
    public void returnIP(String string){
        gui.setServerIP(string);
    }

    /**
     * This method sets the gui on this class
     * @param gui
     */
    public void setGui(GUI gui){
        this.gui=gui;
    }

}
