package repository;


import domain.chess.entity.ChessGameInfoEntity;
import domain.chess.piece.Color;
import domain.chess.repository.ChessGameInfoRepository;
import fake.ChessGameInfoMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChessGameInfoEntityRepositoryTest {
    private ChessGameInfoRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = new ChessGameInfoMemoryRepository();
    }

    @Test
    @DisplayName("게임 아이디에 해당하는 정보가 없으면 Empty 를 반환한다")
    void some() {
        final var gameInfo = repository.getChessGameInfoWithGameId(3);

        assertThat(gameInfo).isEmpty();
    }

    @Test
    @DisplayName("체스 게임 정보를 생성하면 참을 반환한다")
    void create() {
        final var gameInfo = repository.create(ChessGameInfoEntity.valueOf(1, Color.BLACK));

        assertThat(gameInfo).isTrue();
    }

    @Test
    @DisplayName("체스 게임 정보 생성에 실패하면 예외를 반환한다")
    void create1() {
        final ChessGameInfoEntity chessGameInfoEntity = ChessGameInfoEntity.valueOf(1000, Color.BLACK);
        repository.create(chessGameInfoEntity);

        assertThatThrownBy(() -> repository.create(chessGameInfoEntity))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("게임 아이디에 해당하는 정보가 있으면 반환한다")
    void some2() {
        repository.create(ChessGameInfoEntity.valueOf(1124, Color.BLACK));

        final var gameInfo = repository.getChessGameInfoWithGameId(1124);

        assertThat(gameInfo).contains(ChessGameInfoEntity.valueOf(1124, Color.BLACK));
    }

    @Test
    @DisplayName("게임 아이디에 해당하는 정보에서 차례 교체를 성공하면 참을 반환한다")
    void some1() {
        repository.create(ChessGameInfoEntity.valueOf(1350, Color.BLACK));
        final var gameInfo = repository.getChessGameInfoWithGameId(1350)
                                       .get();
        repository.changeTurn(gameInfo.reverseColor());

        final var gameInfo1 = repository.getChessGameInfoWithGameId(1350)
                                        .get();

        assertThat(gameInfo1.color()).isEqualTo(Color.WHITE);
    }
}
