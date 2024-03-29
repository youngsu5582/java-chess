package domain.chess;

public record ChessGameInfo(int chessGameId, Color color) {
    public static ChessGameInfo startWhite(final int chessGameId) {
        return new ChessGameInfo(chessGameId, Color.WHITE);
    }

    public Color getReverseColor() {
        return color.reverse();
    }
}
