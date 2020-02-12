package org.sonar.samples.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

@Rule(key = "ReservedWordAndBracketShouldLeaveSpace")
public class ReservedWordAndBracketShouldLeaveSpaceRule extends BaseTreeVisitor implements JavaFileScanner {
    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitBlock(BlockTree tree) {
        if(tree.parent().is(Tree.Kind.FOR_EACH_STATEMENT)){
            ForEachStatement parent = ((ForEachStatement)tree.parent());
            if(parent.firstToken().column()!=parent.openParenToken().column()-2){
                context.reportIssue(this, tree, "保留字和括号间需要保留空格");
            }
        }
        if(tree.parent().is(Tree.Kind.FOR_STATEMENT)){
            ForStatementTree parent = ((ForStatementTree)tree.parent());
            if(parent.firstToken().column()!=parent.openParenToken().column()-2){
                context.reportIssue(this, tree, "保留字和括号间需要保留空格");
            }
        }
        if(tree.parent().is(Tree.Kind.WHILE_STATEMENT)){
            WhileStatementTree parent = ((WhileStatementTree)tree.parent());
            if(parent.firstToken().column()!=parent.openParenToken().column()-2){
                context.reportIssue(this, tree, "保留字和括号间需要保留空格");
            }
        }
        if(tree.parent().is(Tree.Kind.IF_STATEMENT)){
            IfStatementTree parent = ((IfStatementTree)tree.parent());
            if(parent.firstToken().column()!=parent.openParenToken().column()-2){
                context.reportIssue(this, tree, "保留字和括号间需要保留空格");
            }
        }

        super.visitBlock(tree);
    }
}
