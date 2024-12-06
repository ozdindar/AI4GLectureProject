/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package live.boardgames.chss.bots.AliAkçekoce;

/**
 *
 * @author ALI
 */
public class AlphaBetaEntry {
    AlphaBetaEvaluatedMove evaluatedMove;
    int depth;
    double alpha;
    double beta;
    
    public AlphaBetaEntry(AlphaBetaEvaluatedMove evaluatedMove, int depth, double alpha, double beta) {
        this.evaluatedMove = evaluatedMove;
        this.depth = depth;
        this.alpha = alpha;
        this.beta = beta;
    }
    
    
    public AlphaBetaEvaluatedMove getEvaluatedMove() {
        return evaluatedMove;
    }

    public void setEvaluatedMove(AlphaBetaEvaluatedMove evaluatedMove) {
        this.evaluatedMove = evaluatedMove;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

   
}
