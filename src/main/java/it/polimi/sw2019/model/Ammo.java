package it.polimi.sw2019.model;

import it.polimi.sw2019.view.AmmoView;

import java.io.Serializable;

/**Class Ammo:dedicated to the ammo cards
 * @author Merita Mullameti
 */
public abstract class Ammo extends AmmoView implements Cloneable, Serializable {

    private String imageName;
    private String colorFirst;
    private String colorSecond;
    /**Constructor of the class
     * @param colorFirst the color of the first ammo
     * @param colorSecond the color of the second ammo
     */
    public Ammo(String colorFirst, String colorSecond, String name){
        this.colorFirst=colorFirst;
        this.colorSecond=colorSecond;
        this.imageName = name;
    }


    /**
     * This method returns the color of the first ammo
     * @return the color of the first ammo
     */
    public String getColorFirst(){
        return colorFirst;
    }
    /**
     * This method returns the color of the second ammo
     * @return the color of the second ammo
     */
    public String getColorSecond(){
        return colorSecond;
    }

    /**
     * This method returns the name of the image of the ammo card
     * @return the name of the image of the ammo card
     */
    public String getImageName () {
        return imageName;
    }
}
