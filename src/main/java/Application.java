import domain.chess.controller.ChessController;
import domain.chess.repository.ChessGameInfoSqlRepository;
import domain.chess.repository.PieceEntitySqlRepository;
import domain.chess.service.ChessService;
import util.ConnectionGenerator;

public class Application {
    public static void main(final String[] args) {
        final ChessController controller = new ChessController(
                new ChessService(new PieceEntitySqlRepository(new ConnectionGenerator()), new ChessGameInfoSqlRepository(new ConnectionGenerator())));
        controller.start();
    }
}
