package it.polimi.sw2019.controller.powerup;

import it.polimi.sw2019.events.powerup_events.TargetingScopeChooseEv;
import it.polimi.sw2019.events.powerup_events.TargetingScopeSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.powerup.TargetingScope;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

/**
 * This class represent the controller of Targeting Scope powerUp
 */
public class TargetingScopeCont extends Observable<TargetingScopeChooseEv> implements Observer<TargetingScopeSetEv>, PowerUpController{
    private TargetingScope model;
    private ArrayList<Player> players;
    private Player attacker;

    /**
     * @param model the model of the powerUp
     * @param players the list of the players in the match
     */
    public TargetingScopeCont(TargetingScope model, ArrayList<Player> players) {
        this.model = model;
        this.players = players;
    }

    /**
     * This method search for the players that can be selected as a valid target for the powerUp
     * @param attacker is the player that use the powerUp
     */
    @Override
    public void usePowerUp(Player attacker) {
        ArrayList<String> targets = new ArrayList<>();
        ArrayList<String> ammos = new ArrayList<>();
        this.attacker = attacker;
        for (Player player : attacker.getLastHittedPlayers()){
            targets.add(player.getNickname());
        }
        if (attacker.getAmmo()[0] > 0){
            ammos.add("red");
        }
        if (attacker.getAmmo()[1] > 0){
            ammos.add("blue");
        }
        if (attacker.getAmmo()[2] > 0){
            ammos.add("yellow");
        }
        notify(new TargetingScopeChooseEv(attacker.getNickname(), targets, ammos));
    }

    /**
     * This method catch a TargetingScopeEv event
     * @param message the event that contains the player set as target and the color of the ammunition to be paid
     */
    @Override
    public void update(TargetingScopeSetEv message) {
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget())){
                payAmmo(attacker, message.getAmmo());
                model.setTarget(player);
                model.useEffect(attacker);
                return;
            }
        }
    }

    /**
     * This method pay the chosen ammunition
     * @param attacker the player that use the powerUp
     * @param ammo the color of the chosen ammunition
     */
    private void payAmmo(Player attacker, String ammo){
        switch (ammo){
            case "red":
                attacker.getAmmo()[0]--;
                break;
            case "blue":
                attacker.getAmmo()[1]--;
                break;
            case "yellow":
                attacker.getAmmo()[2]--;
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
