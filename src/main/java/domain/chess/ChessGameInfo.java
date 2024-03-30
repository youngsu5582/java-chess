package domain.chess;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public record ChessGameInfo(int chessGameId, Color color) {
    public static ChessGameInfo valueOf(final int chessGameId, final Color color) {
        final String key = makeKey(chessGameId, color);
        if (!CACHE.containsKey(key)) {
            CACHE.put(key, new ChessGameInfo(chessGameId, color));
        }
        return CACHE.get(key);
    }

    private static final BiFunction<Integer, String, String> makeKeyFunction
            = (integer, s) -> integer + s;
    private static final Map<String, ChessGameInfo> CACHE = new HashMap<>();

    public static ChessGameInfo startWhite(final int chessGameId) {
        return valueOf(chessGameId, Color.WHITE);
    }

    private static String makeKey(final int chessGameId, final Color color) {
        return makeKeyFunction.apply(chessGameId, color.getValue());
    }

    public ChessGameInfo reverseColor() {
        return ChessGameInfo.valueOf(chessGameId, color.reverse());
    }
}
