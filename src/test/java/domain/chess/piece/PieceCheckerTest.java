package domain.chess.piece;

import domain.chess.piece.kind.King;
import domain.chess.piece.kind.Knight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fixture.PointFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class PieceCheckerTest {
    @Test
    @DisplayName("포인트와 포인트 사이에 기물이 있으면 참을 반환한다")
    void true_if_piece_to_point_path_have_any_piece() {
        final var pieces = List.of(new King(C3, Color.WHITE), new Knight(F2, Color.BLACK));
        final var sut = new PieceChecker(pieces);

        final var result = sut.hasAnyPieceInPath(A1, H8);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("포인트와 포인트 사이에 기물이 없으면 거짓을 반환한다")
    void false_if_piece_to_point_path_does_not_have_any_piece() {
        final var pieces = List.of(new King(F1, Color.WHITE), new Knight(F2, Color.BLACK));
        final var sut = new PieceChecker(pieces);

        final var result = sut.hasAnyPieceInPath(A1, C3);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포인트에 아군 기물이 있으면 참을 반환한다")
    void true_if_friend_piece_in_point() {
        final var pieces = List.of(new King(F1, Color.WHITE), new Knight(F2, Color.BLACK));
        final var sut = new PieceChecker(pieces);

        final var result = sut.isEqualColorInPoint(Color.BLACK, F2);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("포인트에 적 기물이 있으면 참을 반환한다")
    void true_if_enemy_piece_in_point() {
        final var pieces = List.of(new King(F1, Color.WHITE), new Knight(F2, Color.BLACK));
        final var sut = new PieceChecker(pieces);

        final var result = sut.isNotEqualColorInPoint(Color.WHITE, F2);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("포인트에 기물이 없을시 참을 반환한다")
    void false_if_enemy_piece_or_empty_in_point() {
        final var pieces = List.of(new King(C3, Color.WHITE), new Knight(F2, Color.BLACK));
        final var sut = new PieceChecker(pieces);

        final var result = sut.isEmptyPoint(C4);

        assertThat(result).isTrue();
    }
}
