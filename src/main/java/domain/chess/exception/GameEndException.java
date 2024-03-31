package domain.chess.exception;

public class GameEndException extends RuntimeException {
    private static final String MESSAGE = "%s의 승리로 게임이 끝났습니다";

    public GameEndException(final String input) {
        super(String.format(MESSAGE, input));
    }
}
