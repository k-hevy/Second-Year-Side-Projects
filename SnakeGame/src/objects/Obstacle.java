package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import java.util.Random;

public class Obstacle {
    int tileSize, boardWidth, boardHeight;
    ArrayList<Tile> obstacles;
    Random random;

    public Obstacle(int tileSize, int boardWidth, int boardHeight) {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.tileSize = tileSize;
        obstacles = new ArrayList<Tile>();
        random =  new Random();
    }

    public void drawObstacles(Graphics g, int tileSize) {

        g.setColor(Color.PINK);

        for (Tile part : obstacles) {
            g.fill3DRect(part.getX() * tileSize, part.getY() * tileSize, tileSize, tileSize, true);
        }
    }

    public void generateObstacles() {
        for (int i =0; i < 5; i++) {

            int x = random.nextInt(boardWidth / tileSize);
            int y = random.nextInt(boardHeight / tileSize);

            obstacles.add(new Tile(x, y));
        }
    }

    public boolean checkSnakeObstacleCollision(Snake snake) {

        for (Tile part : obstacles) {
            if (snake.getHead().getX() == part.getX() && snake.getHead().getY() == part.getY()) return true;
        }

        return false;
    }

    public void reset() {
        obstacles.clear();
        generateObstacles();
    }

    public ArrayList<Tile> getObstacles () {
        return obstacles;
    }

    public boolean foodSpawnsOnObstacle(Tile position) {

        for (Tile walls : obstacles) {
                if (walls.getX() == position.getX() &&  walls.getY() == position.getY()) return true;
            }
        return false;

    }
    
}
