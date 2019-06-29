package it.polimi.sw2019.view;

import it.polimi.sw2019.events.client_event.Cevent.Color;
import it.polimi.sw2019.events.client_event.Cevent.Login;
import it.polimi.sw2019.events.client_event.Cevent.Reconnection;
import it.polimi.sw2019.events.server_event.VCevent.VCColor;
import it.polimi.sw2019.events.server_event.VCevent.VCLogin;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.nethandler.ViewContEvent;
import it.polimi.sw2019.network.Socket.ClientSocket;


public class PlayerView extends ObservableByGame implements Observer <Player> {

    private Player p;
    private UIinterface ui;
    private ClientSocket clientSocket;

    public PlayerView() {}
    public PlayerView(UIinterface uIinterface, ClientSocket clientSocket) {
        this.ui = uIinterface;
        this.clientSocket = clientSocket;
    }



    protected void showPlayer() {

    }

    /**
     * this method show the update that one player did
     */
    public void update(Player message) {
        showPlayer();
    }

    public void requestNickname(Login login) {
        clientSocket.notifyGUI("RequestNickname");
    }

    public void requestColor(Color color) {

        clientSocket.notifyGUI("RequestCharacter");
        clientSocket.setColorList(color.getColors());

    }

    public void requestNickname(Reconnection re) {
        ui.reconnection();
    }

    public void sendNickname(ViewContEvent vce, Reconnection re) {
        vce.sendNickname(re);
    }

    public void sendNickname(ViewContEvent vce, VCLogin vcLogin) {
        vce.sendNickname(vcLogin);
    }

    public void sendColor(ViewContEvent vce, VCColor vcColor) {
        System.out.println("sentColor");
        vce.sendColor(vcColor);
    }

    public void sendOk() {
        clientSocket.getOk();
    }

    public void sendYouAreFirstPlayer(){clientSocket.getYouAreFirstPlayer();}

    public void waitForPing() {
        clientSocket.notifyGUI("Ping");
    }

    public void sendPong(ViewContEvent vce) {
        vce.sendPong();
    }

    public void requestSkull(boolean firstTime) {
        if(firstTime){clientSocket.notifyGUI("RequestSkullNr");}

    }

    public void sendSkull( ViewContEvent vce, String sku) {
        vce.sendSkull(sku);
    }

    public void requestMap(boolean firstTime) {
        if(firstTime){clientSocket.notifyGUI("RequestMapType");}
    }

    public void sendMap(ViewContEvent vce, String maptype) {
        vce.sendMap(maptype);
    }
}
