package controller;

import domain.chess.piece.CheckMateException;
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
    private Integer gameId;

    public ChessController(final ChessService chessService) {
        this.chessService = chessService;
        this.gameId = null;
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
            } catch (final IllegalArgumentException | IllegalStateException | CheckMateException exception) {
                if (exception instanceof CheckMateException) {
                    gameId = null;
                }
                InputView.clear();
                OutputView.printException(exception.getMessage());
            }
        }
    }


    private GameStatus gameStart() {
        checkCacheExist();
        gameId = Integer.parseInt(InputView.inputGameId());
        final var chessBoard = chessService.settingChessBoard(gameId);
        OutputView.printBoard(BoardMapper.toDto(chessBoard));
        return GameStatus.GAME_START;
    }

    private GameStatus pieceMove() {
        checkCache();
        final var source = InputView.inputChessPoint();
        final var destination = InputView.inputChessPoint();
        final var routeDto = new RouteDto(source, destination);

        final var chessBoard = chessService.moveChessBoard(gameId, routeDto);
        OutputView.printBoard(BoardMapper.toDto(chessBoard));
        return GameStatus.GAME_PLAY;
    }

    private GameStatus gameStatus() {
        checkCache();
        final var score = chessService.getChessScore(gameId);
        OutputView.printScore(score);
        return GameStatus.GAME_PLAY;
    }

    private GameStatus gameEnd() {
        gameId = null;
        return GameStatus.GAME_END;
    }

    private void checkCache() {
        if (gameId == null) {
            throw new IllegalStateException(String.format("%s를 입력후 방 번호를 입력해주세요", ChessCommand.START.getCommandText()));
        }
    }

    private void checkCacheExist() {
        if (gameId != null) {
            throw new IllegalStateException(String.format("이미 방 번호(%d)가 있습니다", gameId));
        }
    }
}
