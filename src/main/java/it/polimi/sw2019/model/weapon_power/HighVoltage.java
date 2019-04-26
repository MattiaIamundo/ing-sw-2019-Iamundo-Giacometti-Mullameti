package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the second optional effect of T.H.O.R.
 * @author Mattia Iamundo
 */
public class HighVoltage implements Power{

    private Player target;
    private TwoDamage basiceffect;
    private ChainReaction optionaleffect;

    @Override
    public void usePower(Player attacker){
        initialize(attacker);
        while (true){
            try {
                acquireTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
            }catch (UnreachablePlayerException e){
                System.out.println(e.getMessage()+" isn't visible by your second target\n");
            }catch (IllegalPlayerException e){
                if (e.getCode() == ErrorCode.ATTACKERSELECTED){
                    System.out.println("You can't select yourself as the target\n");
                }else {
                    if (basiceffect.target.getNickname().equals(e.getMessage())){
                        System.out.println(e.getMessage()+" is already selected as yor first target, you can't select him again\n");
                    }else {
                        System.out.println(e.getMessage()+" is already selected as your second target, you can't select him again\n");
                    }
                }
            }
        }
        target.getPlance().giveDamage(attacker, 2);
    }

    /**
     * This method initialize the info about the previous targets
     * @param attacker identify the attacker
     */
    private void initialize(Player attacker){
        DoubleAdditive weapon;
        int i = 0;
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("T.H.O.R."))){
            i++;
        }
        weapon = (DoubleAdditive) attacker.listWeapon()[i];
        basiceffect = (TwoDamage) weapon.getPower();
        optionaleffect = (ChainReaction) weapon.getFirstAdditivePower();
    }

    /**
     * This method acquire and check the validity of the target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of player's nicknames
     * @throws IllegalPlayerException caused by selecting as target the attacker or the target of the basic/first effect of the power
     * @throws UnreachablePlayerException caused by selecting a player that the target of the first optional effect can't see
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException {
        Scanner scanner = new Scanner(System.in);
        String name;
        int i = 0;
        System.out.println("Indicates the nickname of a player that your second target ("+optionaleffect.target.getNickname()+") can see");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if ((basiceffect.target.getNickname().equals(name)) || (optionaleffect.target.getNickname().equals(name))){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        }else if (!Table.getPlayers(i).isVisible(optionaleffect.target)){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
        }
    }
}
