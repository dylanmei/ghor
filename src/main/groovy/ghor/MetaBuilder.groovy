package ghor

class MetaBuilder {
  def commands

  def MetaBuilder(Map commands = [:]) {
    this.commands = commands
  }

  def command(name) {
    println "MetaBuilder.command($name)"
    commands.put(name, 1)
  }
}