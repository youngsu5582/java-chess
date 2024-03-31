package domain.chess.service;

import domain.chess.ChessBoard;
import domain.chess.entity.ChessGameInfoEntity;
import domain.chess.piece.Color;
import domain.chess.exception.GameEndException;
import domain.chess.piece.Piece;
import domain.chess.entity.PieceEntity;
import domain.chess.piece.Pieces;
import domain.chess.dto.RouteDto;
import domain.chess.repository.ChessGameInfoRepository;
import domain.chess.repository.PieceEntityRepository;

import java.util.List;

public class ChessService {
    private final PieceEntityRepository pieceEntityRepository;
    private final ChessGameInfoRepository chessGameInfoRepository;

    public ChessService(final PieceEntityRepository pieceEntityRepository, final ChessGameInfoRepository chessGameInfoRepository) {
        this.pieceEntityRepository = pieceEntityRepository;
        this.chessGameInfoRepository = chessGameInfoRepository;
    }

    public ChessBoard settingChessBoard(final int gameId) {
        return this.chessGameInfoRepository.getChessGameInfoWithGameId(gameId)
                                           .map(this::getExistChessBoard)
                                           .orElseGet(() -> createNewChessBoard(gameId));
    }

    private ChessBoard getExistChessBoard(final int gameId) {
        final var chessGameInfo = this.chessGameInfoRepository
                .getChessGameInfoWithGameId(gameId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("%d 는 없는 아이디입니다", gameId)));
        return getExistChessBoard(chessGameInfo);
    }

    private ChessBoard getExistChessBoard(final ChessGameInfoEntity chessGameInfoEntity) {
        final List<PieceEntity> pieceEntities = this.pieceEntityRepository.findAllByGameId(chessGameInfoEntity.chessGameId());
        final Pieces pieces = new Pieces(pieceEntities.stream()
                                                      .map(PieceEntity::toPiece)
                                                      .toList());
        return new ChessBoard(pieces, chessGameInfoEntity.color(), chessGameInfoEntity.chessGameId());
    }

    private ChessBoard createNewChessBoard(final int gameId) {
        final ChessBoard chessBoard = ChessBoard.createDefaultBoard(gameId);
        this.chessGameInfoRepository.create(ChessGameInfoEntity.startWhite(gameId));
        chessBoard.getPieces()
                  .stream()
                  .map(piece -> PieceEntity.fromPiece(piece, gameId))
                  .forEach(this.pieceEntityRepository::savePieceEntity);
        return chessBoard;
    }


    public ChessBoard moveChessBoard(final int gameId, final RouteDto routeDto) {
        final var chessBoard = getExistChessBoard(gameId);
        final var optionalPiece = chessBoard.findPiece(routeDto.getEndPoint());

        final var piece = chessBoard.move(routeDto.getStartPoint(), routeDto.getEndPoint());
        optionalPiece.ifPresent(endPointPiece -> processDelete(chessBoard, endPointPiece));

        processMove(chessBoard, piece);
        return chessBoard;
    }

    private void processDelete(final ChessBoard chessBoard, final Piece piece) {
        if (piece.isKing()) {
            final int gameId = chessBoard.getGameId();
            this.pieceEntityRepository.deleteAllByGameId(gameId);
            this.chessGameInfoRepository.deleteGameInfo(gameId);
            throw new GameEndException(chessBoard.getTurn()
                                                 .getValue());
        }
        this.pieceEntityRepository.deletePiece(piece.getPieceId());
    }

    private void processMove(final ChessBoard chessBoard, final Piece piece) {
        final int gameId = chessBoard.getGameId();
        final Color color = chessBoard.getTurn();
        this.pieceEntityRepository.updatePiece(PieceEntity.fromPiece(piece, chessBoard.getGameId()));
        this.chessGameInfoRepository.changeTurn(ChessGameInfoEntity.valueOf(gameId, color)
                                                                   .reverseColor());
    }

    public double getChessScore(final int gameId) {
        return getExistChessBoard(gameId).getTurnScore();
    }
}
