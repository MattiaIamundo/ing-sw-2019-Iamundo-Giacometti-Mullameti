package it.polimi.sw2019.model;

import it.polimi.sw2019.Exception.InvalidPlayerException;
import it.polimi.sw2019.model.weapon_power.Power;
import java.util.Scanner;

/**
 * this class represents the weapon cards which have two power to be used
 * @author Luca Giacometti
 */
public class DoubleAdditive extends Weapon{

    private Power firstAdditivePower;
    private String firstExtraCost;
    private Power secondAdditivePower;
    private String secondExtraCost;
    private String descriptionFirstAdditivePower;
    private String descriptionSecondAdditivePower;
    private Scanner scanner = new Scanner(System.in);

    /**
     * this is the constructor
     * @param firstAdditivePower first optional attack
     * @param firstExtraCost the first attack's cost
     * @param secondAdditivePower second optional attack
     * @param secondExtraCost the second attack's cost
     * @param descriptionFirstAdditivePower the first's optional attack description
     * @param descriptionSecondAdditivePower the second's optional attack description
     */
    public DoubleAdditive (String name, Power power, String descriptionPower,
                            Power firstAdditivePower, String firstExtraCost, Power secondAdditivePower,
                           String secondExtraCost, String descriptionFirstAdditivePower,
                           String descriptionSecondAdditivePower){

        super(name, power, descriptionPower);
        this.descriptionFirstAdditivePower = descriptionFirstAdditivePower;
        this.descriptionSecondAdditivePower = descriptionSecondAdditivePower;
        this.firstAdditivePower = firstAdditivePower;
        this.firstExtraCost = firstExtraCost;
        this.secondAdditivePower = secondAdditivePower;
        this.secondExtraCost = secondExtraCost;
    }

    /**
     * @return the first additive power's cost
     */
    public String getFirstExtraCost(){
        return firstExtraCost;
    }

    /**
     * @return the second additive power's cost
     */
    public String getSecondExtraCost(){
        return secondExtraCost;
    }

    /**
     * printing the players list
     * scannering the player's name to damage
     * loop if it isn't present in the list of players
     * or the attacker cannot see him
     */
    public void attackFirstAdditive(){

        System.out.println("This is the list of the players: ");
        for (int i = 0; i < 5; i++){
            //printing the "list"
            if ( Table.getPlayers(i) != null) {
                System.out.println(Table.getPlayers(i).getNickname() + "\n");
            }
        }
        System.out.println("Insert a player to damage: ");

        try{
            findPlayerFirstAdditive(scanner.nextLine());
        }
        catch (InvalidPlayerException e){
            System.out.println(e.getMessage() + "is an invalid target...\n");
            attackFirstAdditive();
        }
    }

    /**
     *
     * @param player the input, it is probably the target's name
     * @throws InvalidPlayerException when the target is incorrect
     */
    public void findPlayerFirstAdditive (String player) throws InvalidPlayerException {
        boolean out = false;
        int i = 0;
        // finding if the name is present in the list of players
        for ( i = 0; i < 5 || out ; i++){

            if (Table.getPlayers(i).getNickname() == player){
                //control: can i see him?
                if (Table.getPlayers(i).isVisible(Turn.getPlayer()) == true) {

                    //first parameter is the player is the attacker
                    //second parameter is the player who will be damaged
                    firstAdditivePower.usePower(Turn.getPlayer(), Table.getPlayers(i));
                    out = true;
                }
            }
        }
        //if usePower was not activated throw the Exception
        if ( i == 5 && out == false ) throw new InvalidPlayerException(player);
    }

    /**
     * printing the players list
     * scannering the player's name to damage
     * loop if it isn't present in the list of players
     * or the attacker cannot see him
     */
    public void attackSecondAdditive(){

        System.out.println("This is the list of the players: ");
        for (int i = 0; i < 5; i++){
            //printing the "list"
            if ( Table.getPlayers(i) != null) {
                System.out.println(Table.getPlayers(i).getNickname() + "\n");
            }
        }
        System.out.println("Insert a player to damage: ");

        try{
            findPlayerSecondAdditive(scanner.nextLine());
        }
        catch (InvalidPlayerException e){
            System.out.println(e.getMessage() + "is an invalid target...\n");
            attackSecondAdditive();
        }
    }

    /**
     *
     * @param player the input, it is probably the target's name
     * @throws InvalidPlayerException when the target is incorrect
     */
    public void findPlayerSecondAdditive (String player) throws InvalidPlayerException {
        boolean out = false;
        int i = 0;
        // finding if the name is present in the list of players
        for ( i = 0; i < 5 || out ; i++){

            if (Table.getPlayers(i).getNickname() == player){
                //control: can i see him?
                if (Table.getPlayers(i).isVisible(Turn.getPlayer()) == true) {

                    //first parameter is the player is the attacker
                    //second parameter is the player who will be damaged
                    secondAdditivePower.usePower(Turn.getPlayer(), Table.getPlayers(i));
                    out = true;
                }
            }
        }
        //if usePower was not activated throw the Exception
        if ( i == 5 && out == false ) throw new InvalidPlayerException(player);
    }

    /**
     * @return the first additive power's description
     */
    public String getDescriptionFirstAdditivePower(){
        return descriptionFirstAdditivePower;
    }

    /**
     * @return the second additive power's description
     */
    public String getDescriptionSecondAdditivePower(){
        return descriptionSecondAdditivePower;
    }
}
