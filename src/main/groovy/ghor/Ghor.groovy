
package ghor

class Ghor {

  def options = [:]
  def commands = [:]

  public void start(String... argv) {
    println "Start called..."
    if (argv[0] && argv[0] == 'fetch')
      this.fetch('blah')
  }
}