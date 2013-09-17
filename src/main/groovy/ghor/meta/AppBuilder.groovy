package ghor.meta

class AppBuilder {
  def commands

  def AppBuilder(Map commands = [:]) {
    this.commands = commands
  }

  def command(name) {
    def command = commands[name]
    if (!command) {
      command = new Command(name)
      commands.put(name, command) 
    }
    return new CommandBuilder(command)
  }
}

