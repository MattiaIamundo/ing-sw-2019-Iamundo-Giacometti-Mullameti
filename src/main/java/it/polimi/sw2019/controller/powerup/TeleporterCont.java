package it.polimi.sw2019.controller.powerup;

import it.polimi.sw2019.events.powerup_events.TeleporterChooseEv;
import it.polimi.sw2019.events.powerup_events.TeleporterSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.powerup.Teleporter;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeleporterCont extends Observable<TeleporterChooseEv> implements Observer<TeleporterSetEv>, PowerUpController{
    private Teleporter model;
    private ArrayList<Player> players;
    private Map map;
    private HashMap<String, Space> positions = new HashMap<>();

    public TeleporterCont(Teleporter model, ArrayList<Player> players, Map map) {
        this.model = model;
        this.players = players;
        this.map = map;
    }

    @Override
    public void usePowerUp(Player attacker) {
        Logger logger = Logger.getLogger("controller.TeleporterCont");
        ArrayList<String> targets = new ArrayList<>();

        try {
            for (int x = 0; x < map.getMaxX(); x++) {
                for (int y = 0; y < map.getMaxY(); y++) {
                    positions.put(x+"-"+y, map.getSpace(x,y));
                }
            }
            for (Player player : players){
                if (player != attacker){
                    targets.add(player.getNickname());
                }
            }
            notify(new TeleporterChooseEv(attacker.getNickname(), targets, new ArrayList<>(positions.keySet())));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Override
    public void update(TeleporterSetEv message) {
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget())){
                model.setMoveto(positions.get(message.getMoveto()));
                model.useEffect(player);
            }
        }
    }
}
