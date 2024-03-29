package domain.chess.piece;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum PieceStatus {
    KING("K"),
    KNIGHT("N"),
    PAWN("P"),
    QUEEN("Q"),
    ROOK("R"),
    BISHOP("B");

    private final String name;

    PieceStatus(final String name) {
        this.name = name;
    }

    public static final PieceStatus from(final String name) {
        return Arrays.stream(values())
                     .filter(pieceStatus -> pieceStatus.name.equals(name))
                     .findAny()
                     .orElseThrow(() -> new IllegalArgumentException(String.format("기물 종류는 %s 입니다.", joinName())));
    }

    private static String joinName() {
        return Arrays.stream(values())
                     .map(PieceStatus::getName)
                     .collect(Collectors.joining(" , "));
    }

    public String getName() {
        return this.name;
    }
}
