package live.boardgames.chss.bots.okehanokcu;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;
import live.boardgames.chss.internal.knowledge.PieceType;

public class MyEvaluator implements BoardEvaluator {

    private int kingValue = 20000;
    private int queenValue = 900;
    private int rookValue = 500;
    private int bishopValue = 330;
    private int knightValue = 320;
    private int pawnValue = 100;

    private static int[][] BlackPawnPos =
            {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {50, 50, 50, 50, 50, 50, 50, 50},
                    {10, 10, 20, 30, 30, 20, 10, 10},
                    {5, 5, 10, 25, 25, 10, 5, 5},
                    {0, 0, 0, 20, 20, 0, 0, 0},
                    {5, -5, -10, 0, 0, -10, -5, 5},
                    {5, 10, 10, -20, -20, 10, 10, 5},
                    {0, 0, 0, 0, 0, 0, 0, 0}
            };

    private static int[][] WhitePawnPos =
            {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {5, 10, 10, -20, -20, 10, 10, 5},
                    {5, -5, -10, 0, 0, -10, -5, 5},
                    {0, 0, 0, 20, 20, 0, 0, 0},
                    {5, 5, 10, 25, 25, 10, 5, 5},
                    {10, 10, 20, 30, 30, 20, 10, 10},
                    {50, 50, 50, 50, 50, 50, 50, 50},
                    {0, 0, 0, 0, 0, 0, 0, 0}
            };

    private static int[][] BlackKnightPos =
            {
                    {-50, -40, -30, -30, -30, -30, -40, -50},
                    {-40, -20, 0, 0, 0, 0, -20, -40},
                    {-30, 0, 10, 15, 15, 10, 0, -30},
                    {-30, 5, 15, 20, 20, 15, 5, -30},
                    {-30, 0, 15, 20, 20, 15, 0, -30},
                    {-30, 5, 10, 15, 15, 10, 5, -30},
                    {-40, -20, 0, 5, 5, 0, -20, -40},
                    {-50, -40, -30, -30, -30, -30, -40, -50}
            };

    private static int[][] WhiteKnightPos =
            {
                    {-50, -40, -30, -30, -30, -30, -40, -50},
                    {-40, -20, 0, 5, 5, 0, -20, -40},
                    {-30, 5, 10, 15, 15, 10, 5, -30},
                    {-30, 0, 15, 20, 20, 15, 0, -30},
                    {-30, 5, 15, 20, 20, 15, 5, -30},
                    {-30, 0, 10, 15, 15, 10, 0, -30},
                    {-40, -20, 0, 0, 0, 0, -20, -40},
                    {-50, -40, -30, -30, -30, -30, -40, -50}
            };

    private static int[][] BlackBishopPos =
            {
                    {-20, -10, -10, -10, -10, -10, -10, -20},
                    {-10, 0, 0, 0, 0, 0, 0, -10},
                    {-10, 0, 5, 10, 10, 5, 0, -10},
                    {-10, 5, 5, 10, 10, 5, 5, -10},
                    {-10, 0, 10, 10, 10, 10, 0, -10},
                    {-10, 10, 10, 10, 10, 10, 10, -10},
                    {-10, 5, 0, 0, 0, 0, 5, -10},
                    {-20, -10, -10, -10, -10, -10, -10, -20}
            };

    private static int[][] WhiteBishopPos =
            {
                    {-20, -10, -10, -10, -10, -10, -10, -20},
                    {-10, 5, 0, 0, 0, 0, 5, -10},
                    {-10, 10, 10, 10, 10, 10, 10, -10},
                    {-10, 0, 10, 10, 10, 10, 0, -10},
                    {-10, 5, 5, 10, 10, 5, 5, -10},
                    {-10, 0, 5, 10, 10, 5, 0, -10},
                    {-10, 0, 0, 0, 0, 0, 0, -10},
                    {-20, -10, -10, -10, -10, -10, -10, -20}
            };

    private static int[][] BlackRookPos =
            {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {5, 10, 10, 10, 10, 10, 10, 5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {0, 0, 0, 5, 5, 0, 0, 0}
            };

    private static int[][] WhiteRookPos =
            {
                    {0, 0, 0, 5, 5, 0, 0, 0},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {5, 10, 10, 10, 10, 10, 10, 5},
                    {0, 0, 0, 0, 0, 0, 0, 0}
            };

    private static int[][] BlackQueenPos =
            {
                    {-20, -10, -10, -5, -5, -10, -10, -20},
                    {-10, 0, 0, 0, 0, 0, 0, -10},
                    {-10, 0, 5, 5, 5, 5, 0, -10},
                    {-5, 0, 5, 5, 5, 5, 0, -5},
                    {0, 0, 5, 5, 5, 5, 0, -5},
                    {-10, 5, 5, 5, 5, 5, 0, -10},
                    {-10, 0, 5, 0, 0, 0, 0, -10},
                    {-20, -10, -10, -5, -5, -10, -10, -20}
            };

    private static int[][] WhiteQueenPos =
            {
                    {-20, -10, -10, -5, -5, -10, -10, -20},
                    {-10, 0, 5, 0, 0, 0, 0, -10},
                    {-10, 5, 5, 5, 5, 5, 0, -10},
                    {0, 0, 5, 5, 5, 5, 0, -5},
                    {-5, 0, 5, 5, 5, 5, 0, -5},
                    {-10, 0, 5, 5, 5, 5, 0, -10},
                    {-10, 0, 0, 0, 0, 0, 0, -10},
                    {-20, -10, -10, -5, -5, -10, -10, -20}
            };

    private static int[][] BlackKingMiddleGamePos =
            {
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-20, -30, -30, -40, -40, -30, -30, -20},
                    {-10, -20, -20, -20, -20, -20, -20, -10},
                    {20, 20, 0, 0, 0, 0, 20, 20},
                    {20, 30, 10, 0, 0, 10, 30, 20}
            };

    private static int[][] WhiteKingMiddleGamePos =
            {
                    {20, 30, 10, 0, 0, 10, 30, 20},
                    {20, 20, 0, 0, 0, 0, 20, 20},
                    {-10, -20, -20, -20, -20, -20, -20, -10},
                    {-20, -30, -30, -40, -40, -30, -30, -20},
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-30, -40, -40, -50, -50, -40, -40, -30}
            };

    private static int[][] BlackKingEndGamePos =
            {
                    {-50, -40, -30, -20, -20, -30, -40, -50},
                    {-30, -20, -10, 0, 0, -10, -20, -30},
                    {-30, -10, 20, 30, 30, 20, -10, -30},
                    {-30, -10, 30, 40, 40, 30, -10, -30},
                    {-30, -10, 30, 40, 40, 30, -10, -30},
                    {-30, -10, 20, 30, 30, 20, -10, -30},
                    {-30, -30, 0, 0, 0, 0, -30, -30},
                    {-50, -30, -30, -30, -30, -30, -30, -50}
            };

    private static int[][] WhiteKingEndGamePos =
            {
                    {-50, -30, -30, -30, -30, -30, -30, -50},
                    {-30, -30, 0, 0, 0, 0, -30, -30},
                    {-30, -10, 20, 30, 30, 20, -10, -30},
                    {-30, -10, 30, 40, 40, 30, -10, -30},
                    {-30, -10, 30, 40, 40, 30, -10, -30},
                    {-30, -10, 20, 30, 30, 20, -10, -30},
                    {-30, -20, -10, 0, 0, -10, -20, -30},
                    {-50, -40, -30, -20, -20, -30, -40, -50}
            };

    @Override
    public double evaluate(Board board, int player) { // Player comes as 1 for white, -1 for black.
        return EvaluateFromAPerspective(board, player);
    }

    private double EvaluateFromAPerspective (Board board, int player)
    {
        // Count of pieces.
        int myRooks = 0;
        int myBishops = 0;
        int myKnights = 0;
        int myQueens = 0;
        int opponentRooks = 0;
        int opponentBishops = 0;
        int opponentKnights = 0;
        int opponentQueen = 0;
        // End of count of pieces.

        // Specific Positions for pieces.
        int wKingPosRow = -1;
        int wKingPosCol = -1;
        int bKingPosRow = -1;
        int bKingPosCol = -1;
        // End of Specific Positions for pieces.

        double posScore = 0;
        double pieceScore = 0;


        ChessBoard chessBoard =  ((ChessBoard) board);
        PieceType[][] pieces = chessBoard.getBoard(); // Get all pieces on the board.

        // Iterate on all pieces.
        for (int i = 0; i < 8; i++)
        {
            if (MyAI.timeIsUp)
                break;
            for (int j = 0; j < 8; j++)
            {
                if (MyAI.timeIsUp)
                    break;
                PieceType piece = pieces[i][j];
                if (piece == PieceType.WPawn) // Evaluate my own pieces.
                {
                    posScore += WhitePawnPos[i][j];
                    pieceScore += pawnValue;
                } else if (piece == PieceType.WRook)
                {
                    posScore += WhiteRookPos[i][j];
                    pieceScore += rookValue;
                    myRooks++;
                } else if (piece == PieceType.WKnight)
                {
                    posScore += WhiteKnightPos[i][j];
                    pieceScore += knightValue;
                    myKnights++;
                } else if (piece == PieceType.WBishop)
                {
                    posScore += WhiteBishopPos[i][j];
                    pieceScore += bishopValue;
                    myKnights++;
                } else if (piece == PieceType.WQueen)
                {
                    posScore += WhiteQueenPos[i][j];
                    pieceScore += queenValue;
                    myQueens++;
                } else if (piece == PieceType.WKing)
                {
                    wKingPosRow = i;
                    wKingPosCol = j;
                } else if (piece == PieceType.BPawn) // Evaluate opponent pieces.
                {
                    posScore -= BlackPawnPos[i][j];
                    pieceScore -= pawnValue;
                } else if (piece == PieceType.BRook)
                {
                    posScore -= BlackRookPos[i][j];
                    pieceScore -= rookValue;
                    opponentRooks++;
                } else if (piece == PieceType.BKnight)
                {
                    posScore -= BlackKnightPos[i][j];
                    pieceScore -= knightValue;
                    opponentKnights++;
                } else if (piece == PieceType.BBishop)
                {
                    posScore -= BlackBishopPos[i][j];
                    pieceScore -= bishopValue;
                    opponentBishops++;
                } else if (piece == PieceType.BQueen)
                {
                    posScore -= BlackQueenPos[i][j];
                    pieceScore -= queenValue;
                    opponentQueen++;
                } else if (piece == PieceType.BKing)
                {
                    bKingPosRow = i;
                    bKingPosCol = j;
                }
            }
        }

        // EVALUATION FOR KINGS' POSITION.
        // Find out if it is end game or not, I do not want to make calculations again if I have already reached an end game.
        boolean endGame = IsItEndGame(myRooks, myBishops, myKnights, myQueens, opponentRooks, opponentBishops, opponentKnights, opponentQueen);
        if (endGame)
        {
            posScore += WhiteKingEndGamePos[wKingPosRow][wKingPosCol];
            posScore -= BlackKingEndGamePos[bKingPosRow][bKingPosCol];
        } else
        {
            posScore += WhiteKingMiddleGamePos[wKingPosRow][wKingPosCol];
            posScore -= BlackKingMiddleGamePos[bKingPosRow][bKingPosCol];
        }
        // END OF EVALUATION OF KINGS' POSITION.

        int moveCount = ((ChessBoard) board).getMoveCount(); // Piece mobility, each possible move adds 1 point.

        double finalScore = posScore + pieceScore + moveCount;

        return finalScore;
    }

    boolean IsItEndGame (int myRooks, int myBishops, int myKnights, int myQueen, int opponentRooks, int opponentBishops, int opponentKnights, int opponentQueen) {
        if (myQueen == 0 && opponentQueen == 0) {
            return true;
        }

        int myImportantPiecesScore = (myRooks * rookValue) + (myBishops * bishopValue) + (myKnights * kingValue) + (myQueen * queenValue);
        int opponentImportantPiecesScore = (opponentRooks * rookValue) + (opponentBishops + bishopValue) + (opponentKnights * kingValue) + (myQueen * queenValue);
        if (myImportantPiecesScore < 1200 || opponentImportantPiecesScore < 1200) {
            return true;
        } else {
            return false;
        }
    }

}
