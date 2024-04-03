package domain.chess.controller;

import domain.chess.dto.RouteDto;
import domain.chess.service.ChessService;
import domain.chess.util.BoardMapper;
import domain.chess.view.ChessCommand;
import domain.chess.view.InputView;
import domain.chess.view.OutputView;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class ChessController {
    private final ChessService chessService;

    public ChessController(final ChessService chessService) {
        this.chessService = chessService;
    }

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
        final var gameId = Integer.parseInt(InputView.inputGameId());
        final var chessBoard = chessService.settingChessBoard(gameId);
        OutputView.printBoard(BoardMapper.toDto(chessBoard));
        return GameStatus.GAME_START;
    }

    private GameStatus pieceMove() {
        final var source = InputView.inputChessPoint();
        final var destination = InputView.inputChessPoint();
        final var routeDto = new RouteDto(source, destination);

        final var gameId = Integer.parseInt(InputView.inputGameId());
        final var chessBoard = chessService.moveChessBoard(gameId, routeDto);
        OutputView.printBoard(BoardMapper.toDto(chessBoard));
        return GameStatus.GAME_PLAY;
    }

    private GameStatus gameStatus() {
        final var gameId = Integer.parseInt(InputView.inputGameId());
        final var score = chessService.getChessScore(gameId);
        OutputView.printScore(score);
        return GameStatus.GAME_PLAY;
    }

    private GameStatus gameEnd() {
        return GameStatus.GAME_END;
    }
}
