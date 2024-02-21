package Tetris;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *  RO: Clasa LeaderBoardFrame se ocupa de afisarea scorului fiecaruia jucator.
 *  In comentarii se afla metoda care citeste din fisier.
 *  Metoda care citeste din baza de date nu se afla in comentarii.
 *  
 *  ENG: The LeaderBoardFrame class takes care of displaying each player's score.
 *  The method that reads from the file is in the comments.
 *  The method that reads from the database is not in the comments.
 */

public class LeaderBoardFrame extends JFrame 
{
    //private static final String FILE_PATH = "/home/stancuvlad-andrei/School/Java/P3/src/Tetris/scores.csv";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Tetris";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public LeaderBoardFrame() 
    {
        super("Leaderboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 400);

        JTextArea leaderboardTextArea = new JTextArea();
        leaderboardTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(leaderboardTextArea);
        add(scrollPane);

        //loadScores(leaderboardTextArea);
        loadScoresFromDatabase(leaderboardTextArea);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*private void loadScores(JTextArea leaderboardTextArea) 
    {
        try {
            Path filePath = Paths.get(FILE_PATH);
            if(Files.exists(filePath)) 
            {
                List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

                
                for (String line : lines) 
                {
                    leaderboardTextArea.append(line + "\n");
                }
            
            }else 
            {
                leaderboardTextArea.setText("No scores available.");
            }
        }catch(Exception e) 
        {
            e.printStackTrace();
        }
    }*/
    
    private void loadScoresFromDatabase(JTextArea leaderboardTextArea) 
    {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) 
        {
            String query = "SELECT playerName, score FROM Scores ORDER BY score DESC";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) 
            {
                try (ResultSet resultSet = preparedStatement.executeQuery()) 
                {
                    while (resultSet.next()) 
                    {
                        String playerName = resultSet.getString("playerName");
                        int score = resultSet.getInt("score");
                        leaderboardTextArea.append(playerName + ": " + score + "\n");
                    }
                }
            }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> new LeaderBoardFrame());
    }
}
