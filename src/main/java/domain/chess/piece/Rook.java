package domain.chess.piece;

import domain.chess.Color;
import domain.chess.Direction;
import domain.chess.Point;

import java.util.List;

import static domain.chess.Direction.*;

public class Rook extends Piece {
    private static final List<Direction> DIRECTION_LIST = List.of(UP, DOWN, RIGHT, LEFT);
    private static final double SCORE = 5.0;


    public Rook(final Point point, final Color color) {
        super(point, color);
    }

    @Override
    public double getScore(final List<Piece> pieces) {
        return SCORE;
    }

    @Override
    public PieceStatus getStatus() {
        return PieceStatus.ROOK;
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
