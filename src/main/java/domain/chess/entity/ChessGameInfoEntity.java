package domain.chess.entity;

import domain.chess.piece.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public record ChessGameInfoEntity(int chessGameId, Color color) {
    private static final BiFunction<Integer, String, String> MAKE_KEY_FUNCTION
            = (gameId, colorValue) -> gameId + colorValue;
    private static final Map<String, ChessGameInfoEntity> CACHE = new HashMap<>();

    public static ChessGameInfoEntity valueOf(final int chessGameId, final Color color) {
        final String key = makeKey(chessGameId, color);

        return CACHE.computeIfAbsent(key, k -> new ChessGameInfoEntity(chessGameId, color));
    }


    public static ChessGameInfoEntity startWhite(final int chessGameId) {
        return valueOf(chessGameId, Color.WHITE);
    }

    private static String makeKey(final int chessGameId, final Color color) {
        return MAKE_KEY_FUNCTION.apply(chessGameId, color.getValue());
    }

    public ChessGameInfoEntity reverseColor() {
        return ChessGameInfoEntity.valueOf(chessGameId, color.reverse());
    }
}
