package repository;

import domain.chess.ChessGameInfo;
import domain.chess.Color;

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
    public boolean create(final ChessGameInfo chessGameInfo) {
        try (final var connection = this.connectionGenerator.getConnection()) {
            final String sql = "INSERT INTO ChessGameInfo (game_id, color) VALUES (?, ?)";
            final var statement = connection.prepareStatement(sql);
            statement.setInt(1, chessGameInfo.chessGameId());
            statement.setString(2, chessGameInfo.color()
                                                .getValue());
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw exceptionMapper.apply(e);
        }
    }

    @Override
    public boolean changeTurn(final ChessGameInfo chessGameInfo) {
        try (final var connection = this.connectionGenerator.getConnection()) {
            final String sql = "UPDATE ChessGameInfo SET color = ? WHERE game_id = ?";
            final var statement = connection.prepareStatement(sql);
            statement.setString(1, chessGameInfo.color()
                                                .getValue());
            statement.setInt(2, chessGameInfo.chessGameId());
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
    public Optional<ChessGameInfo> getChessGameInfoWithGameId(final int gameId) {
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

    private Optional<ChessGameInfo> parseResult(final ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            final var id = resultSet.getInt("game_id");
            final var color = resultSet.getString("color");
            return Optional.ofNullable(new ChessGameInfo(id, Color.from(color)));
        }
        return Optional.empty();
    }
}
