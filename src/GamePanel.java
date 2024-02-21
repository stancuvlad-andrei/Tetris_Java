package Tetris;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.*;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.*;

/**
 *  RO: Clasa GamePanel reprezinta panelu jocului in sine.
 *  
 *  ENG: The GamePanel class represents the game panel itself. 
 */

public class GamePanel extends JPanel
{
    private int righe;
    private int colonne;
    private int cella;
    private int[][] forma;
    private Color[][] sfondo;
    private Pezzo pezzo;
    public Container c;
    private int score;
    private JLabel scoreLabel;
    private int level;
    private JLabel levelLabel;
    public GamePanel()
    {
        this.setBounds(0,0,300,600);
        Border border = LineBorder.createBlackLineBorder();
        this.setBorder(border);
        colonne = 10;
        cella = this.getBounds().width / colonne;
        righe = this.getBounds().height / cella;
        sfondo = new Color[righe][colonne];
        score = 0;
        scoreLabel = new JLabel("Score: " + score);
        add(scoreLabel);
        level = 1;
        levelLabel = new JLabel("Level: " + level);
        add(levelLabel);
    }

    /**
     * RO: Metoda generatorePezzi genereaza aleator o piesa.
     * Se creaza un numar random si se asigna cu respectiva forma prestabilita.
     * 
     * ENG: The generatorePezzi method randomly generates a piece.
     * A random number is created and assigned with the respective predetermined form.
     */
    public void generatorePezzi()
    {
        int casuale = (int)(Math.random()*7);
        if(casuale == 1)
        {
            pezzo = new Pezzo(new int[][]{{1,0}, {1,0}, {1,1}}, Color.orange);
            forma = new int[][]{{1,0}, {1,0}, {1,1}};
            pezzo.generatore();
        }
        else if(casuale == 2)
        {
            pezzo = new Pezzo(new int[][]{{1,0}, {1,0}, {1,0}, {1,0}}, Color.cyan);
            forma = new int[][]{{1,0}, {1,0}, {1,0}, {1,0}};
            pezzo.generatore();
        }
        else if(casuale == 3)
        {
            pezzo = new Pezzo(new int[][]{{1,1}, {1,1}}, Color.yellow);
            forma = new int[][]{{1,1}, {1,1}};
            pezzo.generatore();
        }
        else if(casuale == 4)
        {
            pezzo = new Pezzo(new int[][]{{0,1,1}, {1,1,0}}, Color.green);
            forma = new int[][]{{0,1,1}, {1,1,0}};
            pezzo.generatore();
        }
        else if(casuale == 5)
        {
            pezzo = new Pezzo(new int[][]{{0,1,0}, {1,1,1}}, Color.pink);
            forma = new int[][]{{0,1,0}, {1,1,1}};
            pezzo.generatore();
        }
        else if(casuale == 6)
        {
            pezzo = new Pezzo(new int[][]{{1,1,0}, {0,1,1}}, Color.red);
            forma = new int[][]{{1,1,0}, {0,1,1}};
            pezzo.generatore();
        }
        else if(casuale == 7)
        {
            pezzo = new Pezzo(new int[][]{{0,1}, {0,1}, {1,1}}, Color.blue);
            forma = new int[][]{{0,1}, {0,1}, {1,1}};
            pezzo.generatore();
        }    
    }
    
    /**
     * RO: Aceasta metoda deseneaza piesa pe tabla.
     * 
     * ENG: This method draws the piece on the board.
     * @param g
     */
    public void disegnoPezzo(Graphics g)
    {
        int a = pezzo.getAltezza();
        int l = pezzo.getLarghezza();
        Color c = pezzo.getColore();
        int[][] forma = pezzo.getForma();
        
        for(int rig=0; rig<a; rig++)
        {
            for(int col=0; col<l; col++)
            {
                if(forma[rig][col] == 1)
                {
                    int x = (pezzo.getX()+col)*cella;
                    int y = (pezzo.getY()+rig)*cella;
                    
                    g.setColor(c);
                    g.fillRect(x, y, cella, cella);
                    g.setColor(Color.black);
                    g.drawRect(x, y, cella, cella);
                }
            }
        }
    }
    
    /**
     * RO: Aceasta metoda deseneaza tabla de joc.
     * 
     * ENG: This method draws the game board.
     * @param g
     */
    public void disegnoSfondo(Graphics g)
    {
        Color colore;
        for(int r = 0; r < righe; r++)
        {
            for(int c = 0; c < colonne; c++)
            {
                colore = sfondo[r][c];
                if(colore != null)
                {
                    int x = c * cella;
                    int y = r * cella;
                    
                    g.setColor(colore);
                    g.fillRect(x, y, cella, cella);
                    g.setColor(Color.black);
                    g.drawRect(x, y, cella, cella);
                }
            }
        }
    }
    
    private void setContainer(Container c)
    {
        this.c = c;
    }    
    
    /**
     * RO: Aceasta metoda verifica daca piesa se alfa in afara fundalului(tabelei de joc).
     * 
     * ENG: This method checks if the piece is outside the background (game table).
     * @return
     */
    public boolean pezzoFuoriSfondo()
    {
        if(pezzo.getY() < 0)
        {
            pezzo = null;
            return true;
        }
        return false;
    }
    
    /**
     * RO: Aceasta metoda verifica daca piesa poate sa mai cada verificand ce este sub ea.
     * 
     * ENG: This method checks if the piece can still fall by checking what is under it.
     */
    public boolean cadutaPezzo()
    {
        if(controllaFondo() == false)
        {
            return false;
        }
        pezzo.basso();
        repaint();
        return true;
    }
    
    /**
     * RO: Urmatoarele 3 metode se ocupa de verificare fundalului adiacent cu piesa curenta.
     * 
     * ENG: The following 3 methods deal with checking the background adjacent to the current piece.
     * @return
     */
    public boolean controllaFondo()
    {
        if(pezzo.getMargineInferiore() == righe)
        {
            return false;
        }
        
        int[][] forma = pezzo.getForma();
        int w = pezzo.getLarghezza();
        int h = pezzo.getAltezza();
        
        for(int col=0; col<w; col++)
        {
            for(int rig=h-1; rig>=0; rig--)
            {
                if(forma[rig][col] != 0)
                {
                    int x = col+pezzo.getX();
                    int y = rig+pezzo.getY()+1;
                    if(y<0)
                        break;
                    if(sfondo[y][x] != null)
                        return false;
                    break;
                }
            }
        }
        
        return true;
    }
    public boolean controllaSinistra()
    {
        if(pezzo.getMargineSinistra() == 0)
            return false;
            
        int[][] forma = pezzo.getForma();
        int w = pezzo.getLarghezza();
        int h = pezzo.getAltezza();
        
        for(int rig=0; rig<h; rig++)
        {
            for(int col=0; col<w; col++)
            {
                if(forma[rig][col] != 0)
                {
                    int x = col+pezzo.getX()-1;
                    int y = rig+pezzo.getY();
                    if(y<0)
                        break;
                    if(sfondo[y][x] != null)
                        return false;
                    break;
                }
            }
        }
        
        return true;
    }
    public boolean controllaDestra()
    {
        if(pezzo.getMargineDestra() == colonne)
            return false;
        
        int[][] forma = pezzo.getForma();
        int w = pezzo.getLarghezza();
        int h = pezzo.getAltezza();
        
        for(int rig=0; rig<h; rig++)
        {
            for(int col=w-1; col>=0; col--)
            {
                if(forma[rig][col] != 0)
                {
                    int x = col+pezzo.getX()+1;
                    int y = rig+pezzo.getY();
                    if(y<0)
                        break;
                    if(sfondo[y][x] != null)
                        return false;
                    break;
                }
            }
        }
        
        return true;
    }
    
    /**
     * RO: Aceasta metoda controleaza daca piesa se afla in fundal. 
     * 
     * ENG: This method checks if the piece is in the background.
     */
    public void pezzoNelloSfondo()
    {
        int[][] forma = pezzo.getForma();
        int h = pezzo.getAltezza();
        int w = pezzo.getLarghezza();
        
        int xPos = pezzo.getX();
        int yPos = pezzo.getY();
        
        Color colore = pezzo.getColore();
        
        for(int r=0; r<h; r++)
        {
            for(int c=0; c<w; c++)
            {
                if(forma[r][c] == 1)
                {
                    sfondo[r+yPos][c+xPos] = colore;
                }
            }
        }
    }
    
    /**
     * RO: Aceste metode se ocupa de miscarea piesei curente.
     * 
     * ENG: These methods handle the movement of the current piece.
     */
    public void spostaDestra()
    {
        if(pezzo == null)
            return;
        if(controllaDestra() == false)
            return;
        
        pezzo.destra();
        repaint();
    }
    public void spostaSinistra()
    {
        if(pezzo == null)
            return;
        if(controllaSinistra() == false)
            return;
        
        pezzo.sinistra();
        repaint();
    }
    public void spostaBasso()
    {
        if(pezzo == null)
            return;
        while(controllaFondo() == true)
        {
            pezzo.basso();
        }
        repaint();
    }
    public void ruotaPezzo()
    {
        if(pezzo == null)
            return;
        pezzo.ruotare();
        if(pezzo.getMargineSinistra() < 0)
            pezzo.setX(0);
        if(pezzo.getMargineDestra() >= colonne)
            pezzo.setX(colonne - pezzo.getLarghezza());
        if(pezzo.getMargineInferiore() >= righe)
            pezzo.setY(righe - pezzo.getAltezza());
        repaint();
    }
    public void spostaPezziBasso(int r)
    {
        for(int rig=r; rig>0; rig--)
                {
                    for(int col=0; col<colonne; col++)
                    {
                        sfondo[rig][col] = sfondo[rig-1][col];
                    }
                }
    }
    
    /**
     * RO: Aceasta metoda sterge linile completate.
     * 
     * ENG: This method deletes the completed lines.
     * @return
     */
    public int cancellaLinee()
    {
        boolean lineaCompleta;
        int lineeCancellate = 0;
        for(int r=righe-1; r>=0; r--)
        {
            lineaCompleta = true;
            for(int c=0; c<colonne; c++)
            {
                if(sfondo[r][c] == null)
                {
                    lineaCompleta = false;
                    break;
                }
            }
            if(lineaCompleta == true)
            {
            	lineeCancellate++;
                cancellaLinea(r);
                spostaPezziBasso(r);
                cancellaLinea(0);
                r++;
                repaint();
            }
        }
        return lineeCancellate;
    }
    public void cancellaLinea(int r)
    {
        for(int i=0; i<colonne; i++)
                {
                    sfondo[r][i] = null;
                }
    }
    
    /**
     * RO: Aceste metode se ocupa de scor si de nivel.
     * 
     * ENG: These methods deal with score and level.
     * @param newScore
     */
    public void updateScore(int newScore) 
    {
        this.score = newScore;
        scoreLabel.setText("Score: " + score);
    }
    
    public void updateLevel(int newLevel) 
    {
        this.level = newLevel;
        levelLabel.setText("Level: " + level);
    }
    
    public String promptForName() 
    {
        return JOptionPane.showInputDialog("Game Over! Enter your name:");
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        disegnoSfondo(g);
        disegnoPezzo(g);
    }
}