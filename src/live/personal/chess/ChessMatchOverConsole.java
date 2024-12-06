package live.personal.chess;

import live.boardgames.base.AIPlayer;
import live.boardgames.base.Move;
import live.boardgames.chss.AIProject;
import live.boardgames.chss.ProjectAIPlayer;
import live.boardgames.chss.bots.sample.SampleAIProject;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;
import live.boardgames.chss.internal.knowledge.PieceType;
import live.personal.chess.bots.simple.SimpleAIProject;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class ChessMatchOverConsole {

    void playAMatch(AIProject pWhite, AIProject pBlack)
    {
        ProjectAIPlayer plWhite = new ProjectAIPlayer(pWhite);
        ProjectAIPlayer plBlack = new ProjectAIPlayer(pBlack);

        ChessBoard board = new ChessBoard();

        plWhite.init(board);
        plBlack.init(board);

        while (!board.isGameOver()){
            AIPlayer player = board.currentPlayer()== Chess.WHITE ? plWhite:plBlack;

            while( !player.hasMove())
                player.calculateMove(board);

            Move m = player.getMove();

            board.perform(m);

            try {
                printBoard(board,plWhite,plBlack);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if (board.winner()== Chess.WHITE)
            System.out.println("WHITE WINS");
        else if (board.winner()== Chess.BLACK)
            System.out.println("BLACK WINS");
        else System.out.println("DRAW");
    }

    private void printBoard(ChessBoard board, ProjectAIPlayer plWhite, ProjectAIPlayer plBlack) throws UnsupportedEncodingException {
        clearScreen();

        for (int r = 0; r < 8; r++) {
            printRow(board,r);
            if (r==1)
            {
                printPlayer(board,plBlack,Chess.BLACK);
            }
            else if (r==6)
                printPlayer(board,plWhite, Chess.WHITE);
            System.out.println();
        }
        printBottom(board);

    }

    private void printPlayer(ChessBoard board, ProjectAIPlayer player, int turn) {
        System.out.print("         [ " + player.getName()+" ] ");
        if (board.currentPlayer()==turn)
            System.out.print("(*)");
    }

    private void printRow(ChessBoard board, int r) throws UnsupportedEncodingException {

        for (int c = 0; c < 8; c++) {
            System.out.print("------");
        }
        System.out.println();
        for (int c = 0; c < 8; c++) {
            System.out.print("|     ");
        }
        System.out.println("|");
        for (int c = 0; c < 8; c++) {
            System.out.print("|  "+ board.get(7-r,c).toChar()+"  ");
        }

        System.out.println("|");

        for (int c = 0; c < 8; c++) {
            System.out.print("|     ");
        }
        System.out.print("|");

    }



    private void printBottom(ChessBoard board) throws UnsupportedEncodingException {
        PrintStream writer = new PrintStream(System.out, true, "UTF-8");
        for (int c = 0; c < 8; c++) {
            System.out.print("------");
        }
        System.out.println();
    }

    private void clearScreen() {
        try {
            Thread.sleep(1000);
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ChessMatchOverConsole cm = new ChessMatchOverConsole();

        AIProject p1 = new SimpleAIProject(5,true);
        AIProject p2 = new SampleAIProject();

        cm.playAMatch(p1,p2);
    }
}
