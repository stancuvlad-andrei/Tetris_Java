package Tetris;
import java.awt.Container;
import java.awt.*;
import javax.swing.*;

/**
 *  RO: Clasa test instanteaza frame-ul si panel-ul jocului.
 *  
 *  ENG: The test class instantiates the game frame and panel.
 */

public class Test
{
    public static void main(String[] args)
    {
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StartFrame();
            }
        });
    }
    
    public static void startGame()
    {
    	GamePanel panel1 = new GamePanel();
        GameFrame frame = new GameFrame("Tetris", panel1);
        Container c = frame.getContentPane();
        Null panel2 = new Null();
        new Timer(panel1).start();
        c.add(panel1);
        c.add(panel2);
        frame.setVisible(true);
    }
}