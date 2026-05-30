package user_interface;
import java.awt.*;
import javax.swing.*;

import managers.GameStateManager;
import objects.GameState;


public class SnakeGame extends JFrame {

    JPanel mainPanel;
    CardLayout layout;

    GamePanel gamePanel;
    GameOverPanel gameOverPanel;
    MenuPanel menuPanel;
    
    GameState gameState;
    GameStateManager gameStateManager;


    public SnakeGame(int boardWidth, int boardHeight) {

        gameStateManager = new GameStateManager();
        gameStateManager.setGameState(GameState.MENU);

        layout = new CardLayout();
        mainPanel = new JPanel();

        mainPanel.setLayout(layout);

        gamePanel = new GamePanel(this, boardWidth, boardHeight, gameStateManager);
        menuPanel = new MenuPanel(this);
        gameOverPanel = new GameOverPanel(this);

        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(gamePanel, "GAME");
        mainPanel.add(gameOverPanel, "GAME_OVER");

        add(mainPanel);

        layout.show(mainPanel, "MENU");

        setTitle("Snake Game");
        setSize(boardWidth, boardHeight); // sets frame size
        setLocationRelativeTo(null); // centers the frame upon execution
        setResizable(false); // disables the frame size changing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allows the frame to be closed
        pack(); // To exclude the title bar from the dimension
        requestFocus();
        setVisible(true); // makes the framw visible

    }

    public void startGame() {
        gameStateManager.setGameState(GameState.PLAYING);
        gamePanel.resetGame();
        layout.show(mainPanel, "GAME");
        gamePanel.requestFocusInWindow();
    }

    public void showMenu () {
        gameStateManager.setGameState(GameState.MENU);
        layout.show(mainPanel, "MENU");
    }

    public void showGameOver () {
        gameStateManager.setGameState(GameState.GAME_OVER);
        layout.show(mainPanel, "GAME_OVER");
    }

}