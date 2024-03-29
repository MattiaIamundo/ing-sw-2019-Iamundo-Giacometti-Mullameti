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
import it.polimi.sw2019.utility.SimplifiedPowerUp;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.io.*;
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
    private ArrayList<SimplifiedPowerUp> availablePowerUps = new ArrayList<>();
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
            InputStream in = getClass().getClassLoader().getResourceAsStream("it/polimi/sw2019/file_Json/weaponEffectController.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            effectFiles = gson.fromJson(reader, FILE_TYPE);

            for (EffectFile effct : effectFiles){
                Power effect = (Power) Class.forName(effct.power).getConstructor().newInstance();
                EffectController controller = (EffectController) Class.forName(effct.controller).getConstructor(Power.class).newInstance(effect);
                effectControllers.put(effect.toString(), controller);
            }
            reader.close();
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
        this.weapon = weapon;
        HashMap<String, ArrayList<String>> powers = new HashMap<>();
        availableAmmo.clear();
        availablePowerUps.clear();

        for (PowerUp powerUp : attacker.getPowerup()){
            availablePowerUps.add(new SimplifiedPowerUp(powerUp.getName(), powerUp.getColor()));
        }
        availableAmmo.put("red", attacker.getAmmo()[0]);
        availableAmmo.put("blue", attacker.getAmmo()[1]);
        availableAmmo.put("yellow", attacker.getAmmo()[2]);
        powers.put(weapon.getPower().toString(), null);
        if (weapon instanceof Alternative){
            powers.put(((Alternative) weapon).getAlternativePower().toString(), new ArrayList<>(Arrays.asList(((Alternative) weapon).getExtraCost())));
            notify(new PowerChooseEv(attacker.getNickname(),true, powers, availableAmmo, availablePowerUps));
        }else if (weapon instanceof Additive){
            powers.put(((Additive) weapon).getAdditivePower().toString(), new ArrayList<>(Collections.singletonList(((Additive) weapon).getAdditiveCost())));
            notify(new PowerChooseEv(attacker.getNickname(),false, powers, availableAmmo, availablePowerUps));
        }else {
            powers.put(((DoubleAdditive) weapon).getFirstAdditivePower().toString(), new ArrayList<>(Collections.singletonList(((DoubleAdditive) weapon).getFirstExtraCost())));
            powers.put(((DoubleAdditive) weapon).getSecondAdditivePower().toString(), new ArrayList<>(Collections.singletonList(((DoubleAdditive) weapon).getSecondExtraCost())));
            notify(new PowerChooseEv(attacker.getNickname(),false, powers, availableAmmo, availablePowerUps));
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
            notify(new UnpaidEffectEv(attacker.getNickname(), message.getPowers(), message.getUsedPowerUps(), remainingCosts, availablePowerUps, availableAmmo));
        }
    }

    private void manageAlternative(Alternative weapon, PowerSetEv message) throws UnpaidEffectCostException{

        if (!weapon.getPower().toString().equals(message.getPowers().get(0))) {
            payEffectCost(new ArrayList<>(Arrays.asList(weapon.getExtraCost())), message.getUsedPowerUps());
        }
        effectControllers.get(message.getPowers().get(0)).useEffect(attacker, players, map);
    }

    private void manageAdditive(Additive weapon, PowerSetEv message) throws UnpaidEffectCostException{

        if (message.getPowers().size() > 1){
            payEffectCost(new ArrayList<>(Collections.singletonList(weapon.getAdditiveCost())), message.getUsedPowerUps());
            effectControllers.get(weapon.getPower().toString()).useEffect(attacker, players, map);
            effectControllers.get(weapon.getAdditivePower().toString()).useEffect(attacker, players, map);
        }else {
            effectControllers.get(weapon.getPower().toString()).useEffect(attacker, players, map);
        }
    }

    private void manageDoubleAdditive(DoubleAdditive weapon, PowerSetEv message) throws UnpaidEffectCostException{

        if ((weapon.getName().equals("Cyberblade") || (weapon.getName().equals("Rocket Launcher")) || (weapon.getName().equals("Plasma Gun")))){
            if (message.getPowers().size() == 1){
                effectControllers.get(weapon.getPower().toString()).useEffect(attacker, players, map);
            }else if (message.getPowers().size() == 2){
                if (weapon.getFirstExtraCost() == null){
                    payEffectCost(null, message.getUsedPowerUps());
                }else {
                    payEffectCost(new ArrayList<>(Collections.singletonList(weapon.getFirstExtraCost())), message.getUsedPowerUps());
                }
                if (!weapon.getPower().toString().equals(message.getPowers().get(0))){
                    effectControllers.get(weapon.getFirstAdditivePower().toString()).useEffect(attacker, players, map);
                    effectControllers.get(weapon.getPower().toString()).useEffect(attacker, players, map);
                }else {
                    effectControllers.get(weapon.getPower().toString()).useEffect(attacker, players, map);
                    effectControllers.get(weapon.getFirstAdditivePower().toString()).useEffect(attacker, players, map);
                }
            }else if (message.getPowers().size() == 3){
                if (weapon.getFirstExtraCost() == null){
                    payEffectCost(new ArrayList<>(Collections.singletonList(weapon.getSecondExtraCost())), message.getUsedPowerUps());
                }else {
                    ArrayList<String> effectCostSecond = new ArrayList<>(Collections.singletonList(weapon.getSecondExtraCost()));
                    ArrayList<String> effectCost = new ArrayList<>(Collections.singletonList(weapon.getFirstExtraCost()));
                    effectCost.addAll(effectCostSecond);
                    payEffectCost(effectCost, message.getUsedPowerUps());
                }
                if (!weapon.getPower().toString().equals(message.getPowers().get(0))){
                    effectControllers.get(weapon.getFirstAdditivePower().toString()).useEffect(attacker, players, map);
                    effectControllers.get(weapon.getPower().toString()).useEffect(attacker, players, map);
                    effectControllers.get(weapon.getSecondAdditivePower().toString()).useEffect(attacker, players, map);
                }else {
                    effectControllers.get(weapon.getPower().toString()).useEffect(attacker, players, map);
                    effectControllers.get(weapon.getFirstAdditivePower().toString()).useEffect(attacker, players, map);
                    effectControllers.get(weapon.getSecondAdditivePower().toString()).useEffect(attacker, players, map);
                }
            }
        }else {
            effectControllers.get(message.getPowers().get(0)).useEffect(attacker, players, map);
            if (message.getPowers().size() == 2){
                if (weapon.getFirstExtraCost() == null){
                    payEffectCost(null, message.getUsedPowerUps());
                }else {
                    payEffectCost(new ArrayList<>(Collections.singletonList(weapon.getFirstExtraCost())), message.getUsedPowerUps());
                }
                effectControllers.get(message.getPowers().get(1)).useEffect(attacker, players, map);
            }else if (message.getPowers().size() == 3){
                if (weapon.getFirstExtraCost() == null){
                    payEffectCost(new ArrayList<>(Collections.singletonList(weapon.getSecondExtraCost())), message.getUsedPowerUps());
                }else {
                    ArrayList<String> effectCost = new ArrayList<>(Collections.singletonList(weapon.getFirstExtraCost()));
                    ArrayList<String> effectCostSecond = new ArrayList<>(Collections.singletonList(weapon.getSecondExtraCost()));
                    effectCost.addAll(effectCostSecond);
                    payEffectCost(effectCost, message.getUsedPowerUps());
                }
                effectControllers.get(message.getPowers().get(1)).useEffect(attacker, players, map);
                effectControllers.get(message.getPowers().get(2)).useEffect(attacker, players, map);
            }
        }
    }

    private void payEffectCost(ArrayList<String> effectCost, ArrayList<SimplifiedPowerUp> usedPowerUps) throws UnpaidEffectCostException {
        remainingCosts = (HashMap<String, Integer>) availableAmmo.clone();

        if (effectCost != null) {
            if (usedPowerUps != null) {
                for (SimplifiedPowerUp powerUp : usedPowerUps) {
                    effectCost.remove(powerUp.getColor());
                }
            }
            for (String cost : effectCost) {
                remainingCosts.replace(cost, remainingCosts.get(cost) - 1);
            }
        }
        if ((remainingCosts.get("blue") < 0) || (remainingCosts.get("red") < 0) || (remainingCosts.get("yellow") < 0)){
            throw new UnpaidEffectCostException();
        }
    }

    public HashMap<String, EffectController> getEffectControllers() {
        return effectControllers;
    }
}
