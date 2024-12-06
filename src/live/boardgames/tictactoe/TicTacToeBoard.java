package live.boardgames.tictactoe;

import live.boardgames.base.AbstractBoard;
import live.boardgames.base.Board;
import live.boardgames.base.Move;

import java.util.List;

public class TicTacToeBoard extends AbstractBoard {

    long key;

    TicTacToeCell cells[][];

    int boardSize;

    public TicTacToeBoard(TicTacToeBoard other) {
        super(other);
        boardSize = other.boardSize;
        cells = new TicTacToeCell[boardSize][boardSize];

        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                cells[r][c] = other.cells[r][c];
            }
        }

        key = other.key;
    }

    public TicTacToeBoard(int boardSize) {
        this.boardSize = boardSize;
        cells = TicTacToe.initialBoard(boardSize);

        key = TicTacToe.zobristKey(cells);
    }

    @Override
    protected void _updateBoard(Move m) {
        TicTacToeMove tttMove = (TicTacToeMove) m;

        cells[tttMove.getRow()][tttMove.getCol()] = TicTacToe.playerSymbol(currentPlayer());


        key = TicTacToe.updateKey(key, (TicTacToeMove) m, currentPlayer());
    }

    @Override
    public int playerCount() {
        return 2;
    }

    @Override
    public List<Move> getMoves() {
        return TicTacToe.getMoves(cells);
    }

    @Override
    public boolean isGameOver() {
        return TicTacToe.isGameOver(cells);
    }

    @Override
    public boolean isValid(Move m) {
        TicTacToeMove tttMove = (TicTacToeMove) m;
        return cells[tttMove.getRow()][tttMove.getCol()]== TicTacToeCell.Empty;
    }

    @Override
    public Board cloneBoard() {
        return new TicTacToeBoard(this);
    }

    @Override
    public int winner() {
        return (currentPlayer()+1)%2;
    }

    @Override
    public long getKey() {

        return key;
    }

    public TicTacToeCell[][] getBoard() {
        return cells;
    }


    public String toString()
    {
        String st ="";

        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                if (cells[r][c]!= TicTacToeCell.Empty)
                    st += cells[r][c]== TicTacToeCell.Cross ? "X":"O";
                else st += " ";
                if (c<boardSize-1)
                    st+="|";
            }
            if (r<boardSize-1)
                st+="\n";
        }

        st +=  "\n"+key;
        return st;
    }

    public static void main(String[] args) {
        TicTacToeBoard tttb= new TicTacToeBoard(3);
        TicTacToeBoard tttb2= new TicTacToeBoard(3);
        TicTacToeMove ticTacToeMove1 = new TicTacToeMove(1,1);
        TicTacToeMove ticTacToeMove2 = new TicTacToeMove(2,2);
        TicTacToeMove ticTacToeMove3 = new TicTacToeMove(0,0);

        tttb.perform(ticTacToeMove1);
        tttb.perform(ticTacToeMove2);
        tttb.perform(ticTacToeMove3);

        tttb2.perform(ticTacToeMove3);
        tttb2.perform(ticTacToeMove2);
        tttb2.perform(ticTacToeMove1);
        System.out.println(tttb);
        System.out.println(tttb2);
    }
}


