package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.ExtraGrenadeSetEv;
import it.polimi.sw2019.model.weapon_power.ExtraGrenade;
import it.polimi.sw2019.model.weapon_power.GrenadeLauncher;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExtraGrenadeCont implements Observer<ExtraGrenadeSetEv>, EffectController {

    private ExtraGrenade model;
    private Player attacker;
    private HashMap<String, Space> squares = new HashMap<>();
    private HashMap<String, Space> moveto = new HashMap<>();

    public ExtraGrenadeCont(ExtraGrenade model) {
        this.model = model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireSquares();
        }
    }

    private void acquireSquares(){
        ArrayList<String> romms = loadRooms();
        Logger logger = Logger.getLogger("controller.WeaponEffct.ExtraGrenade");

        try {
            for (int i = 0; i < Table.getMap().getMaxX(); i++) {
                for (int j = 0; j < Table.getMap().getMaxY(); j++) {
                    if (romms.contains(Table.getMap().getSpace(i, j).getRoom())){
                        squares.put(i+"-"+j, Table.getMap().getSpace(i,j));
                    }
                }
            }
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "Left the boundaries of the map");
        }
        if (chechkIsMoved()) {
            model.chooseSquare(attacker, new ArrayList<>(squares.keySet()));
        }else {
            model.chooseSquare(attacker, new ArrayList<>(squares.keySet()), new ArrayList<>(moveto.keySet()));
        }
    }

    private ArrayList<String> loadRooms(){
        ArrayList<String> valid = new ArrayList<>();

        valid.add(attacker.getPosition().getRoom());
        if (!attacker.getPosition().getNorth().isWall() && !valid.contains(attacker.getPosition().getNorth().getSpaceSecond().getRoom())){
            valid.add(attacker.getPosition().getNorth().getSpaceSecond().getRoom());
        }
        if (!attacker.getPosition().getSouth().isWall() && !valid.contains(attacker.getPosition().getSouth().getSpaceSecond().getRoom())){
            valid.add(attacker.getPosition().getSouth().getSpaceSecond().getRoom());
        }
        if (!attacker.getPosition().getWest().isWall() && !valid.contains(attacker.getPosition().getWest().getSpaceSecond().getRoom())) {
            valid.add(attacker.getPosition().getWest().getSpaceSecond().getRoom());
        }
        if (!attacker.getPosition().getEast().isWall() && !valid.contains(attacker.getPosition().getEast().getSpaceSecond().getRoom())){
            valid.add(attacker.getPosition().getEast().getSpaceSecond().getRoom());
        }
        return valid;
    }

    private boolean chechkIsMoved(){
        int i = 0;
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("Grenade Launcher"))){
            i++;
        }
        if (((GrenadeLauncher) attacker.listWeapon()[i].getPower()).isIsmoved()){
            return true;
        }else {
            loadSquares(((GrenadeLauncher) attacker.listWeapon()[i].getPower()).getTarget().getPosition());
            return false;
        }
    }

    private void loadSquares(Space tarpos){
        if (!tarpos.getWest().isWall()){
            moveto.put("west", tarpos.getWest().getSpaceSecond());
        }
        if (!tarpos.getEast().isWall()){
            moveto.put("east", tarpos.getEast().getSpaceSecond());
        }
        if (!tarpos.getNorth().isWall()) {
            moveto.put("north", tarpos.getNorth().getSpaceSecond());
        }
        if (!tarpos.getSouth().isWall()){
            moveto.put("south", tarpos.getSouth().getSpaceSecond());
        }
    }

    @Override
    public void update(ExtraGrenadeSetEv message) {
        model.setTargetarea(squares.get(message.getSquare()));
        model.usePower(attacker);
        if (message.getMoveto() != null){
            model.moveTarget(moveto.get(message.getMoveto()), loadTarget());
        }
    }

    private Player loadTarget(){
        int i = 0;
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("Grenade Launcher"))){
            i++;
        }
        return ((GrenadeLauncher) attacker.listWeapon()[i].getPower()).getTarget();
    }
}
