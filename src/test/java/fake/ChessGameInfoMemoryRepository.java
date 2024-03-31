package fake;

import domain.chess.entity.ChessGameInfoEntity;
import domain.chess.repository.ChessGameInfoRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ChessGameInfoMemoryRepository implements ChessGameInfoRepository {


    private final Map<Integer, ChessGameInfoEntity> map;

    public ChessGameInfoMemoryRepository() {
        this.map = new HashMap<>();
    }

    @Override

    public boolean changeTurn(final ChessGameInfoEntity chessGameInfoEntity) {
        if (map.containsKey(chessGameInfoEntity.chessGameId())) {
            map.put(chessGameInfoEntity.chessGameId(), chessGameInfoEntity);
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
    public boolean create(final ChessGameInfoEntity chessGameInfoEntity) {
        if (map.containsKey(chessGameInfoEntity.chessGameId())) {
            throw new IllegalStateException();
        }
        map.put(chessGameInfoEntity.chessGameId(), chessGameInfoEntity);
        return true;
    }

    @Override
    public Optional<ChessGameInfoEntity> getChessGameInfoWithGameId(final int gameId) {
        return Optional.ofNullable(map.get(gameId));
    }
}
