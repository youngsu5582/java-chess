package domain.chess.repository;

import domain.chess.entity.PieceEntity;
import util.ConnectionGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class PieceEntitySqlRepository implements PieceEntityRepository {
    Function<SQLException, IllegalStateException> exceptionMapper =
            e -> new IllegalStateException("데이터베이스 에서 문제가 발생했습니다.", e);

    private final ConnectionGenerator connectionGenerator;

    public PieceEntitySqlRepository(final ConnectionGenerator connectionGenerator) {
        this.connectionGenerator = connectionGenerator;
    }

    @Override
    public boolean savePieceEntity(final PieceEntity pieceEntity) {
        try (final var connection = this.connectionGenerator.getConnection()) {
            final String sql = "INSERT INTO Piece (piece_id, game_id, point, color, kind) VALUES (?, ?, ?, ?, ?)";
            final var statement = connection.prepareStatement(sql);

            statement.setString(1, pieceEntity.pieceId());
            statement.setInt(2, pieceEntity.gameId());
            statement.setString(3, pieceEntity.point());
            statement.setString(4, pieceEntity.color());
            statement.setString(5, pieceEntity.kind());

            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw exceptionMapper.apply(e);
        }
    }

    @Override
    public List<PieceEntity> findAllByGameId(final int gameId) {
        try (final var connection = this.connectionGenerator.getConnection()) {
            final String sql = "SELECT * FROM Piece WHERE game_id = ?";
            final var statement = connection.prepareStatement(sql);
            statement.setInt(1, gameId);
            final var resultSet = statement.executeQuery();
            return parseResult(resultSet);
        } catch (final SQLException e) {
            throw exceptionMapper.apply(e);
        }
    }

    private List<PieceEntity> parseResult(final ResultSet resultSet) throws SQLException {
        final List<PieceEntity> pieces = new ArrayList<>();
        while (resultSet.next()) {
            parse(resultSet).ifPresent(pieces::add);
        }
        return pieces;
    }

    @Override
    public boolean deletePiece(final String pieceId) {
        try (final var connection = this.connectionGenerator.getConnection()) {
            final String sql = "DELETE FROM Piece WHERE piece_id = ?";
            final var statement = connection.prepareStatement(sql);
            statement.setString(1, pieceId);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw exceptionMapper.apply(e);
        }
    }

    @Override
    public boolean deleteAllByGameId(final int gameId) {
        try (final var connection = this.connectionGenerator.getConnection()) {
            final String sql = "DELETE FROM Piece WHERE game_id = ?";
            final var statement = connection.prepareStatement(sql);
            statement.setInt(1, gameId);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw exceptionMapper.apply(e);
        }
    }

    @Override
    public boolean updatePiece(final PieceEntity pieceEntity) {
        try (final var connection = this.connectionGenerator.getConnection()) {
            final String sql = "UPDATE Piece SET kind = ? , point = ? WHERE piece_id = ?";
            final var statement = connection.prepareStatement(sql);

            statement.setString(1, pieceEntity.kind());
            statement.setString(2, pieceEntity.point());
            statement.setString(3, pieceEntity.pieceId());

            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            System.out.println(e);
            throw exceptionMapper.apply(e);
        }
    }

    @Override
    public Optional<PieceEntity> findOneByPieceId(final String pieceId) {
        try (final var connection = this.connectionGenerator.getConnection()) {
            final String sql = "SELECT * FROM Piece WHERE piece_id = ?";
            final var statement = connection.prepareStatement(sql);
            statement.setString(1, pieceId);
            final var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return parse(resultSet);
            }
            return Optional.empty();
        } catch (final SQLException e) {
            throw exceptionMapper.apply(e);
        }
    }
    private Optional<PieceEntity> parse(final ResultSet resultSet) throws SQLException {

        final String pieceId = resultSet.getString("piece_id");
        final int gameId = resultSet.getInt("game_id");
        final String point = resultSet.getString("point");
        final String color = resultSet.getString("color");
        final String kind = resultSet.getString("kind");

        return Optional.of(new PieceEntity(point, color, kind, pieceId, gameId));
    }
}
