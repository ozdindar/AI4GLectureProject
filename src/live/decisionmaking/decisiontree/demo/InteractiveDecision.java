package live.decisionmaking.decisiontree.demo;



import live.decisionmaking.decisiontree.Decision;
import live.decisionmaking.decisiontree.DecisionData;
import live.decisionmaking.decisiontree.DecisionTreeNode;

import java.util.Scanner;

/**
 * Created by dindar.oz on 10/11/2017.
 */
public class InteractiveDecision extends Decision {
    String question;

    public InteractiveDecision(DecisionTreeNode trueNode, DecisionTreeNode falseNode, String question) {
        super(trueNode, falseNode);
        this.question = question;
    }

    @Override
    protected DecisionTreeNode getBranch(DecisionData testValue) {
        System.out.println(question);
        Scanner scanner = new Scanner(System.in);

        String answer = null;
        answer = scanner.next().toUpperCase();
        if (answer.equals("Y")|| answer.equals("YES")|| answer.equals("1"))
            return trueBranch;
        else if (answer.equals("N")|| answer.equals("NO")|| answer.equals("0"))
            return falseBranch;
        else
        {
            System.out.println("Invalid answer!");
            return getBranch(testValue);
        }
    }
}
