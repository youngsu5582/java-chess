package domain.chess.repository;

import domain.chess.entity.ChessGameInfoEntity;

import java.util.Optional;

public interface ChessGameInfoRepository {
    boolean changeTurn(ChessGameInfoEntity chessGameInfoEntity);

    boolean deleteGameInfo(final int gameId);

    Optional<ChessGameInfoEntity> getChessGameInfoWithGameId(final int gameId);

    boolean create(ChessGameInfoEntity chessGameInfoEntity) throws IllegalStateException;
}
