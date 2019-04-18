package it.polimi.sw2019.model;

import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.model.weapon_power.Power;

import java.util.Scanner;

/**Class Alternative: describes the alternative effect of the weapon that is used instead of the basic one
 * @author Merita Mullameti
 */
public class Alternative extends Weapon{

    //the effect of the weapon that is used instead of the basic one
    private Power alternativePower;
    //the ammo that is needed to pay to be able to use this effect
    private String extraCost;
    //a description of the effect the weapon can do in alternative to the basic effect
    private String descriptionAlternativePower;
    private Scanner scanner = new Scanner(System.in);

    /**Constructor of the class
     * @param alternativePower the second power which can be used instead of the main one
     * @param extraCost the alternative power's cost
     * @param descriptionAlternativePower the alternative power's description
     */
    public Alternative(String name, Power power, String descriptionPower,
                       Power alternativePower , String extraCost , String descriptionAlternativePower){

        super(name, power, descriptionPower);
        this.alternativePower = alternativePower;
        this.extraCost = extraCost;
        this.descriptionAlternativePower = descriptionAlternativePower;
    }
    /**
     * @return the extra cost to pay for the player to use the alternative
     */
    public String getExtraCost(){
        return extraCost;
    }

    public void attackAlternative(){
        System.out.println("This is the list of the players: ");
        for (int i = 0; i < 5; i++){
            //printing the "list"
            if ( Table.getPlayers(i) != null) {
                System.out.println(Table.getPlayers(i).getNickname() + "\n");
            }
        }
        System.out.println("Insert a player to damage: ");

        try{
            findPlayerAlternative(scanner.nextLine());
        }
        catch (InvalidPlayerException e){
            System.out.println(e.getMessage() + "is an invalid target...\n");
            attackAlternative();
        }
    }

    /**
     *
     * @param player the target's name
     * @throws InvalidPlayerException thrown if insert an invalid player
     */
    public void findPlayerAlternative(String player) throws InvalidPlayerException{
        boolean out = false;
        int i = 0;
        // finding if the name is present in the list of players
        for ( i = 0; i < 5 || out ; i++){

            if (Table.getPlayers(i).getNickname() == player){
                //control: can i see him?
                if (Table.getPlayers(i).isVisible(Turn.getPlayer()) == true) {

                    //first parameter is the player is the attacker
                    //second parameter is the player who will be damaged
                    alternativePower.usePower(Turn.getPlayer(), Table.getPlayers(i));
                    out = true;
                }
            }
        }
        //if usePower was not activated throw the exception
        if ( i == 5 && out == false ) throw new InvalidPlayerException(player);
    }
    /**
     * @return a description of the effect the weapon can do in alternative to the basic effect
     */
    public String getDescriptionAlternativePower(){
        return descriptionAlternativePower;
    }
}
