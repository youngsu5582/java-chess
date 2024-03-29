package controller;

import domain.ChessBoard;
import dto.RouteDto;
import service.ChessService;
import util.BoardMapper;
import view.ChessCommand;
import view.InputView;
import view.OutputView;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class ChessController {
    private final ChessService chessService;

    public ChessController(final ChessService chessService) {
        this.chessService = chessService;
    }

    private ChessBoard chessBoard;
    private final Map<ChessCommand, Supplier<GameStatus>> commandHandler = initializeHandler();

    private Map<ChessCommand, Supplier<GameStatus>> initializeHandler() {
        final Map<ChessCommand, Supplier<GameStatus>> handler = new EnumMap<>(ChessCommand.class);
        handler.put(ChessCommand.START, this::gameStart);
        handler.put(ChessCommand.MOVE, this::pieceMove);
        handler.put(ChessCommand.STATUS, this::gameStatus);
        handler.put(ChessCommand.END, this::gameEnd);
        return handler;
    }

    public void start() {
        GameStatus gameStatus = GameStatus.GAME_READY;
        while (gameStatus.playAble()) {
            try {
                final ChessCommand chessCommand = InputView.inputChessCommand();
                gameStatus = commandHandler.get(chessCommand)
                                           .get();
            } catch (final IllegalArgumentException | IllegalStateException exception) {
                InputView.clear();
                OutputView.printException(exception.getMessage());
            }
        }
    }


    private GameStatus gameStart() {
        if (chessBoard != null) {
            throw new IllegalStateException("이미 체스판이 생성되어 있습니다.");
        }
        final String gameId = InputView.inputGameId();
        
        chessBoard = chessService.settingChessBoard(gameId);
        OutputView.printBoard(BoardMapper.toDto(chessBoard));
        return GameStatus.GAME_START;
    }

    private GameStatus pieceMove() {
        if (chessBoard == null) {
            throw new IllegalStateException("체스판이 아직 생성되지 않았습니다.");
        }
        final var source = InputView.inputChessPoint();
        final var destination = InputView.inputChessPoint();
        final var routeDto = new RouteDto(source, destination);

        chessService.moveChessBoard(chessBoard, routeDto);
        OutputView.printBoard(BoardMapper.toDto(chessBoard));
        return GameStatus.GAME_PLAY;
    }

    private GameStatus gameStatus() {
        if (chessBoard == null) {
            throw new IllegalStateException("체스판이 아직 생성되지 않았습니다.");
        }
        final var score = chessBoard.getTurnScore();
        OutputView.printScore(score);
        return GameStatus.GAME_PLAY;
    }

    private GameStatus gameEnd() {
        chessBoard = null;
        return GameStatus.GAME_END;
    }
}
