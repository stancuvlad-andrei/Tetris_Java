package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  RO: Clasa StartFrame se ocupa de meniul principal.
 *  
 *  ENG: The StartFrame class handles the main menu.
 */

public class StartFrame extends JFrame 
{

    public StartFrame() 
    {
        setTitle("Start Frame");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 20, 0);

        JLabel tetrisLabel = new JLabel("Tetris");
        tetrisLabel.setFont(new Font("Arial", Font.BOLD, 40));

        panel.add(tetrisLabel, gbc);

        JButton startButton = new JButton("Start");
        JButton leaderboardButton = new JButton("Leaderboard");
        JButton exitButton = new JButton("Exit");

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(startButton, gbc);

        gbc.gridy = 2;
        panel.add(leaderboardButton, gbc);

        gbc.gridy = 3;
        panel.add(exitButton, gbc);

        startButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                dispose();
                Test.startGame();
            }
        });

        leaderboardButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new LeaderBoardFrame();
            }
        });

        exitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.exit(0);
            }
        });

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                new StartFrame();
            }
        });
    }
}
