package domain.chess.piece;

import domain.chess.Color;
import domain.chess.Direction;
import domain.chess.Point;
import domain.chess.Rank;

import java.util.List;

import static domain.chess.Direction.*;

public class Pawn extends Piece {
    private static final List<Direction> BLACK_DIRECTION_LIST = List.of(DOWN, DOWN_LEFT, DOWN_RIGHT);
    private static final List<Direction> WHITE_DIRECTION_LIST = List.of(UP, UP_LEFT, UP_RIGHT);
    private static final Rank WHITE_START_POSITION = Rank.TWO;
    private static final Rank BLACK_START_POSITION = Rank.SEVEN;
    private static final Double SCORE = 1.0;

    private static final Double SAME_FILE_SCORE = 0.5;

    public Pawn(final Point point, final Color color) {
        super(point, color);
    }

    public boolean canMove(final Point movePoint, final List<Piece> piecesList) {
        final PieceChecker checker = new PieceChecker(piecesList);
        return canMovePoint(movePoint, checker) || canMovePointWithAttack(movePoint, checker);
    }

    private boolean canMovePoint(final Point movePoint, final PieceChecker checker) {
        final Direction direction = this.point.calculate(movePoint);
        if (notContainDirection(direction) || direction.isDiagonal()) {
            return false;
        }
        return singleCase(movePoint, checker) || doubleCase(movePoint, checker);
    }

    private boolean singleCase(final Point movePoint, final PieceChecker checker) {
        return canMovePointOne(movePoint) && notExistPiece(movePoint, checker);
    }

    private boolean doubleCase(final Point movePoint, final PieceChecker checker) {
        final Direction direction = this.point.calculate(movePoint);
        if (direction.isDiagonal()) {
            return false;
        }
        return doubleCase(movePoint, direction) && notExistPieces(checker, direction.movePoint(this.point), movePoint);
    }

    private boolean doubleCase(final Point point, final Direction direction) {
        final Point movedPoint = direction.movePoint(this.point);
        if (this.point.getRankIndex() == getStartIndex()) {
            return direction.movePoint(movedPoint)
                            .equals(point);
        }
        return false;
    }

    private boolean canMovePointWithAttack(final Point movePoint, final PieceChecker checker) {
        final Direction direction = this.point.calculate(movePoint);
        if (notContainDirection(direction) || direction.isVertical()) {
            return false;
        }
        return canMovePointOne(movePoint) && hasEnemyPiece(movePoint, checker);
    }

    private boolean notContainDirection(final Direction direction) {
        return !containDirection(direction);
    }

    private boolean containDirection(final Direction direction) {
        if (this.isBlack()) {
            return BLACK_DIRECTION_LIST.contains(direction);
        }
        return WHITE_DIRECTION_LIST.contains(direction);
    }

    private int getStartIndex() {
        if (this.isBlack()) {
            return BLACK_START_POSITION.ordinal();
        }
        return WHITE_START_POSITION.ordinal();
    }

    public double getScore(final List<Piece> pieces) {
        if (hasEqualFilePointAndFriend(pieces)) {
            return SAME_FILE_SCORE;
        }
        return SCORE;
    }

    private boolean hasEqualFilePointAndFriend(final List<Piece> pieces) {
        return pieces.stream()
                     .filter(piece -> piece.isEqualColor(this.color))
                     .anyMatch(piece -> piece.isEqualFile(this.point));
    }

    @Override
    public PieceStatus getStatus() {
        return PieceStatus.PAWN;
    }
}
