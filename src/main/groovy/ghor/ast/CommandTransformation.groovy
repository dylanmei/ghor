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

    if (!hasInitializedBuilder(classNode))
      initializeBuilder(classNode)

    classNode.addStaticInitializerStatements(
      buildCommand(classNode, methodNode), false)
  }

  def hasInitializedBuilder(classNode) {
    classNode.getNodeMetaData('builderReady') ?: false
  }

  def initializeBuilder(classNode) {
    // http://svn.codehaus.org/groovy/tags/GROOVY_1_7_6/src/test/org/codehaus/groovy/
    // ast/builder/AstBuilderFromSpecificationTest.groovy
    def initializer = new AstBuilder().buildFromSpec {
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
    classNode.addStaticInitializerStatements(initializer, false)
    classNode.setNodeMetaData('builderReady', true)
  }

  def buildCommand(ClassNode classNode, MethodNode methodNode) {
    def params = methodNode.getParameters()
    if (params.size()) {
      methodNode.getParameters().collect { p ->
        buildArgument(classNode, methodNode, p)
      }.flatten()
    }
    else {
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
  }

  def buildArgument(ClassNode classNode, MethodNode methodNode, Parameter param) {
    new AstBuilder().buildFromSpec {
      block {
        expression {
          declaration {
            variable 'command'
            token '='
            methodCall {
              variable 'builder'
              constant 'command'
              argumentList {
                constant classNode.name + ':' + methodNode.name
              }
            }
          }
        }
        expression {
          methodCall {
            variable 'command'
            constant 'argument'
            argumentList {
              constant param.name
            }
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