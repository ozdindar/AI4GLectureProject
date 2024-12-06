package live.boardgames.chss.internal;

import live.base.SimpleGame;
import live.base.SimplePanel;

import live.boardgames.base.*;
import live.boardgames.chss.internal.knowledge.PieceType;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Bootstrap;

import java.awt.geom.Rectangle2D;

public class ChessBoardViewer implements BoardViewer {



    Rectangle2D boundary;
    ChessBoard board;
    private Color SquareColors[]= {Color.white, Color.cyan};
    private Color SelectedCellColor = new Color(30,30,30,60);
    private float cellWidth;

    int selectedRow;
    int selectedCol;

    Image pieceImages[];


    @Override
    public void init(Rectangle2D boundary, Board board) {
        this.boundary= boundary;
        this.board = (ChessBoard) board;
        cellWidth = (float) (boundary.getHeight()/8);

        selectedRow=-1;
        selectedCol=-1;

        fillPieceImages();
    }

    private void fillPieceImages() {
        pieceImages = new Image[PieceType.values().length];

        for (int i = 0; i < PieceType.values().length; i++) {
            try {
                pieceImages[i] = new Image( PieceType.values()[i].toImgFile());
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isSelected(int row, int col)
    {
        return selectedRow==row && selectedCol==col;
    }

    public boolean hasSelected()
    {
        return selectedRow>=0 && selectedCol>=0;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {

        renderBoard(graphics);



    }

    private void renderBoard(Graphics graphics) {

        float x = 0;
        float y = 0;


        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                renderCell(graphics,x,y,cellWidth,(r+c)%2, board.get(7-r,c));
                if (isSelected(7-r,c))
                {
                    graphics.setColor(SelectedCellColor);
                    graphics.fillRect(x,y,cellWidth,cellWidth);
                }
                x += cellWidth;
            }
            y+=cellWidth;
            x = 0;
        }
    }

    private void renderCell(Graphics graphics, float x, float y, float cellWidth, int bgColor, PieceType piece) {
        graphics.setColor(SquareColors[bgColor]);
        graphics.fillRect(x,y,cellWidth-3,cellWidth-3);
        if (piece != PieceType.Empty)
        {
            renderPiece(graphics,x,y,cellWidth,piece);
        }
    }

    private void renderPiece(Graphics graphics, float x, float y, float cellWidth, PieceType piece) {

        pieceImages[piece.ordinal()].draw(x,y, (cellWidth-5)/pieceImages[piece.ordinal()].getWidth() );

        //pieceImg.draw(x,y,x+cellWidth,y+cellWidth, (float) topLeft.getX(),(float) topLeft.getY(),(float) topLeft.getX()+161, (float) topLeft.getY()+161);

    }

    public int getRowFromY(int y) {
        y -= boundary.getMinY();
        return 7-(int) (y/cellWidth);
    }

    public int getColFromX(int x) {
        x -= boundary.getMinX();
        return (int)(x/cellWidth);
    }

    public void select(int row, int col) {
        selectedCol = col;
        selectedRow = row;
    }

    public static void main(String[] args) {

        Board board = new ChessBoard();
        BoardViewer boardViewer = new ChessBoardViewer();
        BoardController boardController = new ChessBoardController();

        BoardGame chess = new BoardGame(board,boardViewer,boardController);

        chess.addPlayer(new SimpleHumanPlayer("Alice"));
        chess.addPlayer(new SimpleHumanPlayer("Bob"));
      //  chess.addPlayer(new SimpleAIPlayer("Bob",new RandomAI(),new NullEvaluator(),1));

        SimplePanel chssPanel = new SimplePanel(chess);

        Game tttGame = new SimpleGame("Chess 1.0", chssPanel);


        Bootstrap.runAsApplication(tttGame,600,600,false);
    }


}
