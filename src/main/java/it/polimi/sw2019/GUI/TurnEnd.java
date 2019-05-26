package it.polimi.sw2019.GUI;

import it.polimi.sw2019.model.Player;
import javax.swing.*;
import java.awt.*;


public class TurnEnd {
    static JFrame frame;


    public static void main(String[] args) {
        String color ;
        color=Player.getColor();
        String actionIcon=new String();
        String reloadIcon=new String();

        switch(color){
            case ("Yellow"):{
                actionIcon="res/yellowMenu1.png";
                reloadIcon="res/yellowMenu2.png";

            }
            break;

            case ("Blue"):{
                actionIcon="res/actionIconBLue.png";
                reloadIcon="res/reloadIconBlue.png";

            }
            break;

            case ("Black"):{
                /*actionIcon="res/greenMenu1.png";
                reloadIcon="res/greenMenu2.png";*/
            }
            break;

            case ("Green"):{
                actionIcon="res/greenReload1.png";
                reloadIcon="res/greenReload2.png";

            }
            break;

            case ("Purple"):{
                actionIcon="res/actionIconPurple.png";
                reloadIcon="res/reloadIconPurple.png";

            }
            break;
        }
        TurnEnd.actionWindow(actionIcon , reloadIcon );
    }



    private static void actionWindow(String action, String reload ) {
        frame = new JFrame("It's your turn !");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(395,258);


        Container pane = frame.getContentPane();
        JButton button1 =new JButton();
        ImageIcon actionIcon = new ImageIcon(new ImageIcon(action).getImage().getScaledInstance(381, 110, Image.SCALE_DEFAULT));

        button1.setPreferredSize(new Dimension(381, 110));
        pane.add(button1, BorderLayout.NORTH);
        //button1.setBorder(BorderFactory.createEmptyBorder());
        button1.setIcon(actionIcon);

        JButton button2 =new JButton();
        ImageIcon reloadIcon = new ImageIcon(new ImageIcon(reload).getImage().getScaledInstance(381, 110, Image.SCALE_DEFAULT));
        button2.setPreferredSize(new Dimension(381, 110));
        pane.add(button2, BorderLayout.SOUTH);
        //button2.setBorder(BorderFactory.createEmptyBorder());
        button2.setIcon(reloadIcon);


        frame.setVisible(true);
        frame.setResizable(true);
    }
}


