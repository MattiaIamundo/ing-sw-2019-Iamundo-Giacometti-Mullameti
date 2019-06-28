package it.polimi.sw2019.view.ControllerClasses;





import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.network.Socket.ClientSocket;


import javafx.fxml.FXML;

import javafx.scene.control.*;



public class NewPlayerController {


    private ClientSocket clientSocket ;
    Player tempPlayer = new Player("",0,null,null);




    @FXML private TextField nicknameField;

    @FXML private Button submitButton;

    @FXML private Button nextButton;
    @FXML private Label validInput;

    private String nickname;


    @FXML
    public void initialize(){
        this.submitButton.setDefaultButton(true);
        this.submitButton.setVisible(true);
        this.nextButton.setVisible(false);
        this.validInput.setText("");

    }


    public void setClientSocket(ClientSocket clientSocket){
        this.clientSocket=clientSocket;
    }


    @FXML
    public void submitButtonClicked() {

        if (nicknameField.getText() == null ||nicknameField.getText().length() == 0 ) {

            this.validInput.setText( "Invalid nickname!");

        }else {
                this.validInput.setText( "");
                nickname=nicknameField.getText();

                this.clientSocket.setInfo("nickname" , nickname);


            if(clientSocket.getOk()){

                this.nextButton.setVisible(true);
            }else if(!(clientSocket.getOk())){
                this.nextButton.setVisible(false);
            }

        }



    }


    @FXML
    public void nextButtonClicked(){
        clientSocket.getContSelect().waitingForColorRequest(clientSocket.getPlayerView());

        /*try{
           Parent chooseMapSkull= FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML/PlayerCharacter.fxml"));
            Scene chooseMapSkullScene = new Scene(chooseMapSkull);

            Stage window = (Stage)nextButton.getScene().getWindow();

            window.setScene(chooseMapSkullScene);
            window.setResizable(false);
            window.show();

        }catch(IOException e){

        }*/

    }




}
