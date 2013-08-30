package ghor.ast

import ghor.Ghor
import ghor.Command
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.*

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class CommandTransformation extends GhorTransformation {

  public CommandTransformation() {
    super(Command.class)
  }

  protected void applyAnnotation(AnnotationNode annotationNode, MethodNode methodNode) {
    def type = methodNode.getDeclaringClass()
    if (!checkCommandType(type)) return

    // TODO: Add expressions to Ghor.addCommands
  }

  protected void applyAnnotation(AnnotationNode annotationNode, FieldNode fieldNode) {
    def type = fieldNode.getDeclaringClass()
    if (!checkCommandType(type)) return

    // TODO: Add expressions to Ghor.addCommands

    if (!fieldNode.hasInitialExpression()) {
      def initExpression = new ConstructorCallExpression(fieldNode.getType(), new ArgumentListExpression())
      fieldNode.setInitialValueExpression(initExpression)
    }
  }

  Boolean checkCommandType(ClassNode commandType) {
    return commandType.isDerivedFrom(new ClassNode(Ghor.class))
  }
}