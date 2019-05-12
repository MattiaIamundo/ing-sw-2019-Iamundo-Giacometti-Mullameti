package it.polimi.sw2019.model;

import it.polimi.sw2019.view.AmmoView;

import java.io.Serializable;

/**Class Ammo:dedicated to the ammo cards
 * @author Merita Mullameti
 */
public abstract class Ammo extends AmmoView implements Cloneable, Serializable {

    private String colorFirst;
    private String colorSecond;
    /**Constructor of the class
     * @param colorFirst the color of the first ammo
     * @param colorSecond the color of the second ammo
     */
    public Ammo(String colorFirst, String colorSecond){
        this.colorFirst=colorFirst;
        this.colorSecond=colorSecond;
    }


    /**
     * @return the color of the first ammo
     */
    public String getColorFirst(){
        return colorFirst;
    }
    /**
     * @return the color of the second ammo
     */
    public String getColorSecond(){
        return colorSecond;
    }
}
