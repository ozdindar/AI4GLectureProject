package live.boardgames.chss;

import live.base.SimpleGame;
import live.base.SimplePanel;
import live.boardgames.base.*;

import live.boardgames.chss.bots.atakan.AtakanProject;
import live.boardgames.chss.bots.okehanokcu.HiraOkehanProject;

import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.ChessBoardController;
import live.boardgames.chss.internal.ChessBoardViewer;

import live.personal.chess.bots.simple.SimpleAIProject;
import org.newdawn.slick.Game;
import org.newdawn.slick.util.Bootstrap;
import sun.reflect.Reflection;

public class ProjectDemo {
    private static final String AlaraIscan = "live.boardgames.chss.bots.AlaraIscan.Frida";
    private static final String AliAkcekoca = "live.boardgames.chss.bots.AliAkçekoce.Janisserie";
    private static final String AnilAdanir = "live.boardgames.chss.bots.aniladanir.MyAIProject";
    private static final String AtakanCamurcu = "live.boardgames.chss.bots.atakan.AtakanProject";
    private static final String AzizReda = "live.boardgames.chss.bots.azizreda.AzizRedaProject";
    private static final String BerkayBayindir = "live.boardgames.chss.bots.berkaybayindir.LynxProject";
    private static final String MutluDikmen = "live.boardgames.chss.bots.botMutluDikmen.AIProject_X";
    private static final String Cenk = "live.boardgames.chss.bots.Cenk.CenkAIProject";
    private static final String HuseyinArdaAsik = "live.boardgames.chss.bots.HüseyinArdaAşık.HuseyinArdaProject";
    private static final String OrcunUslu = "live.boardgames.chss.bots.MOrcunUslu.MyProject";
    private static final String HiraOkehanOkcu = "live.boardgames.chss.bots.okehanokcu.HiraOkehanProject";
    private static final String HasanOzan = "live.boardgames.chss.bots.ozan.AI37";
    private static final String TunaAlaygut = "live.boardgames.chss.bots.tunaalaygut.Sailor";
    private static final String Simple = "live.personal.chess.bots.simple.SimpleAIProject";
    private static final String SinanAyaz = "live.boardgames.chss.bots.sinan.SinanAIProject";
    private static final String Halil = "live.boardgames.chss.bots.halil.HalilAIProject";

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

            playAMatch(Halil,SinanAyaz);

    }

    public static void playAMatch(String project1Name, String project2Name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Board board = new ChessBoard();
        BoardViewer boardViewer = new ChessBoardViewer();
        BoardController boardController = new ChessBoardController();

        BoardGame chess = new BoardGame(board,boardViewer,boardController);
        BoardGameSideBar sideBar = new BoardGameSideBar(chess);
        Class<?> clazz = Class.forName(project1Name);
        AIProject project1 = (AIProject) clazz.newInstance();

        Class<?> clazz2 = Class.forName(project2Name);
        AIProject project2 = (AIProject) clazz2.newInstance();

        chess.addPlayer(new ProjectAIPlayer(project1));
        //chess.addPlayer(new ProjectAIPlayer(project2));
        chess.addPlayer(new SimpleHumanPlayer("Ali"));

        SimplePanel chssPanel = new SimplePanel(chess,sideBar);


        Game tttGame = new SimpleGame("Chess 1.0", chssPanel);

        Bootstrap.runAsApplication(tttGame,800,600,false);
    }
}
