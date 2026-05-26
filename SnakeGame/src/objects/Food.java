package objects;
import java.util.Random;
import java.awt.*;

public class Food {

    Tile foodTile;
    Random random;
    Snake snake;
    Obstacle obstacle;

    int boardwidth;
    int boardHeight;
    int tileSize;

    public Food (Snake snake, int boardWidth, int boardHeight, int tileSize) {
        this.snake = snake;
        this.boardwidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tileSize = tileSize;
        obstacle = new Obstacle(tileSize, boardWidth, boardHeight);

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

    public void draw(Graphics g, int tileSize) {
        //food
        g.setColor(Color.MAGENTA);
        g.fill3DRect(foodTile.getX() * tileSize, foodTile.getY() * tileSize, tileSize, tileSize, true);
    }

    public void reset() {
        randomizeFood();
    }

    public Tile getFoodTile() {
        return foodTile;
    }

}
