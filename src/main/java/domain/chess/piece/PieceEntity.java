package domain.chess.piece;

import domain.chess.Color;
import domain.chess.Point;

public record PieceEntity(String point, String color, String kind, String pieceId, int gameId) {
    public static PieceEntity fromPiece(final Piece piece, final int gameId) {
        final String point = piece.toPointEntity();
        final String color = piece.toColorEntity();
        final String kind = piece.toKindEntity();
        final String pieceId = piece.getPieceId();

        return new PieceEntity(point, color, kind, pieceId, gameId);
    }

    public static Piece toPiece(final PieceEntity pieceEntity) {
        final Point point = Point.from(pieceEntity.point);
        final Color color = Color.from(pieceEntity.color);
        final PieceStatus status = PieceStatus.from(pieceEntity.kind);
        return Piece.newInstance(status, point, color, pieceEntity.pieceId);
    }
}
