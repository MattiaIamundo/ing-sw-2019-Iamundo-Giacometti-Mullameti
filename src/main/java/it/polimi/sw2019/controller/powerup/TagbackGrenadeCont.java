package it.polimi.sw2019.controller.powerup;

import it.polimi.sw2019.events.powerup_events.TagbackGrenadeChooseEv;
import it.polimi.sw2019.events.powerup_events.TagbackGrenadeSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.powerup.TagbackGrenade;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

/**
 * This class represent the controller for Tagback Grenade powerUp
 */
public class TagbackGrenadeCont extends Observable<TagbackGrenadeChooseEv> implements Observer<TagbackGrenadeSetEv>, PowerUpController{
    private TagbackGrenade model;
    private ArrayList<Player> players;
    private Player attacker;
    private Player target;

    /**
     * @param model the model of the powerUp
     * @param players the list of the players in the match
     */
    public TagbackGrenadeCont(TagbackGrenade model, ArrayList<Player> players) {
        this.model = model;
        this.players = players;
    }

    /**
     * This method search for the players that can be selected as a valid target for the powerUp
     * @param attacker is the player that use the powerUp
     */
    @Override
    public void usePowerUp(Player attacker) {
        this.attacker = attacker;
        for (Player player : players){
            if (attacker.getPlance().getDamageTrack().get(attacker.getPlance().getDamageTrack().size() - 1).equals(player.getNickname())){
                target = player;
                notify(new TagbackGrenadeChooseEv(attacker.getNickname(), player.getNickname()));
            }
        }
    }

    /**
     * This method catch a TagbackGrenadeSetEv event
     * @param message the event that contains a boolean that it's true if the attacker want to use the effect of the powerUp
     */
    @Override
    public void update(TagbackGrenadeSetEv message) {
        if (message.getMarkTarget()){
            model.setTarget(target);
            model.useEffect(attacker);
        }
    }

}
