package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the basic effect of Zx-2
 * @author Mattia Iamundo
 */
public class DamageTwoMark implements Power{

    private Player target = null;

    @Override
    public void usePower(Player attacker){
        while (true){
            try {
                acquireTarget(attacker);
                break;
            }catch (IllegalPlayerException e){
                System.out.println("you can't select yourself as the target\n");
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+"isn't an existent player nicknames\n");
            }catch (UnreachablePlayerException e){
                System.out.println("You can't see"+e.getMessage()+", please select a player that you can see\n");
            }
        }
        target.getPlance().giveDamage(attacker, 1);
        target.getPlance().setMark(attacker);
        target.getPlance().setMark(attacker);
    }

    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException {
        Scanner scanner = new Scanner(System.in);
        String name;
        int i = 0;
        System.out.println("Insert the target's nickname, you must see the target\n");
        name = scanner.nextLine();
        while ((i < 5) && !Table.getPlayers(i).getNickname().equals(name)){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (!Table.getPlayers(i).isVisible(attacker)){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
        }
    }
}
