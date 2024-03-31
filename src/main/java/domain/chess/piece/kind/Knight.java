package domain.chess.piece.kind;

import domain.chess.piece.Color;
import domain.chess.piece.Direction;
import domain.chess.piece.Point;
import domain.chess.piece.Piece;
import domain.chess.piece.PieceChecker;
import domain.chess.piece.PieceStatus;

import java.util.List;

import static domain.chess.piece.Direction.*;

public class Knight extends Piece {
    private static final List<Direction> DIRECTION_LIST = List.of(
            UP_UP_LEFT,
            UP_UP_RIGHT,
            RIGHT_UP_RIGHT,
            RIGHT_DOWN_RIGHT,
            DOWN_DOWN_LEFT,
            DOWN_DOWN_RIGHT,
            LEFT_UP_LEFT,
            LEFT_DOWN_LEFT);
    private static final double SCORE = 2.5;

    public Knight(final Point point, final Color color) {
        super(point, color);
    }

    public Knight(final Point point, final Color color, final String pieceId) {
        super(point, color, pieceId);
    }

    public boolean canMove(final Point movePoint, final List<Piece> pieceList) {
        return canMovePoint(movePoint) && hasEnemyPieceOrEmpty(movePoint, new PieceChecker(pieceList));
    }

    private boolean canMovePoint(final Point movePoint) {
        return DIRECTION_LIST.stream()
                             .filter(direction -> direction.canMoveOnePoint(this.point))
                             .map(direction -> direction.movePoint(this.point))
                             .anyMatch(movePoint::equals);
    }

    @Override
    public double getScore(final List<Piece> pieces) {
        return SCORE;
    }

    @Override
    public PieceStatus getStatus() {
        return PieceStatus.KNIGHT;
    }
}
