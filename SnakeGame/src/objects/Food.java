package objects;
import java.util.Random;
import java.awt.*;

public class Food {

    Tile position;
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

        random = new Random();
        position = new Tile(0, 0);

        randomizeFood();

    }

    public void randomizeFood() {

        boolean onSnake;
        boolean onObstacle;

        do {

            onSnake = false;
            onObstacle = false;

            position.setX(random.nextInt(boardwidth / tileSize));
            position.setY(random.nextInt(boardHeight / tileSize));

            for (Tile part : snake.getBody()) {

                if (part.getX() == position.getX() && part.getY() == position.getY()) {
                    onSnake = true;
                    break;
                }

            }

            if (obstacle.foodSpawnsOnObstacle(position)) {
                onObstacle = true;
                break;
            }

        } while (onSnake || onObstacle);
        
    } 

    public void draw(Graphics g, int tileSize) {
        //food
        g.setColor(Color.MAGENTA);
        g.fill3DRect(position.getX() * tileSize, position.getY() * tileSize, tileSize, tileSize, true);
    }

    public void reset() {
        randomizeFood();
    }

    public Tile getFoodPosition() {
        return position;
    }

}
