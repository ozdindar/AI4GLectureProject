package live.personal.chess;



import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;
import live.boardgames.chss.AIProject;
import live.boardgames.chss.ProjectAIPlayer;
import live.boardgames.chss.bots.sample.SampleAIProject;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;
import live.boardgames.chss.internal.knowledge.ChessMove;
import live.boardgames.chss.internal.knowledge.PieceType;
import live.personal.chess.bots.simple.SimpleAIProject;

import java.sql.Time;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.*;

public class UCIPlayer
{

    public final String ENGINENAME ;
    public final String AUTHOR ;

    ChessBoard board;

    BoardAI ai;
    BoardEvaluator evaluator;

    public UCIPlayer(AIProject player) {
        ai = player.createBoardAI();
        evaluator = player.createBoardEvaluator();
        ENGINENAME = player.getName();
        AUTHOR = player.getName();
    }

    public void uciCommunication() {
        Scanner input = new Scanner(System.in);
        while(true) {
            String inputString = input.nextLine();
            if ("uci".equals(inputString)) inputUCI();
            else if (inputString.startsWith("setoption")) setOptions(inputString);
            else if ("isready".equals(inputString)) isReady();
            else if ("ucinewgame".equals(inputString)) newGame();
            else if (inputString.startsWith("position"))newPosition(inputString);
            else if (inputString.startsWith("go")) go();
            else if ("print".equals(inputString)) print();
            else if ("quit".equals(inputString)) {
                quit();
                break;
            }
            // None UCI communication
            else System.out.println("None UCI ommunication!");
        }
        input.close();
    }

    private void inputUCI() {
        System.out.println("id name "+ENGINENAME);
        System.out.println("id author "+AUTHOR);
        // options if any



        System.out.println("uciok");
    }

    private void setOptions(String input) {
        // set options
    }

    private void isReady() {
        System.out.println("readyok");
    }

    private void newGame() {
        board = new ChessBoard();
    }

    private void newPosition(String input) {
        input=input.substring(9).concat(" ");
        if (input.contains("startpos ")) {
            input=input.substring(9);
            board = new ChessBoard();
        }
        else if (input.contains("fen")) {
            input=input.substring(4);
            importFEN(input);
        }
//        if (input.contains("b ")) {
//            MoveIterator.PLAYER = MoveIterator.PLAYER_BLACK;
//        }
//        else if (input.contains("w ")) {
//            MoveIterator.PLAYER = MoveIterator.PLAYER_WHITE;
//        }
        if (input.contains("moves")) {
            input = input.substring(input.indexOf("moves") + 6);
            while (input.length() > 0) {
                makeMove(input);
                input=input.substring(input.indexOf(' ')+1);
            }
        }
    }

    private void importFEN(String fen) {
        PieceType pieces[][] = emptyBoard();


        int r =7;
        int c=0;
        int charIndex = 0;
        int boardIndex = 0;
        while (fen.charAt(charIndex) != ' ') {
            switch (fen.charAt(charIndex++)) {
                case 'P': pieces[r][c++]= PieceType.WPawn;
                    break;
                case 'p': pieces[r][c++]= PieceType.BPawn;
                    break;
                case 'N': pieces[r][c++]= PieceType.WKnight;
                    break;
                case 'n': pieces[r][c++]= PieceType.BKnight;
                    break;
                case 'B': pieces[r][c++]= PieceType.WBishop;
                    break;
                case 'b': pieces[r][c++]= PieceType.BBishop;
                    break;
                case 'R': pieces[r][c++]= PieceType.WRook;
                    break;
                case 'r': pieces[r][c++]= PieceType.BRook;
                    break;
                case 'Q': pieces[r][c++]= PieceType.WQueen;
                    break;
                case 'q': pieces[r][c++]= PieceType.BQueen;
                    break;
                case 'K': pieces[r][c++]= PieceType.WKing;
                    break;
                case 'k': pieces[r][c++]= PieceType.BKing;
                    break;
                case '/': r--; c=0;
                    break;
                case '1': c++;
                    break;
                case '2': c += 2;
                    break;
                case '3': c += 3;
                    break;
                case '4': c += 4;
                    break;
                case '5': c += 5;
                    break;
                case '6': c += 6;
                    break;
                case '7': c += 7;
                    break;
                case '8': c += 8;
                    break;
            }
        }

        charIndex++;

        int player = fen.charAt(charIndex)=='w' ? Chess.WHITE: Chess.BLACK;

        board = new ChessBoard(pieces,player);



    }

    private PieceType[][] emptyBoard() {
        PieceType pieces[][] =  new PieceType[8][8];
        for (int r = 0; r <8; r++) {
            for (int c = 0; c < 8; c++) {
                pieces[r][c] = PieceType.Empty;
            }
        }
        return pieces;
    }

    public String calculateBestMove() {
        ExecutorService executor;
        Future<Move> future;
        executor = Executors.newSingleThreadExecutor();
        future = executor.submit(() -> ai.getBestMove(board, evaluator));
        Move result = null;
        String uciMove;

        try {
            result = future.get(30, TimeUnit.SECONDS);
            System.out.println("result "+ result);
        }
        catch (InterruptedException| ExecutionException e ) {
            e.printStackTrace();
            System.out.println("THREAD ERROR");
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.println("TIMEOUT");
            result = board.getMoves().get(0);
            System.out.println("result "+ result );

        } finally {
            uciMove = toUCIMove((ChessMove) result);
            System.out.println("bestmove "+ uciMove);
        }
        executor.shutdownNow();
        return uciMove;
    }


    private static final char RowChars[] = new char[] {'a','b','c','d','e','f','g','h'};
    private String toUCIMove(ChessMove cm) {
        return ""+RowChars[cm.getFcol()]+(cm.getFrow()+1)+RowChars[cm.getTcol()]+(cm.getTrow()+1);
    }

    private void go() {
        calculateBestMove();
    }

    private static void print() {
    }

    private static void quit() {
        System.out.println("Good game");
    }

    private void makeMove(String input) {
        int moveFrom_vertical = (input.charAt(0)-'a');
        int moveFrom_horizontal = (input.charAt(1)-'1');
        int moveTo_vertical = input.charAt(2)-'a';
        int moveTo_horizontal = input.charAt(3)-'1';
        String move = Integer.toString(moveFrom_horizontal) + Integer.toString(moveFrom_vertical) + Integer.toString(moveTo_horizontal) + Integer.toString(moveTo_vertical);

        ChessMove cm = new ChessMove(moveFrom_horizontal,moveFrom_vertical,moveTo_horizontal,moveTo_vertical);

        board.perform(cm);
    }

    public static void main(String[] args) {

        AIProject simpleAIProject = new SimpleAIProject(6,true);
        UCIPlayer uciPlayer = new UCIPlayer(simpleAIProject);

        /*todo: Test here*/
        uciPlayer.uciCommunication();
    }
}