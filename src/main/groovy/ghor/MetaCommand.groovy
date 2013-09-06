package ghor

class MetaCommand {
  def name
  def nodeName
  def arguments = []
  def options = []
  def description = ''

  public MetaCommand(nodeName) {
    this.nodeName = nodeName
    this.name = nodeName.split(':')[1]
  }
}