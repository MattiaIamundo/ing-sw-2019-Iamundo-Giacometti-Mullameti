package it.polimi.sw2019.GUI;

import javax.swing.*;
import java.awt.*;

public class WeaponTest {

    public static void main (String[]args){
        Weapon weaponFrame = new Weapon();
        weaponFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        weaponFrame.setSize(617,729);
        weaponFrame.setResizable(false);
        weaponFrame.setVisible(true);

    }
}