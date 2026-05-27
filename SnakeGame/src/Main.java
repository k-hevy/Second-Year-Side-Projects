import user_interface.SnakeGame;

public class Main {
    public static void main(String[] args) {
        int boardwidth = 600;
        int boardheight = boardwidth; 
        new SnakeGame(boardwidth, boardheight);

        /*frame.setVisible(true); // makes the framw visible
        frame.setSize(boardwidth, boardheight); // sets frame size
        frame.setLocationRelativeTo(null); // centers the frame upon execution
        frame.setResizable(false); // disables the frame size changing
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allows the frame to be closed
        
        SnakeGame snakeGame = new SnakeGame(boardwidth, boardheight);
        frame.add(snakeGame); // adds the Jframe inside the frame
        frame.pack(); // To exclude the title bar from the dimension
        snakeGame.requestFocus();
        */

    }

    
}
