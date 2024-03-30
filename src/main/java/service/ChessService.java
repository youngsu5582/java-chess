package service;

import domain.ChessBoard;
import domain.chess.ChessGameInfo;
import domain.chess.piece.CheckMateException;
import domain.chess.piece.Piece;
import domain.chess.piece.PieceEntity;
import domain.chess.piece.Pieces;
import dto.RouteDto;
import repository.ChessGameInfoRepository;
import repository.PieceEntityRepository;

import java.util.List;

public class ChessService {
    private final PieceEntityRepository pieceEntityRepository;
    private final ChessGameInfoRepository chessGameInfoRepository;

    public ChessService(final PieceEntityRepository pieceEntityRepository, final ChessGameInfoRepository chessGameInfoRepository) {
        this.pieceEntityRepository = pieceEntityRepository;
        this.chessGameInfoRepository = chessGameInfoRepository;
    }

    public ChessBoard settingChessBoard(final int gameId) {
        final var chessGameInfo = this.chessGameInfoRepository.getChessGameInfoWithGameId(gameId);
        if (chessGameInfo.isPresent()) {
            return getExistChessBoard(chessGameInfo.get());
        }
        return createNewChessBoard(gameId);
    }

    private ChessBoard getExistChessBoard(final int gameId) {
        final var chessGameInfo = this.chessGameInfoRepository
                .getChessGameInfoWithGameId(gameId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("%d 는 없는 아이디입니다", gameId)));
        return getExistChessBoard(chessGameInfo);
    }

    private ChessBoard getExistChessBoard(final ChessGameInfo chessGameInfo) {
        final List<PieceEntity> pieceEntities = this.pieceEntityRepository.findAllByGameId(chessGameInfo.chessGameId());
        return new ChessBoard(new Pieces(pieceEntities.stream()
                                                      .map(PieceEntity::toPiece)
                                                      .toList()), chessGameInfo.color(), chessGameInfo.chessGameId());
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
            throw new CheckMateException(chessBoard.getTurn()
                                                   .getValue());
        }
        this.pieceEntityRepository.deletePiece(piece.getPieceId());
    }

    private void processMove(final ChessBoard chessBoard, final Piece piece) {
        this.pieceEntityRepository.updatePiece(PieceEntity.fromPiece(piece, chessBoard.getGameId()));
        this.chessGameInfoRepository.changeTurn(ChessGameInfo.valueOf(chessBoard.getGameId(), chessBoard.getTurn()));
    }

    public double getChessScore(final int gameId) {
        return getExistChessBoard(gameId).getTurnScore();
    }
}
