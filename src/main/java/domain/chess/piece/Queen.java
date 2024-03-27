package domain.chess.piece;

import domain.chess.Color;
import domain.chess.Point;

import java.util.List;

public class Queen extends Piece {
    public Queen(final Point point, final Color color) {
        super(point, color);
    }

    private static final double SCORE = 9;

    @Override
    public PieceStatus getStatus() {
        return PieceStatus.QUEEN;
    }

    @Override
    public double getScore(final List<Piece> pieces) {
        return SCORE;
    }

    public boolean canMove(final Point movePoint, final List<Piece> pieceList) {
        final PieceChecker checker = new PieceChecker(pieceList);
        return notExistPieceInPath(movePoint, checker) && hasEnemyPieceOrEmpty(movePoint, checker);
    }
}
