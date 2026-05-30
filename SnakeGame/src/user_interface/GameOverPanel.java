package user_interface;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel  {

    JLabel label;
    JButton tryAgainBTN;
    JButton menuBTN;

    
    GameOverPanel (SnakeGame parent) {

        setLayout(new GridLayout(3, 1));

        label = new JLabel();
        tryAgainBTN = new JButton("Try-again");
        menuBTN = new JButton("Menu");

        add(label);
        add(tryAgainBTN);
        add(menuBTN);

        tryAgainBTN.addActionListener( 
            e -> {
                parent.startGame();
            }
        );
        menuBTN.addActionListener(
            e -> {
                parent.showMenu();
            }
        );


    }
    
}
