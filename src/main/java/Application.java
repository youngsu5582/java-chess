import domain.chess.controller.ChessController;
import domain.chess.repository.ChessGameInfoSqlRepository;
import domain.chess.repository.PieceEntitySqlRepository;
import domain.chess.service.ChessService;
import util.ConnectionGenerator;

public class Application {
    public static void main(final String[] args) {
        final var connectionGenerator = new ConnectionGenerator();
        final ChessController controller = new ChessController(
                new ChessService(new PieceEntitySqlRepository(connectionGenerator), new ChessGameInfoSqlRepository(connectionGenerator)));
        controller.start();
    }
}
