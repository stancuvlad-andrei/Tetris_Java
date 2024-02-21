package Tetris;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *  RO: Clasa Timer se ocupa de efectiv de joc. 
 *  Porneste un thread(jocul in sine) si ruleaza la infinit pana cand piesa curenta controlata de jucator iasa din peisaj.
 *  Clasa tine cont si de scorul jucatorului si de nivelul meciului.
 *  La game over se salveaza scorul in baza de date, in comentarii se afla metoda pentru fisier csv. 
 *  
 *  ENG: The Timer class handles the actual game.
 *  Starts a thread (the game itself) and runs indefinitely until the current piece controlled by the player leaves the landscape.
 *  The class also takes into account the player's score and the level of the match.
 *  At game over, the score is saved in the database, in the comments you can find the method for the csv file.
 */

public class Timer extends Thread
{
    private GamePanel panel1;
    private int score = 0;
    private int level = 1;
    private int scorePerLevel = 3;
    private int pausa = 1000;
    private int speed = 100;
    //private static final String FILE_PATH = "/home/stancuvlad-andrei/School/Java/P3/src/Tetris/scores.csv";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Tetris";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    public Timer(GamePanel panel1)
    {
        this.panel1 = panel1;
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            panel1.generatorePezzi();
            while(panel1.cadutaPezzo() == true)
            {
                try
                {
                    Thread.sleep(pausa);
                }
                catch(InterruptedException exception)
                {
                    Logger.getLogger(Timer.class.getName()).log(Level.SEVERE,null,exception);
                }
            }
            if(panel1.pezzoFuoriSfondo() == true)
            {
            	System.out.println("Game Over");
                String playerName = panel1.promptForName();
                saveScore(playerName, score);

                SwingUtilities.invokeLater(() -> {
                    JFrame gameFrame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                    gameFrame.dispose();

                    StartFrame startFrame = new StartFrame();
                    startFrame.setVisible(true);
                });

                break;
            }
            panel1.pezzoNelloSfondo();
            score+=panel1.cancellaLinee();
            panel1.updateScore(score);
            int lvl = score / scorePerLevel + 1;
            if(lvl>level)
            {
            	level = lvl;
            	panel1.updateLevel(level);
            	pausa -= speed;
            }
        }
    }
    
    /*private void saveScore(String playerName, int score) 
    {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }

            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);


            boolean playerExists = false;
            for(int i = 0; i < lines.size(); i++) 
            {
                String[] parts = lines.get(i).split(",");
                if (parts.length == 2 && parts[0].equals(playerName)) 
                {
                    lines.set(i, playerName + "," + score);
                    playerExists = true;
                    break;
                }
            }
            if(!playerExists) 
            {
                lines.add(playerName + "," + score);
            }


            Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
        }catch(IOException exception) 
        {
            Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, exception);
        }
    }*/
    
    private void saveScore(String playerName, int score) 
    {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) 
        {
            String query = "INSERT INTO Scores (playerName, score) VALUES (?, ?) ON DUPLICATE KEY UPDATE score = VALUES(score)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) 
            {
                preparedStatement.setString(1, playerName);
                preparedStatement.setInt(2, score);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) 
        {
            Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

}