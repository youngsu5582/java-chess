package repository;

import domain.chess.ChessGameInfo;

import java.util.Optional;

public interface ChessGameInfoRepository {
    boolean changeTurn(ChessGameInfo chessGameInfo);

    Optional<ChessGameInfo> getChessGameInfoWithGameId(final int gameId);

    boolean create(ChessGameInfo chessGameInfo);
}
