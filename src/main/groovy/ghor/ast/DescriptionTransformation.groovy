package ghor.ast

import ghor.Description
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.builder.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.*

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class DescriptionTransformation extends GhorTransformation {

  public DescriptionTransformation() {
    super(Description.class)
  }

  protected List transform_annotation_to_statements(AnnotationNode annotationNode, ClassNode classNode, MethodNode methodNode) {
    def annotation = annotationNode.members.value
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
            constant 'describe'
            argumentList {
              constant annotation.value
            }
          }
        }
      }
    }
  }
}