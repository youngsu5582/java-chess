package domain.chess.repository;

import domain.chess.entity.ChessGameInfoEntity;
import domain.chess.piece.Color;
import util.ConnectionGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Function;

public class ChessGameInfoSqlRepository implements ChessGameInfoRepository {
    Function<SQLException, IllegalStateException> exceptionMapper =
            e -> new IllegalStateException("데이터베이스 에서 문제가 발생했습니다.", e);

    private final ConnectionGenerator connectionGenerator;

    public ChessGameInfoSqlRepository(final ConnectionGenerator connectionGenerator) {
        this.connectionGenerator = connectionGenerator;
    }


    @Override
    public boolean create(final ChessGameInfoEntity chessGameInfoEntity) {
        try (final var connection = this.connectionGenerator.getConnection()) {
            final String sql = "INSERT INTO ChessGameInfo (game_id, color) VALUES (?, ?)";
            final var statement = connection.prepareStatement(sql);
            statement.setInt(1, chessGameInfoEntity.chessGameId());
            statement.setString(2, chessGameInfoEntity.color()
                                                      .getValue());
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw exceptionMapper.apply(e);
        }
    }

    @Override
    public boolean changeTurn(final ChessGameInfoEntity chessGameInfoEntity) {
        try (final var connection = this.connectionGenerator.getConnection()) {
            final String sql = "UPDATE ChessGameInfo SET color = ? WHERE game_id = ?";
            final var statement = connection.prepareStatement(sql);
            statement.setString(1, chessGameInfoEntity.color()
                                                      .getValue());
            statement.setInt(2, chessGameInfoEntity.chessGameId());
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw exceptionMapper.apply(e);
        }
    }

    @Override
    public boolean deleteGameInfo(final int gameId) {
        try (final var connection = this.connectionGenerator.getConnection()) {
            final String sql = "DELETE FROM ChessGameInfo WHERE game_id = ?";
            final var statement = connection.prepareStatement(sql);
            statement.setInt(1, gameId);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw exceptionMapper.apply(e);
        }
    }

    @Override
    public Optional<ChessGameInfoEntity> getChessGameInfoWithGameId(final int gameId) {
        try (final var connection = this.connectionGenerator.getConnection()) {
            final String sql = "SELECT * FROM ChessGameInfo WHERE game_id = ?";
            final var statement = connection.prepareStatement(sql);
            statement.setInt(1, gameId);
            final var resultSet = statement.executeQuery();
            return parseResult(resultSet);
        } catch (final SQLException e) {
            throw exceptionMapper.apply(e);
        }
    }

    private Optional<ChessGameInfoEntity> parseResult(final ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            final var id = resultSet.getInt("game_id");
            final var color = resultSet.getString("color");
            return Optional.of(new ChessGameInfoEntity(id, Color.from(color)));
        }
        return Optional.empty();
    }
}
