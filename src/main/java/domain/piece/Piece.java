package domain.piece;

import domain.piece.attribute.Color;
import domain.piece.attribute.point.Direction;
import domain.piece.attribute.point.Point;
import domain.piece.kind.PieceStatus;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class Piece implements Movable {

    protected Point point;
    protected final Color color;

    protected Piece(final Point point, final Color color) {
        this.point = point;
        this.color = color;
    }

    public abstract PieceStatus getStatus();

    public void move(final Point point) {
        this.point = point;
    }

    public boolean isEqualPoint(final Point point) {
        return this.point.equals(point);
    }

    public boolean sameColor(final Piece piece) {
        return this.color == piece.color;
    }

    public boolean isBlack() {
        return this.color == Color.BLACK;
    }

    public boolean isWhite() {
        return this.color == Color.WHITE;
    }

    public boolean isDirectionStraight(final Point point) {
        return this.point.calculate(point)
                         .isStraight();
    }

    public boolean isDirectionDiagonal(final Point point) {
        return this.point.calculate(point)
                         .isDiagonal();
    }


    protected final boolean hasAnyPieceInPath(final Point endPoint, final Pieces pieces) {
        final Direction direction = this.point.calculate(endPoint);
        final Point pathPoint = direction.movePoint(this.point);

        final Stream<Point> pathPoints = Stream.iterate(
                pathPoint,
                direction::canMovePoint,
                direction::movePoint);
        
        return pathPoints
                .anyMatch(pieces::containPieceWithPoint);
    }

    protected final boolean hasAnyPiece(final Point endPoint, final Pieces pieces) {
        return pieces.containPieceWithPoint(endPoint);
    }

    protected final boolean hasFriendPiece(final Point endPoint, final Pieces pieces) {
        final Optional<Piece> optionalPiece = pieces.findPieceWithPoint(endPoint);
        if (optionalPiece.isEmpty()) {
            return false;
        }
        final Piece toPiece = optionalPiece.get();
        return sameColor(toPiece);
    }


    public Point getPoint() {
        return this.point;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final Piece piece)) return false;
        return Objects.equals(point, piece.point) && color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, color);
    }

}
