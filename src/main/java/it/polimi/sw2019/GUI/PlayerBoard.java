package it.polimi.sw2019.GUI;


import javax.swing.*;
import java.awt.*;

public class PlayerBoard extends JFrame {

    private JButton buttons[];
    private JButton action[];
    private JButton button;
    private JButton buttonImage;
    private Container container;
    private Container container1;
    private Container container2;
    private Container container3;
    private GridLayout gridLayout1;
    private GridLayout gridLayout2;


    public PlayerBoard(){

        gridLayout1=new GridLayout(3 ,12);
        gridLayout2=new GridLayout(5 ,1);

        container =getContentPane();
        container1=new Container();
        container1.setLayout(gridLayout1);
        container1.setPreferredSize(new Dimension(564 ,204));
        container2=new Container();
        container2.setLayout(gridLayout2);
        container2.setPreferredSize(new Dimension(51 ,122));
        container3=new Container();
        container3.setLayout(new BorderLayout());
        container3.setPreferredSize(new Dimension(51,204));

        buttonImage = new JButton();
        buttonImage.setBorder(BorderFactory.createEmptyBorder());
        ImageIcon moveIconC = new ImageIcon(new ImageIcon("res/greenC.png").getImage().getScaledInstance(185, 204, Image.SCALE_DEFAULT));
        buttonImage.setIcon(moveIconC);
        buttonImage.setPreferredSize(new Dimension(185 , 204));

        button=new JButton();
        button.setBorder(BorderFactory.createEmptyBorder());
        ImageIcon extra= new ImageIcon(new ImageIcon("res/extra.png").getImage().getScaledInstance(68, 204, Image.SCALE_DEFAULT));
        button.setIcon(extra);
        button.setPreferredSize(new Dimension(68, 204));






        //container2.add(button ,BorderLayout.EAST);

        buttons=new JButton[36];



        for(int i=0;i<36;i++){
            buttons[i]=new JButton();
            buttons[i].setBorder(BorderFactory.createEmptyBorder());
            ImageIcon moveIcon = new ImageIcon(new ImageIcon("res/"+i+".png").getImage().getScaledInstance(47, 68, Image.SCALE_DEFAULT));
            buttons[i].setIcon(moveIcon);
            // buttons[i].addActionListener(this);
            //buttons[i].setPreferredSize(new Dimension(51 ,71));
            //container.add(buttons[i]);
        }



        for(int i=0;i<36;i++){

            container1.add(buttons[i]);
        }

        container.add(container1 , BorderLayout.CENTER);
        container.add(button , BorderLayout.WEST);

        container.add(buttonImage , BorderLayout.EAST);

    }

    public static void main(String[]args){

        PlayerBoard PlayerBoardFrame = new PlayerBoard();
        PlayerBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlayerBoardFrame.setSize(811,234);
        PlayerBoardFrame.setResizable(false);
        PlayerBoardFrame.setVisible(true);


    }

}
