package repository;


import domain.chess.ChessGameInfo;
import domain.chess.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChessGameInfoRepositoryTest {
    @Test
    @DisplayName("게임 아이디에 해당하는 정보가 없으면 Empty 를 반환한다")
    void some() {
        final ChessGameInfoRepository repository = new ChessGameInfoMemoryRepository();

        final var gameInfo = repository.getChessGameInfoWithGameId(3);

        assertThat(gameInfo).isEmpty();
    }

    @Test
    @DisplayName("체스 게임 정보를 생성하면 참을 반환한다")
    void create() {
        final ChessGameInfoRepository repository = new ChessGameInfoMemoryRepository();

        final var gameInfo = repository.create(new ChessGameInfo(1, Color.BLACK));

        assertThat(gameInfo).isTrue();
    }

    @Test
    @DisplayName("체스 게임 정보 생성에 실패하면 거짓을 반환한다")
    void create1() {
        final ChessGameInfoRepository repository = new ChessGameInfoMemoryRepository();
        repository.create(new ChessGameInfo(1, Color.BLACK));

        final var gameInfo = repository.create(new ChessGameInfo(1, Color.BLACK));

        assertThat(gameInfo).isFalse();
    }

    @Test
    @DisplayName("게임 아이디에 해당하는 정보가 있으면 반환한다")
    void some2() {
        final ChessGameInfoRepository repository = new ChessGameInfoMemoryRepository();
        repository.create(new ChessGameInfo(1, Color.BLACK));

        final var gameInfo = repository.getChessGameInfoWithGameId(1);

        assertThat(gameInfo.get()).isEqualTo(new ChessGameInfo(1, Color.BLACK));
    }

    @Test
    @DisplayName("게임 아이디에 해당하는 정보에서 차례 교체를 성공하면 참을 반환한다")
    void some1() {
        final ChessGameInfoRepository repository = new ChessGameInfoMemoryRepository();
        repository.create(new ChessGameInfo(1, Color.BLACK));
        final var gameInfo = repository.getChessGameInfoWithGameId(1)
                                       .get();
        repository.changeTurn(gameInfo);

        final var gameInfo1 = repository.getChessGameInfoWithGameId(1)
                                        .get();

        assertThat(gameInfo1.color()).isEqualTo(Color.WHITE);
    }
}
