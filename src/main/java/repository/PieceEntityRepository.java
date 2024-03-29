package repository;

import domain.chess.piece.PieceEntity;

import java.util.List;
import java.util.Optional;

public interface PieceEntityRepository {
    boolean savePieceEntity(final PieceEntity pieceEntity);

    List<PieceEntity> findAllByGameId(final int gameId);


    boolean deletePiece(final String pieceId);

    boolean updatePiece(final PieceEntity pieceEntity);

    Optional<PieceEntity> findOneByPieceId(final String pieceId);
}
