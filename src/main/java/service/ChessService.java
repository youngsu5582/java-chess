package service;

import domain.ChessBoard;
import domain.chess.ChessGameInfo;
import domain.chess.piece.PieceEntity;
import domain.chess.piece.Pieces;
import dto.RouteDto;
import repository.ChessGameInfoMemoryRepository;
import repository.ChessGameInfoRepository;
import repository.PieceEntityMemoryRepository;
import repository.PieceEntityRepository;

import java.util.List;

public class ChessService {
    private final PieceEntityRepository pieceEntityRepository;
    private final ChessGameInfoRepository chessGameInfoRepository;

    public ChessService(final PieceEntityRepository pieceEntityRepository, final ChessGameInfoRepository chessGameInfoRepository) {
        this.pieceEntityRepository = pieceEntityRepository;
        this.chessGameInfoRepository = chessGameInfoRepository;
    }

    public ChessService() {
        this.pieceEntityRepository = new PieceEntityMemoryRepository();
        this.chessGameInfoRepository = new ChessGameInfoMemoryRepository();
    }

    public ChessBoard settingChessBoard(final String gameId) {
        final Integer id = Integer.parseInt(gameId);

        final var chessGameInfo = this.chessGameInfoRepository.getChessGameInfoWithGameId(id);
        if (chessGameInfo.isPresent()) {
            return createExistChessBoard(id);
        }
        return createNewChessBoard(id);
    }

    private ChessBoard createExistChessBoard(final int gameId) {
        final List<PieceEntity> pieceEntities = this.pieceEntityRepository.findAllByGameId(gameId);
        return new ChessBoard(new Pieces(pieceEntities.stream()
                                                      .map(PieceEntity::toPiece)
                                                      .toList()), gameId);
    }

    private ChessBoard createNewChessBoard(final int gameId) {
        final ChessBoard chessBoard = ChessBoard.createDefaultBoard(gameId);
        this.chessGameInfoRepository.create(ChessGameInfo.startWhite(gameId));
        chessBoard.getPieces()
                  .stream()
                  .map(piece -> PieceEntity.fromPiece(piece, gameId))
                  .forEach(this.pieceEntityRepository::savePieceEntity);
        return chessBoard;
    }


    public void moveChessBoard(final ChessBoard chessBoard, final RouteDto routeDto) {
        final var cs = chessBoard.findPiece(routeDto.getEndPoint());
        final var piece = chessBoard.move(routeDto.getStartPoint(), routeDto.getEndPoint());
        if (cs.isPresent()) {
            this.pieceEntityRepository.deletePiece(piece.getPieceId());
        }
        this.pieceEntityRepository.updatePiece(PieceEntity.fromPiece(piece, chessBoard.getGameId()));
    }
}
