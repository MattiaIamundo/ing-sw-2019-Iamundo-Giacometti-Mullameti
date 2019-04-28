package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class implements the basic effect of Power Glove
 * @author Mattia Iamundo
 */
public class PowerGlove implements Power{
    private Player target = null;

    @Override
    public void usePower(Player attacker){
        while (true){
            try {
                acquireTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
            }catch (IllegalPlayerException e){
                System.out.println("You can't select yourself as the target\n");
            }catch (UnreachablePlayerException e){
                System.out.println(e.getMessage()+" isn't 1 move away from you\n");
            }
        }
        attacker.setPosition(target.getPosition());
        target.getPlance().giveDamage(attacker, 1);
        target.getPlance().setMark(attacker);
        target.getPlance().setMark(attacker);
    }

    /**
     * This method acquire and check the validity of the target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of the player's nickname
     * @throws IllegalPlayerException caused by selecting the attacker as the target
     * @throws UnreachablePlayerException caused by selecting a player that isn't exactly 1 move away from the attacker
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String[] validtargets = targetsList(attacker);
        String name;
        System.out.println("Insert the nickname of a player that is exactly 1 move away from you, the available players are:\n"+ Arrays.toString(validtargets));
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (!Arrays.asList(validtargets).contains(name)){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
        }
    }

    /**
     * This method search the players that are exactly 1 move away from the attacker
     * @param attacker identify the attacker
     * @return the list of the players that can be selected as target
     */
    private String[] targetsList(Player attacker){
        ArrayList<String> targets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (!attacker.getPosition().getNorth().isWall() && Table.getPlayers(i).getPosition() == attacker.getPosition().getNorth().getSpaceSecond()){
                targets.add(Table.getPlayers(i).getNickname());
            }else if (!attacker.getPosition().getWest().isWall() && Table.getPlayers(i).getPosition() == attacker.getPosition().getWest().getSpaceSecond()){
                targets.add(Table.getPlayers(i).getNickname());
            }else if (!attacker.getPosition().getSouth().isWall() && Table.getPlayers(i).getPosition() == attacker.getPosition().getSouth().getSpaceSecond()){
                targets.add(Table.getPlayers(i).getNickname());
            }else if (!attacker.getPosition().getEast().isWall() && Table.getPlayers(i).getPosition() == attacker.getPosition().getEast().getSpaceSecond()){
                targets.add(Table.getPlayers(i).getNickname());
            }
        }
        return targets.toArray(new String[0]);
    }
}
