package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class implements the basic effect of Heatseeker
 * @author Mattia Iamundo
 */
public class Heatseeker implements Power{

    private Player target;

    @Override
    public void usePower(Player attacker){
        while (true){
            try {
                acquireTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't a player's nickname\n");
            }catch (IllegalPlayerException e){
                if (e.getCode() == ErrorCode.ATTACKERSELECTED){
                    System.out.println("You can't select yourself as a target\n");
                }else {
                    System.out.println("you must select a player that you can't see, "+e.getMessage()+" is in your field of view\n");
                }
            }
        }
        target.getPlance().giveDamage(attacker, 3);
    }

    /**
     * This method acquire the target and check his validity
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the player's name
     * @throws IllegalPlayerException caused by selecting a player that the attacker can see or the attacker himself
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String[] targets;
        String name;
        targets = identifyTarget(attacker);
        System.out.println("Select a player that you can't see, one of this:\n"+ Arrays.toString(targets));
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (!Arrays.asList(targets).contains(name)){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        }
        target = Table.getPlayers(i);
    }

    /**
     * This method identify the player that the attacker can't see
     * @param attacker identify the attacker
     * @return the list of the valid player that can be selected as the target
     */
    private String[] identifyTarget(Player attacker){
        ArrayList<String> targets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (!Table.getPlayers(i).isVisible(attacker)){
                targets.add(Table.getPlayers(i).getNickname());
            }
        }
        return targets.toArray(new String[0]);
    }
}
