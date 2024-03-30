package repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class ConnectionGeneratorTest {
    @Test
    @DisplayName("연결에 성공하면 connection 을 반환한다")
    void connection_test() {
        final ConnectionGenerator connectionGenerator = new ConnectionGenerator();
        try (final var connection = connectionGenerator.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
