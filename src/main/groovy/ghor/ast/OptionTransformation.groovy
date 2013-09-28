package ghor.ast

import ghor.Option
import ghor.meta.Option
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.builder.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.*

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class OptionTransformation extends GhorTransformation {

  public OptionTransformation() {
    super(ghor.Option.class)
  }

  protected List transform_annotation_to_statements(AnnotationNode annotationNode, ClassNode classNode, MethodNode methodNode) {
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
          declaration {
            variable 'option'
            token '='
            constructorCall(ghor.meta.Option) {
              tuple {
                expression.add(transform_option_values_to_arguments(annotationNode))
              }
            } 
          }        
        }
        expression {
          methodCall {
            variable 'command'
            constant 'option'
            argumentList {
              variable 'option'
            }
          }
        }
      }
    }
  }

  NamedArgumentListExpression transform_option_values_to_arguments(AnnotationNode annotationNode) {
    def entries = []
    def name = annotationNode.members.name
    def alias = annotationNode.members.alias
    def description = annotationNode.members.description

    if (name.value)
      entries += new MapEntryExpression(
        new ConstantExpression('name'),
        new ConstantExpression(name.value))

    if (alias && alias.value.size() == 1)
      entries += new MapEntryExpression(
        new ConstantExpression('alias'),
        new ConstantExpression(alias.value))

    if (description)
      entries += new MapEntryExpression(
        new ConstantExpression('description'),
        new ConstantExpression(description.value))

    new NamedArgumentListExpression(entries)
  }
}