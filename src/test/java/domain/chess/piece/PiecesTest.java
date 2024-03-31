package domain.chess.piece;

import domain.chess.piece.kind.*;
import fixture.PieceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.chess.piece.Color.BLACK;
import static domain.chess.piece.Color.WHITE;
import static fixture.PointFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class PiecesTest {
    @Test
    @DisplayName("기물 목록을 포함한 일급컬렉션을 생성한다.")
    void create_with_PieceList() {
        final var point1 = new Point(File.F, Rank.ONE);
        final var color1 = BLACK;

        final var point2 = new Point(File.F, Rank.ONE);
        final var color2 = BLACK;
        final List<Piece> pieceList = List.of(new PieceImpl(point1, color1), new PieceImpl(point2, color2));

        final var sut = new Pieces(pieceList);

        assertThat(sut).isInstanceOf(Pieces.class);
    }

    @Test
    @DisplayName("기물을 해당 좌표로 이동시킨다.")
    void move_piece_to_point() {
        final var sut = new Pieces(List.of(
                new Knight(A1, BLACK),
                new Queen(C2, BLACK)));

        final var piece = sut.findPieceWithPoint(new Point(File.A, Rank.ONE))
                             .get();

        sut.move(piece, B3);

        final var findPiece = sut.findPieceWithPoint(B3)
                                 .get();
        assertThat(piece).isEqualTo(findPiece);
    }

    @Test
    @DisplayName("기물이 이동할 때 해당 좌표에 적 기물이 있으면 제거한다.")
    void move_piece_with_remove_if_exist_enemy_piece() {
        final var sut = new Pieces(List.of(
                new Knight(A1, BLACK),
                new Queen(C2, WHITE)));

        final var piece = sut.findPieceWithPoint(A1)
                             .get();

        sut.move(piece, C2);

        assertThat(sut.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("룩 1개 5.0 , 나이트 1개 2.5 , 퀸 1개 9.0로 총합은 16.5이다")
    void rook_score_is_5_and_knight_score_is_2_5_and_queen_score_is_9() {
        final List<Piece> pieces =
                List.of(new Rook(A1, BLACK), new Knight(A3, BLACK), new Queen(A4, BLACK));
        final var sut = new Pieces(pieces);

        final var result = sut.getScore(BLACK);

        assertThat(result).isEqualTo(16.5);
    }

    @Test
    @DisplayName("폰은 1개 1.0 , 킹은 0점으로 총합은 1.0이다")
    void pawn_score_is_1_and_king_score_is_0() {
        final List<Piece> pieces =
                List.of(new Pawn(A1, BLACK), new King(A3, BLACK));
        final var sut = new Pieces(pieces);

        final var result = sut.getScore(BLACK);

        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("폰은 세로에 같은 팀 기물이 있으면 0.5점으로 계산한다")
    void pawn_score_calculate_0_5_if_exist_friend_pawn_and_same_rank() {
        final List<Piece> pieces =
                List.of(new Pawn(A1, BLACK), new Pawn(A2, BLACK), new Rook(A5, WHITE));
        final var sut = new Pieces(pieces);

        final var result = sut.getScore(BLACK);

        assertThat(result).isEqualTo(1);
    }
}
