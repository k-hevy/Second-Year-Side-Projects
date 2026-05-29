package objects;
import java.util.Random;

import managers.EntityManager;

import java.awt.*;

public class Food extends Entity {

    Tile foodTile;
    Random random;
    Snake snake;
    Obstacle obstacle;
    EntityManager entityManager;

    int boardwidth;
    int boardHeight;
    int tileSize;

    public Food (Snake snake, int boardWidth, int boardHeight, int tileSize) {
        this.snake = snake;
        this.boardwidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tileSize = tileSize;
        obstacle = new Obstacle(tileSize, boardWidth, boardHeight);
        entityManager = new EntityManager();

        random = new Random();
        foodTile = new Tile(0, 0);

        randomizeFood();

    }

    public void randomizeFood() {

        boolean onSnake;
        boolean onObstacle;

        do {

            onSnake = false;
            onObstacle = false;

            foodTile.setX(random.nextInt(boardwidth / tileSize));
            foodTile.setY(random.nextInt(boardHeight / tileSize));

            for (Tile part : snake.getBody()) {

                if (part.getX() == foodTile.getX() && part.getY() == foodTile.getY()) {
                    onSnake = true;
                    break;
                }

            }

            if (obstacle.foodSpawnsOnObstacle(foodTile)) {
                onObstacle = true;
                break;
            }

        } while (onSnake || onObstacle);
    } 

    public void reset() {
        randomizeFood();
    }

    public Tile getFoodTile() {
        return foodTile;
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fill3DRect(foodTile.getX() * tileSize, foodTile.getY() * tileSize, tileSize, tileSize, true);
    }

}
