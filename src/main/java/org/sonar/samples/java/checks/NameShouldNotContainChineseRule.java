package org.sonar.samples.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Rule(key = "NameShouldNotContainChinese")
public class NameShouldNotContainChineseRule extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        String className = tree.simpleName().name();
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(className);
        if (m.find()) {
            context.reportIssue(this, tree, "类名["+className+"]包含中文，不符合要求");
        }
        super.visitClass(tree);
    }

    @Override
    public void visitMethod(MethodTree tree){
        String methodName = tree.simpleName().name();
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(methodName);
        if (m.find()) {
            context.reportIssue(this, tree, "方法名["+methodName+"]包含中文，不符合要求");
        }
        super.visitMethod(tree);
    }

    @Override
    public void visitVariable(VariableTree tree){
        String variableName = tree.simpleName().name();
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(variableName);
        if (m.find()) {
            context.reportIssue(this, tree, "变量名["+variableName+"]包含中文，不符合要求");
        }
        super.visitVariable(tree);
    }

}
