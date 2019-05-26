package it.polimi.sw2019.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Weapon extends JFrame{

    private JButton weapon1;
    private JButton weapon2;
    private JButton weapon3;

    private JTextArea descriptionText;

    private String weaponDescription ;
    private String description1;
    private String description2;
    private String description3;

    private Container weaponContainer;
    private Container container;
    private Container textContainer;
    private Container labelContainer;
    private Container buttonContainer;


    public Weapon(){

        super("Your Weapon : ");

        //containers

        Container weaponContainer = new Container();
        weaponContainer.setLayout(new FlowLayout());

        Container container = new Container();
        container.setLayout(new BorderLayout());

        Container textContainer = new Container();
        textContainer.setLayout(new FlowLayout());

        Container labelContainer = new Container();
        labelContainer.setLayout(new FlowLayout());

        Container buttonContainer = new Container();
        buttonContainer.setLayout(new FlowLayout());


        //weapons

        JButton weapon1 = new JButton();
        JButton weapon2 = new JButton();
        JButton weapon3 = new JButton();

        weapon1.setPreferredSize(new Dimension(189, 318));
        ImageIcon imageWeapon1 = new ImageIcon(new ImageIcon("res/CyberBlade.png").getImage().getScaledInstance(189, 318, Image.SCALE_DEFAULT));
        weapon1.setIcon(imageWeapon1);


        weapon2.setPreferredSize(new Dimension(189, 318));
        ImageIcon imageWeapon2 = new ImageIcon(new ImageIcon("res/PowerGlove.png").getImage().getScaledInstance(189, 318, Image.SCALE_DEFAULT));
        weapon2.setIcon(imageWeapon2);


        weapon3.setPreferredSize(new Dimension(189, 318));
        ImageIcon imageWeapon3 = new ImageIcon(new ImageIcon("res/SledgeHammer.png").getImage().getScaledInstance(189, 318, Image.SCALE_DEFAULT));
        weapon3.setIcon(imageWeapon3);

        weaponContainer.add(weapon1);
        weaponContainer.add(weapon2);
        weaponContainer.add(weapon3);

        container.add(weaponContainer, BorderLayout.NORTH);

        //buttons

        JButton description1 = new JButton("Description");
        description1.setPreferredSize(new Dimension(189, 50));

        JButton description2 = new JButton("Description");
        description2.setPreferredSize(new Dimension(189, 50));

        JButton description3 = new JButton("Description");
        description3.setPreferredSize(new Dimension(189, 50));


        buttonContainer.add(description1);
        buttonContainer.add(description2);
        buttonContainer.add(description3);

        container.add(buttonContainer, BorderLayout.CENTER);

        //text area

        String weaponDescription ="                 " +
                "POWER GLOVE\n" +
                "   Basic mode:                     Choose 1 target on any square exactly 1 move away.\n" +
                "                                           Move onto that square and give the target\n" +
                "                                           1 damage and 2 marks.\n" +
                "   In rocket fist mode:          Choose a square exactly 1 move away. Move onto that square.\n" +
                "                                           You may deal 2 damage to 1 target there.\n" +
                "                                           If you want, you may move 1 more square in\n" +
                "                                           that same direction (but only if it is a legal\n" +
                "                                           move). You may deal 2 damage to 1 target\n" +
                "                                           there, as well.\n" +
                "   Notes:                              In rocket fist mode, you're flying 2 squares in a straight line,\n" +
                "                                           punching 1 person per square.";

        JTextArea descriptionText = new JTextArea(weaponDescription);

        descriptionText.setPreferredSize(new Dimension(577 ,300));
        descriptionText.setBackground(Color.LIGHT_GRAY);
        descriptionText.setFont(new Font(weaponDescription , Font.ITALIC ,14));
        descriptionText.setEditable(false);
        labelContainer.add(descriptionText );

        container.add(labelContainer , BorderLayout.PAGE_END);

        //Container

        add(container);

        //ActionListener

        Weapon.ButtonHandler buttonHandler = new Weapon.ButtonHandler();
        weapon1.addActionListener(buttonHandler);
        weapon2.addActionListener(buttonHandler);
        weapon3.addActionListener(buttonHandler);


    }

    private class  ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JOptionPane.showMessageDialog(Weapon.this,String.format(
                    "you've pressed : %s",e.getActionCommand()));


        }
    }
}