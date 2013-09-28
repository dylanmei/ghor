package ghor.ast

import ghor.Command
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

//  protected List transform_annotation_to_statements(AnnotationNode annotationNode, ClassNode classNode, FieldNode fieldNode) {
//    def type = fieldNode.getDeclaringClass()
//    if (!canTransformType(type)) return
//
//    if (!fieldNode.hasInitialExpression()) {
//      def initExpression = new ConstructorCallExpression(fieldNode.getType(), new ArgumentListExpression())
//      fieldNode.setInitialValueExpression(initExpression)
//    }
//  }

  protected List transform_annotation_to_statements(AnnotationNode annotationNode, ClassNode classNode, MethodNode methodNode) {
    def params = methodNode.getParameters()
    if (params.size()) {
      methodNode.getParameters().collect { p ->
        param_to_argument_statement(classNode, methodNode, p)
      }.flatten()
    }
    else {
      method_to_command_statement(classNode, methodNode)
    }
  }

  List method_to_command_statement(ClassNode classNode, MethodNode methodNode) {
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

  List param_to_argument_statement(ClassNode classNode, MethodNode methodNode, Parameter param) {
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
}