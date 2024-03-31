package repository;

import domain.chess.piece.Color;
import domain.chess.piece.kind.Knight;
import domain.chess.piece.Piece;
import domain.chess.entity.PieceEntity;
import domain.chess.repository.PieceEntityRepository;
import fake.PieceEntityMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static fixture.PointFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class PieceEntityRepositoryTest {
    private PieceEntityRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = new PieceEntityMemoryRepository();
    }


    @Test
    @DisplayName("기물을 저장하면 참을 반환한다.")
    void true_if_create_piece_entity() {

        final var piece1 = new Knight(A3, Color.BLACK);
        final var pieceEntity1 = PieceEntity.fromPiece(piece1, 5);

        final var result = repository.savePieceEntity(pieceEntity1);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("게임 아이디에 해당하는 모든 PieceEntity 를 가져온다")
    void find_all_piece_by_gameId() {
        final var repository = new PieceEntityMemoryRepository();
        final var piece1 = new Knight(A3, Color.BLACK);
        final var piece2 = new Knight(A4, Color.BLACK);
        final var pieceEntity1 = PieceEntity.fromPiece(piece1, 3);
        final var pieceEntity2 = PieceEntity.fromPiece(piece2, 3);
        repository.savePieceEntity(pieceEntity1);
        repository.savePieceEntity(pieceEntity2);

        final var result = repository.findAllByGameId(3);

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("pieceId 를 통해 기물 삭제를 성공하면 참을 반환한다")
    void true_if_delete_piece_entity() {
        final var repository = new PieceEntityMemoryRepository();
        final var piece = new Knight(A3, Color.BLACK, "1245");

        final var pieceEntity1 = PieceEntity.fromPiece(piece, 3);
        repository.savePieceEntity(pieceEntity1);

        final var result = repository.deletePiece("1245");

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("없는 pieceId 를 통해 삭제하려면 거짓을 반환한다")
    void false_if_delete_not_exist_pieceId() {
        final var piece1 = new Knight(A3, Color.BLACK, "123existId");

        final var pieceEntity1 = PieceEntity.fromPiece(piece1, 5);
        repository.savePieceEntity(pieceEntity1);

        final var result = repository.deletePiece("1234xxId");

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("gameId 가 같은 모든 PieceEntity 를 삭제한다")
    void delete_all_by_gameId() {
        final var piece1 = new Knight(A3, Color.BLACK, "id123");
        final var piece2 = new Knight(A3, Color.BLACK, "id1234");

        repository.savePieceEntity(PieceEntity.fromPiece(piece1, 5));
        repository.savePieceEntity(PieceEntity.fromPiece(piece2, 3));
        repository.deleteAllByGameId(3);

        assertThat(repository.findAllByGameId(3)).isEmpty();
    }

    @Test
    @DisplayName("pieceId 를 통해 기물을 찾는다")
    void find_piece_by_pieceId() {
        final var piece = new Knight(A3, Color.BLACK, "idFind123");

        final var pieceEntity1 = PieceEntity.fromPiece(piece, 5);
        repository.savePieceEntity(pieceEntity1);

        final var result = repository.findOneByPieceId("idFind123");

        assertThat(result).contains(PieceEntity.fromPiece(piece, 5));
    }

    @Test
    @DisplayName("없는 pieceId 를 통해 기물을 찾을 시 empty 를 반환한다")
    void empty_if_find_with_not_exist_pieceId() {
        final var piece = new Knight(A3, Color.BLACK);

        final var pieceEntity1 = PieceEntity.fromPiece(piece, 5);
        repository.savePieceEntity(pieceEntity1);

        final var result = repository.findOneByPieceId("Not Existed");

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("업데이트에 성공하면 참을 반환다")
    void true_if_update_piece_entity() {
        final var piece = new Knight(A3, Color.BLACK, "13updateId");
        savePiece(piece, 5);
        piece.move(A5);

        final var result = repository.updatePiece(PieceEntity.fromPiece(piece, 5));
        final var findEntity = repository.findOneByPieceId("13updateId");
        System.out.println(findEntity);
        assertThat(result).isTrue();
        assertThat(findEntity.get()
                             .point()).isEqualTo("a5");
    }

    private void savePiece(final Piece piece, final int gameId) {
        final var pieceEntity = PieceEntity.fromPiece(piece, gameId);
        repository.savePieceEntity(pieceEntity);

    }
}
