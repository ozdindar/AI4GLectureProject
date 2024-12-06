package live.decisionmaking.decisiontree.demo;


import live.decisionmaking.decisiontree.Action;
import live.decisionmaking.decisiontree.Decision;
import live.decisionmaking.decisiontree.DecisionTreeNode;

/**
 * Created by dindar.oz on 10/11/2017.
 */
public class DecisionTreeDemo {

    public static void main(String[] args) {
        DecisionTreeNode root = buildDemoDecisionTree();

        for (;;)
        {
            root.makeDecision(null);
            System.out.println("----------------------------------------------------------");
            System.out.println();
        }
    }

    private static DecisionTreeNode buildDemoDecisionTree() {

        Action seekOutEnemies = new PrintAction("A1. Seek out enemies.");
        Action seekOutHealthPacks = new PrintAction("A2. Seek out health packs.");
        Action attackEnemy = new PrintAction("A3. Attack enemy.");
        Action runAway = new PrintAction("A4. Run away from enemy.");
        Action headForCover = new PrintAction("A5. Head for cover.");



        Decision nearbyCoverTest = new InteractiveDecision(headForCover,attackEnemy,"Is there cover nearby?");
        Decision inCoverTest = new InteractiveDecision(attackEnemy,nearbyCoverTest,"Are you in cover?");
        Decision healthOkeyTest = new InteractiveDecision(attackEnemy,runAway,"Is your health okay?");
        Decision enemyDangerousTest = new InteractiveDecision(inCoverTest,attackEnemy,"Is the enemy dangerous?");
        Decision enemyCloseByTest= new InteractiveDecision(enemyDangerousTest,healthOkeyTest,"Is the enemy close by?");
        Decision idleHealthOkeyTest= new InteractiveDecision(seekOutEnemies,seekOutHealthPacks,"Is your health okay?");
        Decision enemySeenTest = new InteractiveDecision(enemyCloseByTest,idleHealthOkeyTest,"Can you see an enemy?");


        return enemySeenTest;
    }
}
