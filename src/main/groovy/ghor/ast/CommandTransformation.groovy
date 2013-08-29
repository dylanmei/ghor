package ghor.ast

import ghor.Ghor
import ghor.Command
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.*

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class CommandTransformation extends GhorTransformation implements ASTTransformation {

    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        if (!checkFieldNode(nodes, Command.class.name)) return

        def fieldNode = nodes[1]
        def fieldType = fieldNode.getType()
        if (!checkCommandType(fieldType)) return

        if (!fieldNode.hasInitialExpression()) {
          def initExpression = new ConstructorCallExpression(fieldNode.getType(), new ArgumentListExpression())
          fieldNode.setInitialValueExpression(initExpression)
          // need to inject an initial expression
//          println fieldNode.getType()
        }
    }

    Boolean checkCommandType(ClassNode fieldType) {
      return fieldType.isDerivedFrom(new ClassNode(Ghor.class))
    }
}