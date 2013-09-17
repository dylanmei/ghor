package ghor.meta

class CommandBuilder {
  def command

  def CommandBuilder(command) {
    this.command = command
  }

  def describe(description) {
    command.description = description
  }

  def option(option) {
    command.options += option
  }

  def argument(name) {
    command.arguments += name
  }
}