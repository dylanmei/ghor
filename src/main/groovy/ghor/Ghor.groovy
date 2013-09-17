
package ghor

class Ghor {

  static def metaCommands = [:]

  public void start(String... argv) {

    metaCommands.each {
      println it
    }

  }
}