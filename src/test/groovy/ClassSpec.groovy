
package ghor.specs

import ghor.*
import spock.lang.*

class ClassSpec extends Specification {
  def commands
  def 'app with function'() {
    def type = new GroovyClassLoader()
      .parseClass('''
        import ghor.Ghor
        class App extends Ghor {
          @Command def func() {}
        }''')

    when:
      commands = type.commands
    then:
      commands['func'] != null
  }
}