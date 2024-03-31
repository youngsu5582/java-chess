package domain.chess.piece.kind;

import domain.chess.piece.Point;
import domain.chess.piece.Piece;

import java.util.List;

public interface Movable {

    boolean canMove(Point point, List<Piece> pieceList);
}
