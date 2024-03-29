import controller.ChessController;
import repository.ChessGameInfoMemoryRepository;
import repository.PieceEntityMemoryRepository;
import service.ChessService;

public class Application {
    public static void main(final String[] args) {
        final ChessController controller = new ChessController(
                new ChessService(new PieceEntityMemoryRepository(), new ChessGameInfoMemoryRepository()));
        controller.start();
    }
}
