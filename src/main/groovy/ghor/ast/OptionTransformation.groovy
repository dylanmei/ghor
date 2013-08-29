package ghor.ast

import ghor.Option
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.*

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class OptionTransformation extends GhorTransformation implements ASTTransformation {

    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        if (!checkMethodNode(nodes, Option.class.name)) return

        def methodNode = nodes[1]
        def name = nodes[0].members.name
        def desc = nodes[0].members.description
        def alias = nodes[0].members.alias

        def text = "Option $name.value: $desc.value"
        if (alias)
            text += "; alias: --$alias.value"

        def message = createPrintlnAst(text)
        def existingStatements = methodNode.getCode().getStatements()
        existingStatements.add(0, message)
    }
}