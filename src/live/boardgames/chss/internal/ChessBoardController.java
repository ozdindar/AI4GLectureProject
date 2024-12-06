package live.boardgames.chss.internal;

import live.boardgames.base.*;
import live.boardgames.chss.internal.knowledge.Chess;
import live.boardgames.chss.internal.knowledge.ChessMove;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class ChessBoardController implements BoardController {

    ChessBoardViewer viewer;

    @Override
    public void init(BoardViewer viewer) {
        this.viewer= (ChessBoardViewer) viewer;

    }

    @Override
    public void update(Board board, GameContainer gameContainer, StateBasedGame stateBasedGame, float time, HumanPlayer currentPlayer) {
        ChessBoard chessBoard = (ChessBoard) board;

        if (gameContainer.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
        {
            int x = gameContainer.getInput().getMouseX();
            int y = gameContainer.getInput().getMouseY();

            int row = viewer.getRowFromY(y);
            int col = viewer.getColFromX(x);


            if (col>=0 && row>=0 && col<8 && row < 8)
            {
                if (viewer.hasSelected())
                {
                    if (!viewer.isSelected(row,col)&& ((ChessBoard) board).get(row,col).toPlayer()!= board.currentPlayer())
                    {
                        ChessMove m = new ChessMove(viewer.selectedRow,viewer.selectedCol,row,col);
                        if (Chess.isValid(((ChessBoard) board).board,board.currentPlayer(),m))
                            currentPlayer.setMove(m);
                    }
                    else if (((ChessBoard) board).get(row,col).toPlayer()== board.currentPlayer())
                        viewer.select(row,col);
                }
                viewer.select(row,col);
            }

        }
    }
}
