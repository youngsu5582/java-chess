package domain.chess.util;

import java.util.UUID;

public class PieceIdGenerator {
    private PieceIdGenerator() {
    }

    public static String generate() {
        return UUID.randomUUID()
                   .toString();
    }
}
