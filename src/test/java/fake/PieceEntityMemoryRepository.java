package fake;

import domain.chess.entity.PieceEntity;
import domain.chess.repository.PieceEntityRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PieceEntityMemoryRepository implements PieceEntityRepository {
    private final Map<String, PieceEntity> map;

    @Override
    public boolean savePieceEntity(final PieceEntity pieceEntity) {
        map.put(pieceEntity.pieceId(), pieceEntity);
        return true;
    }

    public PieceEntityMemoryRepository() {
        this.map = new HashMap<>();
    }

    @Override
    public List<PieceEntity> findAllByGameId(final int gameId) {
        return this.map.values()
                       .stream()
                       .filter(entity -> entity.gameId() == gameId)
                       .toList();
    }

    @Override
    public boolean deletePiece(final String pieceId) {
        if (map.containsKey(pieceId)) {
            map.remove(pieceId);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAllByGameId(final int gameId) {
        final List<PieceEntity> entities = findAllByGameId(gameId);
        entities.stream()
                .map(PieceEntity::pieceId)
                .forEach(map::remove);
        return true;
    }

    @Override
    public boolean updatePiece(final PieceEntity pieceEntity) {
        if (!map.containsKey(pieceEntity.pieceId())) {
            return false;
        }
        map.put(pieceEntity.pieceId(), pieceEntity);
        return true;
    }

    @Override
    public Optional<PieceEntity> findOneByPieceId(final String pieceId) {
        return Optional.ofNullable(map.get(pieceId));
    }
}

