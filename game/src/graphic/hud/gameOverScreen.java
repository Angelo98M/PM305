package graphic.hud;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Visibility;

public class gameOverScreen extends JFrame {
    private JPanel GameOverPan;

    public void neustart(){


    }
    public void beenden() {
        System.exit(0);
    }

    public gameOverScreen() {
            setTitle("Game Over");
            setBounds(100, 150, 329, 169);

            GameOverPan = new JPanel();
        GameOverPan.setBorder(new EmptyBorder( 5, 5, 5, 5));
        setContentPane(GameOverPan);
        GameOverPan.setLayout(null);

        JButton btNeustart = new JButton("Neustart");
        btNeustart.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btNeustart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                neustart();
            }
        });
        btNeustart.setBounds(20, 82, 89, 25);
        GameOverPan.add(btNeustart);

        JButton btBeenden = new JButton("Beenden");
        btBeenden.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btBeenden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                beenden();
            }
        });
        btBeenden.setBounds(121, 82, 89, 25);
        GameOverPan.add(btBeenden);
        setVisible(true);
    }
}
