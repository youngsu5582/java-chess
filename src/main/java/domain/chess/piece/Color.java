package domain.chess.piece;

import java.util.Arrays;

public enum Color {
    BLACK("black"),
    WHITE("white");
    private final String value;

    Color(final String value) {
        this.value = value;
    }

    public boolean isBlack() {
        return this == BLACK;
    }

    public boolean isWhite() {
        return this == WHITE;
    }

    public Color reverse() {
        return switch (this) {
            case BLACK -> WHITE;
            case WHITE -> BLACK;
        };
    }

    public String getValue() {
        return this.value;
    }

    public static final Color from(final String value) {
        return Arrays.stream(values())
                     .filter(color -> color.getValue()
                                           .equals(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException(String.format("%s 은 Color 에 없습니다", value)));
    }
}