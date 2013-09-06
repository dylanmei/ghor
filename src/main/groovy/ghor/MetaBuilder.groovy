package ghor

class MetaBuilder {
  def commands

  def MetaBuilder(Map commands = [:]) {
    this.commands = commands
  }

  def command(name) {
    commands.put(name, new MetaCommand(name))
  }
}