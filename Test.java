import java.awt.Container;
import java.awt.*;
import javax.swing.*;

public class Test
{
    public static void main(String[] args)
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