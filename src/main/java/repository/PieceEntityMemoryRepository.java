package repository;

import domain.chess.piece.PieceEntity;

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
        return this.map.entrySet()
                       .stream()
                       .filter(pieceEntry -> pieceEntry.getValue()
                                                       .gameId() == gameId)
                       .map(Map.Entry::getValue)
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
        for (final var entrySet : map.entrySet()) {
            if (entrySet.getValue()
                        .gameId() == gameId) {
                map.remove(entrySet.getKey());
            }
        }
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

