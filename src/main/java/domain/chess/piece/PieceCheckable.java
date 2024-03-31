package domain.chess.piece;

import java.util.Optional;

public interface PieceCheckable {

    Optional<Piece> findPieceWithPoint(final Point point);

    boolean containPieceWithPoint(final Point point);

    boolean isEqualColorInPoint(final Color color, final Point point);

    boolean isNotEqualColorInPoint(final Color color, final Point point);

    boolean isEmptyPoint(final Point point);

    boolean hasAnyPieceInPath(final Point startPoint, final Point endPoint);

}
