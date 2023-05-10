package graphic.hud;


import starter.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gameOverScreen extends JFrame {
    private JPanel GameOverPan;
    /**
     * JPanel For the GameOver Window
     * gameOverScreen for the screen
     * Implements theButtons andthe panel
     */
    public gameOverScreen() {
        setTitle("Game Over");
        setBounds(100, 150, 500, 300);

        GameOverPan = new JPanel();
        GameOverPan.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(GameOverPan);
        GameOverPan.setLayout(null);

        /**
         * Jbutton for the reset
         */
        JButton btNeustart = new JButton("Neustart");
        btNeustart.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btNeustart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
              Restart();
            }
        });
        btNeustart.setBounds(20, 80, 89, 25);
        GameOverPan.add(btNeustart);
        /**
         * Jbutton for the end
         */
        JButton btBeenden = new JButton("Beenden");
        btBeenden.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btBeenden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                beenden();
            }

        });
        btBeenden.setBounds(150, 80, 89, 25);
        GameOverPan.add(btBeenden);
        setVisible(true);
    }
    /**
     * Restart Call neustart from Game
     *
     */
    private void Restart() {
    Game game =new Game();
    game.neustart();
    }
    /**
     * beenden ends the programm
     */
    public void beenden() {
        System.exit(0);
    }
}

