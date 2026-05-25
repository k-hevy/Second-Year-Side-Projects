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
import objects.Obstacle;
import objects.PowerUps;

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
    Obstacle obstacle;
    PowerUps powerUps;

    InputManager input;

    private boolean boostActive = false;
    private long powerUpEndTime = 0;
    private long nextPowerUpSpawnTime = 0;

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
        powerUps = new PowerUps(tileSize, boardWidth, boardHeight);
        obstacle = new Obstacle(tileSize, boardWidth, boardHeight);
        obstacle.generateObstacles();
        nextPowerUpSpawnTime = powerUps.scheduleNextPowerUp();

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

        if (snake.collidedWithSelf(snake)) return true;
        if (snake.collidedWithWall(snake, boardWidth, boardHeight, tileSize)) return true;
        if (obstacle.checkSnakeObstacleCollision(snake)) return true; 

        return false;
    } // also move this to snake

    public void resetGame() {

        score = 0;
        level = 1;
        nextPowerUpSpawnTime = powerUps.scheduleNextPowerUp();

        snake.reset();
        food.reset();
        obstacle.reset();
        powerUps.reset();

        gameLoop.start();
        gameLoop.setDelay(startDelay);
        delay = 140;
        
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

        if (snake.eats(food)) {
            score++;
            food.randomizeFood();
            snake.grow();

            if (score % 5 == 0 && delay > minDelay) {
                level++;
                delay -= 4;
                if (!boostActive) gameLoop.setDelay(delay);
            }

        }

        if (score > highscore) updateHighscore();

        if (System.currentTimeMillis() > nextPowerUpSpawnTime) {
            powerUps.randomizePowerUp(snake, obstacle);
            nextPowerUpSpawnTime = powerUps.scheduleNextPowerUp();
        }
        
        if (powerUps.isCollected(snake)) {
            powerUps.reset();
            boostActive = true;
            powerUpEndTime = System.currentTimeMillis() + 5000;
            gameLoop.setDelay(80);
        }

        if (boostActive && System.currentTimeMillis() > powerUpEndTime) {
            boostActive = false;
            gameLoop.setDelay(delay);
        }

        snake.move();

    }   

    public void render(Graphics g) {

    snake.draw(g, tileSize);
    food.draw(g, tileSize);
    obstacle.drawObstacles(g, tileSize);
    powerUps.draw(g);

    g.setColor(Color.BLUE);
    
    // score
    g.setFont(new Font("Arial", Font.PLAIN, 16));
    drawHudAndScores(g);

    if (parent.getGameState() == GameState.PAUSED) drawPausedOverlay(g);

    }

    //features 

    
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
