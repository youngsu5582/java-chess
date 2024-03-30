package domain.chess.piece;

public class CheckMateException extends RuntimeException {
    private static final String MESSAGE = "%s의 승리로 게임이 끝났습니다";

    public CheckMateException(final String input) {
        super(String.format(MESSAGE, input));
    }
}
