package domain.chess.piece;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

    @Override
    public boolean isNotEqualColorInPoint(final Color color, final Point point) {
        final Optional<Piece> optionalPiece = this.findPieceWithPoint(point);
        if (optionalPiece.isEmpty()) {
            return false;
        }
        final Piece toPiece = optionalPiece.get();
        return !toPiece.isEqualColor(color);
    }

    @Override
    public boolean isEqualColorInPoint(final Color color, final Point point) {
        final Optional<Piece> optionalPiece = this.findPieceWithPoint(point);
        if (optionalPiece.isEmpty()) {
            return false;
        }
        final Piece toPiece = optionalPiece.get();
        return toPiece.isEqualColor(color);
    }

    @Override
    public boolean isEmptyPoint(final Point point) {
        final Optional<Piece> optionalPiece = this.findPieceWithPoint(point);
        return optionalPiece.isEmpty();
    }


    public final boolean hasAnyPieceInPath(final Point startPoint, final Point endPoint) {
        final Direction direction = startPoint.calculate(endPoint);
        final Point pathPoint = direction.movePoint(startPoint);

        final Stream<Point> pathPoints = Stream.iterate(
                pathPoint,
                movePoint -> direction.canMovePoint(movePoint) && movePoint.notEquals(endPoint),
                direction::movePoint);

        return pathPoints
                .anyMatch(this::containPieceWithPoint);
    }

}
