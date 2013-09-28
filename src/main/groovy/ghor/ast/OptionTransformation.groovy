package ghor.ast

import ghor.Option
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.*

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class OptionTransformation extends GhorTransformation {

  public OptionTransformation() {
    super(Option.class)
  }

//  protected List transform_annotation_to_statements(AnnotationNode annotationNode, ClassNode classNode, MethodNode methodNode) {
//    def name = annotationNode.members.name
//    def desc = annotationNode.members.description
//    def alias = annotationNode.members.alias

//    def text = "Option $name.value: $desc.value"
//    if (alias)
//      text += "; alias: --$alias.value"

//    def message = createPrintlnAst(text)
//    def existingStatements = methodNode.getCode().getStatements()
//    existingStatements.add(0, message)
//  }
}