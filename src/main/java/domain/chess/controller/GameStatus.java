package domain.chess.controller;

public enum GameStatus {
    GAME_READY,
    GAME_START,
    GAME_PLAY,
    GAME_STATUS,
    GAME_END;

    public boolean playAble() {
        return this != GAME_END;
    }
}
