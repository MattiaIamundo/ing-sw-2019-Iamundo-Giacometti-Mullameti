package it.polimi.sw2019.utility;

import java.io.Serializable;

public class SimplifiedPowerUp implements Serializable {
    private String name;
    private String color;

    public SimplifiedPowerUp(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
