
package ghor.specs

import ghor.meta.*
import spock.lang.*


class AppBuilderSpec extends Specification {
  def 'building a command'() {
    setup:
      def b = new AppBuilder()

    when:
      b.command('App:func')
    then:
      b.commands['App:func'] instanceof Command
    and:
      b.commands['App:func'].name == 'func'
      b.commands['App:func'].nodeName == 'App:func'
      b.commands['App:func'].arguments == []
      b.commands['App:func'].options == []
      b.commands['App:func'].description == ''
  }

  def 'building a command with a description'() {
    setup:
      def b = new AppBuilder()

    when:
      b.command('App:func').describe('abc')
    then:
      b.commands['App:func'].description == 'abc'    
  }

  def 'building a command with an option'() {
    setup:
      def b = new AppBuilder()

    when:
      b.command('App:func').option(
        new Option(name: 'abc'))
    then:
      b.commands['App:func'].options[0].name == 'abc'
  }

  def 'building a command with an argument'() {
    setup:
      def b = new AppBuilder()

    when:
      b.command('App:func').argument('abc')
    then:
      b.commands['App:func'].arguments[0] == 'abc'    
  }
}