package live.boardgames.chss.bots.botMutluDikmen;

//15070006003 Mutlu Dikmen

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;
import live.boardgames.chss.internal.knowledge.ChessMove;
import live.boardgames.chss.internal.knowledge.PieceType;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static live.boardgames.chss.internal.knowledge.Chess.BLACK;
import static live.boardgames.chss.internal.knowledge.Chess.WHITE;
//import static live.boardgames.chss.internal.knowledge.PieceType.*;


public class BoardEvaluatorX  implements BoardEvaluator {

    private static final double PHASE_CONSTANT = 256.0;

    private static final double pawnValue   = 100.0;
    private static final double knightValue = 320.0;
    private static final double bishopValue = 330.0;
    private static final double rookValue   = 500.0;
    private static final double queenValue  = 900.0;
    private static final double kingValue   = 300000.0;

    private static final double knightPenalty = -10.0;
    private static final double rookPenalty = -20.0;
    private static final double noPawnsPenalty = -20.0;

    private static final double[] knightPawnAdjustment =
            {-30.0, -20.0, -15.0, -10.0, -5.0, 0.0, 5.0, 10.0, 15.0};

    private static final double[] rookPawnAdjustment =
            {25.0, 20.0, 15.0, 10.0, 5.0, 0.0, -5.0, -10.0, -15.0};

    private static final double[] dualBishopPawnAdjustment =
            {40.0, 40.0, 35.0, 30.0, 25.0, 20.0, 20.0, 15.0, 15.0};

    private static final double tempoBonus = 10.0;

    private static PieceType pieces[] = {PieceType.Empty, PieceType.BKing, PieceType.BQueen, PieceType.BRook, PieceType.BBishop, PieceType.BKnight, PieceType.BPawn,
            PieceType.WKing, PieceType.WQueen, PieceType.WRook, PieceType.WBishop, PieceType.WKnight, PieceType.WPawn};


    BoardEvaluatorX(){}

    @Override
    public double evaluate(Board board, int player) {

        //double exception1 = isQueenInDanger((ChessBoard) board, player);
        //double exception2 = isRookInDanger((ChessBoard) board, player);
        double normal = eval((ChessBoard) board, player);
        /*if (exception1 >= max(exception2,normal))
            return exception1;
        if (exception2 >= max(exception1,normal))
            return exception2;*/
        return normal;
    }


    public int countOfPiece(ChessBoard b,PieceType p){
        int count = 0;
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (b.get(i,j).equals(p)){
                    count++;
                }
            }
        }
        return count;
    }

    public double eval(ChessBoard board, int player) {

        int opponent = (player == WHITE) ? BLACK : WHITE;

        double openingPlayerValue = getValueOfPieces(board, player, false);
        double endPlayerValue = getValueOfPieces(board, player, true);

        double openingOpponentValue = -getValueOfPieces(board, opponent, false);
        double endOpponentValue = -getValueOfPieces(board, opponent, true);

        double phase = currentPhase(board);
        double playerValue = ((openingPlayerValue * (PHASE_CONSTANT - phase)) +
                (endPlayerValue * phase)) / PHASE_CONSTANT;
        double opponentValue = ((openingOpponentValue * (PHASE_CONSTANT - phase)) +
                (endOpponentValue * phase)) / PHASE_CONSTANT;

        return playerValue - opponentValue;
    }

    private double getValueOfPieces(ChessBoard board, int player, boolean isEndGame) {

        PieceType[][] pieceList = board.getBoard();
        double[][] pawnPos, knightPos, bishopPos, rookPos, queenPos, kingPos;
        int pawnCount, opponentPawnCount, bishopCount,
                knightCount, rookCount, queenCount;
        if (player == Chess.WHITE) {
            pawnPos = pawnPosWhite;
            knightPos = knightPosWhite;
            bishopPos = bishopPosWhite;
            rookPos = rookPosWhite;
            queenPos = queenPosWhite;
            if (isEndGame) {
                kingPos = kingPosWhiteEnd;
            }
            else {
                kingPos = kingPosWhite;
            }
            pawnCount = countOfPiece(board, PieceType.WPawn);
            opponentPawnCount = countOfPiece(board,PieceType.BPawn);
            bishopCount = countOfPiece(board,PieceType.WBishop);
            knightCount = countOfPiece(board,PieceType.WKnight);
            rookCount = countOfPiece(board,PieceType.WRook);
            queenCount = countOfPiece(board, PieceType.WQueen);

        }
        else {
            pawnPos = pawnPosBlack;
            knightPos = knightPosBlack;
            bishopPos = bishopPosBlack;
            rookPos = rookPosBlack;
            queenPos = queenPosBlack;
            if (isEndGame) {
                kingPos = kingPosBlackEnd;
            }
            else {
                kingPos = kingPosBlack;
            }
            pawnCount = countOfPiece(board,PieceType.BPawn);
            opponentPawnCount = countOfPiece(board, PieceType.WPawn);
            bishopCount = countOfPiece(board,PieceType.BBishop);
            knightCount = countOfPiece(board,PieceType.BKnight);
            rookCount = countOfPiece(board,PieceType.BRook);
            queenCount = countOfPiece(board, PieceType.BQueen);
        }

        int value = 0;
        for(int i = 0; i<8 ;i++) {
            for (int j= 0; j<8; j++){
                if(pieceList[i][j].equals(PieceType.WPawn) || pieceList[i][j].equals(PieceType.BPawn))
                        value += pawnValue + pawnPos[i][j];

                if(pieceList[i][j].equals(PieceType.WKnight) || pieceList[i][j].equals(PieceType.BKnight))
                    value += knightValue + knightPos[i][j];

                if(pieceList[i][j].equals(PieceType.WBishop) || pieceList[i][j].equals(PieceType.BBishop))
                    value += bishopValue + bishopPos[i][j];

                if(pieceList[i][j].equals(PieceType.WRook) || pieceList[i][j].equals(PieceType.BRook))
                    value += rookValue + rookPos[i][j];

                if(pieceList[i][j].equals(PieceType.WQueen) || pieceList[i][j].equals(PieceType.BQueen))
                    value += queenValue + queenPos[i][j];

                if(pieceList[i][j].equals(PieceType.WKing) || pieceList[i][j].equals(PieceType.BKing))
                    value += kingValue + kingPos[i][j];

            }

        }

        if (bishopCount > 1) {
            value += dualBishopPawnAdjustment[pawnCount];
        }
        if (knightCount > 1) {
            value += knightPawnAdjustment[pawnCount];
        }
        if (rookCount > 1) {
            value += rookPawnAdjustment[pawnCount];
        }
        if (pawnCount == 0) {
            value += noPawnsPenalty;
        }
        if (knightCount == 0){
            value += knightPenalty;
        }
        if (rookCount == 0){
            value += rookPenalty;
        }

        if (player == board.currentPlayer()) {
            value += tempoBonus;
        }

        if ((pawnCount == 0) && (value < bishopValue) && (value > 0)) {
            return 0;
        }

        if (value > 0 && pawnCount == 0 && opponentPawnCount == 0 && knightCount == 2 &&
                bishopCount == 0 && rookCount == 0 && queenCount == 0) {
            return 0;
        }

        return value;
    }

    private double currentPhase(ChessBoard board) {
        int knightPhase = 1;
        int bishopPhase = 1;
        int rookPhase = 2;
        int queenPhase = 4;
        int totalPhase = knightPhase*4 + bishopPhase*4 + rookPhase*4 + queenPhase*2;
        int phase = totalPhase;

        phase -= (countOfPiece(board,PieceType.WKnight) + countOfPiece(board,PieceType.BKnight)) * knightPhase;
        phase -= (countOfPiece(board,PieceType.WBishop) + countOfPiece(board,PieceType.BBishop)) * bishopPhase;
        phase -= (countOfPiece(board,PieceType.WRook) + countOfPiece(board,PieceType.BRook)) * rookPhase;
        phase -= (countOfPiece(board, PieceType.WQueen) + countOfPiece(board, PieceType.BQueen)) * queenPhase;

        return (phase * PHASE_CONSTANT + (totalPhase / 2)) / totalPhase;
    }

    private static final double pawnPosWhite[][] =
            {
                    {0.0,   0.0,   0.0,   0.0,  0.0,  0.0, 0.0,  0.0},
                    {5.0,  10.0,  10.0, -20.0, -20.0,  10.0, 10.0,  5.0},
                    {5.0,  -5.0, -10.0,   0.0,   0.0, -10.0, -5.0,  5.0},
                    {0.0,   0.0,   0.0,  20.0,  20.0,   0.0,  0.0,  0.0},
                    {5.0,   5.0,  10.0,  25.0,  25.0,  10.0,  5.0,  5.0},
                    {10.0, 10.0,  20.0,  30.0,  30.0,  20.0, 10.0, 10.0},
                    {50.0, 50.0,  50.0,  50.0,  50.0,  50.0, 50.0, 50.0},
                    {0.0,   0.0,   0.0,   0.0,   0.0,   0.0,  0.0,  0.0}
            };

    private static final double pawnPosBlack[][] =
            {
                    {0.0,   0.0,   0.0,   0.0,   0.0,   0.0,  0.0,  0.0},
                    {50.0, 50.0,  50.0,  50.0,  50.0,  50.0, 50.0, 50.0},
                    {10.0, 10.0,  20.0,  30.0,  30.0,  20.0, 10.0, 10.0},
                    {5.0,   5.0,  10.0,  25.0,  25.0,  10.0,  5.0,  5.0},
                    {0.0,   0.0,   0.0,  20.0,  20.0,   0.0,  0.0,  0.0},
                    {5.0,  -5.0, -10.0,   0.0,   0.0, -10.0, -5.0,  5.0},
                    {5.0,  10.0,  10.0, -20.0, -20.0,  10.0, 10.0,  5.0},
                    {0.0,   0.0,   0.0,   0.0,   0.0,   0.0,  0.0,  0.0}
            };

    private static final double knightPosWhite[][] =
            {
                    {-50.0, -40.0, -30.0, -30.0, -30.0, -30.0, -40.0, -50.0},
                    {-40.0, -20.0,   0.0,   5.0,   5.0,   0.0, -20.0, -40.0},
                    {-30.0,   5.0,  10.0,  15.0,  15.0,  10.0,   5.0, -30.0},
                    {-30.0,   0.0,  15.0,  20.0,  20.0,  15.0,   0.0, -30.0},
                    {-30.0,   5.0,  15.0,  20.0,  20.0,  15.0,   5.0, -30.0},
                    {-30.0,   0.0,  10.0,  15.0,  15.0,  10.0,   0.0, -30.0},
                    {-40.0, -20.0,   0.0,   0.0,   0.0,   0.0, -20.0, -40.0},
                    {-50.0, -40.0, -30.0, -30.0, -30.0, -30.0, -40.0, -50.0}
            };

    private static final double knightPosBlack[][] =
            {
                    {-50.0, -40.0, -30.0, -30.0, -30.0, -30.0, -40.0, -50.0},
                    {-40.0, -20.0,   0.0,   0.0,   0.0,   0.0, -20.0, -40.0},
                    {-30.0,   0.0,  10.0,  15.0,  15.0,  10.0,   0.0, -30.0},
                    {-30.0,   5.0,  15.0,  20.0,  20.0,  15.0,   5.0, -30.0},
                    {-30.0,   0.0,  15.0,  20.0,  20.0,  15.0,   0.0, -30.0},
                    {-30.0,   5.0,  10.0,  15.0,  15.0,  10.0,   5.0, -30.0},
                    {-40.0, -20.0,   0.0,   5.0,   5.0,   0.0, -20.0, -40.0},
                    {-50.0, -40.0, -30.0, -30.0, -30.0, -30.0, -40.0, -50.0}
            };

    private static final double bishopPosWhite[][] =
            {
                    {-20.0, -10.0, -10.0, -10.0, -10.0, -10.0, -10.0, -20.0},
                    {-10.0,   5.0,   0.0,   0.0,   0.0,   0.0,   5.0, -10.0},
                    {-10.0,  10.0,  10.0,  10.0,  10.0,  10.0,  10.0, -10.0},
                    {-10.0,   0.0,  10.0,  10.0,  10.0,  10.0,   0.0, -10.0},
                    {-10.0,   5.0,   5.0,  10.0,  10.0,   5.0,   5.0, -10.0},
                    {-10.0,   0.0,   5.0,  10.0,  10.0,   5.0,   0.0, -10.0},
                    {-10.0,   0.0,   0.0,   0.0,   0.0,   0.0,   0.0, -10.0},
                    {-20.0, -10.0, -10.0, -10.0, -10.0, -10.0, -10.0, -20.0}
            };

    private static final double bishopPosBlack[][] =
            {
                    {-20.0, -10.0, -10.0, -10.0, -10.0, -10.0, -10.0, -20.0},
                    {-10.0,   0.0,   0.0,   0.0,   0.0,   0.0,   0.0, -10.0},
                    {-10.0,   0.0,   5.0,  10.0,  10.0,   5.0,   0.0, -10.0},
                    {-10.0,   5.0,   5.0,  10.0,  10.0,   5.0,   5.0, -10.0},
                    {-10.0,   0.0,  10.0,  10.0,  10.0,  10.0,   0.0, -10.0},
                    {-10.0,  10.0,  10.0,  10.0,  10.0,  10.0,  10.0, -10.0},
                    {-10.0,   5.0,   0.0,   0.0,   0.0,   0.0,   5.0, -10.0},
                    {-20.0, -10.0, -10.0, -10.0, -10.0, -10.0, -10.0, -20.0}
            };

    private static final double rookPosWhite[][] =
            {
                    {0.0,  0.0,  0.0,  5.0,  5.0,  0.0,  0.0,  0.0},
                    {-5.0, 0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
                    {-5.0, 0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
                    {-5.0, 0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
                    {-5.0, 0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
                    {-5.0, 0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
                    {5.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0,  5.0},
                    {0.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  0.0}
            };

    private static final double rookPosBlack[][] =
            {
                    {0.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  0.0},
                    {5.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0,  5.0},
                    {-5.0, 0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
                    {-5.0, 0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
                    {-5.0, 0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
                    {-5.0, 0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
                    {-5.0, 0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
                    {0.0,  0.0,  0.0,  5.0,  5.0,  0.0,  0.0,  0.0}
            };

    private static final double queenPosWhite[][] =
            {
                    {-20.0, -10.0, -10.0, -5.0, -5.0, -10.0, -10.0, -20.0},
                    {-10.0,   0.0,   5.0,  0.0,  0.0,   0.0,   0.0, -10.0},
                    {-10.0,   5.0,   5.0,  5.0,  5.0,   5.0,   0.0, -10.0},
                    {0.0,     0.0,   5.0,  5.0,  5.0,   5.0,   0.0, -5.0},
                    {-5.0,    0.0,   5.0,  5.0,  5.0,   5.0,   0.0, -5.0},
                    {-10.0,   0.0,   5.0,  5.0,  5.0,   5.0,   0.0, -10.0},
                    {-10.0,   0.0,   0.0,  0.0,  0.0,   0.0,   0.0, -10.0},
                    {-20.0, -10.0, -10.0, -5.0, -5.0, -10.0, -10.0, -20.0}
            };

    private static final double queenPosBlack[][] =
            {
                    {-20.0, -10.0, -10.0, -5.0, -5.0, -10.0, -10.0, -20.0},
                    {-10.0,   0.0,   0.0,  0.0,  0.0,   0.0,   0.0, -10.0},
                    {-10.0,   0.0,   5.0,  5.0,  5.0,   5.0,   0.0, -10.0},
                    {-5.0,    0.0,   5.0,  5.0,  5.0,   5.0,   0.0, -5.0},
                    {0.0,     0.0,   5.0,  5.0,  5.0,   5.0,   0.0, -5.0},
                    {-10.0,   5.0,   5.0,  5.0,  5.0,   5.0,   0.0, -10.0},
                    {-10.0,   0.0,   5.0,  0.0,  0.0,   0.0,   0.0, -10.0},
                    {-20.0, -10.0, -10.0, -5.0, -5.0, -10.0, -10.0, -20.0}
            };

    private static final double kingPosWhite[][] =
            {
                    {20.0,   30.0,  0.0,   0.0,   0.0,  10.0,  30.0,  20.0},
                    {20.0,   20.0,   0.0,   0.0,   0.0,   0.0,  20.0,  20.0},
                    {-10.0, -20.0, -20.0, -20.0, -20.0, -20.0, -20.0, -10.0},
                    {-20.0, -30.0, -30.0, -40.0, -40.0, -30.0, -30.0, -20.0},
                    {-30.0, -40.0, -40.0, -50.0, -50.0, -40.0, -40.0, -30.0},
                    {-30.0, -40.0, -40.0, -50.0, -50.0, -40.0, -40.0, -30.0},
                    {-30.0, -40.0, -40.0, -50.0, -50.0, -40.0, -40.0, -30.0},
                    {-30.0, -40.0, -40.0, -50.0, -50.0, -40.0, -40.0, -30.0}
            };

    private static final double kingPosBlack[][] =
            {
                    {-30.0, -40.0, -40.0, -50.0, -50.0, -40.0, -40.0, -30.0},
                    {-30.0, -40.0, -40.0, -50.0, -50.0, -40.0, -40.0, -30.0},
                    {-30.0, -40.0, -40.0, -50.0, -50.0, -40.0, -40.0, -30.0},
                    {-30.0, -40.0, -40.0, -50.0, -50.0, -40.0, -40.0, -30.0},
                    {-20.0, -30.0, -30.0, -40.0, -40.0, -30.0, -30.0, -20.0},
                    {-10.0, -20.0, -20.0, -20.0, -20.0, -20.0, -20.0, -10.0},
                    {20.0,   20.0,   0.0,   0.0,   0.0,   0.0,  20.0,  20.0},
                    {20.0,   30.0,  10.0,   0.0,   0.0,  10.0,  30.0,  20.0}
            };

    private static final double kingPosWhiteEnd[][] =
            {
                    {-50.0, -30.0, -30.0, -30.0, -30.0, -30.0, -30.0, -50.0},
                    {-30.0, -30.0,   0.0,   0.0,   0.0,   0.0, -30.0, -30.0},
                    {-30.0, -10.0,  20.0,  30.0,  30.0,  20.0, -10.0, -30.0},
                    {-30.0, -10.0,  30.0,  40.0,  40.0,  30.0, -10.0, -30.0},
                    {-30.0, -10.0,  30.0,  40.0,  40.0,  30.0, -10.0, -30.0},
                    {-30.0, -10.0,  20.0,  30.0,  30.0,  20.0, -10.0, -30.0},
                    {-30.0, -20.0, -10.0,   0.0,   0.0, -10.0, -20.0, -30.0},
                    {-50.0, -40.0, -30.0, -20.0, -20.0, -30.0, -40.0, -50.0}
            };

    private static final double kingPosBlackEnd[][] =
            {
                    {-50.0, -40.0, -30.0, -20.0, -20.0, -30.0, -40.0, -50.0},
                    {-30.0, -20.0, -10.0,   0.0,   0.0, -10.0, -20.0, -30.0},
                    {-30.0, -10.0,  20.0,  30.0,  30.0,  20.0, -10.0, -30.0},
                    {-30.0, -10.0,  30.0,  40.0,  40.0,  30.0, -10.0, -30.0},
                    {-30.0, -10.0,  30.0,  40.0,  40.0,  30.0, -10.0, -30.0},
                    {-30.0, -10.0,  20.0,  30.0,  30.0,  20.0, -10.0, -30.0},
                    {-30.0, -30.0,   0.0,   0.0,   0.0,   0.0, -30.0, -30.0},
                    {-50.0, -30.0, -30.0, -30.0, -30.0, -30.0, -30.0, -50.0}
            };

      /*private double isQueenInDanger(ChessBoard board, int player) {

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board.getBoard()[r][c].toPlayer() == Chess.opponent(player)) {
                    PieceType opponentPiece = createPiece(board.getBoard()[r][c]);
                    List<Move> moves = getMovesWithoutKingCheckControl(opponentPiece,board,Chess.opponent(player),r,c);
                    if (!moves.isEmpty()){
                        for (Move m : moves) {
                            ChessMove cm = (ChessMove) m;
                            if ((board.getBoard()[cm.getTrow()][cm.getTcol()] == WQueen && player == BLACK) || (board.getBoard()[cm.getTrow()][cm.getTcol()] == BQueen) && player == WHITE) {
                                return 200000.0;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }*/

    /*private double isRookInDanger(ChessBoard board, int player) {

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board.getBoard()[r][c].toPlayer() == Chess.opponent(player)) {
                    PieceType opponentPiece = createPiece(board.getBoard()[r][c]);
                    List<Move> moves = getMovesWithoutKingCheckControl(opponentPiece,board,Chess.opponent(player),r,c);
                    if (!moves.isEmpty()){
                        for (Move m : moves) {
                            ChessMove cm = (ChessMove) m;
                            if ((board.getBoard()[cm.getTrow()][cm.getTcol()] == WRook && player == BLACK) || (board.getBoard()[cm.getTrow()][cm.getTcol()] == BRook) && player == WHITE) {
                                return 100000.0;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }*/




    private static List<Move> getMovesWithoutKingCheckControl(PieceType opponentPiece, ChessBoard board, int player, int r, int c) {

           if (opponentPiece.equals(PieceType.WPawn) || opponentPiece.equals(PieceType.BPawn)){
               List<Move> moves= new ArrayList<>();
               if (player==WHITE && canPieceMove(opponentPiece,board,player,r,c ,r+1,c))
                   moves.add(new ChessMove(r,c,r+1,c));
               if (player==WHITE && canPieceMove(opponentPiece,board,player,r,c ,r+2,c))
                   moves.add(new ChessMove(r,c,r+2,c));
               if (player==WHITE && canPieceMove(opponentPiece,board,player,r,c ,r+1,c+1))
                   moves.add(new ChessMove(r,c,r+1,c+1));
               if (player==WHITE && canPieceMove(opponentPiece,board,player,r,c ,r+1,c-1))
                   moves.add(new ChessMove(r,c,r+1,c-1));

               if (player==BLACK && canPieceMove(opponentPiece,board,player,r,c ,r-1,c))
                   moves.add(new ChessMove(r,c,r-1,c));
               if (player==BLACK && canPieceMove(opponentPiece,board,player,r,c ,r-2,c))
                   moves.add(new ChessMove(r,c,r-2,c));
               if (player==BLACK && canPieceMove(opponentPiece,board,player,r,c ,r-1,c-1))
                   moves.add(new ChessMove(r,c,r-1,c-1));
               if (player==BLACK && canPieceMove(opponentPiece,board,player,r,c ,r-1,c+1))
                   moves.add(new ChessMove(r,c,r-1,c+1));

               return moves;
           }

        if (opponentPiece.equals(PieceType.WKnight) || opponentPiece.equals(PieceType.BKnight)){
            List<Move> moves= new ArrayList<>();
            if (r+2<8 && c+1<8 && board.getBoard()[r+2][c+1].toPlayer()!=player )
                moves.add(new ChessMove(r,c,r+2,c+1));
            if (r+1<8 && c+2<8 && board.getBoard()[r+1][c+2].toPlayer()!=player )
                moves.add(new ChessMove(r,c,r+1,c+2));
            if (r-2>=0 && c+1<8 && board.getBoard()[r-2][c+1].toPlayer()!=player )
                moves.add(new ChessMove(r,c,r-2,c+1));
            if (r+2<8 && c-1>=0 && board.getBoard()[r+2][c-1].toPlayer()!=player )
                moves.add(new ChessMove(r,c,r+2,c-1));
            if (r-1>=0 && c+2<8 && board.getBoard()[r-1][c+2].toPlayer()!=player )
                moves.add(new ChessMove(r,c,r-1,c+2));
            if (r+1<8 && c-2>=0 && board.getBoard()[r+1][c-2].toPlayer()!=player )
                moves.add(new ChessMove(r,c,r+1,c-2));
            if (r-1>=0 && c-2>=0 && board.getBoard()[r-1][c-2].toPlayer()!=player )
                moves.add(new ChessMove(r,c,r-1,c-2));
            if (r-2>=0 && c-1>=0 && board.getBoard()[r-2][c-1].toPlayer()!=player )
                moves.add(new ChessMove(r,c,r-2,c-1));
            return moves;
        }

        if (opponentPiece.equals(PieceType.WBishop) || opponentPiece.equals(PieceType.BBishop)){
            List<Move> moves= new ArrayList<>();
            for (int i = 1; i < 8 && r-i>=0 && c-i>=0 ; i++) {
                if (  canPieceMove(opponentPiece,board,player,r,c,r-i,c-i))
                    moves.add(new ChessMove(r,c,r-i,c-i));
                else break;
            }

            for (int i = 1; i < 8 && r+i<8 && c+i<8 ; i++) {
                if (canPieceMove(opponentPiece,board,player,r,c,r+i,c+i))
                    moves.add(new ChessMove(r,c,r+i,c+i));
                else break;
            }

            for (int i = 1; i < 8 && r+i<8 && c-i>=0 ; i++) {
                if (canPieceMove(opponentPiece,board,player,r,c,r+i,c-i))
                    moves.add(new ChessMove(r,c,r+i,c-i));
                else break;
            }
            for (int i = 1; i < 8 && r-i>=0 && c+i<8 ; i++) {
                if (canPieceMove(opponentPiece,board,player,r,c,r-i,c+i))
                    moves.add(new ChessMove(r,c,r-i,c+i));
                else break;
            }
            return moves;
        }

        if (opponentPiece.equals(PieceType.WRook) || opponentPiece.equals(PieceType.BRook)){
            List<Move> moves= new ArrayList<>();
            for (int i = 1; r+i<8  ; i++) {
                if ( canPieceMove(opponentPiece,board,player,r,c,r+i,c))
                    moves.add(new ChessMove(r,c,r+i,c));
                else break;
            }
            for (int i = 1; r-i>=0  ; i++) {
                if ( canPieceMove(opponentPiece,board,player,r,c,r-i,c))
                    moves.add(new ChessMove(r,c,r-i,c));
                else break;
            }

            for (int i = 1; c+i<8  ; i++) {
                if ( canPieceMove(opponentPiece,board,player,r,c,r,c+i))
                    moves.add(new ChessMove(r,c,r,c+i));
                else break;
            }
            for (int i = 1; c-i>=0  ; i++) {
                if ( canPieceMove(opponentPiece,board,player,r,c,r,c-i))
                    moves.add(new ChessMove(r,c,r,c-i));
                else break;
            }
            return moves;
        }

        if (opponentPiece.equals(PieceType.WQueen) || opponentPiece.equals(PieceType.BQueen)){
            List<Move> moves= new ArrayList<>();
            // ROOK MOVES
            for (int i = 1; r+i<8  ; i++) {
                if ( canPieceMove(opponentPiece,board,player,r,c,r+i,c))
                    moves.add(new ChessMove(r,c,r+i,c));
                else break;
            }
            for (int i = 1; r-i>=0  ; i++) {
                if ( canPieceMove(opponentPiece,board,player,r,c,r-i,c))
                    moves.add(new ChessMove(r,c,r-i,c));
                else break;
            }

            for (int i = 1; c+i<8  ; i++) {
                if ( canPieceMove(opponentPiece,board,player,r,c,r,c+i))
                    moves.add(new ChessMove(r,c,r,c+i));
                else break;
            }
            for (int i = 1; c-i>=0  ; i++) {
                if ( canPieceMove(opponentPiece,board,player,r,c,r,c-i))
                    moves.add(new ChessMove(r,c,r,c-i));
                else break;
            }

            // BISHOP MOVES
            for (int i = 1; i < 8 && r-i>=0 && c-i>=0 ; i++) {
                if (  canPieceMove(opponentPiece,board,player,r,c,r-i,c-i))
                    moves.add(new ChessMove(r,c,r-i,c-i));
                else break;
            }

            for (int i = 1; i < 8 && r+i<8 && c+i<8 ; i++) {
                if (canPieceMove(opponentPiece,board,player,r,c,r+i,c+i))
                    moves.add(new ChessMove(r,c,r+i,c+i));
                else break;
            }

            for (int i = 1; i < 8 && r+i<8 && c-i>=0 ; i++) {
                if (canPieceMove(opponentPiece,board,player,r,c,r+i,c-i))
                    moves.add(new ChessMove(r,c,r+i,c-i));
                else break;
            }
            for (int i = 1; i < 8 && r-i>=0 && c+i<8 ; i++) {
                if (canPieceMove(opponentPiece,board,player,r,c,r-i,c+i))
                    moves.add(new ChessMove(r,c,r-i,c+i));
                else break;
            }
            return moves;
        }

        return  new ArrayList<Move>();

    }

    private static boolean canPieceMove(PieceType opponentPiece, ChessBoard board, int player, int fr, int fc, int tr, int tc) {

        if (opponentPiece.equals(PieceType.WPawn) || opponentPiece.equals(PieceType.BPawn)){
            if (  tr>=8 || tc>=8 || tr<0 || tc<0 || board.getBoard()[tr][tc].toPlayer()==player)
                return false;

            if (player==WHITE) {
                if (tr-fr==1)
                    return ( (tc==fc && board.getBoard()[tr][tc]== PieceType.Empty ) ||
                            (Math.abs(tc-fc)==1 && board.getBoard()[tr][tc].toPlayer()== Chess.opponent(player) ) );

                return (tr-fr == 2 && board.getBoard()[fr+1][fc]== PieceType.Empty&& board.getBoard()[tr][tc]==PieceType.Empty && fr ==1);
            }
            else {
                if (tr-fr==-1)
                    return ( (tc==fc && board.getBoard()[tr][tc]== PieceType.Empty ) ||
                            (Math.abs(tc-fc)==1 && board.getBoard()[tr][tc].toPlayer()== Chess.opponent(player) ) );

                return (tr-fr == -2 && board.getBoard()[fr-1][fc]==PieceType.Empty&&  board.getBoard()[tr][tc]==PieceType.Empty && fr ==6);
            }
        }

        if (opponentPiece.equals(PieceType.WKnight) || opponentPiece.equals(PieceType.BKnight)){
            int dr = Math.abs(fr-tr);
            int dc = Math.abs(fc-tc);

            return  ((dr==2 && dc==1)||(dr==1 && dc==2)) && board.getBoard()[tr][tc].toPlayer()!=player;
        }

        if (opponentPiece.equals(PieceType.WBishop) || opponentPiece.equals(PieceType.BBishop)){
            if ( fr==tr || fc==tc)
                return false;
            return clearPath(board.getBoard(),fr,fc,tr,tc)&& board.getBoard()[tr][tc].toPlayer()!=player;
        }

        if (opponentPiece.equals(PieceType.WRook) || opponentPiece.equals(PieceType.BRook)){
            if ( fr!=tr && fc!=tc)
                return false;
            return clearPath(board.getBoard(),fr,fc,tr,tc) && board.getBoard()[tr][tc].toPlayer()!=player;
        }

        if (opponentPiece.equals(PieceType.WQueen) || opponentPiece.equals(PieceType.BQueen)){
            return board.getBoard()[tr][tc].toPlayer()!=player&& clearPath(board.getBoard(),fr,fc,tr,tc) ;
        }

        return  false;

    }

    private static boolean clearPath(PieceType[][] board, int fr, int fc, int tr, int tc) {
        int dr = Math.abs(fr-tr);
        int dc = Math.abs(fc-tc);

        if ( !( dr==0|| dc==0 ||  dr ==dc))
            return false;

        int sr = (tr>fr) ? 1 : (tr<fr)? -1 : 0;
        int sc = (tc>fc) ? 1 : (tc<fc)? -1 : 0;


        for (int r=fr+sr, c = fc+sc; !(r==tr && c==tc ); r+=sr, c+=sc  )
        {
            if (board[r][c]!= PieceType.Empty)
                return false;
        }

        return true;
    }

    private static  PieceType createPiece(PieceType piece) {

        return pieces[piece.ordinal()];
    }

}
