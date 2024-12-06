/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package live.boardgames.chss.bots.AliAkçekoce;

import live.boardgames.base.Move;

/**
 *
 * @author ALI
 */
public class AlphaBetaEvaluatedMove {
    
    Move move;
    double score;
    double alpha;
    double beta;

    
    public AlphaBetaEvaluatedMove(Move move, double score,double alpha,double beta) {
        this.move = move;
        this.score = score;
        this.alpha = alpha;
        this.beta = beta;
    }

    public Move getMove() {
        return move;
    }

    public double getScore() {
        return score;
    }
    
    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }
}


