package ghor.ast

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.*

public class GhorTransformation {
    protected Boolean checkMethodNode(astNodes, annotationType) {
        if (astNodes.size() >= 2) {
            if (astNodes[0] instanceof AnnotationNode && astNodes[1] instanceof MethodNode)
                if (astNodes[0].classNode?.name == annotationType) return true
        }
        false 
    }

    protected Boolean checkFieldNode(astNodes, annotationType) {
        if (astNodes.size() >= 2) {
            if (astNodes[0] instanceof AnnotationNode && astNodes[1] instanceof FieldNode)
                if (astNodes[0].classNode?.name == annotationType) return true
        }
        false 
    }

    protected Statement createPrintlnAst(String message) {
        return new ExpressionStatement(
            new MethodCallExpression(
                new VariableExpression("this"),
                new ConstantExpression("println"),
                new ArgumentListExpression(
                    new ConstantExpression(message)
                )
            )
        )
    }
}