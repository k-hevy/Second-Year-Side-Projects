package user_interface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import objects.Food;
import objects.Snake;
import objects.Tile;
import objects.GameState;

import Controls.InputManager;



public class GamePanel extends JPanel implements ActionListener {

    private SnakeGame parent;

    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    final int startDelay = 140;
    int delay;
    final int minDelay = 90;
    int level;

    CardLayout layout;
    JPanel mainPanel;

    int score;
    private int highscore;
    BufferedReader br;

    Snake snake;
    Food food;
    Tile tile;

    InputManager input;
    // Game logic
    Timer gameLoop;

    // Constructor
    GamePanel (SnakeGame parent, int boardWidth, int boardHeight) {

        this.parent = parent;

        setFocusable(true);
        requestFocusInWindow();

        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        setFocusable(true);

        snake = new Snake(12, 5);
        food = new Food(snake, boardWidth, boardHeight, tileSize);

        input = new InputManager(snake, this);
        input.setUpInput(this);

        loadHighscore();

        level = 1;
        delay = 140;

        gameLoop = new Timer(startDelay, this);
        gameLoop.start();

    }

    //Flow
    @Override 
    public void actionPerformed(ActionEvent e) {

        update();
        repaint();
        
    }

    public boolean gameOver() {

        for (int i = 0; i < snake.getLength(); i++) {

            Tile snakePart = snake.getBody().get(i);

            if (snake.getHead().getX() == snakePart.getX() && snake.getHead().getY() == snakePart.getY()) {
                return true;
            }

        }

        if (snake.getHead().getX() >= boardWidth/tileSize || snake.getHead().getX() * tileSize < 0 || 
                snake.getHead().getY() >= boardHeight/tileSize || snake.getHead().getY() * tileSize < 0) {
                return true;
            }

        return false;
    }

    public void resetGame() {

        score = 0;
        snake.reset();
        food.reset();
        gameLoop.start();
        gameLoop.setDelay(startDelay);
        delay = 140;
        level = 1;
        repaint();

    }

//background initializing

    public void updateHighscore () {
        try (FileWriter fw = new FileWriter("highscore.txt")) {
            fw.write(String.valueOf(score));
            highscore = score;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void loadHighscore () {

        File file = new File("highscore.txt");

        if (!file.exists()) {
                highscore = 0;
                return;
            }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            highscore = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            highscore = 0;
        }
    }

    public void togglePause () {
        if (parent.getGameState() == GameState.PLAYING) {
            parent.setGameState(GameState.PAUSED);
        } else if (parent.getGameState()== GameState.PAUSED) {
            parent.setGameState(GameState.PLAYING);
        }
    }
    
    // Game Logic
    public void update() {

        if (gameOver()) {
                gameLoop.stop();
                parent.showGameOver();
            }

        if (parent.getGameState() != GameState.PLAYING) return;

        snake.move();

        if (snake.eats(food)) {
            score++;
            food.randomizeFood();
            snake.grow();

            if (score % 5 == 0 && delay > minDelay) {
                level++;
                delay -= 4;
                gameLoop.setDelay(delay);
            }

        }

        if (score > highscore) updateHighscore();
        

    }   

    public void render(Graphics g) {

    snake.draw(g, tileSize);
    food.draw(g, tileSize);

    g.setColor(Color.BLUE);
    
    // score
    g.setFont(new Font("Arial", Font.PLAIN, 16));
    drawHudAndScores(g);

    if (parent.getGameState() == GameState.PAUSED) drawPausedOverlay(g);

    }

    // visual painting
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        render(g);
    
    }

    public void drawHudAndScores(Graphics g) {
        for (int i =0; i < boardWidth/tileSize; i++) {
                // x1, y1, x2, y2
                g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
                g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
            }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, tileSize - 16, tileSize);
        g.drawString("Highscore: " + highscore, tileSize - 16 , tileSize + 25);
        g.drawString("Level: " + level, tileSize - 16 , tileSize + 50);
        g.drawString("Speed: " + delay, tileSize - 16 , tileSize + 75);
        
    }

    private void drawPausedOverlay(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));

        FontMetrics fm = g.getFontMetrics();

        String text = "PAUSED";
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = getHeight()/2;

        g.drawString(text, x, y);
        g.drawString("Press 'P' To Continue.", getWidth() - 450, getHeight()/2 + 40);
    }

}
