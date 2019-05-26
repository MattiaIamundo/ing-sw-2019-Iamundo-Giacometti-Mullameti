package it.polimi.sw2019.GUI;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DirectionTable extends  JFrame{

    private Container containerNord ;
    private Container containerCenter ;
    private Container containerSouth ;
    private Container container;

    public DirectionTable(){

        Container containerNord =new Container();
        containerNord.setPreferredSize(new Dimension(227 , 83));
        containerNord.setLayout(new BorderLayout());

        Container containerCenter =new Container();
        containerCenter.setPreferredSize(new Dimension(227 , 61));
        containerCenter.setLayout(new BorderLayout());

        Container containerSouth =new Container();
        containerSouth.setPreferredSize(new Dimension(227 , 83));
        containerSouth.setLayout(new BorderLayout());

        Container container =new Container();
        container.setPreferredSize(new Dimension(230 , 230));
        container.setLayout(new BorderLayout());

        JLabel square1 = new JLabel();
        square1.setPreferredSize(new Dimension(83 , 83));
        Image image = null;
        try {
            image = ImageIO.read(new File("Images/centro.png"));
        } catch (IOException e) {
            System.out.println("Problema nel caricamento immagine");
        }
        Image newImage = image.getScaledInstance(83, 83,  java.awt.Image.SCALE_SMOOTH);
        square1.setIcon(new ImageIcon(newImage));
        containerNord.add(square1 , BorderLayout.WEST);

        JLabel square3 = new JLabel();
        square3.setIcon(new ImageIcon(newImage));
        containerNord.add(square3 , BorderLayout.EAST);

        JLabel square7 = new JLabel();
        square7.setIcon(new ImageIcon(newImage));
        containerSouth.add(square7 , BorderLayout.WEST);

        JLabel square9 = new JLabel();
        square9.setIcon(new ImageIcon(newImage));
        containerSouth.add(square9 , BorderLayout.EAST);

        JLabel squareMini= new JLabel();
        squareMini.setPreferredSize(new Dimension(61 , 61));
        Image imageMini = null;
        try {
            imageMini = ImageIO.read(new File("Images/centro.png"));
        } catch (IOException e) {
            System.out.println("Problema nel caricamento immagine");
        }
        Image newImageMini = imageMini.getScaledInstance(61, 61,  java.awt.Image.SCALE_SMOOTH);
        squareMini.setIcon(new ImageIcon(newImageMini));
        squareMini.setIcon(new ImageIcon(newImageMini));
        containerCenter.add(squareMini ,BorderLayout.CENTER);


        JButton north = new JButton();
        north.setPreferredSize(new Dimension(61, 83));
        ImageIcon imageNorth = new ImageIcon(new ImageIcon("Images/North.png").getImage().getScaledInstance(61, 83, Image.SCALE_DEFAULT));
        north.setIcon(imageNorth);
        containerNord.add(north , BorderLayout.CENTER);


        JButton west = new JButton();
        west.setPreferredSize(new Dimension(83, 61));
        ImageIcon imageWest = new ImageIcon(new ImageIcon("Images/West.png").getImage().getScaledInstance(83, 61, Image.SCALE_DEFAULT));
        west.setIcon(imageWest);
        containerCenter.add(west , BorderLayout.WEST);


        JButton south = new JButton();
        south.setPreferredSize(new Dimension(61, 83));
        ImageIcon imageSouth = new ImageIcon(new ImageIcon("Images/South.png").getImage().getScaledInstance(61, 83, Image.SCALE_DEFAULT));
        south.setIcon(imageSouth);
        containerSouth.add(south , BorderLayout.CENTER);

        JButton east = new JButton();
        east.setPreferredSize(new Dimension(83, 61));
        ImageIcon imageEast = new ImageIcon(new ImageIcon("Images/East.png").getImage().getScaledInstance(83, 61, Image.SCALE_DEFAULT));
        east.setIcon(imageEast);
        containerCenter.add(east , BorderLayout.EAST);

        container.add(containerNord , BorderLayout.NORTH);
        container.add(containerCenter , BorderLayout.CENTER);
        container.add(containerSouth , BorderLayout.SOUTH);

        add(container);


    }
}
