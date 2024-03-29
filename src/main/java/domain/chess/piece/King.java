package domain.chess.piece;

import domain.chess.Color;
import domain.chess.Point;

import java.util.List;

public class King extends Piece {
    private static final double SCORE = 0;

    public King(final Point point, final Color color) {
        super(point, color);
    }

    public King(final Point point, final Color color, final String pieceId) {
        super(point, color, pieceId);
    }

    @Override
    public PieceStatus getStatus() {
        return PieceStatus.KING;
    }

    @Override
    public double getScore(final List<Piece> pieces) {
        return SCORE;
    }

    public boolean canMove(final Point movePoint, final List<Piece> pieceList) {
        return canMovePointOne(movePoint) && hasEnemyPieceOrEmpty(movePoint, new PieceChecker(pieceList));
    }
}
