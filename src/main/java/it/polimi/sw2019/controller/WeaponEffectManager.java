package it.polimi.sw2019.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.sw2019.controller.weaponeffect.EffectController;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.weapon_event.PowerChooseEv;
import it.polimi.sw2019.events.weapon_event.UnpaidEffectEv;
import it.polimi.sw2019.exception.UnpaidEffectCostException;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.events.weapon_event.PowerSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeaponEffectManager extends Observable<NotifyReturn> implements Observer<PowerSetEv> {
    private ArrayList<Player> players;
    private Map map;
    private Player attacker;
    private Weapon weapon;
    private HashMap<String, EffectController> effectControllers = new HashMap<>();
    //the following hash map are event specific
    private HashMap<String, Integer> availableAmmo = new HashMap<>();
    private HashMap<String, String> availablePowerUps = new HashMap<>();
    private HashMap<String, Integer> remainingCosts = new HashMap<>();

    public WeaponEffectManager(ArrayList<Player> players, Map map, Game game) {
        this.players = players;
        this.map = map;
        initEffectCont();
        for (java.util.Map.Entry<String, EffectController> controller : effectControllers.entrySet()){
            controller.getValue().addObserver(game);
        }
    }

    private class EffectFile{
        private String power;
        private String controller;
    }

    /**
     * This method initialize an hash map with all the controllers for the weapon effect handling
     */
    private void initEffectCont(){
        Logger logger = Logger.getLogger("controller.WeaponEffct.WeaponEffctManager");
        Gson gson = new Gson();
        ArrayList<EffectFile> effectFiles;
        Type FILE_TYPE = new TypeToken<ArrayList<EffectFile>>(){}.getType();

        try {
            FileReader fileReader = new FileReader("File_Json/weaponEffectController.json");
            effectFiles = gson.fromJson(fileReader, FILE_TYPE);

            for (EffectFile effct : effectFiles){
                Power effect = (Power) Class.forName(effct.power).getConstructor().newInstance();
                EffectController controller = (EffectController) Class.forName(effct.controller).getConstructor(Power.class).newInstance(effect);
                effectControllers.put(effect.toString(), controller);
            }
        }catch (FileNotFoundException e){
            logger.log(Level.SEVERE, "File not found");
        }catch (ClassNotFoundException e){
            logger.log(Level.SEVERE, "Class not found");
        }catch (NoSuchMethodException e){
            logger.log(Level.SEVERE, "Method not found");
        }catch (Exception e){
            logger.log(Level.SEVERE, e.toString());
        }
    }

     void acquirePower(Weapon weapon, Player attacker){
        this.attacker = attacker;
        HashMap<String, ArrayList<String>> powers = new HashMap<>();
        availableAmmo.clear();
        availablePowerUps.clear();

        for (PowerUp powerUp : attacker.getPowerup()){
            availablePowerUps.put(powerUp.getName(), powerUp.getColor());
        }
        availableAmmo.put("red", attacker.getAmmo()[0]);
        availableAmmo.put("blue", attacker.getAmmo()[1]);
        availableAmmo.put("yellow", attacker.getAmmo()[2]);
        powers.put(weapon.getPower().toString(), null);
        if (weapon instanceof Alternative){
            powers.put(((Alternative) weapon).getAlternativePower().toString(), new ArrayList<>(Arrays.asList(((Alternative) weapon).getExtraCost())));
            notify(new PowerChooseEv(true, powers, availableAmmo, availablePowerUps));
        }else if (weapon instanceof Additive){
            powers.put(((Additive) weapon).getAdditivePower().toString(), new ArrayList<>(Collections.singletonList(((Additive) weapon).getAdditiveCost())));
            notify(new PowerChooseEv(false, powers, availableAmmo, availablePowerUps));
        }else {
            powers.put(((DoubleAdditive) weapon).getFirstAdditivePower().toString(), new ArrayList<>(Collections.singletonList(((DoubleAdditive) weapon).getFirstExtraCost())));
            powers.put(((DoubleAdditive) weapon).getSecondAdditivePower().toString(), new ArrayList<>(Collections.singletonList(((DoubleAdditive) weapon).getSecondExtraCost())));
            notify(new PowerChooseEv(false, powers, availableAmmo, availablePowerUps));
        }
    }

    @Override
    public void update(PowerSetEv message) {
        try {
            if (weapon instanceof Alternative) {
                manageAlternative((Alternative) weapon, message);
            } else if (weapon instanceof Additive) {
                manageAdditive((Additive) weapon, message);
            } else if (weapon instanceof DoubleAdditive) {
                manageDoubleAdditive((DoubleAdditive) weapon, message);
            }
        }catch (UnpaidEffectCostException e){
            HashMap<String, String> availablePowerUps = new HashMap<>();
            notify(new UnpaidEffectEv(attacker.getNickname(), message.getPowers(), message.getUsedPowerUps(), remainingCosts, availablePowerUps, availableAmmo));
        }
    }

    private void manageAlternative(Alternative weapon, PowerSetEv message) throws UnpaidEffectCostException{
        HashMap<String, String> availablePowerUps = message.getUsedPowerUps();

        if (!weapon.getPower().toString().equals(message.getPowers().get(0))) {
            payEffectCost(new ArrayList<>(Arrays.asList(weapon.getExtraCost())), availablePowerUps);
        }
        effectControllers.get(message.getPowers().get(0)).useEffect(attacker, players, map);
    }

    private void manageAdditive(Additive weapon, PowerSetEv message) throws UnpaidEffectCostException{
        HashMap<String, String> availablePowerUps = message.getUsedPowerUps();

        if (message.getPowers().size() > 1){
            payEffectCost(new ArrayList<>(Collections.singletonList(weapon.getAdditiveCost())), availablePowerUps);
            effectControllers.get(weapon.getPower().toString()).useEffect(attacker, players, map);
            effectControllers.get(weapon.getAdditivePower().toString()).useEffect(attacker, players, map);
        }else {
            effectControllers.get(weapon.getPower().toString()).useEffect(attacker, players, map);
        }
    }

    private void manageDoubleAdditive(DoubleAdditive weapon, PowerSetEv message) throws UnpaidEffectCostException{
        HashMap<String, String> availablePowerUps = message.getUsedPowerUps();

        if ((weapon.getName().equals("Cyberblade") || (weapon.getName().equals("Rocket Launcher")) || (weapon.getName().equals("Plasma Gun")))){
            if (!weapon.getPower().toString().equals(message.getPowers().get(0))) {
                payEffectCost(new ArrayList<>(Collections.singletonList(weapon.getFirstExtraCost())), availablePowerUps);
            }
            effectControllers.get(message.getPowers().get(0)).useEffect(attacker, players, map);
            if (message.getPowers().size() > 1){
                if (weapon.getPower().toString().equals(message.getPowers().get(1))){
                    effectControllers.get(message.getPowers().get(1)).useEffect(attacker, players, map);
                }else {
                    payEffectCost(new ArrayList<>(Collections.singletonList(weapon.getFirstExtraCost())), availablePowerUps);
                    effectControllers.get(message.getPowers().get(1)).useEffect(attacker, players, map);
                }
                payEffectCost(new ArrayList<>(Collections.singletonList(weapon.getSecondExtraCost())), availablePowerUps);
                effectControllers.get(message.getPowers().get(2)).useEffect(attacker, players, map);
            }
        }else {
            effectControllers.get(message.getPowers().get(0)).useEffect(attacker, players, map);
            if (message.getPowers().size() == 2){
                payEffectCost(new ArrayList<>(Collections.singletonList(weapon.getFirstExtraCost())), availablePowerUps);
                effectControllers.get(message.getPowers().get(1)).useEffect(attacker, players, map);
            }else if (message.getPowers().size() == 3){
                payEffectCost(new ArrayList<>(Collections.singletonList(weapon.getFirstExtraCost())), new ArrayList<>(Collections.singletonList(weapon.getSecondExtraCost())), availablePowerUps);
                effectControllers.get(message.getPowers().get(1)).useEffect(attacker, players, map);
                effectControllers.get(message.getPowers().get(2)).useEffect(attacker, players, map);
            }
        }
    }

    private void payEffectCost(ArrayList<String> effectCost, HashMap<String, String> availablePowerUps) throws UnpaidEffectCostException {
        remainingCosts = (HashMap<String, Integer>) availableAmmo.clone();
    }

    private void payEffectCost(ArrayList<String> effectCostFirst, ArrayList<String> effectCostSecond, HashMap<String, String> availablePowerUps) throws UnpaidEffectCostException{

    }

    public HashMap<String, EffectController> getEffectControllers() {
        return effectControllers;
    }
}
