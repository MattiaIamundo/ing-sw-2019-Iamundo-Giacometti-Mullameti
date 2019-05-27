package it.polimi.sw2019.GUI;


import javax.swing.*;


public class PlayerNickname {

    static JTextField textField;

    static JFrame frame;

    static JButton button;

    static JLabel label;

    public static void main(String[] args)
    {
       
        //String nickname =JOptionPane.showInputDialog("Enter your Nickname : ");
        


        frame = new JFrame("New Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,100);

        label = new JLabel("Enter your nickname :");

        button = new JButton("Submit");

        textField = new JTextField(16);

        JPanel p = new JPanel();

        p.add(label);
        p.add(textField);
        p.add(button);

        frame.add(p);

        frame.setResizable(false);
        frame.setVisible(true);
    }
}
