package it.polimi.sw2019.view.ControllerClasses;





import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.network.Socket.ClientSocket;


import javafx.fxml.FXML;

import javafx.scene.control.*;



public class NewPlayerController {


    private ClientSocket clientSocket ;
    Player tempPlayer = new Player("",0,null,null);



    @FXML private Label enterNickname;
    @FXML private TextField nicknameField;
    @FXML private Button submitButton;
    @FXML private Button okButton;
    @FXML private Button nextButton;
    @FXML private Label validInput;

    private String nickname;


    @FXML
    public void initialize(){
        this.submitButton.setDefaultButton(true);
        this.submitButton.setVisible(true);
        this.nextButton.setVisible(false);
        this.validInput.setVisible(false);
        this.okButton.setVisible(false);

    }


    public void setClientSocket(ClientSocket clientSocket){
        this.clientSocket=clientSocket;
    }


    @FXML
    public void submitButtonClicked() {


        nickname=nicknameField.getText();
        this.clientSocket.setInfo("nickname" , nickname);


        if(clientSocket.getOk()){
            this.submitButton.setVisible(false);
            this.nextButton.setVisible(true);
        }else if(!(clientSocket.getOk())){

            this.enterNickname.setVisible(false);
            this.nicknameField.setVisible(false);
            this.submitButton.setVisible(false);
            this.nextButton.setVisible(false);
            this.validInput.setVisible(true);
            this.okButton.setVisible(true);
        }

    }

    @FXML
    public void handleOkButton(){
        clientSocket.getContSelect().waitForNicknameRequest(clientSocket.getPlayerView());
    }

    @FXML
    public void nextButtonClicked(){
        System.out.println("newPlayer");
        clientSocket.getContSelect().waitingForColorRequest(clientSocket.getPlayerView());

        /*try{
           Parent chooseMapSkull= FXMLLoader.load(getClass().getResource("/it/polimi/sw2019/FXML_File/PlayerCharacter.fxml"));
            Scene chooseMapSkullScene = new Scene(chooseMapSkull);

            Stage window = (Stage)nextButton.getScene().getWindow();

            window.setScene(chooseMapSkullScene);
            window.setResizable(false);
            window.show();

        }catch(IOException e){

        }*/

    }




}
