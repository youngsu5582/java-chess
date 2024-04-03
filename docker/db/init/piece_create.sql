CREATE TABLE Piece
(
    piece_id VARCHAR(255) NOT NULL PRIMARY KEY,
    game_id  INT          NOT NULL,
    point    VARCHAR(2)   NOT NULL,
    color    VARCHAR(6)   NOT NULL,
    kind     VARCHAR(2)   NOT NULL,
    FOREIGN KEY (game_id) REFERENCES ChessGameInfo (game_id)
);
