/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package live.boardgames.chss.bots.AliAkçekoce;

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import live.boardgames.chss.bots.sample.NullEvaluator;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author ALI
 */
public class Janisserie implements AIProject{

    @Override
    public BoardAI createBoardAI() {
        return new AlphaBetaWithTT(3);
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new Evaluation();
    }

    @Override
    public Image createProjectLogo() {
        try {

            return new Image("C:\\Users\\ALI\\Desktop\\res\\res\\janisserie.jpg");
        } catch (SlickException e) {
            return null;
        }
        catch (Exception e ){
            return null;
        }
    }

    @Override
    public String getName() {
        return "Janisserie";
    }
    
}
