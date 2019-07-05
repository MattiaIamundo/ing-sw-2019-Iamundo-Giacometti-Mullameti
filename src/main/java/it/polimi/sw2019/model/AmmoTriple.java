package it.polimi.sw2019.model;
/**Class AmmoTriple :  dedicated to the ammo cards that provide the player with three ammo
 * @author Merita Mullameti
 */
public class AmmoTriple extends Ammo{

    private String colorThird;
    /**Constructor of the class
     * @param colorFirst the color of the first ammo
     * @param colorSecond the color of the second ammo
     * @param colorThird the color of the third ammo
     */
    public AmmoTriple(String colorFirst, String colorSecond , String colorThird, String name){
        super(colorFirst,colorSecond,name);
        this.colorThird = colorThird;
    }
    /**
     * This method returns the color of the third ammo
     * @return the color of the third ammo
     */
    public String getColorThird() {
        return colorThird;
    }

}
