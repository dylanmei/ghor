
package ghor.specs

import ghor.*
import spock.lang.*


class MetaBuilderSpec extends Specification {
  def 'building a method command'() {
    def builder = new MetaBuilder()

    when:
      builder.command('App:func')
    then:
      builder.commands['App:func'] instanceof MetaCommand
    then:
      builder.commands['App:func'].name == 'func'
    then:
      builder.commands['App:func'].nodeName == 'App:func'
    then:
      builder.commands['App:func'].arguments == []
    then:
      builder.commands['App:func'].options == []
    then:
      builder.commands['App:func'].description == ''
  }
}