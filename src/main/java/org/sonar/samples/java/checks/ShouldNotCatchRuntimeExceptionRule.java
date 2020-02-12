package org.sonar.samples.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = "ShouldNotCatchRuntimeException")
public class ShouldNotCatchRuntimeExceptionRule extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitCatch(CatchTree tree) {
        if(tree.parameter().type().symbolType().name().equals("NullPointerException")||tree.parameter().type().symbolType().name().equals("IndexOutOfBoundsException")){
            context.reportIssue(this, tree, "不能直接捕获RuntimeException");
        }
        super.visitCatch(tree);
    }
}
