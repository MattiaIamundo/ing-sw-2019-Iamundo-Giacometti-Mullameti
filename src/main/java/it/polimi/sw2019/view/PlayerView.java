package it.polimi.sw2019.view;

import it.polimi.sw2019.events.client_event.Cevent.Color;
import it.polimi.sw2019.events.client_event.Cevent.Login;
import it.polimi.sw2019.events.client_event.Cevent.Reconnection;
import it.polimi.sw2019.events.server_event.VCevent.VCColor;
import it.polimi.sw2019.events.server_event.VCevent.VCLogin;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.nethandler.ViewContEvent;


public class PlayerView extends ObservableByGame implements Observer <Player> {

    private Player p;
    private UIinterface ui;

    public PlayerView() {}
    public PlayerView(UIinterface uIinterface) {
        this.ui = uIinterface;
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
        ui.requestNickname(login.isFirstTime(), login.getNickname());
    }

    public void requestColor(Color color) {
        ui.requestColor(color.isFirstTime(), color.isDuplicated(), color.getColors());
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
        vce.sendColor(vcColor);
    }

    public void sendOk() {
        ui.sendOk();
    }

    public void waitForPing() {

    }

    public void sendPing(ViewContEvent vce) {
        vce.sendPing();
    }

}
