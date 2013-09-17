package ghor.ast

import ghor.Ghor
import ghor.Command
import ghor.meta.AppBuilder
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.builder.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.*

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class CommandTransformation extends GhorTransformation {

  static int PUBLIC = 1
  static int PRIVATE = 2
  static int PROTECTED = 4
  static int STATIC = 8

  public CommandTransformation() {
    super(Command.class)
  }

  protected void applyAnnotation(AnnotationNode annotationNode, MethodNode methodNode) {
    def classNode = methodNode.getDeclaringClass()
    if (!canTransformType(classNode))
      return

    if (!hasInitializer(classNode))
      setupInitializer(classNode)

    classNode.addStaticInitializerStatements(
      buildTransformer(classNode, methodNode), false)
  }

  def setupInitializer(classNode) {
    classNode.addStaticInitializerStatements(buildInitializer(), false)
    classNode.setNodeMetaData('hasInitializer', true)
  }

  def hasInitializer(classNode) {
    classNode.getNodeMetaData('hasInitializer') ?: false
  }

  def buildInitializer() {
    // http://svn.codehaus.org/groovy/tags/GROOVY_1_7_6/src/test/org/codehaus/groovy/
    // ast/builder/AstBuilderFromSpecificationTest.groovy
    new AstBuilder().buildFromSpec {
      expression {
        declaration {
          variable 'builder'
          token '='
          constructorCall(AppBuilder) {
            argumentList {
              variable 'metaCommands'
            }
          } 
        }
      }
    }
  }

  def buildTransformer(ClassNode classNode, MethodNode methodNode) {
    new AstBuilder().buildFromSpec {
      expression {
        methodCall {
          variable 'builder'
          constant 'command'
          argumentList {
            constant classNode.name + ':' + methodNode.name
          }
        }
      }
    }    
  }

  protected void applyAnnotation(AnnotationNode annotationNode, FieldNode fieldNode) {
    def type = fieldNode.getDeclaringClass()
    if (!canTransformType(type)) return

    if (!fieldNode.hasInitialExpression()) {
      def initExpression = new ConstructorCallExpression(fieldNode.getType(), new ArgumentListExpression())
      fieldNode.setInitialValueExpression(initExpression)
    }
  }

  Boolean canTransformType(ClassNode commandType) {
    return commandType.isDerivedFrom(new ClassNode(Ghor.class))
  }
}