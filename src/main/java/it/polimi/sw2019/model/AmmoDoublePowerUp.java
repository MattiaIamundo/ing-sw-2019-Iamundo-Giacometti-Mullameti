package it.polimi.sw2019.model;
/**Class AmmoDoublePowerUp : dedicated to the ammo cards that provide the player with two ammo and one power up card
 * @author Merita Mullameti
 */
public class AmmoDoublePowerUp extends Ammo{
    private PowerUp powerup; //the power up included in the ammo card
    /**Constructor of the class
     * @param colorFirst the color of the first ammo
     * @param colorSecond the color of the second ammo
     * @param powerup the power up included in the ammo card
     */
    public AmmoDoublePowerUp(String colorFirst, String colorSecond,PowerUp powerup, String name){
        super(colorFirst,colorSecond, name);
        this.powerup=powerup;
    }
    /**
     * This method returns the power up included in the ammo card
     * @return the power up included in the ammo card
     */
    public PowerUp getPowerUp(){
        return powerup;
    }

    /**
     * This method sets the power up included in the ammo card
     * @param pw the power up included in the ammo card
     */
    public void setPowerup(PowerUp pw) {
        this.powerup = pw;
    }
}
