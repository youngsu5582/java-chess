package domain.chess.piece;

import domain.chess.Point;

import java.util.List;
import java.util.Optional;

public class PieceChecker implements PieceCheckable {
    private final List<Piece> value;

    public PieceChecker(final List<Piece> value) {
        this.value = value;
    }

    @Override
    public Optional<Piece> findPieceWithPoint(final Point point) {
        return value.stream()
                    .filter(piece -> piece.isEqualPoint(point))
                    .findAny();
    }

    @Override
    public boolean containPieceWithPoint(final Point point) {
        return findPieceWithPoint(point).isPresent();
    }
}
