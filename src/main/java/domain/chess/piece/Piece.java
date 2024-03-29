package domain.chess.piece;

import domain.chess.Color;
import domain.chess.Direction;
import domain.chess.Point;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class Piece implements Movable {

    protected Point point;
    protected final Color color;
    private final String pieceId;

    protected Piece(final Point point, final Color color, final String pieceId) {
        this.point = point;
        this.color = color;
        this.pieceId = pieceId;
    }

    protected Piece(final Point point, final Color color) {
        this.point = point;
        this.color = color;
        this.pieceId = PieceIdGenerator.generate();
    }

    public static Piece newInstance(final PieceStatus pieceStatus, final Point point, final Color color, final String pieceId) {
        return switch (pieceStatus) {
            case KING -> new King(point, color, pieceId);
            case PAWN -> new Pawn(point, color, pieceId);
            case ROOK -> new Rook(point, color, pieceId);
            case QUEEN -> new Queen(point, color, pieceId);
            case BISHOP -> new Bishop(point, color, pieceId);
            case KNIGHT -> new Knight(point, color, pieceId);
        };
    }

    public abstract PieceStatus getStatus();

    public void move(final Point point) {
        this.point = point;
    }

    public String toPointEntity() {
        return this.point.toEntity();
    }

    public String toColorEntity() {
        return this.color.getValue();
    }

    public String toKindEntity() {
        return this.getStatus()
                   .getName();
    }

    public boolean canMove(final Point movePoint) {
        return canMove(movePoint, List.of());
    }

    protected boolean canMovePointOne(final Point movePoint) {
        final Direction direction = this.point.calculate(movePoint);
        if (direction.canMovePoint(this.point)) {
            return direction.movePoint(this.point)
                            .equals(movePoint);
        }
        return false;
    }

    protected final boolean notExistPieceInPath(final Point endPoint, final PieceCheckable checker) {
        return !checker.hasAnyPieceInPath(this.point, endPoint);
    }

    protected final boolean notExistPieces(final PieceCheckable checker, final Point... points) {
        return Arrays.stream(points)
                     .allMatch(value -> notExistPiece(value, checker));
    }

    protected final boolean notExistPiece(final Point findPoint, final PieceCheckable checker) {
        return !checker.containPieceWithPoint(findPoint);
    }

    protected final boolean hasEnemyPiece(final Point point, final PieceCheckable checker) {
        return checker.isNotEqualColorInPoint(this.color, point);
    }

    protected final boolean hasEnemyPieceOrEmpty(final Point endPoint, final PieceCheckable checker) {
        return checker.isNotEqualColorInPoint(this.color, endPoint) || checker.isEmptyPoint(endPoint);
    }

    public boolean isEqualFile(final Point point) {
        return this.point.isEqualFile(point);
    }

    public boolean isEqualPoint(final Point point) {
        return this.point.equals(point);
    }

    public boolean isEqualColor(final Color color) {
        return this.color == color;
    }

    public boolean isBlack() {
        return this.color.isBlack();
    }

    public boolean isWhite() {
        return this.color.isWhite();
    }

    public abstract double getScore(List<Piece> pieces);

    public Point getPoint() {
        return this.point;
    }

    public String getPieceId() {
        return pieceId;
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
