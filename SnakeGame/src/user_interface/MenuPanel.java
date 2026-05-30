package user_interface;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    JLabel label;
    JButton startBTN;
    JButton exitBTN;

    MenuPanel (SnakeGame parent) {

        setLayout( new GridLayout(3, 1));

        label = new JLabel("Welcome to Snake!!!", SwingConstants.CENTER);
        startBTN = new JButton("Start");
        exitBTN = new JButton("exit");

        add(label);
        add(startBTN);
        add(exitBTN);

        startBTN.addActionListener( e ->
            { parent.startGame();}
        );

        exitBTN.addActionListener( e -> {
            System.exit(0);
        }

        );
        
    }
    
}
