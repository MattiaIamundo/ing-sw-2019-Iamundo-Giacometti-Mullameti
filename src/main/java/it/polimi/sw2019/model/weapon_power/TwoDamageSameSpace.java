package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the basic effect of Cyberblade and Sledgehammer
 * @author Mattia Iamundo
 */
public class TwoDamageSameSpace implements Power{

    Player target;

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
                System.out.println(e.getMessage()+" isn't on your square, so you can't select him\n");
            }
        }
        target.getPlance().giveDamage(attacker, 2);
    }

    /**
     * This method acquire and check the validity of the target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of the player's nickname
     * @throws IllegalPlayerException caused by selecting as target the attacker
     * @throws UnreachablePlayerException caused by selecting a player that isn't on the same square of the attacker
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException{
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the nickname of a player that is on your square");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (Table.getPlayers(i).getPosition() != attacker.getPosition()){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
        }
    }
}
