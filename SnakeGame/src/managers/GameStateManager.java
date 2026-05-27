package managers;

import objects.GameState;

public class GameStateManager {

    private GameState currentState;

    public GameStateManager() {
        currentState = GameState.MENU;
    }

    public void setGameState(GameState state) {
        currentState = state;
    }

    public GameState getGameState() {
        return currentState;
    }

}
