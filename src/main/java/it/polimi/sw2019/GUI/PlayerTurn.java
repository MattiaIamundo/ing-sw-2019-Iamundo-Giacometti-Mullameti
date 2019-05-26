package it.polimi.sw2019.GUI;

import javax.swing.*;
import java.awt.*;
import it.polimi.sw2019.model.Player;

public class PlayerTurn {
    static JFrame frame;


    public static void main(String[] args) {
        String color ;
        color=Player.getColor();
        String moveIcon=new String();
        String grabIcon=new String();
        String shootIcon=new String();
        switch(color){
            case ("Yellow"):{
                moveIcon="Images/yellowMenu1.png";
                grabIcon="Images/yellowMenu2.png";
                shootIcon="Images/yellowMenu3.png";
            }
            break;

            case ("Blue"):{
                moveIcon="Images/moveIconBLue.png";
                grabIcon="Images/grabIconBlue.png";
                shootIcon="Images/shootIconBlue.png";
            }
            break;

            case ("Black"):{
                /*moveIcon="Images/greenMenu1.png";
                grabIcon="Images/greenMenu2.png";
                shootIcon="Images/greenMenu3.png";*/
            }
            break;

            case ("Green"):{
                moveIcon="Images/greenMenu1.png";
                grabIcon="Images/greenMenu2.png";
                shootIcon="Images/greenMenu3.png";
            }
            break;

            case ("Purple"):{
                moveIcon="Images/moveIconPurple.png";
                grabIcon="Images/grabIconPurple.png";
                shootIcon="Images/shootIconPurple.png";
            }
            break;
        }
        PlayerTurn.actionWindow(moveIcon , grabIcon , shootIcon);
    }



    private static void actionWindow(String move, String grab ,String shoot) {
        frame = new JFrame("It's your turn !");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(435,295);


        Container pane = frame.getContentPane();
        JButton button1 =new JButton();
        ImageIcon moveIcon = new ImageIcon(new ImageIcon(move).getImage().getScaledInstance(435, 90, Image.SCALE_DEFAULT));

        button1.setPreferredSize(new Dimension(435, 90));
        pane.add(button1, BorderLayout.NORTH);
        //button1.setBorder(BorderFactory.createEmptyBorder());
        button1.setIcon(moveIcon);

        JButton button2 =new JButton();
        ImageIcon grabIcon = new ImageIcon(new ImageIcon(grab).getImage().getScaledInstance(435, 105, Image.SCALE_DEFAULT));
        button2.setPreferredSize(new Dimension(435, 105));
        pane.add(button2, BorderLayout.CENTER);
        //button2.setBorder(BorderFactory.createEmptyBorder());
        button2.setIcon(grabIcon);

        JButton button3 =new JButton();
        ImageIcon shootIcon = new ImageIcon(new ImageIcon(shoot).getImage().getScaledInstance(435, 90, Image.SCALE_DEFAULT));
        button3.setPreferredSize(new Dimension(435, 90));
        pane.add(button3, BorderLayout.SOUTH);
        //button3.setBorder(BorderFactory.createEmptyBorder());
        button3.setIcon(shootIcon);

        frame.setVisible(true);
        frame.setResizable(false);
    }
}

