
package ghor.specs

import ghor.*
import spock.lang.*

class AstSpec extends Specification {
  private Closure loadClass = { classText ->
    def type = new GroovyClassLoader()
      .parseClass(classText)
    type.newInstance()
    type    
  }

  def 'app with method command'() {
    def commands
    def type = loadClass('''
      import ghor.*
      class App extends Ghor {
        @Command def func() {}
      }''')

    when:
      commands = type.metaCommands
    then:
      commands['App:func'] instanceof MetaCommand
  }
}