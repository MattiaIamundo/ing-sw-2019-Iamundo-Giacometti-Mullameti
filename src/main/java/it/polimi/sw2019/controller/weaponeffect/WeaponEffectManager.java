package it.polimi.sw2019.controller.weaponeffect;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.events.weaponEffectController_events.PowerSelectEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeaponEffectManager implements Observer<PowerSelectEv> {
    private ArrayList<Player> players;
    private Map map;
    private Player attacker;
    private Weapon weapon;
    private Power power;
    private String basicpower = "basic";
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
        ArrayList<String> powers = new ArrayList<>();
        if (weapon instanceof Alternative){
            powers.add(basicpower);
            powers.add(((Alternative) weapon).getAlternativePower().toString());
            weapon.choosePower(powers);
        }else if ((weapon instanceof DoubleAdditive) && (weapon.getName().equals("Plasma Gun") || weapon.getName().equals("Rocket Launcher") || weapon.getName().equals("Cyberblade"))){
            powers.add(basicpower);
            powers.add(((DoubleAdditive) weapon).getFirstAdditivePower().toString());
            weapon.choosePower(powers);
        }else {
            power = weapon.getPower();
            effectControllers.get(power.toString()).useEffect(attacker, players, map);
        }
    }

    @Override
    public void update(PowerSelectEv message) {
        if (message.getPower().equals(basicpower)){
            effectControllers.get(weapon.getPower().toString()).useEffect(attacker, players, map);
        }else if (weapon instanceof Alternative){
            power = ((Alternative) weapon).getAlternativePower();
            effectControllers.get(power.toString()).useEffect(attacker, players, map);
        }else {
            power = ((DoubleAdditive) weapon).getFirstAdditivePower();
            effectControllers.get(power.toString()).useEffect(attacker, players, map);
        }
    }

    public HashMap<String, EffectController> getEffectControllers() {
        return effectControllers;
    }
}
