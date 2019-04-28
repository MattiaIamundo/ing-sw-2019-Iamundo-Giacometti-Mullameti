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
 * This class implements the second optional effect of Machine Gun
 * @author Mattia Iamundo
 */
public class TurretTripod implements Power{

    private Player target = null;
    private TwoTargetDamage basiceffect;
    private FocusShot optionaleffect;

    @Override
    public void usePower(Player attacker){
        Scanner scanner = new Scanner(System.in);
        String answer;
        initializer(attacker);
        if ((basiceffect.target2 == null) && (optionaleffect.target != null)){
            return;
        }else if ((basiceffect.target2 == null) && (optionaleffect.target == null)){
            target = basiceffect.target1;
            target.getPlance().giveDamage(attacker, 1);
        }else {
            System.out.println("Do you want to deal 1 extra damage to one of your previous target? [yes, no]");
            answer = scanner.nextLine().toLowerCase();
            if (answer.equals("yes")) {
                if (optionaleffect.target == null) {
                    while (true) {
                        System.out.println("Which target would you want to hit: " + basiceffect.target1.getNickname() + " or " + basiceffect.target2.getNickname() + "?");
                        answer = scanner.nextLine();
                        if (answer.equals(basiceffect.target1.getNickname())) {
                            target = basiceffect.target1;
                            break;
                        } else if (answer.equals(basiceffect.target2.getNickname())) {
                            target = basiceffect.target2;
                            break;
                        } else {
                            System.out.println(answer + " isn't one of your previous target\n");
                        }
                    }
                    target.getPlance().giveDamage(attacker, 1);
                } else {
                    if (optionaleffect.target == basiceffect.target1) {
                        target = basiceffect.target2;
                    } else {
                        target = basiceffect.target1;
                    }
                    target.getPlance().giveDamage(attacker, 1);
                }
            }
        }
        if (isThirdAviable(attacker)) {
            System.out.println("Would you want to hit another target? [yes, no]");
            answer = scanner.nextLine().toLowerCase();
            if (answer.equals("yes")){
                while (true){
                    try {
                        acquireTarget(attacker);
                        break;
                    }catch (InvalidPlayerException e){
                        System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
                    }catch (IllegalPlayerException e){
                        if (e.getCode() == ErrorCode.ATTACKERSELECTED){
                            System.out.println("You can't select yourself as the target\n");
                        }else if (basiceffect.target1.getNickname().equals(e.getMessage())){
                            System.out.println("You can't select "+e.getMessage()+", he is your first target\n");
                        }else {
                            System.out.println("You can't select "+e.getMessage()+", he is your second target\n");
                        }
                    }catch (UnreachablePlayerException e){
                        System.out.println("You can't select "+e.getMessage()+", you can't see him\n");
                    }
                }
                target.getPlance().giveDamage(attacker, 1);
            }
        }
        basiceffect.target1 = null;
        basiceffect.target2 = null;
        optionaleffect.target = null;
    }

    /**
     * This method recover the needed information about the basic and the first optional effects
     * @param attacker identify the attacker
     */
    private void initializer(Player attacker){
        DoubleAdditive weapon;
        int i = 0;
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("Machine Gun"))){
            i++;
        }
        weapon = (DoubleAdditive) attacker.listWeapon()[i];
        basiceffect = (TwoTargetDamage) weapon.getPower();
        optionaleffect = (FocusShot) weapon.getFirstAdditivePower();
    }

    /**
     * This method acquire and check the validity of the target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of the player's nickname
     * @throws IllegalPlayerException caused by selecting as target the attacker, or the first/second target of the basic effect of the weapon
     * @throws UnreachablePlayerException caused by selecting a player that the attacker can't see
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException{
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the nickname of a player that you can see, he can't be your first or second target");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if ((Table.getPlayers(i) == basiceffect.target1) || (Table.getPlayers(i) == basiceffect.target2)){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        }else if (!Table.getPlayers(i).isVisible(attacker)){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
        }
    }

    /**
     * This method check if the attacker see a player that could be select as a valid third target
     * @param attacker identify the attacker
     * @return true if the attacker see a player that isn't his first or second target, false otherwise
     */
    private boolean isThirdAviable(Player attacker){
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != attacker) && (Table.getPlayers(i) != basiceffect.target1) && (Table.getPlayers(i) != basiceffect.target2) && (Table.getPlayers(i).isVisible(attacker))){
                return true;
            }
        }
        return false;
    }
}
