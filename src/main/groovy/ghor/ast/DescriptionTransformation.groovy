package ghor.ast

import ghor.Description
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.*

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class DescriptionTransformation extends GhorTransformation {

  public DescriptionTransformation() {
    super(Description.class)
  }

  protected void applyAnnotation(AnnotationNode annotationNode, MethodNode methodNode) {
    def annotation = annotationNode.members.value
    def message = createPrintlnAst("Description of $methodNode.name: $annotation.value")

    def existingStatements = methodNode.getCode().getStatements()
    existingStatements.add(0, message)
  }
}