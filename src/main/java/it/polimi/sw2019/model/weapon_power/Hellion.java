package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the basic effect of Hellion
 * @author Mattia Iamundo
 */
public class Hellion implements Power{
    //basic effect for Hellion
    private Player target;
    private Space targetarea;

    @Override
    public void usePower(Player attacker){
        while (true){
            try {
                acquireTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't a player's nickname\n");
            }catch (UnreachablePlayerException e){
                System.out.println(e.getMessage()+" isn't in your filed of view, please select another player\n");
            }catch (IllegalPlayerException e){
                if (e.getCode() == ErrorCode.ATTACKERSELECTED){
                    System.out.println("You can't select yourself as the target\n");
                }else {
                    System.out.println(e.getMessage()+" is on your square, the target must be at least 1 move away from you\n");
                }
            }
        }
        target.getPlance().giveDamage(attacker, 1);
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition() == targetarea){
                Table.getPlayers(i).getPlance().setMark(attacker);
            }
        }
    }

    /**
     * This method acquire and verify the target's nickname
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the player's nickname
     * @throws IllegalPlayerException caused by selecting the attacker as the target or selecting a player that is on the player's square
     * @throws UnreachablePlayerException caused by selecting a player that the attacker can't see
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException {
        Scanner scanner = new Scanner(System.in);
        String name;
        int i = 0;
        System.out.println("Insert the nickname of a player you can see at least 1 move away");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (!Table.getPlayers(i).isVisible(attacker)){
            throw new UnreachablePlayerException(name);
        }else if (attacker.getPosition() == Table.getPlayers(i).getPosition()){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        }else {
            target = Table.getPlayers(i);
            targetarea = target.getPosition();
        }
    }
}
