package live.boardgames.chss.internal.knowledge;

import live.boardgames.base.Move;

public class ChessMove implements Move {

    public int getFrow() {
        return frow;
    }

    public int getFcol() {
        return fcol;
    }

    public int getTrow() {
        return trow;
    }

    public int getTcol() {
        return tcol;
    }

    int frow;
    int fcol;
    int trow;
    int tcol;

    public ChessMove(int frow, int fcol, int trow, int tcol) {
        this.frow = frow;
        this.fcol = fcol;
        this.trow = trow;
        this.tcol = tcol;
    }

    public String toString()
    {
        return "["+ frow + "," + fcol + "] ["+ trow+ ","+ tcol+"]";
    }
}
