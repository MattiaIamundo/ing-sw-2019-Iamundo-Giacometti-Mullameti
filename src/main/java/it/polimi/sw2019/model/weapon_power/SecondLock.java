package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This method implements the optional effect of Lock Rifle
 * @author Mattia Iamundo
 */
public class SecondLock implements Power{

    private Player target;
    private TwoDamageMark basiceffect;

    @Override
    public void usePower(Player attacker){
        initialize(attacker);
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
                    System.out.println("You already select "+e.getMessage()+" as the target of the basic effect\n");
                }
            }catch (UnreachablePlayerException e){
                System.out.println("You can't see "+e.getMessage()+"\n");
            }
        }
        target.getPlance().setMark(attacker);
    }

    /**
     * This method recover the player selected as target of the basic effect of the weapon
     * @param attacker identify the attacker
     */
    private void initialize(Player attacker){
        int i = 0;
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("LockRifle"))){
            i++;
        }
        basiceffect = (TwoDamageMark) attacker.listWeapon()[i].getPower();
    }

    /**
     * This method acquire and check the validity of the target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of yhe player's nickname
     * @throws IllegalPlayerException caused by selecting as target the attacker, or the target of the basic effect
     * @throws UnreachablePlayerException caused by selecting a player that the attacker can't see
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException{
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the nickname of a player that you can see, but not the player selected for the basic effect");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (Table.getPlayers(i) == basiceffect.target){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        }else if (!Table.getPlayers(i).isVisible(attacker)){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
        }
    }
}
