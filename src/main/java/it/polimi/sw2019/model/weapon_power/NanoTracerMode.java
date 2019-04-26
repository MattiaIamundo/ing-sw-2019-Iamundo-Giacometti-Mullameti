package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the alternative effect of Hellion
 * @author Mattia Iamundo
 */
public class NanoTracerMode implements Power{

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
                if (e.getCode() == ErrorCode.ATTACKERSELECTED){
                    System.out.println("You can't select yourself as the target\n");
                }else {
                    System.out.println(e.getMessage()+" is on your square, you can't select him as a valid target\n");
                }
            }catch (UnreachablePlayerException e){
                System.out.println("You can't see "+e.getMessage()+", select a player that you can see\n");
            }
        }
        target.getPlance().giveDamage(attacker, 1);
        for (int i = 0; i < 5; i++) {
            if (target.getPosition() == Table.getPlayers(i).getPosition()){
                Table.getPlayers(i).getPlance().setMark(attacker);
                Table.getPlayers(i).getPlance().setMark(attacker);
            }
        }
    }

    /**
     * This method acquire and verify the validity of the target
     * @param attacker identify the attacker
     * @throws IllegalPlayerException caused by select as target the attacker ot a player on the same square of the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of player's nickname
     * @throws UnreachablePlayerException caused by selecting a player that isn't visible to the attacker
     */
    private void acquireTarget(Player attacker) throws IllegalPlayerException, InvalidPlayerException, UnreachablePlayerException {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the nickname of a player that you can see and it's at least 1 move away from you");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (attacker.getPosition() == Table.getPlayers(i).getPosition()){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        }else if (!Table.getPlayers(i).isVisible(attacker)){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
        }
    }
}
