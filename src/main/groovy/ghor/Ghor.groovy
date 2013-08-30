
package ghor

class Ghor {
  public static Map getCommands() {
    def map = [:]
    addCommands(map)
    map
  }

  private static void addCommands(map) {
  }

  public void start(String... argv) {
    println "Start called..."
    if (argv[0] && argv[0] == 'fetch')
      this.fetch('blah')
  }
}