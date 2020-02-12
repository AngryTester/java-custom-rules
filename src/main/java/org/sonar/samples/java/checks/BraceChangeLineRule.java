package org.sonar.samples.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

@Rule(key = "BraceChangeLine")
public class BraceChangeLineRule extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        if (tree.simpleName().identifierToken().line() != tree.openBraceToken().line()) {
            context.reportIssue(this, tree, "左大括号前不换行");
        }
        if (!tree.members().isEmpty()) {
            if (tree.members().get(0).firstToken().line() == tree.openBraceToken().line()) {
                context.reportIssue(this, tree, "左大括号后需换行");
            }
            int size = tree.members().size();
            if(size!=0){
                if (tree.members().get(size - 1).lastToken().line() == tree.closeBraceToken().line()) {
                    context.reportIssue(this, tree, "右大括号前需换行");
                }
            }
        }
        super.visitClass(tree);
    }

    @Override
    public void visitMethod(MethodTree tree) {
        if (tree.firstToken().line() != tree.block().openBraceToken().line()) {
            context.reportIssue(this, tree, "左大括号前不换行");
        }
        if (!tree.block().body().isEmpty()) {
            if (tree.block().body().get(0).firstToken().line() == tree.block().openBraceToken().line()) {
                context.reportIssue(this, tree, "左大括号后需换行");
            }
            int size = tree.block().body().size();
            if(size!=0){
                if (tree.block().body().get(size - 1).lastToken().line() == tree.block().closeBraceToken().line()) {
                    context.reportIssue(this, tree, "右大括号前需换行");
                }                
            }
        }
        super.visitMethod(tree);
    }

    @Override
    public void visitBlock(BlockTree tree) {
        if (tree.openBraceToken().line() != tree.closeBraceToken().line()
                || tree.openBraceToken().column() != tree.closeBraceToken().column() - 1) {
            int size = tree.body().size();
            if(size!=0){
                if (tree.body().get(0).firstToken().text().equals("{") && !tree.body().isEmpty()) {
                    if (tree.body().get(0).firstToken().line() == tree.openBraceToken().line()) {
                        context.reportIssue(this, tree, "左大括号后需换行");
                    }
                    if (tree.body().get(size - 1).lastToken().text().equals("}") && tree.body().get(size - 1).lastToken().line() == tree.closeBraceToken().line()) {
                        context.reportIssue(this, tree, "右大括号前需换行");
                    }
                    for (StatementTree statement : tree.body()) {
                        if (statement.is(Tree.Kind.FOR_EACH_STATEMENT)) {
                            StatementTree block = ((ForEachStatement)statement).statement();
                            if(block.firstToken().text().equals("{")&&block.firstToken().line()!=statement.firstToken().line()){
                                context.reportIssue(this, tree, "FOREACH左大括号前不换行");
                            }
                        }else  if (statement.is(Tree.Kind.FOR_STATEMENT)) {
                            StatementTree block = ((ForStatementTree)statement).statement();
                            if(block.firstToken().text().equals("{")&&block.firstToken().line()!=statement.firstToken().line()){
                                context.reportIssue(this, tree, "FOR左大括号前不换行");
                            }
                        }else  if (statement.is(Tree.Kind.WHILE_STATEMENT)) {
                            StatementTree block = ((WhileStatementTree)statement).statement();
                            if(block.firstToken().text().equals("{")&&block.firstToken().line()!=statement.firstToken().line()){
                                context.reportIssue(this, tree, "WHILE左大括号前不换行");
                            }
                        }else  if (statement.is(Tree.Kind.IF_STATEMENT)) {
                            if(((IfStatementTree)statement).openParenToken().text().equals("{")&&((IfStatementTree)statement).openParenToken().line()!=((IfStatementTree)statement).ifKeyword().line()){
                                context.reportIssue(this, tree, "IF左大括号前不换行");
                            }
                        }
                    }
                }
            }
        }
        if(tree.parent().is(Tree.Kind.FOR_EACH_STATEMENT)){
            ForEachStatement parent = ((ForEachStatement)tree.parent());
            int size = tree.body().size();
            if(size!=0){
                if(tree.body().get(0).firstToken().line()==parent.openParenToken().line()){
                    context.reportIssue(this, tree, "FOREACH左大括号后需换行");
                }
                if(tree.body().get(size-1).lastToken().line()==parent.closeParenToken().line()){
                    context.reportIssue(this, tree, "FOREACH右大括号前需换行");
                }
            }
        }
        if(tree.parent().is(Tree.Kind.FOR_STATEMENT)){
            ForStatementTree parent = ((ForStatementTree)tree.parent());
            int size = tree.body().size();
            if(size!=0){
                if(tree.body().get(0).firstToken().line()==parent.openParenToken().line()){
                    context.reportIssue(this, tree, "FOR左大括号后需换行");
                }
                if(tree.body().get(size-1).lastToken().line()==parent.closeParenToken().line()){
                    context.reportIssue(this, tree, "FOR右大括号前需换行");
                }
            }

        }
        if(tree.parent().is(Tree.Kind.WHILE_STATEMENT)){
            WhileStatementTree parent = ((WhileStatementTree)tree.parent());
            int size = tree.body().size();
            if(size!=0){
                if(tree.body().get(0).firstToken().line()==parent.openParenToken().line()){
                    context.reportIssue(this, tree, "WHILE左大括号后需换行");
                }
                if(tree.body().get(size-1).lastToken().line()==parent.closeParenToken().line()){
                    context.reportIssue(this, tree, "WHILE右大括号前需换行");
                }
            }
        }
        if(tree.parent().is(Tree.Kind.IF_STATEMENT)){
            IfStatementTree parent = ((IfStatementTree)tree.parent());
            int size = tree.body().size();
            if(size!=0){
                if(tree.body().get(0).firstToken().line()==parent.openParenToken().line()){
                    context.reportIssue(this, tree, "IF左大括号后需换行");
                }
                if(tree.body().get(size-1).lastToken().line()==parent.closeParenToken().line()){
                    context.reportIssue(this, tree, "IF右大括号前需换行");
                }
            }
        }
        super.visitBlock(tree);
    }
}
