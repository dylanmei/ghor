package ghor

import java.lang.annotation.*
import org.codehaus.groovy.transform.GroovyASTTransformationClass

@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.METHOD])
@GroovyASTTransformationClass(["ghor.ast.DescriptionTransformation"])
@interface Description {
  String value() default ''
}
