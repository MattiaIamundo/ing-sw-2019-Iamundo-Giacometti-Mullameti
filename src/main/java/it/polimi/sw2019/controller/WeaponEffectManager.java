package it.polimi.sw2019.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.sw2019.controller.weaponeffect.EffectController;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.events.weaponeffect_controller_events.PowerSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;
import sun.security.jca.GetInstance;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeaponEffectManager implements Observer<PowerSetEv> {
    private ArrayList<Player> players;
    private Map map;
    private Player attacker;
    private Weapon weapon;
    private HashMap<String, EffectController> effectControllers = new HashMap<>();

    public WeaponEffectManager(ArrayList<Player> players, Map map) {
        this.players = players;
        this.map = map;
        initEffectCont();
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

    public void acquirePower(Weapon weapon, Player attacker){
        this.attacker = attacker;
        HashMap<String, String> availablePowerUps = new HashMap<>();
        HashMap<String, ArrayList<String>> powers = new HashMap<>();
        HashMap<String, Integer> availableAmmo = new HashMap<>();

        for (PowerUp powerUp : attacker.getPowerup()){
            availablePowerUps.put(powerUp.getName(), powerUp.getColor());
        }
        availableAmmo.put("red", attacker.getAmmo()[0]);
        availableAmmo.put("blue", attacker.getAmmo()[1]);
        availableAmmo.put("yellow", attacker.getAmmo()[2]);
        powers.put(weapon.getPower().toString(), null);
        if (weapon instanceof Alternative){
            powers.put(((Alternative) weapon).getAlternativePower().toString(), new ArrayList<>(Arrays.asList(((Alternative) weapon).getExtraCost())));
            weapon.choosePower(true, powers, availableAmmo, availablePowerUps);
        }else if (weapon instanceof Additive){
            powers.put(((Additive) weapon).getAdditivePower().toString(), new ArrayList<>(Collections.singletonList(((Additive) weapon).getAdditiveCost())));
            weapon.choosePower(false, powers, availableAmmo, availablePowerUps);
        }else {
            powers.put(((DoubleAdditive) weapon).getFirstAdditivePower().toString(), new ArrayList<>(Collections.singletonList(((DoubleAdditive) weapon).getFirstExtraCost())));
            powers.put(((DoubleAdditive) weapon).getSecondAdditivePower().toString(), new ArrayList<>(Collections.singletonList(((DoubleAdditive) weapon).getSecondExtraCost())));
            weapon.choosePower(false, powers, availableAmmo, availablePowerUps);
        }
    }

    @Override
    public void update(PowerSetEv message) {
        ArrayList<String> availablePowerUps = message.getUsedPowerUps();

        if (weapon instanceof Alternative){
            if (!weapon.getPower().toString().equals(message.getPowers().get(0))) {
                payEffectCost(new ArrayList<>(Arrays.asList(((Alternative) weapon).getExtraCost())), availablePowerUps);
            }
            effectControllers.get(message.getPowers().get(0)).useEffect(attacker, players, map);
        }else if (weapon instanceof Additive){
            if (message.getPowers().get(0).equals(weapon.getPower().toString())){
                effectControllers.get(message.getPowers().get(0)).useEffect(attacker, players, map);
                if (message.getPowers().size() > 1){
                    payEffectCost(new ArrayList<>(Collections.singletonList(((Additive) weapon).getAdditiveCost())), availablePowerUps);
                    effectControllers.get(message.getPowers().get(1)).useEffect(attacker, players, map);
                }
            }else {
                effectControllers.get(message.getPowers().get(1)).useEffect(attacker, players, map);
                if (message.getPowers().size() > 1){
                    payEffectCost(new ArrayList<>(Collections.singletonList(((Additive) weapon).getAdditiveCost())), availablePowerUps);
                    effectControllers.get(message.getPowers().get(0)).useEffect(attacker, players, map);
                }
            }
        }else if ((weapon instanceof DoubleAdditive) && (weapon.getName().equals("Cyberblade") || weapon.getName().equals("Rocket Launcher") || weapon.getName().equals("Plasma Gun"))){
            if (!weapon.getPower().toString().equals(message.getPowers().get(0))) {
                payEffectCost(new ArrayList<>(Collections.singletonList(((DoubleAdditive) weapon).getFirstExtraCost())), availablePowerUps);
            }
            effectControllers.get(message.getPowers().get(0)).useEffect(attacker, players, map);
            if (message.getPowers().size() > 1){
                if (weapon.getPower().toString().equals(message.getPowers().get(1))){
                    effectControllers.get(message.getPowers().get(1)).useEffect(attacker, players, map);
                }else {
                    payEffectCost(new ArrayList<>(Collections.singletonList(((DoubleAdditive) weapon).getFirstExtraCost())), availablePowerUps);
                    effectControllers.get(message.getPowers().get(1)).useEffect(attacker, players, map);
                }
                payEffectCost(new ArrayList<>(Collections.singletonList(((DoubleAdditive) weapon).getSecondExtraCost())), availablePowerUps);
                effectControllers.get(message.getPowers().get(2)).useEffect(attacker, players, map);
            }
        }else {
            effectControllers.get(message.getPowers().get(0)).useEffect(attacker, players, map);
            if (message.getPowers().size() == 1){
                payEffectCost(new ArrayList<>(Collections.singletonList(((DoubleAdditive) weapon).getFirstExtraCost())), availablePowerUps);
                effectControllers.get(message.getPowers().get(1)).useEffect(attacker, players, map);
            }
            if (message.getPowers().size() == 3){
                payEffectCost(new ArrayList<>(Collections.singletonList(((DoubleAdditive) weapon).getFirstExtraCost())), availablePowerUps);
                payEffectCost(new ArrayList<>(Collections.singletonList(((DoubleAdditive) weapon).getSecondExtraCost())), availablePowerUps);
                effectControllers.get(message.getPowers().get(1)).useEffect(attacker, players, map);
                effectControllers.get(message.getPowers().get(2)).useEffect(attacker, players, map);
            }
        }
    }

    private void payEffectCost(ArrayList<String> effectCost, ArrayList<String> availablePowerUps){
        for (String cost : effectCost){
            switch (cost){
                case "red":
                    if (attacker.getAmmo()[0] > 1){
                        attacker.getAmmo()[0]--;
                    }else {
                        for (String powerCost : availablePowerUps){
                            for (PowerUp powerUp : attacker.getPowerup()){
                                if ((powerCost.equals(powerUp.getName())) && (powerUp.getColor().equals("red"))){
                                    attacker.removePowerUp(powerUp);
                                }
                            }
                        }
                    }
                    break;
                case "blue":
                    if (attacker.getAmmo()[1] > 1){
                        attacker.getAmmo()[1]--;
                    }else {
                        for (String powerCost : availablePowerUps){
                            for (PowerUp powerUp : attacker.getPowerup()){
                                if ((powerCost.equals(powerUp.getName())) && (powerUp.getColor().equals("blue"))){
                                    attacker.removePowerUp(powerUp);
                                }
                            }
                        }
                    }
                    break;
                case "yellow":
                    if (attacker.getAmmo()[2] > 1){
                        attacker.getAmmo()[2]--;
                    }else {
                        for (String powerCost : availablePowerUps){
                            for (PowerUp powerUp : attacker.getPowerup()){
                                if ((powerCost.equals(powerUp.getName())) && (powerUp.getColor().equals("yellow"))){
                                    attacker.removePowerUp(powerUp);
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public HashMap<String, EffectController> getEffectControllers() {
        return effectControllers;
    }
}
