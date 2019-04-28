package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.model.Player;

import java.util.Scanner;

/**
 * This class implements the first optional effect of Machine Gun
 * @author Mattia Iamundo
 */
public class FocusShot implements Power{
    Player target = null; //choose one of the target of the basic effect
    private TwoTargetDamage basiceffect;

    @Override
    public void usePower(Player attacker){
        initialize(attacker);
        if (basiceffect.target2 == null){
            target = basiceffect.target1;
        }else {
            while (true) {
                try {
                    acquireTarget();
                    break;
                } catch (InvalidPlayerException e) {
                    System.out.println(e.getMessage() + " isn't one of the two possible target\n");
                }
            }
        }
        target.getPlance().giveDamage(attacker, 1);
    }

    /**
     * This method recover the target of the basic effect of the weapon
     * @param attacker identify the attacker
     */
    private void initialize(Player attacker){
        int i = 0;
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("MachineGun"))){
            i++;
        }
        basiceffect = (TwoTargetDamage) attacker.listWeapon()[i].getPower();
    }

    /**
     * This method acquired and check the validity of the target
     * @throws InvalidPlayerException caused by non selecting one of the two target of the basic effect
     */
    private void acquireTarget() throws InvalidPlayerException {
        Scanner scanner = new Scanner(System.in);
        String name;
        System.out.println("Choose your target: "+basiceffect.target1.getNickname()+" or "+basiceffect.target2.getNickname()+"\n");
        name = scanner.nextLine();
        if (basiceffect.target1.getNickname().equals(name)){
            target = basiceffect.target1;
        }else if (basiceffect.target2.getNickname().equals(name)){
            target = basiceffect.target2;
        }else {
            throw new InvalidPlayerException(name);
        }
    }
}
