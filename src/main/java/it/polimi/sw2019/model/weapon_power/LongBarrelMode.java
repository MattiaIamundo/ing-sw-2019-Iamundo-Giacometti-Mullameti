package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the alternative effect of Shotgun
 * @author Mattia Iamundo
 */
public class LongBarrelMode implements Power{

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
                System.out.println(e.getMessage()+" isn't on a square at 1 move from you\n");
            }
        }
        target.getPlance().giveDamage(attacker, 2);
    }

    /**
     * This method acquire and verify the validity of the target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of the player's nickname
     * @throws IllegalPlayerException caused by selecting the attacker as the target
     * @throws UnreachablePlayerException caused by selecting a player that isn't 1 move away from the attacker
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;

        System.out.println("Indicates the nickname of a player that is exactly 1 move away from you");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (!isInValidPosition(attacker, Table.getPlayers(i))){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
        }
    }

    /**
     * This method check if the target is 1 move away from the attacker
     * @param attacker identify the attacker
     * @param target identify the currently selected target
     * @return true if the target is valid, false otherwise
     */
    private boolean isInValidPosition(Player attacker, Player target){
        if (attacker.getPosition().getNord().getSpaceSecond() == target.getPosition()){
            return true;
        }else if (attacker.getPosition().getOvest().getSpaceSecond() == target.getPosition()){
            return true;
        }else if (attacker.getPosition().getSud().getSpaceSecond() == target.getPosition()){
            return true;
        }else if (attacker.getPosition().getEst().getSpaceSecond() == target.getPosition()){
            return true;
        }else {
            return false;
        }
    }
}
