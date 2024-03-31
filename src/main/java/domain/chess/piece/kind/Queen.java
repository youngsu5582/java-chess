package domain.chess.piece.kind;

import domain.chess.piece.Color;
import domain.chess.piece.Point;
import domain.chess.piece.Piece;
import domain.chess.piece.PieceChecker;
import domain.chess.piece.PieceStatus;

import java.util.List;

public class Queen extends Piece {
    public Queen(final Point point, final Color color) {
        super(point, color);
    }

    public Queen(final Point point, final Color color, final String pieceId) {
        super(point, color, pieceId);
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
