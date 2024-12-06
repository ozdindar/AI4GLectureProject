package live.boardgames.tictactoe;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;

public class SimpleTicTacToeEvaluator implements BoardEvaluator {

    final double WIN_SCORE = 10000;
    final double LOSS_SCORE = -10000;
    final double DRAW_SCORE = 0;


    @Override
    public double evaluate(Board board, int player) {
        if (!board.isGameOver())
            return DRAW_SCORE;

        if (winBy((TicTacToeBoard) board,player))
            return WIN_SCORE-((TicTacToeBoard) board).getMoveCount();
        else if (winBy((TicTacToeBoard) board, TicTacToe.otherPlayer(player)))
            return LOSS_SCORE;
        else return DRAW_SCORE;
    }

    private boolean winBy(TicTacToeBoard  board, int player) {
        if (TicTacToe.hasPerfectRowOf(board.getBoard(),TicTacToe.playerSymbol(player)))
            return true;

        if (TicTacToe.hasPerfectColOf(board.getBoard(),TicTacToe.playerSymbol(player)))
            return true;



        return TicTacToe.hasPerfectDiagonalOf(board.getBoard(),TicTacToe.playerSymbol(player));

    }
}
