CREATE TABLE Piece
(
    piece_id VARCHAR(255) NOT NULL PRIMARY KEY,
    game_id  INT          NOT NULL,
    point    VARCHAR(2)   NOT NULL,
    color    ENUM('black', 'white') NOT NULL,
    kind     ENUM('K', 'P', 'N', 'Q', 'R', 'B') NOT NULL,
    FOREIGN KEY (game_id) REFERENCES ChessGameInfo (game_id)
);
