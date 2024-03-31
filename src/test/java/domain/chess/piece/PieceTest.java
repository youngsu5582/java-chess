package domain.chess.piece;

import domain.chess.piece.kind.King;
import domain.chess.piece.kind.Knight;
import domain.chess.piece.kind.Rook;
import fixture.PieceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fixture.PointFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PieceTest {

    @Test
    @DisplayName("좌표와 색깔을 통해 기물을 생성한다,")
    void create_with_point_and_color() {
        final Piece piece = new PieceImpl(new Point(File.A, Rank.ONE), Color.BLACK);

        assertThat(piece).isInstanceOf(Piece.class);
    }


    @Test
    @DisplayName("기물과 포인트 사이가 갈 수 없으면 예외를 발생한다")
    void throw_exception_when_non_direction_that_piece_to_point() {
        final Piece piece = new Rook(A1, Color.BLACK);
        final var pieces = List.of(new King(F1, Color.WHITE), new Knight(F2, Color.BLACK));
        assertThatThrownBy(() -> piece.canMove(C4, pieces))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
