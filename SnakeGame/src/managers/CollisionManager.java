package managers;

import objects.Food;
import objects.Obstacle;
import objects.Snake;
import objects.Tile;
import user_interface.GamePanel;


public class CollisionManager {

    private GamePanel gamePanel;
    private PowerUpManager powerUpManager;
    private SoundManager soundManager;
    private Obstacle obstacle;
    private Snake snake;
    private Food food;
    private int boardWidth;
    private int boardHeight;
    private int tileSize;
    
    public CollisionManager(SoundManager soundManager, GamePanel gamePanel, PowerUpManager powerUpManager,
        Obstacle obstacle, Snake snake, Food food, int boardWidth, int boardHeight,
        int tileSize) {
        this.gamePanel = gamePanel;
        this.soundManager = soundManager;
        this.powerUpManager = powerUpManager;
        this.obstacle = obstacle;
        this.snake = snake;
        this.food = food;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tileSize = tileSize;
    }

    public void update() {
        checkSelfCollision();
        checkFoodCollision();
        checkPowerUpCollision();
        checkWallCollision();
        checkObstacleCollision();
    }

    public void checkFoodCollision () {
        if (snake.getHead().getX() == food.getFoodTile().getX() &&
            snake.getHead().getY() == food.getFoodTile().getY()) {
                gamePanel.handleFoodEaten();
                SoundManager.playSound(SoundManager.SoundEffect.EAT_FOOD);
        }
    }

    public void checkSelfCollision() {

        for (Tile snakePart : snake.getBody()) {
            if (snake.getHead().getX() == snakePart.getX() &&
                snake.getHead().getY() == snakePart.getY()
            ) {
                gamePanel.gameOver(); 
                SoundManager.playSound(SoundManager.SoundEffect.COLLISION);
            }
        }
    }

    public void checkPowerUpCollision() {
        if (powerUpManager.getPowerUps() != null && 
            snake.getHead().getX() == powerUpManager.getPowerUps().getX() &&
            snake.getHead().getY() == powerUpManager.getPowerUps().getY()) {
                powerUpManager.applyPowerUp();
                SoundManager.playSound(SoundManager.SoundEffect.POWERUP);
        }
    }

    public void checkWallCollision() {

        if (snake.getHead().getX() >= boardWidth/tileSize || 
            snake.getHead().getX() * tileSize < 0 || 
            snake.getHead().getY() >= boardHeight/tileSize || 
            snake.getHead().getY() * tileSize < 0
        ) {
            gamePanel.gameOver(); 
            SoundManager.playSound(SoundManager.SoundEffect.GAME_OVER);
        }
        
    }

    public void checkObstacleCollision() {
        for (Tile part : obstacle.getObstacles()) {
            if (snake.getHead().getX() == part.getX() && snake.getHead().getY() == part.getY()
            ) {
            gamePanel.gameOver(); 
            SoundManager.playSound(SoundManager.SoundEffect.GAME_OVER);
        }
        }
    }

}
