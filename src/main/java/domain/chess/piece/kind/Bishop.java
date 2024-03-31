package domain.chess.piece.kind;

import domain.chess.piece.Color;
import domain.chess.piece.Direction;
import domain.chess.piece.Point;
import domain.chess.piece.Piece;
import domain.chess.piece.PieceChecker;
import domain.chess.piece.PieceStatus;

import java.util.List;

import static domain.chess.piece.Direction.*;

public class Bishop extends Piece {
    private static final List<Direction> DIRECTION_LIST = List.of(DOWN_LEFT, DOWN_RIGHT, UP_LEFT, UP_RIGHT);
    private static final double SCORE = 3.0;

    public Bishop(final Point point, final Color color) {
        super(point, color);
    }

    public Bishop(final Point point, final Color color, final String pieceId) {
        super(point, color, pieceId);
    }

    @Override
    public PieceStatus getStatus() {
        return PieceStatus.BISHOP;
    }

    @Override
    public double getScore(final List<Piece> pieces) {
        return SCORE;
    }

    public boolean canMove(final Point movePoint, final List<Piece> pieceList) {
        final Direction direction = this.point.calculate(movePoint);
        if (DIRECTION_LIST.contains(direction)) {
            final PieceChecker checker = new PieceChecker(pieceList);
            return notExistPieceInPath(movePoint, checker) && hasEnemyPieceOrEmpty(movePoint, checker);
        }
        return false;
    }
}
