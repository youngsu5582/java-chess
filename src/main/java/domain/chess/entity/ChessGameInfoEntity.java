package domain.chess.entity;

import domain.chess.piece.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public record ChessGameInfoEntity(int chessGameId, Color color) {
    public static ChessGameInfoEntity valueOf(final int chessGameId, final Color color) {
        final String key = makeKey(chessGameId, color);
        if (!CACHE.containsKey(key)) {
            CACHE.put(key, new ChessGameInfoEntity(chessGameId, color));
        }
        return CACHE.get(key);
    }

    private static final BiFunction<Integer, String, String> makeKeyFunction
            = (integer, s) -> integer + s;
    private static final Map<String, ChessGameInfoEntity> CACHE = new HashMap<>();

    public static ChessGameInfoEntity startWhite(final int chessGameId) {
        return valueOf(chessGameId, Color.WHITE);
    }

    private static String makeKey(final int chessGameId, final Color color) {
        return makeKeyFunction.apply(chessGameId, color.getValue());
    }

    public ChessGameInfoEntity reverseColor() {
        return ChessGameInfoEntity.valueOf(chessGameId, color.reverse());
    }
}
