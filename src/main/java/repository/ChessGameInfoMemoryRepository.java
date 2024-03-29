package repository;

import domain.chess.ChessGameInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ChessGameInfoMemoryRepository implements ChessGameInfoRepository {


    private final Map<Integer, ChessGameInfo> map;

    public ChessGameInfoMemoryRepository() {
        this.map = new HashMap<>();
    }

    @Override

    public boolean changeTurn(final ChessGameInfo chessGameInfo) {
        final int chessGameId = chessGameInfo.chessGameId();
        if (map.containsKey(chessGameInfo.chessGameId())) {
            map.put(chessGameInfo.chessGameId(), new ChessGameInfo(chessGameId, chessGameInfo.getReverseColor()));
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteGameInfo(final int gameId) {
        if (map.containsKey(gameId)) {
            map.remove(gameId);
            return true;
        }
        return false;
    }

    @Override
    public boolean create(final ChessGameInfo chessGameInfo) {
        if (map.containsKey(chessGameInfo.chessGameId())) {
            return false;
        }
        map.put(chessGameInfo.chessGameId(), chessGameInfo);
        return true;
    }

    @Override
    public Optional<ChessGameInfo> getChessGameInfoWithGameId(final int gameId) {
        return Optional.ofNullable(map.get(gameId));
    }
}
