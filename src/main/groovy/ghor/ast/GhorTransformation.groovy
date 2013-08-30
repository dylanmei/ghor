package ghor.ast

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.*

public abstract class GhorTransformation implements ASTTransformation {
  protected SourceUnit sourceUnit
  protected Class annotationClass

  protected GhorTransformation(Class annotationClass) {
    this.annotationClass = annotationClass
  }

  public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
    this.sourceUnit = sourceUnit
    if (!checkNodes(nodes)) return

    if (nodes[1] instanceof FieldNode) applyAnnotation(nodes[0], (FieldNode)nodes[1])
    if (nodes[1] instanceof MethodNode) applyAnnotation(nodes[0], (MethodNode)nodes[1])
  }

  protected void applyAnnotation(AnnotationNode annotationNode, MethodNode methodNode) {
  }

  protected void applyAnnotation(AnnotationNode annotationNode, FieldNode fieldNode) {
  }

  protected Boolean checkNodes(astNodes) {
    if (astNodes.size() != 2) return false
    if (astNodes[0] instanceof AnnotationNode) {
      if (astNodes[1] instanceof MethodNode || astNodes[1] instanceof FieldNode)
        return (astNodes[0].classNode?.name == annotationClass.name)
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
