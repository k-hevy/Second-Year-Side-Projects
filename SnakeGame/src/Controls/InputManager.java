package Controls;

import java.awt.event.ActionEvent;
import javax.swing.*;

import user_interface.GamePanel;
import objects.Snake;

public class InputManager {

    private Snake snake;
    private GamePanel gamePanel;
    InputMap im;
    ActionMap am;

    public InputManager (Snake snake, GamePanel gamePanel) {
        this.snake = snake;
        this.gamePanel = gamePanel;
    }

    public void setUpInput (JComponent component) {

        im = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        am = component.getActionMap();

        //UP
        im.put(KeyStroke.getKeyStroke("W"), "up");
        im.put(KeyStroke.getKeyStroke("UP"), "up");
        am.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (snake.getVelocityY() != 1) { snake.setDirection(0, -1); }
            }
        } );

        //DOWN
        im.put(KeyStroke.getKeyStroke("S"), "down");
        im.put(KeyStroke.getKeyStroke("DOWN"), "down");
        am.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (snake.getVelocityY() != -1) { snake.setDirection(0, 1); }
            }
        } );

        //LEFT
        im.put(KeyStroke.getKeyStroke("A"), "left");
        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (snake.getVelocityX()!= 1) { snake.setDirection(-1, 0); }
            }
        } );

        //RIGHT
        im.put(KeyStroke.getKeyStroke("D"), "right");
        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (snake.getVelocityX() != -1) { snake.setDirection(1, 0); } 
            }
        } );

        //PAUSE
        im.put(KeyStroke.getKeyStroke("P"), "pause");
        am.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.togglePause();
            }
        } );

    }

}
