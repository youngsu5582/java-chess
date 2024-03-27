package domain.chess.piece;

import domain.chess.Point;

import java.util.Optional;

public interface PieceCheckable {

    Optional<Piece> findPieceWithPoint(final Point point);

    boolean containPieceWithPoint(final Point point);
}
