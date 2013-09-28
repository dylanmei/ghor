
package ghor.specs

import ghor.meta.*
import spock.lang.*

class AstSpec extends Specification {
  private Closure loadClass = { classText ->
    def type = new GroovyClassLoader()
      .parseClass(classText)
    type.newInstance()
    type    
  }

  def 'app command'() {
    def command
    def type = loadClass('''
      import ghor.*
      class App extends Ghor {
        @Command def func() {}
      }''')

    when:
      command = type.metaCommands['App:func']
    then:
      command instanceof Command
  }

  def 'app command with descrition'() {
    def command
    def type = loadClass('''
      import ghor.*
      class App extends Ghor {
        @Description("hello")
        @Command def func() {}
      }''')

    when:
      command = type.metaCommands['App:func']
    then:
      command.description == 'hello'
  }

  def 'app command with arguments'() {
    def command
    def type = loadClass('''
      import ghor.*
      class App extends Ghor {
        @Command def func(user, password) {}
      }''')

    when:
      command = type.metaCommands['App:func']
    then:
      command.arguments[0] == 'user'
      command.arguments[1] == 'password'
  }
}