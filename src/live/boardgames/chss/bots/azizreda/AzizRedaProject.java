/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package live.boardgames.chss.bots.azizreda;

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;

import org.newdawn.slick.Image;

/**
 *
 * @author aziz
 */
public class AzizRedaProject implements AIProject{

    @Override
    public BoardAI createBoardAI() {
        return new v2AI();
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new v2Eval();
    }

    @Override
    public Image createProjectLogo() {
        return null;
    }

    @Override
    public String getName() {
        return "v2";
    }
    
}
