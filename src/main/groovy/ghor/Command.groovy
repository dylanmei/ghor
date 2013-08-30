
package ghor

import java.lang.annotation.*
import org.codehaus.groovy.transform.GroovyASTTransformationClass

@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.FIELD, ElementType.METHOD])
@GroovyASTTransformationClass(["ghor.ast.CommandTransformation"])
@interface Command {
}