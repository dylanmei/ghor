package ghor.ast

import ghor.Description
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.*

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class DescriptionTransformation extends CommandTransformation implements ASTTransformation {

    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        if (!checkNodes(nodes, Description.class.name)) return

        def methodNode = nodes[1]
        def annotation = nodes[0].members.value
        if (annotation.class != ConstantExpression) {
            // add better error handling
            return
        }        

        def message = createPrintlnAst("Description of $methodNode.name: $annotation.value")
        def existingStatements = methodNode.getCode().getStatements()
        existingStatements.add(0, message)
    }
}