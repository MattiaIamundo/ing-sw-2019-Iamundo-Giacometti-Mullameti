package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the first optional power for the T.H.O.R.
 * @author Mattia Iamundo
 */
public class ChainReaction implements Power{

    Player target;
    private TwoDamage basiceffect;

    @Override
    public void usePower(Player attacker){
        initializer(attacker);
        while (true){
            try {
                acquireTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+"isn't a player\n");
            }catch (UnreachablePlayerException e){
                System.out.println(e.getMessage()+"isn't visible by"+basiceffect.target.getNickname()+"\n");
            }catch (IllegalPlayerException e){
                if (e.getCode() == ErrorCode.ATTACKERSELECTED){
                    System.out.println("You can't select yourself as a target\n");
                }else if (e.getCode() == ErrorCode.NOTSELECTABLE){
                    System.out.println(e.getMessage()+"is already selected as the first target, please select another player\n");
                }
            }
        }
        target.getPlance().giveDamage(attacker, 1);
    }

    /**
     * This method acquire and verify the validity of the second target for T.H.O.R. weapon
     * @param attacker is the player who use the weapon
     * @throws InvalidPlayerException caused by a non-existent player or a missed insertion of a player name
     * @throws IllegalPlayerException caused by selecting a non valid player (e.g. the attacker or the weapon's first target)
     * @throws UnreachablePlayerException caused by selecting a player that isn't visible by the first one
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the name of the second target, it must be seen by"+basiceffect.target.getNickname());
        name = scanner.nextLine();
        if (name.isEmpty()){
            throw new InvalidPlayerException(name);
        }
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (basiceffect.target.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        }else if (!Table.getPlayers(i).isVisible(basiceffect.target)){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
        }
    }

    /**
     * This method recover the needed data from the weapon's basic effect
     * @param attacker identify the attacker
     */
    private void initializer(Player attacker){
        int i = 0;
        while ((i < 4) && (!attacker.listWeapon()[i].getName().equals("T.H.O.R."))){
            i++;
        }
        basiceffect = (TwoDamage) attacker.listWeapon()[i].getPower();
    }
}
