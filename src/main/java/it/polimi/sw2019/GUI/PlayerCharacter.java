package it.polimi.sw2019.GUI;

import javax.swing.*;
import java.awt.*;

public class PlayerCharacter {

    static JFrame frame ;

    public static void main(String[]args){

        frame = new JFrame("New Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,300);

        Container buttonContainer=new Container();
        buttonContainer.setLayout(new FlowLayout());
        Container container = new Container();
        container.setLayout(new BorderLayout());

        JLabel text = new JLabel("Choose your " );
        JButton yellow =new JButton();
        JButton blue = new JButton();
        JButton black = new JButton();
        JButton green = new JButton();
        JButton purple = new JButton();

        yellow.setPreferredSize(new Dimension(170, 155));
        ImageIcon imageYellow = new ImageIcon(new ImageIcon("Images/yellowFigurine.png").getImage().getScaledInstance(170, 155, Image.SCALE_DEFAULT));
        yellow.setIcon(imageYellow);

        blue.setPreferredSize(new Dimension(170, 155));
        ImageIcon imageBlue = new ImageIcon(new ImageIcon("Images/blueFigurine.png").getImage().getScaledInstance(170, 155, Image.SCALE_DEFAULT));
        blue.setIcon(imageBlue);

        black.setPreferredSize(new Dimension(170, 155));
        ImageIcon imageBlack = new ImageIcon(new ImageIcon("Images/blackFigurine.png").getImage().getScaledInstance(170, 155, Image.SCALE_DEFAULT));
        black.setIcon(imageBlack);

        green.setPreferredSize(new Dimension(170, 155));
        ImageIcon imageGreen = new ImageIcon(new ImageIcon("Images/greenFigurine.png").getImage().getScaledInstance(170, 155, Image.SCALE_DEFAULT));
        green.setIcon(imageGreen);

        purple.setPreferredSize(new Dimension(170, 155));
        ImageIcon imagePurple = new ImageIcon(new ImageIcon("Images/purpleFigurine.png").getImage().getScaledInstance(170, 155, Image.SCALE_DEFAULT));
        purple.setIcon(imagePurple);


        buttonContainer.add(yellow);
        buttonContainer.add(blue);
        buttonContainer.add(black);
        buttonContainer.add(green);
        buttonContainer.add(purple);

        container.add(text,BorderLayout.PAGE_START);
        container.add(buttonContainer,BorderLayout.PAGE_END);
        frame.setContentPane(container);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}

