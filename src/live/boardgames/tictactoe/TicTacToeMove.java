package live.boardgames.tictactoe;

import live.boardgames.base.Move;

public class TicTacToeMove implements Move {
    int row;
    int col;


    public TicTacToeMove(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
