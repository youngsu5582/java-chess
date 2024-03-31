package domain.chess.repository;

import domain.chess.entity.PieceEntity;

import java.util.List;
import java.util.Optional;

public interface PieceEntityRepository {
    boolean savePieceEntity(final PieceEntity pieceEntity);

    List<PieceEntity> findAllByGameId(final int gameId);


    boolean deletePiece(final String pieceId);

    boolean deleteAllByGameId(final int gameId);

    boolean updatePiece(final PieceEntity pieceEntity);

    Optional<PieceEntity> findOneByPieceId(final String pieceId);
}