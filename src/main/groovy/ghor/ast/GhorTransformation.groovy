package ghor.ast

import ghor.Ghor
import ghor.meta.AppBuilder

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.builder.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.*

public abstract class GhorTransformation implements ASTTransformation {
  Class annotationClass

  protected GhorTransformation(Class annotationClass) {
    this.annotationClass = annotationClass
  }

  public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
    if (!can_transform_annotated_nodes(nodes)) return

    if (nodes[1] instanceof FieldNode) apply_transformation(nodes[0], (FieldNode)nodes[1])
    if (nodes[1] instanceof MethodNode) apply_transformation(nodes[0], (MethodNode)nodes[1])
  }

  void apply_transformation(AnnotationNode annotationNode, MethodNode methodNode) {
    def classNode = get_and_initialize_class(methodNode)
    classNode.addStaticInitializerStatements(
      transform_annotation_to_statements(annotationNode, classNode, methodNode), false)
  }

  void apply_transformation(AnnotationNode annotationNode, FieldNode fieldNode) {
    def classNode = get_and_initialize_class(fieldNode)
    classNode.addStaticInitializerStatements(
      transform_annotation_to_statements(annotationNode, classNode, fieldNode), false)
  }

  protected List transform_annotation_to_statements(AnnotationNode annotationNode, ClassNode classNode, FieldNode fieldNode) {
    []
  }

  protected List transform_annotation_to_statements(AnnotationNode annotationNode, ClassNode classNode, MethodNode methodNode) {
    []
  }

  ClassNode get_and_initialize_class(AnnotatedNode node) {
    def classNode = node.getDeclaringClass()
    if (!can_transform_class(classNode))
      throw new GroovyRuntimeException('Class <$classNode.name> is not a Ghor class.')

    declare_builder(classNode)
    classNode
  }

  Boolean can_transform_class(ClassNode commandType) {
    return commandType.isDerivedFrom(new ClassNode(Ghor.class))
  }

  def declare_builder(classNode) {
    if (classNode.getNodeMetaData('builderReady'))
      return

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


  Boolean can_transform_annotated_nodes(astNodes) {
    if (astNodes.size() != 2) return false
    if (astNodes[0] instanceof AnnotationNode) {
      if (astNodes[1] instanceof MethodNode || astNodes[1] instanceof FieldNode)
        return (astNodes[0].classNode?.name == annotationClass.name)
      }
      false
  }
}
