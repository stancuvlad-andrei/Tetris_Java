package Tetris;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *  RO: Aceasta clasa reprezinta frame-ul jocului.
 *  Metoda Controlli mapeaza tastele directionale pentru controlarea piesei de joc.
 *  
 *  ENG: This class represents the frame of the game.
 *  The Controlli method maps the directional keys to control the game piece.
 */

public class GameFrame extends JFrame 
{
    GamePanel panel1;
    public GameFrame(String titolo, GamePanel panel1)
    {
        super(titolo);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setSize(300, 600);
        controlli();
        this.panel1 = panel1;
    }
    
    private void controlli()
    {
        InputMap a = this.getRootPane().getInputMap();
        ActionMap b = this.getRootPane().getActionMap();
        a.put(KeyStroke.getKeyStroke("RIGHT"),"destra");
        a.put(KeyStroke.getKeyStroke("LEFT"),"sinistra");
        a.put(KeyStroke.getKeyStroke("UP"),"su");
        a.put(KeyStroke.getKeyStroke("DOWN"),"giu");
        
        
        
        b.put("destra", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                panel1.spostaDestra();
            }
        });
        b.put("sinistra", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                panel1.spostaSinistra();
            }
        });
        b.put("su", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                panel1.ruotaPezzo();
            }
        });
        b.put("giu", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                panel1.spostaBasso();
            }
        });
    }
}