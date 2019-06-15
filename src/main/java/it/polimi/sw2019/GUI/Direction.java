package it.polimi.sw2019.GUI;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import it.polimi.sw2019.model.Player;

public class Direction {

    static JFrame frame;

  /*  public static void directionWindow (String moveIcon){
        frame = new JFrame("Move");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,150);

        Container directionContainer = new Container();
        directionContainer.setLayout(new GridLayout(2,2));
        Container answerContainer = new Container();
        answerContainer.setLayout(new FlowLayout());
        Container container = new Container();
        container.setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel();
        Image image = null;
        try {
            image = ImageIO.read(new File(moveIcon));
        } catch (IOException e) {
            System.out.println("Problema nel caricamento immagine");
        }

        Image newImage = image.getScaledInstance(50, 25,  java.awt.Image.SCALE_SMOOTH);

        JLabel text = new JLabel("In what direction do you want to move ?");
        text.setIcon(new ImageIcon(newImage));


        container.add(text , BorderLayout.NORTH);
        container.add(directionContainer , BorderLayout.CENTER);
        container.add(answerContainer , BorderLayout.PAGE_END);
        frame.setContentPane(container);


        JRadioButton north = new JRadioButton("North");
        JRadioButton west = new JRadioButton("West");
        JRadioButton south = new JRadioButton("South");
        JRadioButton east = new JRadioButton("East");
        ButtonGroup gruppo = new ButtonGroup();

        gruppo.add(north);
        gruppo.add(west);
        gruppo.add(south);
        gruppo.add(east);
        north.setSelected(true);


        directionContainer.add(north);
        directionContainer.add(west);
        directionContainer.add(south);
        directionContainer.add(east);


        JButton move = new JButton("MoveAgain");
        JButton done = new JButton("Done");
        JButton cancel = new JButton("Cancel");

        answerContainer.add(move);
        answerContainer.add(done);
        answerContainer.add(cancel);

        frame.setVisible(true);
        frame.setResizable(false);


    }

   */

  /*  public static void main(String[]args){
        String color ;
        color=Player.getColor();
        String moveIcon=new String();

        switch(color){
            case ("Yellow"):{
                moveIcon="Images/moveIconYellow.png";
            }
            break;

            case ("Blue"):{
                moveIcon="Images/moveIconBLue.png";
            }
            break;

            case ("Black"):{
                moveIcon="Images/moveIconBlack.png";
            }
            break;

            case ("Green"):{
                moveIcon="Images/moveIconGreen.png";
            }
            break;

            case ("Purple"):{
                moveIcon="Images/moveIconPurple.png";
            }
            break;
        }
        Direction.directionWindow(moveIcon);
    }

   */
}

