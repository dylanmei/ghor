package ghor.meta

class Command {
  def name
  def nodeName
  def arguments = []
  def options = []
  def description = ''

  public Command(nodeName) {
    this.nodeName = nodeName
    this.name = nodeName.split(':')[1]
  }

  public String toString() {
    "$name [$nodeName] $description \n" +
    "  arguments=$arguments \n" +
    "  options=$options"
  }
}