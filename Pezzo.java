import java.awt.Color;
import java.awt.Graphics;
import javax.swing.border.*;
import java.awt.*;
import javax.swing.*;
public class Pezzo extends GamePanel
{
    private int[][] forma;
    private Color colore;
    private int x;
    private int y;
    private int[][][] forme;
    private int rotazioneCorrente;
    public Pezzo(int[][] forma, Color colore)
    {
        this.forma = forma;
        this.colore = colore;
        forme();
        generatore();
    }
    public void forme()
    {
        forme = new int[4][][];
        for(int i = 0; i < 4; i++)
        {
            int r = forma[0].length;
            int c = forma.length;
            forme[i] = new int[r][c];
            for(int y = 0; y < r; y++)
            {
                for(int x = 0; x < c; x++)
                {
                    forme[i][y][x] = forma[c - x - 1][y];
                }
            }
            forma = forme[i];
        }
    }
    public void ruotare()
    {
        rotazioneCorrente++;
        if(rotazioneCorrente > 3)
            rotazioneCorrente = 0;
        forma = forme[rotazioneCorrente];
    }
    public void generatore()
    {
        rotazioneCorrente = 0;
        forma = forme[rotazioneCorrente];
        x = (10 - getLarghezza()) / 2;
        y = -getAltezza();
    }
    
    public int[][] getForma()
    {
        return forma;
    }
    public Color getColore()
    {
        return colore;
    }
    
    public int getAltezza()
    {
        return forma.length;
    }
    public int getLarghezza()
    {
        return forma[0].length;
    }
    
    public void setX(int x)
    {
        this.x=x;
    }
    public int getX()
    {
        return x;
    }
    public void setY(int y)
    {
        this.y=y;
    }
    public int getY()
    {
        return y;
    }
    
    public int getMargineInferiore()
    {
        return y+getAltezza();
    }
    public int getMargineDestra()
    {
        return x+getLarghezza();
    }
    public int getMargineSinistra()
    {
        return x;
    }

    
    public void basso()
    {
        y++;
    }
    public void sinistra()
    {
        x--;
    }
    public void destra()
    {
        x++;
    }
}
