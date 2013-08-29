package ghor

import java.lang.annotation.*
import org.codehaus.groovy.transform.GroovyASTTransformationClass

@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.METHOD])
@GroovyASTTransformationClass(["ghor.ast.OptionTransformation"])
@interface Option {
  String name() default ''
  String alias() default ''
  String description() default ''
}