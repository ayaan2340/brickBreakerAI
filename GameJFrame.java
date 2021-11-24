package brickbreaker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GameJFrame extends JFrame{
    GameJPanel panel;

    GameJFrame() {
        panel = new GameJPanel();
        this.add(panel);
        this.setTitle("Brick Breaker");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String args[])
    {
        GameJFrame JFrame = new GameJFrame();
    }

}
