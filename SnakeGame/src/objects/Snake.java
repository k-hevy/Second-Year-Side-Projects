package objects;
import java.util.ArrayList;
import java.awt.*;
import java.util.Random;



public class Snake {

    // Snake head and body
    private Tile head;
    private ArrayList<Tile> body;
    private int velocityX;
    private int velocityY;
    Random random;

    public Snake (int x, int y) {
        random = new Random();
        head = new Tile(x, y);
        body = new ArrayList<>();
        velocityX = 0;
        velocityY = 0;
    }

    public void move() {

        // moves snakebody
        for (int i = body.size()-1 ; i >= 0; i--) {
            Tile lastPart = body.get(i);

            if (i == 0) {
                lastPart.setX(head.getX());
                lastPart.setY(head.getY());
            } else {
                Tile secondLastPart  = body.get(i-1);
                lastPart.setX(secondLastPart.getX());
                lastPart.setY(secondLastPart.getY());
            }
        }

        // moves snakehead
        head.setX(head.getX() + velocityX);
        head.setY(head.getY() + velocityY);

    }

    public void grow() {
            body.add(new Tile(-1, -1));
    }

    public void draw(Graphics g, int tileSize) {
        // snake head
        g.setColor(Color.GREEN);
        g.fill3DRect(head.getX() * tileSize, head.getY() * tileSize, tileSize, tileSize, true);

        // snakebody
        for (int i = 0; i < body.size(); i++) {
            Tile snakePart = body.get(i);
            g.fill3DRect(snakePart.getX() * tileSize, snakePart.getY() * tileSize, tileSize, tileSize, true);
        }
    }

    public int getLength() {
        return body.size();
    }

    public Tile getHead() {
        return head;
    }

    public ArrayList<Tile> getBody() {
        return body;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setDirection(int x, int y) {
        velocityX = x;
        velocityY = y;
    }

    public void reset() {
        body.clear();
        head.setX(random.nextInt(24));
        head.setY(random.nextInt(24));
        setDirection(0, 0);
    }
}
