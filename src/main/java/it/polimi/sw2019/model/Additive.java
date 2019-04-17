package it.polimi.sw2019.model;

import it.polimi.sw2019.Exception.InvalidPlayerException;
import it.polimi.sw2019.model.weapon_power.Power;

import java.util.Scanner;

/**Class Additive : describes the effects some weapons  can do in addition to the basic effect.
 * @author Merita Mullameti
 */
public class Additive extends Weapon{

    //the effect the weapon can do in addition to the basic effect
    private Power additivePower;
    //the ammo that is needed to pay to be able to use this effect
    private String additiveCost;
    //describes the effect the weapon can do in addition to the basic effect
    private String descriptionAdditivePower;
    private Scanner scanner = new Scanner(System.in);

    /**Constructor of the class
     * @param additivePower the second power which can be used with the main one
     * @param additiveCost the additive power's cost
     * @param descriptionAdditivePower the additive power's description
     */
    public Additive (String name, Power power, String descriptionPower,
                     Power additivePower , String additiveCost , String descriptionAdditivePower ){

        super(name, power, descriptionPower);
        this.additivePower = additivePower;
        this.additiveCost = additiveCost;
        this.descriptionAdditivePower = descriptionAdditivePower;
    }
    /**
     * @return the extra cost needed to activate this part of the weapon
     */
    public String getExtraCost(){
        return additiveCost;
    }

    public void attackAdditive (){

        System.out.println("This is the list of the players: ");
        for (int i = 0; i < 5; i++){
            //printing the "list"
            if ( Table.getPlayers(i) != null) {
                System.out.println(Table.getPlayers(i).getNickname() + "\n");
            }
        }
        System.out.println("Insert a player to damage: ");

        try{
            findPlayerAdditive(scanner.nextLine());
        }
        catch (InvalidPlayerException e){
            System.out.println(e.getMessage() + "is an invalid target...\n");
            attackAdditive();
        }
    }

    /**
     *
     * @param player the player which will be damaged if his name is correct
     * @throws InvalidPlayerException when the target is incorrect
     */
    public void findPlayerAdditive(String player) throws InvalidPlayerException{

        boolean out = false;
        int i = 0;
        // finding if the name is present in the list of players
        for ( i = 0; i < 5 || out ; i++){

            if (Table.getPlayers(i).getNickname() == player){
                //control: can i see him?
                if (Table.getPlayers(i).isVisible(Turn.getPlayer()) == true) {

                    //first parameter is the player is the attacker
                    //second parameter is the player who will be damaged
                    additivePower.usePower(Turn.getPlayer(), Table.getPlayers(i));
                    out = true;
                }
            }
        }
        //if usePower was not activated throw the Exception
        if ( i == 5 && out == false ) throw new InvalidPlayerException(player);
    }
    /**
     * @return a description of the effect the weapon can do in addition to the basic effect
     */
    public String getDescriptionAdditivePower(){
        return descriptionAdditivePower;
    }
}
