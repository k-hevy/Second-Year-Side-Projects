package objects;

import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;

public class PowerUps {

    int tileSize;
    int boardWidth, boardHeight;
    Tile powerUp;
    Random random;

    public PowerUps (int tileSize, int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tileSize = tileSize;
        random = new Random();
        powerUp = new Tile(0,0);
    }

    public void randomizePowerUp (Snake snake, Obstacle obstacle) {

        boolean onSnake;
        boolean onObstacle;

        int attempts = 0;

        do {

            onSnake = false;
            onObstacle = false;

            powerUp.setX(random.nextInt(boardWidth / tileSize));
            powerUp.setY(random.nextInt(boardHeight / tileSize));

            for (Tile part : snake.getBody()) {
                if (part.getX() == powerUp.getX() &&
                    part.getY() == powerUp.getY()) {
                        onSnake = true;
                        break;
                }
            }

            for (Tile wall : obstacle.getObstacles()) {
                if (wall.getX() == powerUp.getX() &&
                    wall.getY() == powerUp.getY()) {
                        onObstacle = true;
                        break;
                }

            }

            attempts++;

        } while ((onSnake || onObstacle) && attempts < 50);
        
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(powerUp.getX() * tileSize, powerUp.getY() * tileSize, tileSize, tileSize);
    }

    public boolean isCollected(Snake snake) {
        return snake.getHead().getX() == powerUp.getX() && snake.getHead().getY() == powerUp.getY();
    }

    public void reset() { 
        powerUp = new Tile(-1, -1);
    }

    public long scheduleNextPowerUp () {
        long randomSec = 15 + (long) random.nextInt(6);
        return System.currentTimeMillis() + (randomSec * 1000);
    }


}
