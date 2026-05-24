package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import java.util.Random;

public class Obstacle {
    

    int x, y, tileSize;

    ArrayList<Tile> obstacles;
    Random random;
    Snake snake;

    public Obstacle(Snake snake, int tileSize) {
        
        this.snake = snake;
        this.tileSize = tileSize;
        obstacles = new ArrayList<Tile>();
        random =  new Random();
    }

    public void drawObstacles(Graphics g, int tileSize) {
        System.out.println("lol");

        g.setColor(Color.PINK);

        for (Tile part : obstacles) {
            g.fill3DRect(part.getX() * tileSize, part.getY() * tileSize, tileSize, tileSize, true);
        }
    }

    public void generateObstacles() {
        for (int i =0; i < 5; i++) {
            int x = random.nextInt(tileSize);
            int y = random.nextInt(tileSize);

            obstacles.add(new Tile(x, y));
        }
    }

    public boolean checkSnakeObtstacleCollision(Snake snake) {

        for (Tile part : obstacles) {
            if (snake.getHead().getX() == part.getX() && snake.getHead().getY() == part.getY()) {
                return true;
            }
        }

        return false;
    }

    public void reset() {
        obstacles.clear();
        generateObstacles();
    }
    
}
